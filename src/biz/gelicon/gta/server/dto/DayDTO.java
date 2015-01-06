package biz.gelicon.gta.server.dto;

import java.util.Date;

public class DayDTO {
	private Date day;
	private Double hours;
	private Double activity;
	private int activityWidth;
	
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
	public Double getHours() {
		return hours;
	}
	public void setHours(Double value) {
		this.hours = value;
	}
	public Double getActivity() {
		return activity;
	}
	public void setActivity(Double activity) {
		this.activity = activity;
	}

	public int getActivityWidth() {
		return activityWidth;
	}

	public void setActivityWidth(int activityWidth) {
		this.activityWidth = activityWidth;
	}
}
