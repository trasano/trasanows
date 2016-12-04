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
@Table(name = "serviceambulanceHistoric")
public class ServiceAmbulanceHistoric {

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="serviceAmbulanceId")
	long serviceAmbulanceId;
	@Column(name="idPatient")
	long idPatient;
	@Column(name="idSite")
	long idSite;
	@Column(name="idAmbulance")
	long idAmbulance;
	@Column(name="idServiceStatus")
	long idserviceStatus;
	
	String details;
	@Column(name="Incidence")
	String incidence;
	@Column(name="numClaim")
	Integer numClaim;
	
	@Column(name="serviceTime")
	Timestamp serviceTime;
	@Column(name="serviceFinishedTime")
	Timestamp serviceFinishedTime;
	
	private void initFinishedTime () {
		this.serviceFinishedTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}
	
	public ServiceAmbulanceHistoric(ServiceAmbulance serviceAmbulance) {
		this.idAmbulance = serviceAmbulance.getIdAmbulance();
		this.idPatient = serviceAmbulance.getIdPatient();
		this.idserviceStatus = serviceAmbulance.getIdserviceStatus();
		this.idSite = serviceAmbulance.getIdSite();
		this.incidence = serviceAmbulance.getIncidence();
		this.numClaim = serviceAmbulance.getNumClaim();
		this.serviceAmbulanceId = serviceAmbulance.getId();
		this.serviceTime = serviceAmbulance.getServiceTime();
		this.initFinishedTime();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getServiceAmbulanceId() {
		return serviceAmbulanceId;
	}

	public void setServiceAmbulanceId(long serviceAmbulanceId) {
		this.serviceAmbulanceId = serviceAmbulanceId;
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

	public Timestamp getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Timestamp serviceTime) {
		this.serviceTime = serviceTime;
	}

	public Timestamp getServiceFinishedTime() {
		return serviceFinishedTime;
	}

	public void setServiceFinishedTime(Timestamp serviceFinishedTime) {
		this.serviceFinishedTime = serviceFinishedTime;
	}

	@Override
	public String toString() {
		return "serviceAmbulanceHistoric [id=" + id + ", serviceAmbulanceId=" + serviceAmbulanceId + ", idPatient="
				+ idPatient + ", idSite=" + idSite + ", idAmbulance=" + idAmbulance + ", idserviceStatus="
				+ idserviceStatus + ", details=" + details + ", incidence=" + incidence + ", numClaim=" + numClaim
				+ ", serviceTime=" + serviceTime + ", serviceFinishedTime=" + serviceFinishedTime + "]";
	}
	
	
}
