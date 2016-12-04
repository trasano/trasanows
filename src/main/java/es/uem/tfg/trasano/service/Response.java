/**
 * 
 */
package es.uem.tfg.trasano.service;

import java.sql.Timestamp;
import java.util.HashMap;

import es.uem.tfg.trasano.entity.Ambulance;
import es.uem.tfg.trasano.entity.Driver;
import es.uem.tfg.trasano.entity.Patient;
import es.uem.tfg.trasano.entity.ServiceAmbulance;
import es.uem.tfg.trasano.entity.Site;

/**
 * @author rvallinot
 *
 */
public class Response {

	// App, Little and Driver
	private String idService;
	private Integer numAmbulance;
	private Integer numClaim;
	private String companyAmbulance;
	private String headquarters;
	private String department;
	private String locality;
	private String serviceTime;
	private String name;
	private String surname;
	private String patientHome;
	private String error;
	private String lastClaimInMillis;
	private String telephone1;
	private String telephone2;
	private String comment;
	private String details;
	private String incidence;
	private String idServiceStatus;
	private String latitude;
	private String longitude;
	// --------------------------------------------------------
	
	// Admin
	private String numOfServices;
	private String numAmbulancesWorking;
	private String numOfPatients;
	private String numOfServicesClaimed;
	private String numServicesWaiting;
	private String numServicesInTransit;
	
	private HashMap<Integer, Integer> servicesMoreClaimed;
	private HashMap<Integer, Integer> servicesClaimedSorted;
	
	private String numOfCanceled;
	private String avgNumClaim;
	private HashMap<Integer, Integer> servicesCanceledSorted;
	// --------------------------------------------------------	

	/**
	 * Transform to string #serviceTime.
	 * <p>
	 * Avoid Null Pointer Exception.
	 *
	 * @param	serviceTime Timestamp
	 * @return	void
	 * 
	 */
	private void initServiceTime(Timestamp serviceTime) {
		if (serviceTime == null) {
			this.serviceTime = "";
		} else {
			this.serviceTime = String.valueOf(serviceTime.getTime());
		}		
	}
	
	/**
	 * Transform to string #lastClaimInMillis.
	 * <p>
	 * Avoid Null Pointer Exception.
	 *
	 * @param	lastClaimInMillis long
	 * @return	void
	 * 
	 */
	private void initLastClaim(long lastClaimInMillis) {
		if (lastClaimInMillis == 0) {
			this.lastClaimInMillis = "";
		} else {
			this.lastClaimInMillis = String.valueOf(lastClaimInMillis);
		}		
	}
	
	private void initResponse() {
		this.idService = "";
		this.headquarters = "";
		this.companyAmbulance = "";
		this.department = "";
		this.locality = "";
		this.numAmbulance = -1;
		this.numClaim = -1;
		this.name = "";
		this.surname = "";
		this.patientHome = "";
		this.error = "";
		this.telephone1 = "";
		this.telephone2 = "";
		this.comment = "";
		this.details = "";
		this.incidence = "";
		this.latitude = "";
		this.longitude = "";
		this.initServiceTime(null);
		this.initLastClaim(0);
	}
	
	/**
	 * 
	 */
	public Response() {
		super();
		this.initResponse();
	}
	
	public Response(Ambulance ambulance, Site site, Driver driver, Patient patient, Timestamp serviceTime) {
		this.initResponse();
		this.initServiceTime(serviceTime);
		this.numAmbulance = ambulance.getNumAmbulance();
		this.companyAmbulance = ambulance.getCompany();
		this.headquarters = site.getHeadquarters();
		this.department = site.getDepartment();
		this.locality = site.getLocality();
		this.name = driver.getName();
		this.surname = driver.getSurname();
		this.patientHome = patient.getPatienHome();
		this.latitude = String.valueOf(patient.getLatitude());
		this.longitude = String.valueOf(patient.getLongitude());
	}
	
