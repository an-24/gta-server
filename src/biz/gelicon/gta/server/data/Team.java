package biz.gelicon.gta.server.data;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;


@Entity
@Table(name = "TEAM")
@XmlRootElement
public class Team  implements Serializable {
	private static final long serialVersionUID = -3155318672624022555L;
	private Integer id;
	private String name;
	private Boolean active;
	private Set<Person> persons = new HashSet<>();
	private Date createDate;
	private Integer limit;
	private Integer workedOfDay;
	private Integer workedOfWeek;
	private Integer workedOfMonth;
	private Integer workedOfBeginProject;
	
	@Id
	@javax.persistence.SequenceGenerator(name="newRec", sequenceName="NEWRECORDID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newRec")
	@Column(name = "TEAM_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "TEAM_NAME", length = 100, unique = true, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column(name = "TEAM_ACTIVE", nullable = false)
	public Boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Column(name = "TEAM_CREATEDATE", nullable = false)
	@Temporal(TemporalType.DATE)
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	@Column(name = "TEAM_LIMIT", nullable = false)
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	@Transient
	public Integer getWorkedOfDay() {
		return workedOfDay;
	}
	public void setWorkedOfDay(Integer workedOfDay) {
		this.workedOfDay = workedOfDay;
	}
	
	@Transient
	public Integer getWorkedOfWeek() {
		return workedOfWeek;
	}
	public void setWorkedOfWeek(Integer workedOfWeek) {
		this.workedOfWeek = workedOfWeek;
	}
	
	@Transient
	public Integer getWorkedOfMonth() {
		return workedOfMonth;
	}
	
	public void setWorkedOfMonth(Integer workedOfMonth) {
		this.workedOfMonth = workedOfMonth;
	}
	
	@Transient
	public Integer getWorkedOfBeginProject() {
		return workedOfBeginProject;
	}
	public void setWorkedOfBeginProject(Integer workedOfBeginProject) {
		this.workedOfBeginProject = workedOfBeginProject;
	}
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "team")
	public Set<Person> getPersons() {
		return persons;
	}
	public void setPersons(Set<Person> persons) {
		this.persons = persons;
	}
	
	@Transient
	public Person getManager() {
		Optional<Person> opt = getPersons().stream().filter(p->p.getPostDict()!=null?
															p.getPostDict().getId()==1:false).findFirst();
		return opt.isPresent()?opt.get():null;
	}
	
	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", active=" + active
				+ ", createDate=" + createDate + ", limit=" + limit
				+ ", workedOfDay=" + workedOfDay + ", workedOfWeek="
				+ workedOfWeek + ", workedOfMonth=" + workedOfMonth
				+ ", workedOfBeginProject=" + workedOfBeginProject + "]";
	}

}
