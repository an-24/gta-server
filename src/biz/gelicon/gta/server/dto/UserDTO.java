package biz.gelicon.gta.server.dto;

import biz.gelicon.gta.server.data.User;

public class UserDTO {
	private Integer id;
	private String name;
	private String email;
	private String members;
	private Integer mode;
	private String locale;
	private String timeZoneId;

	public UserDTO() {
	}
	public UserDTO(Integer mode) {
		this.mode = mode;
	}
	public UserDTO(User u) {
		id = u.getId();
		name = u.getName();
		email=u.getEmail();
		locale=u.getLocale();
		timeZoneId=u.getTimeZoneId();
	}
	
	public UserDTO(User u, Integer mode) {
		this(u);
		this.mode =  mode;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
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
	public String getLocale() {
		return locale;
	}
	public void setLocale(String locale) {
		this.locale = locale;
	}
	public String getTimeZoneId() {
		return timeZoneId;
	}
	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}
	

}
