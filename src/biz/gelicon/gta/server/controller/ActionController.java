package biz.gelicon.gta.server.controller;



import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import biz.gelicon.gta.server.Login;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/action")
public class ActionController {

	@RequestMapping(value = "/login", method=RequestMethod.POST)
 	@ResponseBody
    public String login(Model ui, MainController.UserInput in, HttpServletRequest request) {
		if(in.getName().isEmpty())
			throw new SpringException("Name is empty");
		try {
			String token = new Login().login(in.getName(), in.getPassword());
			if(token.isEmpty())
				throw new Exception("The username or password you entered is incorrect");
	    	HttpSession session = request.getSession();
			session.setAttribute("user",in.getName());
			return "\""+token+"\"";
		} catch (Throwable e) {
			throw new SpringException(e.getMessage());
		}
    }

	@RequestMapping(value = "/logout", method=RequestMethod.GET)
	public String logout(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
		session.removeAttribute("user");
		return "redirect:../../web/";
	}
}
