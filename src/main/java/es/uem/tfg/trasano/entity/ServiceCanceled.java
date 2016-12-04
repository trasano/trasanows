/**
 * 
 */
package es.uem.tfg.trasano.entity;

import java.sql.Timestamp;
import java.util.Calendar;

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
@Table(name = "serviceCanceled")
public class ServiceCanceled {
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
	@Column(name="timeCanceled")	
	Timestamp timeCanceled;
	
	@Column(name="Comment")
	String comment;
	@Column(name="Details")
	String details;
	@Column(name="Incidence")
	String incidence;
	@Column(name="numClaim")
	Integer numClaim;
	@Column(name="Reason")
	String reason;
	@Column(name="reasonCode")
	Integer reasonCode;
	
	public ServiceCanceled() {
		super();
	}
	
	public ServiceCanceled(ServiceAmbulance serviceAmbulance, String code, String reason) {
		this.idAmbulance = serviceAmbulance.getIdAmbulance();
		this.idPatient = serviceAmbulance.getIdPatient();		
		this.idSite = serviceAmbulance.getIdSite();
		this.idserviceStatus = serviceAmbulance.getIdserviceStatus();
		this.comment = serviceAmbulance.getComment();
		this.details = serviceAmbulance.getDetails();
		this.incidence = serviceAmbulance.getIncidence();
		this.numClaim = serviceAmbulance.getNumClaim();
		this.reason = reason;
		this.reasonCode = Integer.decode(code);
		this.serviceTime = serviceAmbulance.getServiceTime();
		this.timeCanceled = new Timestamp(Calendar.getInstance().getTimeInMillis());
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
	public long getIdserviceStatus() {
		return idserviceStatus;
	}
	public void setIdserviceStatus(long idserviceStatus) {
		this.idserviceStatus = idserviceStatus;
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
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public Timestamp getTimeCanceled() {
		return timeCanceled;
	}
	public void setTimeCanceled(Timestamp timeCanceled) {
		this.timeCanceled = timeCanceled;
	}
	public Integer getReasonCode() {
		return reasonCode;
	}
	public void setReasonCode(Integer reasonCode) {
		this.reasonCode = reasonCode;
	}
}
