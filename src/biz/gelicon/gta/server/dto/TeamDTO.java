package biz.gelicon.gta.server.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.utils.DateUtils;

public class TeamDTO {
	private Integer id;
	private String name;
	private Boolean active;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date createDate;
	
	private Integer limit;
	private Integer workerCount;
	private Integer mode;

	public TeamDTO() {
	}

	public TeamDTO(Team t) {
		this.id = t.getId();
		this.name = t.getName();
		this.active = t.isActive();
		this.createDate = t.getCreateDate();
		this.limit = t.getLimit();
	}

	public TeamDTO(Integer mode) {
		this.mode = mode;
		setCreateDate(DateUtils.cleanTime(new Date()));
		
	}

	public TeamDTO(Team t, Integer mode) {
		this(t);
		this.mode = mode;
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

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public Integer getWorkerCount() {
		return workerCount;
	}

	public void setWorkerCount(Integer workerCount) {
		this.workerCount = workerCount;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

}
