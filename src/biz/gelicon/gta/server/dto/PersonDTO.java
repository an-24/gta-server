package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.Person;

public class PersonDTO {
	private Integer id;
	private String nic;
	private UserDTO user;
	private Integer post;
	private String postName;
	private Integer limit;
	private Boolean manager;
	private Integer mode;
	private TeamDTO team;
	private Boolean internal;
	
	public PersonDTO() {
	}

	public PersonDTO(Integer id, String nic) {
		this.id = id;
		this.nic = nic;
	}

	public PersonDTO(Person person,Integer mode) {
		this.id = person.getId();
		this.nic = person.getNic();
		if(person.getUser()!=null) this.user = new UserDTO(person.getUser());else
			this.user = null;
		this.postName = person.getPost();
		this.post = person.getPostDict()!=null?person.getPostDict().getId():null;
		this.limit = person.getLimit();
		this.manager = person.isManager();
		this.team = new TeamDTO();
		this.team.setId(person.getTeam().getId());
		this.team.setName(person.getTeam().getName());
		this.mode = mode;
		this.internal = person.getInternal();
	}
	
	public PersonDTO(Integer mode) {
		this.mode = mode;
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

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getManager() {
		return manager;
	}

	public void setManager(Boolean manager) {
		this.manager = manager;
	}

	public TeamDTO getTeam() {
		return team;
	}

	public void setTeam(TeamDTO team) {
		this.team = team;
	}

	public Boolean getInternal() {
		return internal;
	}

	public void setInternal(Boolean internal) {
		this.internal = internal;
	}
}