package biz.gelicon.gta.server.dto;

import java.util.Date;

public class DayDTO {
	private Date day;
	private int hours;
	private int activity;
	
	public DayDTO() {
	}
	
	public DayDTO(Date day) {
		this.day = day;
	}
	
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public int getHours() {
		return hours;
	}
	public void setHours(int hours) {
		this.hours = hours;
	}
	public int getActivity() {
		return activity;
	}
	public void setActivity(int activity) {
		this.activity = activity;
	}
}
