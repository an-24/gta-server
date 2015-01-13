package biz.gelicon.gta.server.controller;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.data.Message;
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.ScreenShot;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.data.WeeklySignature;
import biz.gelicon.gta.server.dto.DayDTO;
import biz.gelicon.gta.server.dto.HourDTO;
import biz.gelicon.gta.server.dto.MonthDTO;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.dto.ScreenShotDTO;
import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.repo.MessageRepository;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.ScreenShotRepository;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.repo.WeeklySignatureRepository;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/inner/diary")
public class WorkDiaryController {
	@Inject
	private TeamRepository teamRepository;
	@Inject
	private PersonRepository personRepository;
	@Inject
	private MessageRepository messageRepository;
	@Inject
	private ScreenShotRepository screenShotRepository;
	@Inject
	private WeeklySignatureRepository weeklySignatureRepository;
	

    @RequestMapping(method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String select(Model ui) {
    	List<Team> teams = teamRepository.findByUser_Id(UserService.getCurrentUser().getId());
    	List<TeamDTO> list = teams.stream()
    			.map(t->new TeamDTO(t)).collect(Collectors.toList());
    	if(list.size()==1) {
            return "redirect:../inner/diary/"+list.get(0).getId();
    	}
    	ui.addAttribute("teams", list);
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "inner/diary/select";
    }

    @RequestMapping(value = "/{teamId}", method=RequestMethod.GET, produces = "application/json")
    @Transactional
    public String main(Model ui, 
    		@PathVariable Integer teamId,
    		@RequestParam(required=false) String currentMonthId,
    		@RequestParam(required=false) Integer currentPersonId) {
    	Team team = teamRepository.findOne(teamId);
    	TeamDTO teamDto = new TeamDTO(team);
    	Set<Person> persons;
    	if(team.isManagerCurrentUser()) {
    		persons = team.getPersons();
    	} else {
    		persons = personRepository.findByUserAndTeam(UserService.getCurrentUser(), team);
    		if(persons.size()==1)
    			currentPersonId = persons.iterator().next().getId(); 
    	}
    	teamDto.setPersons(persons.stream()
    			.map(p->new PersonDTO(p,0)).collect(Collectors.toList()));
    	if(team.isManagerCurrentUser()) {
    		teamDto.getPersons().add(0, new PersonDTO(-1,"-all-"));
    	}
    	
    	List<MonthDTO> months = makeMonths(team.getCreateDate());
    	if(currentMonthId==null) {
    		Date now = new Date();
    		currentMonthId = DateUtils.codeMonth(DateUtils.extractMonth(now), 
    				DateUtils.extractYear(now));
    	}
    	ui.addAttribute("currentMonthId", currentMonthId);
    	ui.addAttribute("currentPersonId", currentPersonId);
    	ui.addAttribute("months", months);
    	ui.addAttribute("team", teamDto);
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "inner/diary/calendar";
    }

    @RequestMapping(value = "/data", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String data(Model ui,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true) String monthId,
    		@RequestParam(required=true) Integer personId) {
    	
    	final TimeZone tzDestination;
    	String tzId = UserService.getCurrentUser().getTimeZoneId();
    	if(tzId!=null && !tzId.isEmpty())
    		tzDestination = TimeZone.getTimeZone(tzId);else
    		tzDestination = TimeZone.getDefault();	
    	
    	Team team = teamRepository.findOne(teamId);
    	
    	Date start = DateUtils.newDate(1,DateUtils.decodeMonth(monthId),DateUtils.decodeYear(monthId));
    	Date end = DateUtils.getEndOfMonth(start);
    	start = DateUtils.getStartOfWeek(start);
    	end = DateUtils.getEndOfWeek(end);
    	
    	List<Message> timing;
    	if(personId>=0) {
    		User user = personRepository.findOne(personId).getUser();
    		timing = messageRepository.findByTeamAndUserAndDtBeginBetween(team,user,start, end);
    	} else {
    		timing = messageRepository.findByTeamAndDtBeginBetween(team,start, end);
    	}
    	//group
    	Map<Date, Double> groupHours = timing.stream()
    			.collect(Collectors.groupingBy(t->DateUtils.cleanTime(DateUtils.convertToTimeZone(t.getDtBegin(),tzDestination)),
    					 Collectors.summingDouble(Message::getHours)));
    	Map<Date, Double> groupActivity = timing.stream()
    			.collect(Collectors.groupingBy(t->DateUtils.cleanTime(DateUtils.convertToTimeZone(t.getDtBegin(),tzDestination)),
    					 Collectors.summingDouble(Message::getActivity)));
    	// total
    	DayDTO total = new DayDTO();
    	total.setHours(groupHours.values().stream().mapToDouble(v->v).sum());
    	Double totalActivity = groupActivity.values().stream().mapToDouble(v->v).sum();
    	total.setActivity(normalizeActivity(totalActivity,total.getHours()));
    	// per day
    	Date curr = start;
    	Date now = new Date();
    	List<Week> data = new ArrayList<>(); 
    	while(curr.before(end)) {
    		Week week = new Week();
    		for (int i = 0; i < 7; i++) {
        		DayDTO day = new DayDTO(curr);
        		day.setHours(groupHours.get(curr));
        		// суммарная активность
        		Double sumActivity = groupActivity.get(curr);
        		// нужно нормировать
        		day.setActivity(normalizeActivity(sumActivity,day.getHours()));
        		// макс ширина 88px
        		day.setActivityWidth((int)(88*day.getActivity()/100));
        		
        		week.getDays().add(day);
        		curr = DateUtils.incDay(curr, 1);
			}
    		Date last = week.getDays().get(6).getDay();
    		week.setSignAvailable(now.after(last));
    		WeeklySignature sign = weeklySignatureRepository.findByDtDay(last);
    		week.setSignNeeded(sign==null);
    		week.setSignature(sign);
    		data.add(week);
    	}
    	ui.addAttribute("axis", DateUtils.getDayOfWeekNames(GtaSystem.getLocale()));
    	ui.addAttribute("data", data);
    	ui.addAttribute("total", total);
    	ui.addAttribute("teamId", teamId);
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "inner/diary/data";
    }

    @RequestMapping(value = "/day", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String day(Model ui,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true) String date,
    		@RequestParam(required=true) Integer personId) {

    	final TimeZone tzDestination;
    	String tzId = UserService.getCurrentUser().getTimeZoneId();
    	if(tzId!=null && !tzId.isEmpty())
    		tzDestination = TimeZone.getTimeZone(tzId);else
    		tzDestination = TimeZone.getDefault();	
    	
    	Team team = teamRepository.findOne(teamId);
    	
    	Date curr;
    	try {
			curr = new SimpleDateFormat("dd.MM.yyyy").parse(date);
		} catch (ParseException e) {
			throw new SpringException("Unknown date format");
		}
    	List<Message> timing;
    	if(personId>=0) {
    		User user = personRepository.findOne(personId).getUser();
    		timing = messageRepository.findByTeamAndUserAndDtBeginBetween(team,user,curr, DateUtils.getEndOfDay(curr));
    	} else {
    		timing = messageRepository.findByTeamAndDtBeginBetween(team,curr, DateUtils.getEndOfDay(curr));
    	}
    	//group on hours
    	Map<Integer, Double> groupHours = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					 Collectors.summingDouble(Message::getHours)));
    	Map<Integer, Double> groupActivity = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					 Collectors.summingDouble(Message::getActivity)));
    	Map<Integer, Integer> groupOnKeys = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					 Collectors.summingInt(Message::getKey)));
    	Map<Integer, Integer> groupOnMouseClicks = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					 Collectors.summingInt(Message::getMouse)));
    	Map<Integer, Integer> groupOnMouseMove = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					 Collectors.summingInt(Message::getMouseMove)));
    	Map<Integer,List<Message>> groupMessages = timing.stream()
    			.collect(Collectors.groupingBy(t->exctactLocalHour(t,tzDestination),
    					Collectors.toList()));
    	// total
    	HourDTO total = new HourDTO();
    	total.setWorktime(groupHours.values().stream().mapToDouble(v->v).sum());
    	
    	List<HourDTO> hours = new ArrayList<>();
    	for (int i = 0; i < 24; i++) {
    		HourDTO h = new HourDTO(i);
    		h.setWorktime(groupHours.get(i));
    		h.setActivity(groupActivity.get(i));
    		h.setActivityPercent(normalizeActivity(h.getActivity(),h.getWorktime()));
    		h.setKeyDown(groupOnKeys.get(i));
    		h.setMouseClick(groupOnMouseClicks.get(i));
    		h.setMouseMove(groupOnMouseMove.get(i));
    		List<Message> messages = groupMessages.get(i);
    		if(messages!=null) {
    			List<ScreenShotDTO> screenshots = messages.stream()
    					.map(m->new ScreenShotDTO(m.getScreenshot())).collect(Collectors.toList());
    			h.setScreenshots(screenshots);
    		}
    		
    		hours.add(h);
		}
    	
    	ui.addAttribute("total", total);
    	ui.addAttribute("hours", hours);
    	ui.addAttribute("date", curr);
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "inner/diary/day";
    }	
    
    private static int exctactLocalHour(Message m,TimeZone tz) {
    	return DateUtils.extractHour(DateUtils.convertToTimeZone(m.getDtBegin(),tz));
    }
    
    @RequestMapping(value = "/screenshot.png", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public ResponseEntity<byte[]> getScreenshot(Model ui,
    		@RequestParam(required=true)  Integer id) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        ScreenShot ss = screenShotRepository.findOne(id);
    	return new ResponseEntity<byte[]>(ss.getScreenshot(), headers, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/thumbnail", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public ResponseEntity<byte[]> getThumbnailScreenshot(Model ui,
    		@RequestParam(required=true)  Integer id) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        ScreenShot ss = screenShotRepository.findOne(id);
        
		ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
			BufferedImage buf = ImageIO.read(new ByteArrayInputStream(ss.getScreenshot()));
			
			BufferedImage thumbnail = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
			thumbnail.createGraphics().drawImage(buf.getScaledInstance(50, 50, Image.SCALE_SMOOTH),0,0,null);
			
			ImageIO.write(thumbnail, "png", out);
			
		} catch (IOException e) {
			throw new SpringException(e.getMessage());
		}
        
        
    	return new ResponseEntity<byte[]>(out.toByteArray(), headers, HttpStatus.CREATED);
    }
        
    
    private Double normalizeActivity(Double sumActivity, Double hours) {
    	return Message.getActivityPercent(hours, sumActivity);
	}

	private List<MonthDTO> makeMonths(Date start) {
    	List<MonthDTO> months = new ArrayList<>();
    	Date end = DateUtils.getEndOfDay(DateUtils.getEndOfMonth(new Date())); 
    	while(start.before(end)) {
    		months.add(new MonthDTO(start));
    		start = DateUtils.incMonth(start, 1);
    	}
		return months;
	}
	
	
	public class Week {
		private List<DayDTO> days = new ArrayList<>();
		private Boolean signNeeded;
		private Boolean signAvailable;
		private WeeklySignature signature;
		
		public Boolean getSignNeeded() {
			return signNeeded;
		}
		public void setSignature(WeeklySignature sign) {
			this.signature = sign;
		}
		public void setSignNeeded(Boolean signNeeded) {
			this.signNeeded = signNeeded;
		}
		public Boolean getSignAvailable() {
			return signAvailable;
		}
		public void setSignAvailable(Boolean signAviable) {
			this.signAvailable = signAviable;
		}
		public List<DayDTO> getDays() {
			return days;
		}
		public void setDays(List<DayDTO> days) {
			this.days = days;
		}
		public WeeklySignature getSignature() {
			return signature;
		}
	}
	
/*
	private List<WeekDTO> makeWeeks(Date dayOfMonth) {
    	List<WeekDTO> weeks = new ArrayList<>();
    	Date startDate = DateUtils.getStartOfMonth(dayOfMonth);
    	Date endOfMonth = DateUtils.getEndOfMonth(dayOfMonth);
    	Date startWeek = DateUtils.getStartOfWeek(startDate);
    	Date curr = startWeek;
    	while (curr.before(endOfMonth)) {
    		WeekDTO w =  new WeekDTO(curr);
    		weeks.add(w);
    		curr = DateUtils.incDay(curr, 7);
    	}
    	return weeks;
    }
*/   
}
