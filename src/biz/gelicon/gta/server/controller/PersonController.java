package biz.gelicon.gta.server.controller;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import biz.gelicon.gta.server.controller.MainController.UserInput;
import biz.gelicon.gta.server.data.Person;
import biz.gelicon.gta.server.repo.PersonRepository;

@Controller
@RequestMapping("/inner/person")
public class PersonController {

	@Inject
	private PersonRepository personRepository;
	
    static public class PersonalInput {
    	private String nic;
    	private Integer user;
    	private Integer post;
    	private String postName;
    	private Integer limit;
    	private Boolean manager;
    	
		public PersonalInput(Person person) {
			this.nic = person.getNic();
			this.user = person.getUser().getId();
			this.postName = person.getPost();
			this.post = person.getPostDict()!=null?person.getPostDict().getId():null;
			this.limit = person.getLimit();
			this.manager = person.isManager();
		}
		
		public String getNic() {
			return nic;
		}
		public void setNic(String nic) {
			this.nic = nic;
		}
		public Integer getUser() {
			return user;
		}
		public void setUser(Integer user) {
			this.user = user;
		}
		public Integer getPost() {
			return post;
		}
		public void setPost(Integer post) {
			this.post = post;
		}
		public String getPostName() {
			return postName;
		}
		public void setPostName(String postName) {
			this.postName = postName;
		}
		public Integer getLimit() {
			return limit;
		}
		public void setLimit(Integer limit) {
			this.limit = limit;
		}

		public boolean isManager() {
			return manager;
		}

		public void setManager(boolean manager) {
			this.manager = manager;
		}
    }
	
    @RequestMapping(value = "{id}", method=RequestMethod.GET)
    public ModelAndView personInner(Model ui,
    		@PathVariable Integer id) {
    	Person person = personRepository.findOne(id);
    	return new ModelAndView("inner/getperson","person",new PersonalInput(person));
    }
    
	
}
