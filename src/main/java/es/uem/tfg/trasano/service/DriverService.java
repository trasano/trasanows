/**
 * 
 */
package es.uem.tfg.trasano.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import es.uem.tfg.trasano.entity.Ambulance;
import es.uem.tfg.trasano.entity.AmbulanceRepository;
import es.uem.tfg.trasano.entity.Driver;
import es.uem.tfg.trasano.entity.DriverRepository;
import es.uem.tfg.trasano.entity.Patient;
import es.uem.tfg.trasano.entity.PatientRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulance;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoric;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoricRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceStatusRepository;
import es.uem.tfg.trasano.entity.Site;
import es.uem.tfg.trasano.entity.SiteRepository;

/**
 * @author rvallinot
 *
 */
@Service
@Repository
public class DriverService {
	
	private long idDriver;
	private long idService;
	private long idSite;
	private long idAmbulance;
	private long trasanoCode;
	private long idServiceStatus;
	private long idPatient;
	private Integer numDriver;
	private Integer numAmbulance;
	private Integer numClaim;
	
	/**
	 * True if it is a number.
	 *
	 * @param	input String
	 * @return	void
	 * 
	 */
	private boolean isInteger(String input) {
	    try {
	        Integer.parseInt(input);
	        return true;
	    }
	    catch(Exception e) {
	        return false;
	    }
	}
	
	/**
	 * Set to integer #numDriver.
	 *
	 * @param	numDriver String
	 * @return	void
	 * 
	 */
	private void initNumDriver(String numDriver) {
		if (!numDriver.isEmpty() && this.isInteger(numDriver)) {
			this.numDriver = Integer.parseInt(numDriver);
		}
	}
	
	/**
	 * Set to integer #numAmbulance.
	 *
	 * @param	numAmbulance String
	 * @return	void
	 * 
	 */
	private void initNumAmbulance(String numAmbulance) {
		if (!numAmbulance.isEmpty() && this.isInteger(numAmbulance)) {
			this.numAmbulance = Integer.parseInt(numAmbulance);
		}
	}
	
	/**
	 * Set #idDriver if it exists in DriverRepository.
	 *
	 * @param	driverRepository DriverRepository
	 * @return	void
	 * 
	 */
	private void initIdDriver(DriverRepository driverRepository) {
		if (this.numDriver > 0) {
			Iterable<Driver> iter = driverRepository.findAll();
			iter.forEach(item->{						
				if(item.getNumdriver() == this.numDriver){			
					this.idDriver = item.getId();				
				}
			});
		}	
	}
	
	/**
	 * Set #idAmbulance if it exists in AmbulanceRepository
	 *
	 * @param	ambulanceRepository AmbulanceRepository
	 * @return	void
	 * 
	 */
	private void initIdAmbulance(AmbulanceRepository ambulanceRepository) {
		if (this.numAmbulance > 0) {
			Iterable<Ambulance> iter = ambulanceRepository.findAll();
			iter.forEach(item->{
				if(item.getNumAmbulance().equals(this.numAmbulance)){
					this.idAmbulance = item.getId();				
				}
			});
		}	
	}
	
	private void initDriverService() {
		this.idDriver = -1;
		this.idService = -1;
		this.idSite = -1;
		this.idAmbulance = -1;
		this.trasanoCode = -1;
		this.idServiceStatus = -1;
		this.idPatient = -1;
		this.numClaim = 0;
		this.numDriver = -1;
		this.numAmbulance = -1;
	}

	public DriverService() {
		super();
		this.initDriverService();
	}
	
	public DriverService(String numDriver, String numAmbulance, 
			DriverRepository driverRepository, AmbulanceRepository ambulanceRepository) {
		
		this.initDriverService();		
		this.initNumDriver(numDriver);
		this.initNumAmbulance(numAmbulance);
		this.initIdDriver(driverRepository);
		this.initIdAmbulance(ambulanceRepository);
	}

	public long getIdDriver() {
		return idDriver;
	}

	public void setIdDriver(long idDriver) {
		this.idDriver = idDriver;
	}
	
	public void setIdDriver(DriverRepository driverRepository) {

	}

	public long getIdService() {
		return idService;
	}

