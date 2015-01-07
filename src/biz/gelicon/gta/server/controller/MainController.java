package biz.gelicon.gta.server.controller;

import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.Teams;
import biz.gelicon.gta.server.service.UserService;
import biz.gelicon.gta.server.utils.SpringException;

@Controller
@RequestMapping("/")
public class MainController {
	
    @RequestMapping(method=RequestMethod.GET)
    public String main(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	ui.addAttribute("menu","home");
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }

    @RequestMapping(method=RequestMethod.POST)
    public String mainPost(Model ui, HttpServletRequest request) {
        return main(ui,request);
    }

    @RequestMapping(value = "/proj",method=RequestMethod.GET)
    public String projGET(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	ui.addAttribute("menu","projects");
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }

    @RequestMapping(value = "/diary",method=RequestMethod.GET)
    public String diaryGET(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	Integer diaryId = (Integer) session.getAttribute("diaryId");
    	if(diaryId!=null) {
    		// забываем
    		session.removeAttribute("diaryId");
    	}
    	ui.addAttribute("menu","diary"+(diaryId==null?"":"/"+diaryId));
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }

    @RequestMapping(value = "/diary/{id}",method=RequestMethod.GET)
    public String diaryOneGET(Model ui, HttpServletRequest request,
    		@PathVariable Integer id, RedirectAttributes ra) {
    	HttpSession session = request.getSession();
    	session.setAttribute("diaryId", id);
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	ui.addAttribute("menu","diary");
    	ui.addAttribute("base",getBaseURL(request));
        return "redirect:../diary";
    }
    
    @RequestMapping(value = "/admin",method=RequestMethod.GET)
    public String adminGET(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	ui.addAttribute("menu","admin");
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }
    
    @RequestMapping(value = "/profile",method=RequestMethod.GET)
    public String profileGET(Model ui, HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	ui.addAttribute("user",session.getAttribute("user"));
    	ui.addAttribute("userObj",UserService.getCurrentUser());
    	ui.addAttribute("menu","admin/profile/edit");
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }
    
    @RequestMapping(value = "/proj",method=RequestMethod.POST)
    public String projPOST(Model ui, HttpServletRequest request) {
    	return projGET(ui, request);
    }
    
    @RequestMapping(value = "/login", method=RequestMethod.GET)
    public String login(Model ui, HttpServletRequest request) {
    	ui.addAttribute("menu","login");
    	ui.addAttribute("base",getBaseURL(request));
    	ui.addAttribute("loc",GtaSystem.getLocale().toLanguageTag());
        return "index";
    }

    @RequestMapping(value = "/login", method=RequestMethod.POST)
    public String loginPost(Model ui, HttpServletRequest request) {
        return login(ui,request);
    }
    
    @RequestMapping(value = "inner/home", method=RequestMethod.GET)
    public String homeInner(Model ui) {
    	return "inner/home";
    }

    @RequestMapping(value = "inner/projects", method=RequestMethod.GET)
    public String projInner(Model ui) {
    	ui.addAttribute("teams",new Teams().getTeams(UserService.getCurrentUser()));
    	return "inner/projects";
    }
    
    static public class UserInput {
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
    public ModelAndView loginInner(Model ui) {
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
