package biz.gelicon.gta.server.dto;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import biz.gelicon.gta.server.GtaSystem;
import biz.gelicon.gta.server.data.Team;
import biz.gelicon.gta.server.utils.DateUtils;

public class TeamDTO {
	private Integer id;
	private String name;
	private Boolean active;
	
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date createDate;
	
	private Integer limit;
	private Integer workedOfDay;
	private Integer workedOfWeek;
	private Integer workedOfMonth;
	private Integer workedOfBeginProject;
	
	private Integer workerCount;
	private Integer mode;

	private List<PersonDTO> persons;
	
	private PersonDTO manager;
	
	private boolean managerCurrentUser;

	public TeamDTO() {
	}

	public TeamDTO(Team t) {
		this.id = t.getId();
		this.name = t.getName();
		this.active = t.isActive();
		this.createDate = t.getCreateDate();
		this.limit = t.getLimit();
		this.workedOfBeginProject = t.getWorkedOfBeginProject();
		this.workedOfDay = t.getWorkedOfDay();
		this.workedOfMonth = t.getWorkedOfMonth();
		this.workedOfWeek = t.getWorkedOfWeek();
		if(t.getManager()!=null) this.manager = new PersonDTO(t.getManager(),0);else
			this.manager = new PersonDTO();
	}

	public TeamDTO(Integer mode) {
		this.mode = mode;
		this.createDate = DateUtils.cleanTime(new Date());
		this.manager = new PersonDTO();
		this.active = mode == GtaSystem.MODE_ADD;
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

	public PersonDTO getManager() {
		return manager;
	}

	public void setManager(PersonDTO manager) {
		this.manager = manager;
	}

	public Integer getWorkedOfDay() {
		return workedOfDay;
	}

	public void setWorkedOfDay(Integer workedOfDay) {
		this.workedOfDay = workedOfDay;
	}

	public Integer getWorkedOfWeek() {
		return workedOfWeek;
	}

	public void setWorkedOfWeek(Integer workedOfWeek) {
		this.workedOfWeek = workedOfWeek;
	}

	public Integer getWorkedOfMonth() {
		return workedOfMonth;
	}

	public void setWorkedOfMonth(Integer workedOfMonth) {
		this.workedOfMonth = workedOfMonth;
	}

	public Integer getWorkedOfBeginProject() {
		return workedOfBeginProject;
	}

	public void setWorkedOfBeginProject(Integer workedOfBeginProject) {
		this.workedOfBeginProject = workedOfBeginProject;
	}

	public List<PersonDTO> getPersons() {
		return persons;
	}

	public void setPersons(List<PersonDTO> persons) {
		this.persons = persons;
	}

	public boolean isManagerCurrentUser() {
		return managerCurrentUser;
	}

	public void setManagerCurrentUser(boolean managerCurrentUser) {
		this.managerCurrentUser = managerCurrentUser;
	}

}
