package biz.gelicon.gta.server.data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "WEEKLYSIGN")
public class WeeklySignature {

	private Integer id;
	private Date dtDay;
	private Date dtSignDay;
	private Team team;
	private User user;
	private byte[] signature;
	private byte[] data;
	private byte[] hash;
	private byte[] sertificate;
	

	@Id
	@javax.persistence.SequenceGenerator(name="newRec", sequenceName="NEWRECORDID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newRec")
	@Column(name = "WEEKLYSIGN_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "WEEKLYSIGN_DAY", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtDay() {
		return dtDay;
	}

	public void setDtDay(Date day) {
		this.dtDay = day;
	}

	@Column(name = "WEEKLYSIGN_SIGNDAY", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtSignDay() {
		return dtSignDay;
	}

	public void setDtSignDay(Date dtSignDay) {
		this.dtSignDay = dtSignDay;
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

	@Column(name = "WEEKLYSIGN_SIGNATURE")
	public byte[] getSignature() {
		return signature;
	}

	public void setSignature(byte[] signature) {
		this.signature = signature;
	}
	@Column(name = "WEEKLYSIGN_DATA")
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}
	@Column(name = "WEEKLYSIGN_HASH")
	public byte[] getHash() {
		return hash;
	}
	public void setHash(byte[] hash) {
		this.hash = hash;
	}
	@Column(name = "WEEKLYSIGN_CERT")
	public byte[] getSertificate() {
		return sertificate;
	}
	public void setSertificate(byte[] sertificate) {
		this.sertificate = sertificate;
	}

}
