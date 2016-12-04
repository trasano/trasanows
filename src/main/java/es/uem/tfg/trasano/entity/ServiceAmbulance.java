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
@Table(name = "serviceAmbulance")
public class ServiceAmbulance {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;	
	@Column(name="idPatient")
	long idPatient;
	@Column(name="idSite")
	long idSite;
	@Column(name="idAmbulance")
	long idAmbulance;
	@Column(name="idServiceStatus")
	long idserviceStatus;
	
	@Column(name="serviceTime")
	Timestamp serviceTime;
	
	@Column(name="Comment")
	String comment;
	@Column(name="Details")
	String details;
	@Column(name="Incidence")
	String incidence;
	@Column(name="numClaim")
	Integer numClaim;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdPatient() {
		return idPatient;
	}
	public void setIdPatient(long idPatient) {
		this.idPatient = idPatient;
	}
	public long getIdSite() {
		return idSite;
	}
	public void setIdSite(long idSite) {
		this.idSite = idSite;
	}
	public long getIdAmbulance() {
		return idAmbulance;
	}
	public void setIdAmbulance(long idAmbulance) {
		this.idAmbulance = idAmbulance;
	}
	public Timestamp getServiceTime() {
		return serviceTime;
	}
	public void setServiceTime(Timestamp serviceTime) {
		this.serviceTime = serviceTime;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public String getIncidence() {
		return incidence;
	}
	public void setIncidence(String incidence) {
		this.incidence = incidence;
	}
	public Integer getNumClaim() {
		return numClaim;
	}
	public void setNumClaim(Integer numClaim) {
		this.numClaim = numClaim;
	}
	public long getIdserviceStatus() {
		return idserviceStatus;
	}
	public void setIdserviceStatus(long idserviceStatus) {
		this.idserviceStatus = idserviceStatus;
	}
	@Override
	public String toString() {
		return "ServiceAmbulance [id=" + id + ", idPaciente=" + idPatient + ", idSite=" + idSite + ", idAmbulance="
				+ idAmbulance + ", idserviceStatus=" + idserviceStatus + ", serviceTime=" + serviceTime + ", comment="
				+ comment + ", details=" + details + ", incidence=" + incidence + ", numClaim=" + numClaim + "]";
	}	
}
