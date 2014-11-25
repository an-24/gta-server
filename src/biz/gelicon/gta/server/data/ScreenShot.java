package biz.gelicon.gta.server.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "SCREENSHOT")
public class ScreenShot  implements Serializable {
	private static final long serialVersionUID = 4643983144935957597L;
	private Integer id;
	private Message message;
	private byte[] screenshot;
	
	public ScreenShot() {
	}

	public ScreenShot(Message message, byte[] screenshot) {
		this.message = message;
		this.screenshot = screenshot;
	}

	@Id
	@Column(name = "TIMING_ID", unique = true, nullable = false)
    @GeneratedValue(generator="gen")
    @GenericGenerator(name="gen", strategy="foreign", parameters={@Parameter(name="property", value="message")})
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@OneToOne(fetch = FetchType.LAZY)
	@PrimaryKeyJoinColumn
	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}
	
	@Column(name = "SCREENSHOT_PICT")
	public byte[] getScreenshot() {
		return screenshot;
	}
	public void setScreenshot(byte[] screenshot) {
		this.screenshot = screenshot;
	}


}
