package biz.gelicon.gta.server.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "PROGUSER")
public class User implements Serializable {
	private static final long serialVersionUID = 8130815778826505788L;
	private Integer id;
	private String name;
	private String password;
	private String email;
	
	@Id
	@javax.persistence.SequenceGenerator(name="newRec", sequenceName="NEWRECORDID")	
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "newRec")
	@Column(name = "PROGUSER_ID", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	
	@Column(name = "PROGUSER_NAME", length = 100, unique = true, nullable = false)
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "PROGUSER_PASSWORD", length = 40, nullable = false)
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "PROGUSER_EMAIL", length = 200, nullable = true)
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Transient
	public boolean isSysAdmin() {
		return id==1;
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + "]";
	}

}