	public Response(Ambulance ambulance, Site site, Driver driver, Timestamp serviceTime) {	
		this.initResponse();
		this.initServiceTime(serviceTime);
		this.numAmbulance = ambulance.getNumAmbulance();
		this.companyAmbulance = ambulance.getCompany();
		this.headquarters = site.getHeadquarters();
		this.department = site.getDepartment();
		this.locality = site.getLocality();
		this.name = driver.getName();
		this.surname = driver.getSurname();
	}
	
	public Response(ServiceAmbulance serviceAmbulance, Site site, Patient patient) {	
		this.initResponse();
		this.headquarters = site.getHeadquarters();
		this.department = site.getDepartment();
		this.locality = site.getLocality();
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.patientHome = patient.getPatienHome();
		this.latitude = String.valueOf(patient.getLatitude());
		this.longitude = String.valueOf(patient.getLongitude());
		
		// Avoiding null pointer exception
		if (patient.getTelephone1() != null) {
			this.telephone1 = patient.getTelephone1();
		}
		
		if (patient.getTelephone2() != null) {
			this.telephone2 = patient.getTelephone2();
		}
		
		if (patient.getComment() != null) {
			this.comment = patient.getComment();
		}
		
		if (patient.getDetails() != null) {
			this.details = patient.getDetails();
		}
		
		if (serviceAmbulance.getNumClaim() != null) {
			this.numClaim = serviceAmbulance.getNumClaim();
		}
		
		if (serviceAmbulance.getIncidence() != null) {
			this.incidence = serviceAmbulance.getIncidence();
		}
		
		if (serviceAmbulance.getId() > 0) {
			this.idService = String.valueOf(serviceAmbulance.getId());
		}
		// ---------------------------------
	}

	public Response(TrasanoService trService, Site site) {	
		this.initResponse();
		this.initLastClaim(trService.getLastClaimInMillis());
		this.headquarters = site.getHeadquarters();
		this.department = site.getDepartment();
		this.locality = site.getLocality();
		this.numClaim = trService.getNumClaim();
		
		// Avoiding null pointer exception
		if (trService.getIdService() > 0) {
			this.idService = String.valueOf(trService.getIdService());
		}
		// ---------------------------------
	}
	
	public Response(Patient patient) {	
		this.initResponse();
		this.name = patient.getName();
		this.surname = patient.getSurname();
		this.patientHome = patient.getPatienHome();
		this.latitude = String.valueOf(patient.getLatitude());
		this.longitude = String.valueOf(patient.getLongitude());
	}

	public Response(Driver driver) {
		this.initResponse();
		this.name = driver.getName();
		this.surname = driver.getSurname();
	}

	public Response(AdminService adminService) {
		this.error = "";
		
		// Service
		this.numAmbulancesWorking = String.valueOf(adminService.getNumAmbulancesWorking()); 
		this.numOfPatients = String.valueOf(adminService.getNumOfPatients());
		this.numOfServices = String.valueOf(adminService.getNumOfServices());
		this.numOfServicesClaimed = String.valueOf(adminService.getNumOfServicesClaimed());
		this.numServicesInTransit = String.valueOf(adminService.getNumServicesInTransint());
		this.numServicesWaiting = String.valueOf(adminService.getNumServicesWaiting());
		
		// Clam
		this.servicesClaimedSorted = adminService.getHmServicesClaimed();
		this.servicesMoreClaimed = adminService.getHighestNumOfClaim();
		
		// Cancel
		this.numOfCanceled = String.valueOf(adminService.getNumOfCanceled());
		this.avgNumClaim = String.format("%.02f", adminService.getAvgNumClaim());
		this.servicesCanceledSorted = adminService.getHmCancelReason();
	}

	public String getIdService() {
		return idService;
	}

	public void setIdService(String idService) {
		this.idService = idService;
	}

	public Integer getNumAmbulance() {
		return numAmbulance;
	}

	public void setNumAmbulance(Integer numAmbulance) {
		this.numAmbulance = numAmbulance;
	}

