package biz.gelicon.gta.server.dto;

import java.util.Date;

import biz.gelicon.gta.server.utils.DateUtils;

public class WeekDTO {
	private Date start;
	private Date end;

	public WeekDTO() {
	}

	public WeekDTO(Date start) {
		this.start = DateUtils.cleanTime(start);
		end = DateUtils.getEndOfWeek(start);
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
	
	public Date getDayOfWeek(int dayOfweek) {
		return DateUtils.incDay(start, dayOfweek);
	}

}
