package biz.gelicon.gta.server.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.inject.Inject;

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
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.DayDTO;
import biz.gelicon.gta.server.dto.MonthDTO;
import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.dto.WeekDTO;
import biz.gelicon.gta.server.repo.MessageRepository;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.DateUtils;

@Controller
@RequestMapping("/inner/diary")
public class WorkDiaryController {
	@Inject
	private TeamRepository teamRepository;
	@Inject
	private PersonRepository personRepository;
	@Inject
	private MessageRepository messageRepository;

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
    	ui.addAttribute("axis", DateUtils.getDayOfWeekNames(GtaSystem.getLocale()));
    	ui.addAttribute("currentMonthId", currentMonthId);
    	ui.addAttribute("currentPersonId", currentPersonId);
    	ui.addAttribute("months", months);
    	ui.addAttribute("team", teamDto);
        return "inner/diary/calendar";
    }

    @RequestMapping(value = "/data", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String data(Model ui,
    		@RequestParam(required=true)  Integer teamId,
    		@RequestParam(required=true) String monthId,
    		@RequestParam(required=true) Integer personId) {
    	
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
    	Map<Date, Double> groups = timing.stream()
    			.collect(Collectors.groupingBy(t->DateUtils.cleanTime(t.getDtBegin()),
    					 Collectors.summingDouble(Message::getHours)));
    	
    	//timing.stream().flatMapToDouble(t->24*DateUtils.substractDate(t.getDtFinish(),t.getDtBegin()));
    	
    	Date curr = start;
    	List<List<DayDTO>> data = new ArrayList<>(); 
    	while(curr.before(end)) {
    		List<DayDTO> week = new ArrayList<>();
    		for (int i = 0; i < 7; i++) {
        		DayDTO day = new DayDTO(curr);
        		//TODO
        		week.add(day);
        		curr = DateUtils.incDay(curr, 1);
			}
    		data.add(week);
    	}
    	ui.addAttribute("data", data);
        return "inner/diary/data";
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
    
}
