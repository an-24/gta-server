package biz.gelicon.gta.server.controller;

import java.security.NoSuchAlgorithmException;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.Login;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.dto.TeamDTO;
import biz.gelicon.gta.server.dto.UserDTO;
import biz.gelicon.gta.server.repo.TeamRepository;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.SpringException;


@Controller
@RequestMapping("/inner/admin")
public class AdminController {
	
	@Inject
	private UserService userService;
	@Inject
	private TeamRepository teamRepository;
	

    @RequestMapping(method=RequestMethod.GET)
    public String main(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("menu","admin");
        return "inner/admin/index";
    }
    
    @RequestMapping(value = "/users", method=RequestMethod.GET)
    @Transactional(readOnly=true)
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
    	mv.getModelMap().addAttribute("pswd_confirmation", "");
        return mv;
    }
    
    @RequestMapping(value = "/users/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editUser(Model ui,
    		@PathVariable Integer id) {
    	ModelAndView mv = new ModelAndView("inner/admin/user","user",new UserDTO(userService.findUser(id),GtaSystem.MODE_EDIT));
        return mv;
    }

    @RequestMapping(value = "/users/update", method=RequestMethod.POST, produces = "application/json")
    @ResponseBody
    @Transactional
    public String updateUser(Model ui,UserDTO dto, String pswd_confirmation) {
    	User user;
    	if(dto.getMode()==GtaSystem.MODE_ADD) {
    		User u = userService.findByName(dto.getName());
    		if(u!=null)
    			throw new SpringException("A user with the same name already exists");
        	user = new User();
        	user.setId(dto.getId());
    	} else {
    		user = userService.findUser(dto.getId());
    	}
    	user.setName(dto.getName());
    	user.setEmail(dto.getEmail());
    	
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
    	List<TeamDTO> list = teamRepository.findAll().stream().map(t->{
    		TeamDTO dto = new TeamDTO(t);
    		dto.setWorkerCount(t.getPersons().size());;
    		return dto;
    	}).collect(Collectors.toList());
    	ui.addAttribute("teams", list);
		return "inner/admin/teams";
    }

    @RequestMapping(value = "/teams/add", method=RequestMethod.GET)
    public ModelAndView addTeam(Model ui) {
    	ModelAndView mv = new ModelAndView("inner/admin/team","team",new TeamDTO(GtaSystem.MODE_ADD));
    	mv.getModelMap().addAttribute("pswd_confirmation", "");
        return mv;
    }
    
    @RequestMapping(value = "/teams/edit/{id}", method=RequestMethod.GET)
    public ModelAndView editTeam(Model ui,
    		@PathVariable Integer id) {
    	ModelAndView mv = new ModelAndView("inner/admin/team","team",new TeamDTO(teamRepository.findOne(id),GtaSystem.MODE_EDIT));
        return mv;
    }
}
