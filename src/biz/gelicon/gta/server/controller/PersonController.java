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
	
    @RequestMapping(value = "{id}", method=RequestMethod.GET)
    @Transactional(readOnly=true)
    public ModelAndView personInner(Model ui, HttpServletRequest request,
    		@PathVariable Integer id) {
    	Person person = personRepository.findOne(id);
    	ModelAndView mv = new ModelAndView("inner/getperson","command",new PersonDTO(person));
    	boolean manager = userService.isCurrentPM(person.getTeam());
    	mv.getModelMap().addAttribute("manager", manager);
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
	
}
