package biz.gelicon.gta.server.controller;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.Login;
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.Post;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.dto.UserDTO;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.PostRepository;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.service.PersonService;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.DateUtils;
import biz.gelicon.gta.server.utils.SpringException;


@Controller
@RequestMapping("/inner/admin")
public class AdminController {
	
	@Inject
	private UserService userService;
	@Inject
	private TeamRepository teamRepository;
	@Inject
	private PostRepository postRepository;
	@Inject
	private PersonRepository personRepository;
	@Inject
	private PersonService personService;
	

    @RequestMapping(method=RequestMethod.GET)
    public String main(Model ui, HttpServletRequest request) {
    	checkAdmin();
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("menu","admin");
        return "inner/admin/index";
    }

    @RequestMapping(value = "/getusers", params={"term"},method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getUsers(@RequestParam(value = "term") String term) {
    	checkAdmin();
    	List<User> ulist = userService.findByNameLike(term+"%");
    	List<String> suser = ulist.stream().map(u->"{\"label\":\""+u.getName()+"\",\"id\":"+u.getId()+"}").collect(Collectors.toList());
    	return suser.toString();
    }

    @RequestMapping(value = "/getteams", params={"term"},method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getTeams(@RequestParam(value = "term") String term) {
    	checkAdmin();
    	List<Team> list = teamRepository.findByNameLike(term+"%");
    	List<String> slist = list.stream().map(t->"{\"label\":\""+t.getName()+"\",\"id\":"+t.getId()+"}").collect(Collectors.toList());
    	return slist.toString();
    }
    
    @RequestMapping(value = "/users", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String users(Model ui) {
    	checkAdmin();
    	List<UserDTO> list = userService.findAll().stream().map(u->{
    		UserDTO dto = new UserDTO(u);
    		dto.setMembers(
    				userService.getMemebersOfTeam(u)
    					.stream().map(t->t.getName())
    					.distinct()
    					.collect(Collectors.toList())
    					.toString()
    		);
    		return dto;
    	}).collect(Collectors.toList());
    	ui.addAttribute("users", list);
		return "inner/admin/users";
    }
    
    @RequestMapping(value = "/users/add", method=RequestMethod.GET)
    public ModelAndView addUser(Model ui) {
    	checkAdmin();
    	ModelAndView mv = new ModelAndView("inner/admin/user","user",new UserDTO(GtaSystem.MODE_ADD));
    	mv.getModelMap().addAttribute("pswd_confirmation", "");
    	List<Locale> locales =GtaSystem.getAviableLocales();
    	mv.getModelMap().addAttribute("locales", locales);
        return mv;
    }
    
    @RequestMapping(value = "/users/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editUser(Model ui,
    		@PathVariable Integer id) {
    	User u = userService.findUser(id);
    	checkAdminOrSelf(u);
    	ModelAndView mv = new ModelAndView("inner/admin/user","user",new UserDTO(u,GtaSystem.MODE_EDIT));
    	List<Locale> locales =GtaSystem.getAviableLocales();
    	mv.getModelMap().addAttribute("locales", locales);
        return mv;
    }

    @RequestMapping(value = "/profile/edit", method=RequestMethod.GET)
    public ModelAndView profileUser(Model ui) {
    	User u = UserService.getCurrentUser();
    	ModelAndView mv = new ModelAndView("inner/admin/profile","user",new UserDTO(u,GtaSystem.MODE_EDIT));
        return mv;
    }
    
    
    @RequestMapping(value = "/users/update", method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @Transactional
    public String updateUser(Model ui,UserDTO dto, String pswd_confirmation) {
    	User user;
    	if(dto.getMode()==GtaSystem.MODE_ADD) {
        	checkAdmin();
    		User u = userService.findByName(dto.getName());
    		if(u!=null)
    			throw new SpringException("A user with the same name already exists");
        	user = new User();
        	user.setId(dto.getId());
    	} else {
    		user = userService.findUser(dto.getId());
        	checkAdminOrSelf(user);
    		User u = userService.findByIdNotAndName(dto.getId(),dto.getName());
    		if(u!=null)
    			throw new SpringException("A user with the same name already exists");
    	}
    	user.setName(dto.getName());
    	user.setEmail(dto.getEmail());
    	user.setLocale(dto.getLocale());
    	user.setTimeZoneId(dto.getTimeZoneId());
    	
    	if(pswd_confirmation!=null && !pswd_confirmation.isEmpty()) {

    		try {
				String hash = Login.hashPassword(user,pswd_confirmation);
				user.setPassword(hash);
			} catch (NoSuchAlgorithmException e) {
				throw new SpringException(e.getMessage());
			}
    		
    	};
    	
    	userService.save(user);
    	
		String result;
		if(dto.getMode()==GtaSystem.MODE_EDIT)
			result = "User successfully changed";else
				result = "User successfully added";	
		return "{\"message\":\""+result+"\"}";
    }
    
	@RequestMapping(value = "/teams", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String teams(Model ui) {
    	checkAdmin();
    	List<TeamDTO> list = teamRepository.findAll().stream().map(t->{
    		TeamDTO dto = new TeamDTO(t);
    		dto.setWorkerCount(t.getPersons().size());
    		return dto;
    	}).collect(Collectors.toList());
    	ui.addAttribute("teams", list);
		return "inner/admin/teams";
    }

    @RequestMapping(value = "/teams/add", method=RequestMethod.GET)
    public ModelAndView addTeam(Model ui) {
    	checkAdmin();
    	ModelAndView mv = new ModelAndView("inner/admin/team","team",new TeamDTO(GtaSystem.MODE_ADD));
        return mv;
    }
    
    @RequestMapping(value = "/teams/edit/{id}", method=RequestMethod.GET)
    @Transactional
    public ModelAndView editTeam(Model ui,
    		@PathVariable Integer id) {
    	checkAdmin();
    	ModelAndView mv = new ModelAndView("inner/admin/team","team",new TeamDTO(teamRepository.findOne(id),GtaSystem.MODE_EDIT));
        return mv;
    }
    
    
    @RequestMapping(value = "/teams/update", method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @Transactional
    public String updateTeam(Model ui,TeamDTO dto) {
    	checkAdmin();
    	Team team;
    	if(dto.getMode()==GtaSystem.MODE_ADD) {
    		Team t = teamRepository.findByName(dto.getName());
    		if(t!=null)
    			throw new SpringException("A team with the same name already exists");
        	team = new Team();
        	team.setId(dto.getId());
    	} else {
    		team = teamRepository.findOne(dto.getId());
    		Team t = teamRepository.findByIdNotAndName(dto.getId(),dto.getName());
    		if(t!=null)
    			throw new SpringException("A team with the same name already exists");
    	}
    	team.setName(dto.getName());
    	team.setActive(dto.getActive());
    	team.setLimit(dto.getLimit());
    	team.setCreateDate(DateUtils.cleanTime(new Date()));
    	
    	Person manager;
    	PersonDTO manDto = dto.getManager();
    	if(dto.getMode()==GtaSystem.MODE_ADD 
    			|| (dto.getMode()==GtaSystem.MODE_EDIT && dto.getManager().getId()==null)) {
        	manager = new Person();
        	manager.setNic(manDto.getNic());
        	manager.setPostDict(postRepository.findOne(Post.MANAGERID));
        	manager.setPost("Project Manager");
        	manager.setUser(userService.findUser(manDto.getUser().getId()));
    	} else {
        	manager = personRepository.findOne(dto.getManager().getId());
        	team.getPersons().remove(manager);
        	manager.setNic(manDto.getNic());
        	manager.setUser(userService.findUser(manDto.getUser().getId()));
    	}
    	manager.setTeam(team);
    	team.getPersons().add(manager);
    	
    	teamRepository.save(team);
    	personService.save(manager);
    	
		String result;
		if(dto.getMode()==GtaSystem.MODE_EDIT)
			result = "Team successfully changed";else
				result = "Team successfully added";	
		return "{\"message\":\""+result+"\"}";
    }
    
    @RequestMapping(value = "/persons", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public String persons(Model ui) {
    	checkAdmin();
    	List<PersonDTO> list = personRepository.findAll().stream().map(p->{
    		PersonDTO dto = new PersonDTO(p,0);
    		return dto;
    	}).collect(Collectors.toList());
    	ui.addAttribute("persons", list);
		return "inner/admin/persons";
    }
    @RequestMapping(value = "/persons/add", method=RequestMethod.GET)
    @Transactional
    public ModelAndView addPerson(Model ui) {
    	ModelAndView mv = new ModelAndView("inner/admin/person","person",
    			new PersonDTO(GtaSystem.MODE_ADD));
    	checkAdmin();
    	mv.getModelMap().addAttribute("posts", postRepository.findAll());
        return mv;
    }
    
    @RequestMapping(value = "/persons/edit/{id}", method=RequestMethod.GET)
    @Transactional
    public ModelAndView editPerson(Model ui,
    		@PathVariable Integer id) {
    	checkAdmin();
    	ModelAndView mv = new ModelAndView("inner/admin/person","person",
    			new PersonDTO(personRepository.findOne(id),GtaSystem.MODE_EDIT));
    	mv.getModelMap().addAttribute("posts", postRepository.findAll());
        return mv;
    }
    
    @RequestMapping(value = "/persons/update", method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @Transactional
    public String updatePerson(Model ui,PersonDTO dto) {
    	checkAdmin();
    	Person p;
		Team team = teamRepository.findOne(dto.getTeam().getId());
    	if(dto.getMode()==GtaSystem.MODE_ADD) {
    		p = new Person();
    		p.setTeam(team);
    		Person p1 = personRepository.findByTeam_IdAndNic(dto.getTeam().getId(),dto.getNic());
    		if(p1!=null)
    			throw new SpringException("A person with the same name already exists");
    	} else {
    		p = personRepository.findOne(dto.getId());
    		p.setTeam(team);
    		Person p1 = personRepository.findByIdNotAndTeam_IdAndNic(dto.getId(),dto.getTeam().getId(),dto.getNic());
    		if(p1!=null)
    			throw new SpringException("A person with the same name already exists");
    	}
    	p.setNic(dto.getNic());
    	p.setUser(userService.findUser(dto.getUser().getId()));
    	if(dto.getPost()!=null)
    		p.setPostDict(postRepository.findOne(dto.getPost()));
    	p.setPost(dto.getPostName());
    	p.setLimit(dto.getLimit());
    	p.setInternal(dto.getInternal());
    	
    	personService.save(p);
    	
		String result;
		if(dto.getMode()==GtaSystem.MODE_EDIT)
			result = "Team successfully changed";else
				result = "Team successfully added";	
		return "{\"message\":\""+result+"\"}";
    }

    private void checkAdmin(){
    	if(!UserService.getCurrentUser().isSysAdmin())
    		throw new SpringException("System admin role needed");
    }

    private void checkAdminOrSelf(User user) {
    	if(!UserService.getCurrentUser().isSysAdmin() &&
    	   !UserService.getCurrentUser().getId().equals(user.getId()))
    		throw new SpringException("System admin role needed");
	}
    
}
