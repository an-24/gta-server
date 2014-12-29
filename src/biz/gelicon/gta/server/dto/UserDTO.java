package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.User;

public class UserDTO {
	private int id;
	private String name;
	private String email;
	private String members;
	private Integer mode;

	public UserDTO() {
	}
	public UserDTO(Integer mode) {
		this.mode = mode;
	}
	public UserDTO(User u) {
		id = u.getId();
		name = u.getName();
		email=u.getEmail();
	}
	
	public UserDTO(User u, Integer mode) {
		this(u);
		this.mode =  mode;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMembers() {
		return members;
	}
	public void setMembers(String members) {
		this.members = members;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	

}
