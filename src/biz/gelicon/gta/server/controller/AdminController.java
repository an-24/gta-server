package biz.gelicon.gta.server.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.dto.PersonDTO;
import biz.gelicon.gta.server.dto.UserDTO;
import biz.gelicon.gta.server.service.UserService;


@Controller
@RequestMapping("/inner/admin")
public class AdminController {
	
	@Inject
	private UserService userService;
	

    @RequestMapping(method=RequestMethod.GET)
    public String main(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("menu","admin");
        return "inner/admin/index";
    }
    
    @RequestMapping(value = "/users", method=RequestMethod.GET)
    public String users(Model ui) {
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
    	ModelAndView mv = new ModelAndView("inner/admin/user","user",new UserDTO(GtaSystem.MODE_ADD));
        return mv;
    }
    
    @RequestMapping(value = "/users/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editUser(Model ui,
    		@PathVariable Integer id) {
    	ModelAndView mv = new ModelAndView("inner/admin/user","user",new UserDTO(userService.findUser(id),GtaSystem.MODE_EDIT));
        return mv;
    }

    
    @RequestMapping(value = "/teams", method=RequestMethod.GET)
    public String teams(Model ui) {
		return "inner/admin/teams";
    }

}
