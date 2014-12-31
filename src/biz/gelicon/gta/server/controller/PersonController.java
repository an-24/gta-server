package biz.gelicon.gta.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

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
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.repo.PostRepository;
import biz.gelicon.gta.server.service.UserService;

@Controller
@RequestMapping("/inner/person")
public class PersonController {

	@Inject
	private PersonRepository personRepository;
	@Inject
	private PostRepository postRepository;
	@Inject
	private UserService userService;
	
    @RequestMapping(value = "/edit/{id}", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public ModelAndView personEdit(Model ui,
    		@PathVariable Integer id) {
    	Person person = personRepository.findOne(id);
    	ModelAndView mv = new ModelAndView("inner/getperson","command",new PersonDTO(person, GtaSystem.MODE_EDIT));
    	boolean manager = userService.isCurrentPM(person.getTeam());
    	mv.getModelMap().addAttribute("manager", manager);
    	mv.getModelMap().addAttribute("teamId", person.getTeam().getId());
    	mv.getModelMap().addAttribute("posts", postRepository.findAll());
    	return mv;
    }

    @RequestMapping(value = "/add/{teamId}", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public ModelAndView personAdd(Model ui, @PathVariable Integer teamId) {
    	Person person = new Person();
    	person.setTeam(userService.findTeam(teamId));
    	ModelAndView mv = new ModelAndView("inner/getperson","command",new PersonDTO(person, GtaSystem.MODE_ADD));
    	boolean manager = userService.isCurrentPM(person.getTeam());
    	mv.getModelMap().addAttribute("manager", manager);
    	mv.getModelMap().addAttribute("teamId", person.getTeam().getId());
    	mv.getModelMap().addAttribute("posts", postRepository.findAll());
    	return mv;
    }
    
    @RequestMapping(value = "/getusers", params={"term"},method=RequestMethod.GET, produces="application/json")
    @ResponseBody
    public String getUsers(@RequestParam(value = "term") String term) {
    	List<User> ulist = userService.findByNameLike(term+"%");
    	List<String> suser = ulist.stream().map(u->"{\"label\":\""+u.getName()+"\",\"id\":"+u.getId()+"}").collect(Collectors.toList());
    	return suser.toString();
    	
    } 

    
	@RequestMapping(value = "/update", method=RequestMethod.POST, produces = "application/json")
 	@ResponseBody
	public String updatePerson(Model ui, PersonDTO dto, Integer teamId) {
		
		Person p = new Person(); 
		p.setId(dto.getId());
		p.setNic(dto.getNic());
		p.setPost(dto.getPostName());
		p.setLimit(dto.getLimit());
		p.setUser(userService.findUser(dto.getUser().getId()));
		if(dto.getPost()!=null)
			p.setPostDict(userService.findPost(dto.getPost()));
		p.setTeam(userService.findTeam(teamId));
		personRepository.save(p);
		String result;
		if(dto.getMode()==GtaSystem.MODE_EDIT)
			result = "Team member successfully changed";else
				result = "Team member successfully added";	
		return "{\"message\":\""+result+"\"}";
	}
    
}