	public void setIdService(long idService) {
		this.idService = idService;
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

	public long getTrasanoCode() {
		return trasanoCode;
	}

	public void setTrasanoCode(long trasanoCode) {
		this.trasanoCode = trasanoCode;
	}

	public long getIdServiceStatus() {
		return idServiceStatus;
	}

	public void setIdServiceStatus(long idServiceStatus) {
		this.idServiceStatus = idServiceStatus;
	}

	public long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(long idPatient) {
		this.idPatient = idPatient;
	}

	public Integer getNumClaim() {
		return numClaim;
	}

	public void setNumClaim(Integer numClaim) {
		this.numClaim = numClaim;
	}

	public boolean isDriver() {
		return this.idDriver > 0;
	}

	public boolean isAmbulance() {
		return this.idAmbulance > 0;
	}

	public Driver getDriver(DriverRepository driverRepository) {
		return driverRepository.findOne(this.idDriver);
	}

	public Ambulance getAmbulance(AmbulanceRepository ambulanceRepository) {
		return ambulanceRepository.findOne(this.idAmbulance);
	}
	
	/**
	 * Set #idServiceStatus if the parameter given it is not null or empty.
	 *
	 * @param	idServiceStatus String
	 * @return	void
	 * 
	 */
	public void initIdServiceStatus (String idServiceStatus) {
		if (idServiceStatus != null && !idServiceStatus.isEmpty()) {
			this.idServiceStatus = Long.valueOf(idServiceStatus);
		}
	}
	
	/**
	 * Set #idService if the parameter given it is not null or empty.
	 *
	 * @param	idService String
	 * @return	void
	 * 
	 */
	public void initIdService (String idService) {
		if (idService != null && !idService.isEmpty()) {
			this.idService = Long.valueOf(idService);
		}
	}
	
	/**
	 * Return all services of #idAmbulance.
	 *
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	patientRepository PatientRepository
	 * @param	siteRepository SiteRepository
	 * @return	List of Responses
	 * 
	 */
	public List<Response> setServices(ServiceAmbulanceRepository saRepository, 
			PatientRepository patientRepository, 
			SiteRepository siteRepository) {
		
		List<Response> listOfResponses = new ArrayList<>();
		if (this.numAmbulance > 0) {
			Iterable<ServiceAmbulance> iter = saRepository.findAll();
			iter.forEach(item->{
				if(item.getIdAmbulance() == this.idAmbulance){
					
					Patient patient = new Patient();
					Site site = new Site();
					
					if (item.getIdPatient() > 0) {
						patient = patientRepository.findOne(item.getIdPatient());
					}
					if (item.getIdSite() > 0) {
						site = siteRepository.findOne(item.getIdSite());
					}
					
					Response response = new Response(item, site, patient);
					response.setNumAmbulance(this.numAmbulance);
					
					if (item.getServiceTime() != null) {
						response.setServiceTime(item.getServiceTime().getTime());
					}
					
					listOfResponses.add(response);
				}
			});
		}
		return listOfResponses;	
	}
	
	/**
	 * Return all services of #idAmbulance.
	 *
	 * @param	statusRepository ServiceStatusRepository
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	saHisRepostory ServiceAmbulanceHistoricRepository
	 * @return	List of Responses
	 * 
	 */
	public Response setServiceStatus (ServiceStatusRepository statusRepository, 
			ServiceAmbulanceRepository saRepository, ServiceAmbulanceHistoricRepository saHisRepostory) {
		
		Response response = new Response();
		if (this.isDriver()) {
			if (this.isAmbulance()) {
				if (this.idService > 0) {					
					if (statusRepository.exists(this.idServiceStatus)) {
						ServiceAmbulance serviceAmbulance = saRepository.findOne(Long.valueOf(this.idService));
						if (serviceAmbulance.getIdserviceStatus() != this.idServiceStatus) {
							serviceAmbulance.setIdserviceStatus(this.idServiceStatus);
							// Mejorar con HASHMAP
							if (this.idServiceStatus == 3) {
								ServiceAmbulanceHistoric saHistoric = new ServiceAmbulanceHistoric(serviceAmbulance);
								saHisRepostory.save(saHistoric);
								saRepository.delete(serviceAmbulance);
							} else {
								saRepository.save(serviceAmbulance);
							}
							
						} else {
							response.setError("Error! El estado introducido ya fue asignado con anterioridad");
						}
					} else {
						response.setError("Error! Estado indicado no válido");
					}
				} else {
					response.setError("Error! No se encuentra el servicio indicado");
				}
			} else {
				response.setError("El número de ambulancia no está dado de alta en el sistema");
			}
		} else {
			response.setError("El conductor no está dado de alta en el sistema");
		}
		return response;
	}

	@Override
	public String toString() {
		return "DriverService [idDriver=" + idDriver + ", idService=" + idService + ", idSite=" + idSite
				+ ", idAmbulance=" + idAmbulance + ", trasanoCode=" + trasanoCode + ", idServiceStatus="
				+ idServiceStatus + ", idPatient=" + idPatient + ", numDriver=" + numDriver + ", numAmbulance="
				+ numAmbulance + ", numClaim=" + numClaim + "]";
	}
}
