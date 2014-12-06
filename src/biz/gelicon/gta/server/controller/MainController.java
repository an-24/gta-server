package biz.gelicon.gta.server.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/")
public class MainController {

    @RequestMapping(method=RequestMethod.GET)
    @ExceptionHandler({SpringException.class})
    public String main(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("menu","home");
    	ui.addAttribute("base",getBaseURL(request));
        return "index";
    }

    @RequestMapping(value = "/login", method=RequestMethod.GET)
    @ExceptionHandler({SpringException.class})
    public String login(Model ui, HttpServletRequest request) {
    	ui.addAttribute("menu","login");
    	ui.addAttribute("base",getBaseURL(request));
        return "index";
    }

    @RequestMapping(value = "inner/home", method=RequestMethod.GET)
    public String homeInner(Model ui, HttpServletRequest request) {
    	return "inner/home";
    }

    public class UserInput {
    	private String name = "";
    	private String password = "";

		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
    }
    
    @RequestMapping(value = "inner/login", method=RequestMethod.GET)
    public ModelAndView loginInner(Model ui, HttpServletRequest request) {
    	return new ModelAndView("inner/login","command",new UserInput());
    }
    
    private String getBaseURL(HttpServletRequest request) {
		try {
			URL url = new URL(request.getRequestURL().toString());
			return url.getProtocol()+"://"+url.getHost()+":"+url.getPort()+request.getContextPath()+request.getServletPath();
		} catch (MalformedURLException e) {
			throw new SpringException(e.getMessage());
		}
    }
}
