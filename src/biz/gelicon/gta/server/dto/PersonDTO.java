package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.Person;

public class PersonDTO {
	private String nic;
	private UserDTO user;
	private Integer post;
	private String postName;
	private Integer limit;
	private Boolean manager;
	
	public PersonDTO(Person person) {
		this.nic = person.getNic();
		this.user = new UserDTO(person.getUser());
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
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
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