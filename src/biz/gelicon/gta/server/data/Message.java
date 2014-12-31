package biz.gelicon.gta.server.data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@Table(name = "TIMING")
public class Message implements Serializable {
	private static final long serialVersionUID = 8206944234521512766L;
	private Integer id;
	private Integer key = 0;
	private Integer mouse = 0;
	private Integer mouseMove = 0;
	private Date dtBegin;
	private Date dtFinish;
	private String captureFileName;
	private String teamName;
	private Team team;
	private User user;
	private ScreenShot screenshot;

	public Message() {
	}
	
	public Message(int id, Date dtBegin, Date dtFinish, 
			int key, int mouse, int mouseMove, String captureFileName) {
		this.id = id;
		this.key = key;
		this.mouse = mouse;
		this.mouseMove = mouseMove;
		this.captureFileName = captureFileName;
		this.dtBegin = dtBegin;
		this.dtFinish = dtFinish;
	}

	@Id
	@javax.persistence.SequenceGenerator(name="newRec", sequenceName="NEWRECORDID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newRec")
	@Column(name = "TIMING_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	@Column(name = "TIMING_KEY", nullable = false)
	public Integer getKey() {
		return key;
	}

	public void setKey(Integer key) {
		this.key = key;
	}

	@Column(name = "TIMING_NOUSE", nullable = false)
	public Integer getMouse() {
		return mouse;
	}

	public void setMouse(Integer mouse) {
		this.mouse = mouse;
	}

	@Column(name = "TIMING_MOUSEMOVE", nullable = false)
	public Integer getMouseMove() {
		return mouseMove;
	}

	public void setMouseMove(Integer mouseMove) {
		this.mouseMove = mouseMove;
	}

	@Column(name = "TIMING_DTBEGIN", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtBegin() {
		return dtBegin;
	}

	public void setDtBegin(Date dtBegin) {
		this.dtBegin = dtBegin;
	}

	@Column(name = "TIMING_DTFINISH", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getDtFinish() {
		return dtFinish;
	}

	public void setDtFinish(Date dtFinish) {
		this.dtFinish = dtFinish;
	}

	@Transient
	public String getCaptureFileName() {
		return captureFileName;
	}

	public void setCaptureFileName(String captureFileName) {
		this.captureFileName = captureFileName;
	}

	@Transient
	public String getTeamName() {
		return teamName;
	}

	public void setTeamName(String team) {
		this.teamName = team;
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

	public void setId(Integer id) {
		this.id = id;
	}

	@XmlTransient
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "message", cascade = CascadeType.ALL)
	public ScreenShot getScreenshot() {
		return screenshot;
	}

	public void setScreenshot(ScreenShot screenshot) {
		this.screenshot = screenshot;
	}

	@Override
	public String toString() {
		return "Message [id=" + id + ", key=" + key + ", mouse=" + mouse
				+ ", mouseMove=" + mouseMove + ", dtBegin=" + dtBegin
				+ ", dtFinish=" + dtFinish + ", captureFileName="
				+ captureFileName + ", teamName=" + teamName + ", team=" + team
				+ ", user=" + user + "]";
	}
	



}