	public String getHeadquarters() {
		return headquarters;
	}

	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Timestamp serviceTime) {
		if (serviceTime != null) {
			this.serviceTime = String.valueOf(serviceTime.getTime());
		}
	}
	
	public void setServiceTime(long serviceTime) {
		if (serviceTime > 0) {
			this.serviceTime = String.valueOf(serviceTime);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}
	
	public Integer getNumClaim() {
		return numClaim;
	}

	public void setNumClaim(Integer numClaim) {
		this.numClaim = numClaim;
	}
	
	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getCompanyAmbulance() {
		return companyAmbulance;
	}

	public void setCompanyAmbulance(String companyAmbulance) {
		this.companyAmbulance = companyAmbulance;
	}
	
	public String getPatientHome() {
		return patientHome;
	}

	public void setPatientHome(String patientHome) {
		this.patientHome = patientHome;
	}

	public String getLastClaimInMillis() {
		return lastClaimInMillis;
	}

	public void setLastClaimInMillis(String lastClaimInMillis) {
		this.lastClaimInMillis = lastClaimInMillis;
	}

	public String getTelephone1() {
		return telephone1;
	}

	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}

	public String getTelephone2() {
		return telephone2;
	}

	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
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

	public void setServiceTime(String serviceTime) {
		this.serviceTime = serviceTime;
	}

	public String getIdServiceStatus() {
		return idServiceStatus;
	}

	public void setIdServiceStatus(String idServiceStatus) {
		this.idServiceStatus = idServiceStatus;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getNumOfServices() {
		return numOfServices;
	}

	public void setNumOfServices(String numOfServices) {
		this.numOfServices = numOfServices;
	}

	public String getNumAmbulancesWorking() {
		return numAmbulancesWorking;
	}

	public void setNumAmbulancesWorking(String numAmbulancesWorking) {
		this.numAmbulancesWorking = numAmbulancesWorking;
	}

	public String getNumOfPatients() {
		return numOfPatients;
	}

	public void setNumOfPatients(String numOfPatients) {
		this.numOfPatients = numOfPatients;
	}

	public String getNumOfServicesClaimed() {
		return numOfServicesClaimed;
	}

	public void setNumOfServicesClaimed(String numOfServicesClaimed) {
		this.numOfServicesClaimed = numOfServicesClaimed;
	}

	public String getNumServicesWaiting() {
		return numServicesWaiting;
	}

	public void setNumServicesWaiting(String numServicesWaiting) {
		this.numServicesWaiting = numServicesWaiting;
	}

	public String getNumServicesInTransit() {
		return numServicesInTransit;
	}

	public void setNumServicesInTransit(String numServicesInTransit) {
		this.numServicesInTransit = numServicesInTransit;
	}

	public HashMap<Integer, Integer> getServicesMoreClaimed() {
		return servicesMoreClaimed;
	}

	public void setServicesMoreClaimed(HashMap<Integer, Integer> servicesMoreClaimed) {
		this.servicesMoreClaimed = servicesMoreClaimed;
	}

	public HashMap<Integer, Integer> getServicesClaimedSorted() {
		return servicesClaimedSorted;
	}

	public void setServicesClaimedSorted(HashMap<Integer, Integer> servicesClaimedSorted) {
		this.servicesClaimedSorted = servicesClaimedSorted;
	}

	public String getNumOfCanceled() {
		return numOfCanceled;
	}

	public void setNumOfCanceled(String numOfCanceled) {
		this.numOfCanceled = numOfCanceled;
	}

	public String getAvgNumClaim() {
		return avgNumClaim;
	}

	public void setAvgNumClaim(String avgNumClaim) {
		this.avgNumClaim = avgNumClaim;
	}

	public HashMap<Integer, Integer> getServicesCanceledSorted() {
		return servicesCanceledSorted;
	}

	public void setServicesCanceledSorted(HashMap<Integer, Integer> servicesCanceledSorted) {
		this.servicesCanceledSorted = servicesCanceledSorted;
	}

	
}
