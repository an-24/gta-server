package biz.gelicon.gta.server.dto;

import java.util.List;


public class HourDTO {
	private int hour;
	private Double activity;
	private Double activityPercent;
	private Double worktime;
	private Integer keyDown;
	private Integer mouseClick;
	private Integer mouseMove;
	private List<ScreenShotDTO> screenshots;

	public HourDTO() {
	}
	
	public HourDTO(int hour) {
		this.hour = hour;
	}
	
	public String getInfo() {
		return hour+":00 - "+(hour+1)+":00";
	}

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public Double getWorktime() {
		return worktime;
	}

	public void setWorktime(Double worktime) {
		this.worktime = worktime;
	}

	public Integer getKeyDown() {
		return keyDown;
	}

	public void setKeyDown(Integer keyDown) {
		this.keyDown = keyDown;
	}

	public Integer getMouseClick() {
		return mouseClick;
	}

	public void setMouseClick(Integer mouseClick) {
		this.mouseClick = mouseClick;
	}

	public Integer getMouseMove() {
		return mouseMove;
	}

	public void setMouseMove(Integer mouseMove) {
		this.mouseMove = mouseMove;
	}

	public Double getActivity() {
		return activity;
	}

	public void setActivity(Double activity) {
		this.activity = activity;
	}

	public Double getActivityPercent() {
		return activityPercent;
	}

	public void setActivityPercent(Double activityPercent) {
		this.activityPercent = activityPercent;
	}

	public List<ScreenShotDTO> getScreenshots() {
		return screenshots;
	}

	public void setScreenshots(List<ScreenShotDTO> screenshots) {
		this.screenshots = screenshots;
	}


}
