package biz.gelicon.gta.server.dto;

import java.util.Date;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.utils.DateUtils;

public class MonthDTO {
	private String id;
	private int number;
	private String name;
	private int year;
	
	public MonthDTO() {
	}

	public MonthDTO(Date dayOfMonth) {
		this(DateUtils.extractMonth(dayOfMonth),DateUtils.extractYear(dayOfMonth));
	}
	
	public MonthDTO(int number, int year) {
		this.number = number;
		this.name = DateUtils.getMonthName(number,GtaSystem.getLocale());
		this.year = year;
		this.id = DateUtils.codeMonth(number, year);
	}
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
		this.id = DateUtils.codeMonth(number, year);
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
		this.id = DateUtils.codeMonth(number, year);
	}
	
	public String getFullName() {
		return (number+1)+", "+name+" "+year;
	}

	public String getId() {
		return id;
	}

}
