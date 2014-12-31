package biz.gelicon.gta.server.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.gelicon.gta.server.Login;
import biz.gelicon.gta.server.Sessions;
import biz.gelicon.gta.server.data.User;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/action")
public class LoginController {
	

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
	
}
