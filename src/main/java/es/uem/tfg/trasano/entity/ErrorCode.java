package es.uem.tfg.trasano.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author rvallinot
 *
 */
@Entity
@Table(name = "errorcode")
public class ErrorCode {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="codeError")
	long codeError;
	@Column(name="Description")
	String description;
	
	public ErrorCode() {
		super();
		this.id = -1;
		this.codeError = -1;
		this.description = "";
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getCodeError() {
		return codeError;
	}
	public void setCodeError(long codeError) {
		this.codeError = codeError;
	}
	@Override
	public String toString() {
		return "ErrorCode [id=" + id + ", codeError=" + codeError + ", description=" + description + "]";
	}

}