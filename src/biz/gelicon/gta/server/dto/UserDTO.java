package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.User;

public class UserDTO {
	private int id;
	private String name;

	public UserDTO() {
	}
	public UserDTO(User u) {
		id = u.getId();
		name = u.getName();
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

}
