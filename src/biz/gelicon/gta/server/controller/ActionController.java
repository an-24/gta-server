package biz.gelicon.gta.server.controller;



import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.Login;
import biz.gelicon.gta.server.Sessions;
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.repo.PersonRepository;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/action")
public class ActionController {
	
	@Inject
	private UserService userService;
	@Inject
	private PersonRepository personRepository;

	@RequestMapping(value = "/login", method=RequestMethod.POST, produces = "application/json")
 	@ResponseBody
    public String login(Model ui, MainController.UserInput in, HttpServletRequest request) {
		if(in.getName().isEmpty())
			throw new SpringException("Name is empty");
		try {
			String token = new Login().login(in.getName(), in.getPassword());
			if(token.isEmpty())
				throw new Exception("The username or password you entered is incorrect");
	    	HttpSession session = request.getSession();
			User u = (User) Sessions.getSessionAttr(token);
			session.setAttribute("user",u.getName());
			String referer = request.getHeader("referer");
			if(referer.endsWith("login")) referer = referer + "/../";
			return "{\"token\":\""+token+"\",\"userId\":"+u.getId()+",\"userName\":\""+u.getName()
					+"\",\"redirect\":\""+referer+"\"}";
		} catch (Throwable e) {
			throw new SpringException(e.getMessage());
		}
    }

	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
		session.removeAttribute("user");
		UserService.setCurrentUser(null);
		return "redirect:../../web/";
	}
	
	@RequestMapping(value = "/updatePerson", method=RequestMethod.POST, produces = "application/json")
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
