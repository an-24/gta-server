package biz.gelicon.gta.server.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import biz.gelicon.gta.server.dto.PersonDTO;

@Entity
@Table(name = "PERSON",
	   uniqueConstraints = @UniqueConstraint(columnNames = {"TEAM_ID", "PERSON_NIC"}))
@XmlRootElement
public class Person implements Serializable {
	private static final long serialVersionUID = -3868288445640424189L;
	private Integer id;
	private String nic;
	private String post;
	private Boolean active = false;
	private Integer limit;
	private Team team;
	private User user;
	private Post postDict;

	public Person() {
	}

	@Id
	@javax.persistence.SequenceGenerator(name="newRec", sequenceName="NEWRECORDID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newRec")
	@Column(name = "PERSON_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "POST_ID", nullable = true)	
	public Post getPostDict() {
		return postDict;
	}
	public void setPostDict(Post postdict) {
		this.postDict = postdict;
	}
	
	@Column(name = "PERSON_NIC", length = 100, nullable = false)
	public String getNic() {
		return nic;
	}
	public void setNic(String nic) {
		this.nic = nic;
	}
	
	@Column(name = "PERSON_POST", length = 30, nullable = false)
	public String getPost() {
		return post;
	}
	public void setPost(String post) {
		this.post = post;
	}
	
	@Column(name = "PERSON_ACTIVE", nullable = false)
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}

	@Column(name = "PERSON_LIMIT", nullable = true)
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "TEAM_ID", nullable = false)	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	
	
	@XmlTransient
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "USER_ID", nullable = false)	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	
	@Transient
	public boolean isManager() {
		return postDict==null?false:postDict.getId()==1;
	}

}
