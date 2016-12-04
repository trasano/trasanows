/**
 * 
 */
package es.uem.tfg.trasano.entity;

import java.sql.Timestamp;

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
@Table(name = "serviceClaim")
public class ServiceClaim {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;	
	@Column(name="idService")
	long idService;
	@Column(name="Detail")
	String detail;	
	
	@Column(name="Time")	
	Timestamp time;
	
	public ServiceClaim() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ServiceClaim(long idService, String detail, Timestamp time) {
		super();
		this.idService = idService;
		this.detail = detail;
		this.time = time;
	}
	public long getId() {
		return id;
	}	
	public void setId(long id) {
		this.id = id;
	}
	public long getIdService() {
		return idService;
	}
	public void setIdService(long idService) {
		this.idService = idService;
	}
	public Timestamp getTime() {
		return time;
	}
	public void setTime(Timestamp time) {
		this.time = time;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	@Override
	public String toString() {
		return "ServiceClaim [id=" + id + ", idService=" + idService + ", detail=" + detail + ", time=" + time + "]";
	}	
}
