/**
 * 
 */
package es.uem.tfg.trasano.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import es.uem.tfg.trasano.entity.Ambulance;
import es.uem.tfg.trasano.entity.AmbulanceRepository;
import es.uem.tfg.trasano.entity.Driver;
import es.uem.tfg.trasano.entity.DriverRepository;
import es.uem.tfg.trasano.entity.ErrorCode;
import es.uem.tfg.trasano.entity.ErrorCodeRepository;
import es.uem.tfg.trasano.entity.Patient;
import es.uem.tfg.trasano.entity.PatientRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulance;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoric;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoricRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceCanceled;
import es.uem.tfg.trasano.entity.ServiceCanceledRepository;
import es.uem.tfg.trasano.entity.ServiceClaim;
import es.uem.tfg.trasano.entity.ServiceClaimRepository;
import es.uem.tfg.trasano.entity.ServiceStatus;
import es.uem.tfg.trasano.entity.ServiceStatusRepository;
import es.uem.tfg.trasano.entity.Site;
import es.uem.tfg.trasano.entity.SiteRepository;

/**
 * @author rvallinot
 *
 */
@Service
@Repository
public class TrasanoService {

	private static final int ONE_HOUR = 3600000;
	
	private long idPatient;
	private long idService;
	private long idSite;
	private long idAmbulance;
	private long trasanoCode;
	private long idServiceStatus;
	private Integer tagCode;
	private Timestamp serviceTime;
	private Integer numClaim;
	private long lastClaimInMillis;
	private HashMap<String, Long> statusMap;
	
	/**
	 * Set #statusMap attribute.
	 *
	 * @param		statusRepository	ServiceStatusRepository
	 * @return     	void
	 * 
	 */
	private void setStatusMap (ServiceStatusRepository statusRepository) {
		this.statusMap = new HashMap<String, Long>();
		Iterable<ServiceStatus> iter = statusRepository.findAll();
		iter.forEach(item->{
			this.statusMap.put(item.getDescription(), item.getId());
		});
	}		

	/**
	 * Set #idPatient attribute by dni. 
	 * <p>
	 * If the patient does not exists the value is -1.
	 *
	 * @param	patientRepository	PatientRepository
	 * @param	dni	String
	 * @return	void
	 * 
	 */
	private void setPatientIdByDNI(String dni, PatientRepository patientRepository) {		
		this.idPatient = -1;
		Iterable<Patient> iterPatient = patientRepository.findAll();		
		iterPatient.forEach(item->{
			if(item.getDni().equals(dni)){
				this.idPatient = item.getId();
			}
		});				
	}
	
	/**
	 * Set #idSite, #idAmbulance, #serviceTime, #numClaim. #idServiceStatus attributes by a service
	 * ambulance given. 
	 * <p>
	 * Default values for this attributes 
	 * 	#idSite, #idAmbulance = 1
	 * 	#serviceTime = null
	 *  #numClaim = 0 
	 *  #idServiceStatus = 6
	 *
	 * @param	saRepository	ServiceAmbulanceRepository
	 * @return	void
	 * 
	 */
	private void setServiceId(ServiceAmbulanceRepository saRepository) {	
		this.idSite = -1;
		this.idAmbulance = -1;
		this.serviceTime = null;
		this.numClaim = 0;
		this.idServiceStatus = 6;
		Iterable<ServiceAmbulance> iter = saRepository.findAll();
		iter.forEach(item->{
			if(item.getIdPatient() == this.idPatient){
				this.idService = item.getId();
				this.idSite = item.getIdSite();
				this.idAmbulance = item.getIdAmbulance();
				this.serviceTime = item.getServiceTime();
				this.idServiceStatus = item.getIdserviceStatus();
				this.numClaim = item.getNumClaim();
			}
		});		
	}
	
	/**
	 * Set patient by dni, service by id and initialize the status map by dni calling 
	 * #setPatientIdByDNI, #setServiceId, #setStatusMap
	 *
	 * @param	dni	String
	 * @param 	patientRepository PatientRepository
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	statusRepository ServiceStatusRepository
	 * @return	void
	 * 
	 */
	private void initializePatientAndService(String dni, 
			PatientRepository patientRepository, 
			ServiceAmbulanceRepository saRepository, 
			ServiceStatusRepository statusRepository) {
		this.setPatientIdByDNI(dni, patientRepository);
		this.setServiceId(saRepository);
		this.setStatusMap(statusRepository);
	}

	public TrasanoService() {
		this.idPatient = -1;
		this.idSite = -1;
		this.idAmbulance = -1;
		this.trasanoCode = -1;
		this.tagCode = -1;
		this.serviceTime = null;
		this.numClaim = 0;
		// Realizarlo con HASHMAP
		this.idServiceStatus = 6;
	}
	
	public TrasanoService(String dni, 
			PatientRepository patientRepository) {
		super();
		this.idPatient = -1;
		this.idSite = -1;
		this.idAmbulance = -1;
		this.trasanoCode = -1;
		this.tagCode = -1;
		this.serviceTime = null;
		this.numClaim = 0;
		// Realizarlo con HASHMAP
		this.idServiceStatus = 6;
		// ----------------------
		this.setPatientIdByDNI(dni, patientRepository);
	}
	
	
	public TrasanoService(String dni, 
			PatientRepository patientRepository, 
			ServiceAmbulanceRepository saRepository,
			ServiceStatusRepository statusRepository) {
		super();
		this.trasanoCode = -1;
		this.tagCode = -1;
		this.idSite = -1;	
		this.serviceTime = null;
		this.numClaim = 0;
		// Realizarlo con HASHMAP
		this.idServiceStatus = 6;
		// ----------------------
		this.initializePatientAndService(dni, patientRepository, saRepository, statusRepository);
	}

	/**
	 * True if #idService > 0.
	 *
	 * @param	void
	 * @return	void
	 * 
	 */
	public boolean hasService() {
		boolean flag = false;
		if (this.idService > 0)
		{
			flag = true;
		}		
		return flag;
	}
	
	/**
	 * True if #idServiceStatus = 6.
	 *
	 * @param	void
	 * @return	void
	 * 
	 */
	public boolean isServiceOrdered() {
		boolean flag = true;
		if (this.idServiceStatus == 6) {
			flag = false;
		}
		return flag;
	}
	
	/**
	 * True if #idPatient >= 0.
	 *
	 * @param	void
	 * @return	void
	 * 
	 */
	public boolean isPatient() {
		boolean flag = false;
		if (this.idPatient >= 0) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * True if #numss is in the repository given.
	 *
	 * @param	numss String
	 * @param	patientRepository PatientRepository
	 * @return	void
	 * 
	 */
	public boolean allowUser(String numss, PatientRepository patientRepository) {
		boolean flag = false;
		if (!numss.isEmpty()) {
			Patient patient = patientRepository.findOne(this.idPatient);
			if (patient.getNumSegSoc() == Long.parseLong(numss.trim())) {
				flag = true;
			}
		}

		return flag;
	}

	/**
	 * Set #idSite attribute by tagCode.
	 *
	 * @param	siteRepository SiteRepository
	 * @return	void
	 * 
	 */
	public void setSiteByTagCode(SiteRepository siteRepository) {
		Iterable<Site> iter = siteRepository.findAll();
		iter.forEach(item->{						
			if(item.getTagCode() == this.tagCode){			
				this.idSite = item.getId();				
			}
		});		
	}

	/**
	 * Claim a service ambulance.
	 * <p>
	 * Reason can be given.
	 *
	 * @param	reason String
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	sclaimRepository ServiceClaimRepository
	 * @return	void
	 * 
	 */
	public void claimService(String reason,   
		ServiceAmbulanceRepository saRepository, 
		ServiceClaimRepository sclaimRepository) {
		
		ServiceAmbulance service = new ServiceAmbulance();
		service = saRepository.findOne(this.idService);
		service.setNumClaim(service.getNumClaim() + 1);
		this.numClaim = service.getNumClaim();
		
		ServiceClaim claim = new ServiceClaim(service.getId(), reason, new Timestamp(Calendar.getInstance().getTimeInMillis()));
		sclaimRepository.save(claim);
	}

	/**
	 * Cancel a service ambulance with a code of cancellation.
	 * <p>
	 * Reason can be given.
	 *
	 * @param	code String
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	saHisRepository ServiceAmbulanceHistoricRepository
	 * @param	scanceledRepository ServiceCanceledRepository
	 * @return	void
	 * 
	 */
	public void cancelService(String code, String reason, 
			ServiceAmbulanceRepository saRepository,
			ServiceAmbulanceHistoricRepository saHisRepository,
			ServiceCanceledRepository scanceledRepository) {
		
		ServiceAmbulance serviceAmbulance = new ServiceAmbulance();
		serviceAmbulance = saRepository.findOne(this.idService);		
		ServiceCanceled serviceCanceled = new ServiceCanceled(serviceAmbulance, code, reason);
		
		// Set new status = CANCELED
		serviceCanceled.setIdserviceStatus(this.statusMap.get("Canceled"));		
		
		scanceledRepository.save(serviceCanceled);
		
		ServiceAmbulanceHistoric saHistoric = new ServiceAmbulanceHistoric(serviceAmbulance);
		saHisRepository.save(saHistoric);
		
		saRepository.delete(this.idService);
	}

	/**
	 * Close a service ambulance.
	 *
	 * @param	code String
	 * @param	saRepository ServiceAmbulanceRepository
	 * @param	patientRepository PatientRepository
	 * @return	void
	 * 
	 */
	public void closeService(ServiceAmbulanceRepository saRepository, PatientRepository patientRepository) {
		ServiceAmbulance service = new ServiceAmbulance();
		service = saRepository.findOne(this.idService);
		
		Patient patient = new Patient();
		patient = patientRepository.findOne(this.idPatient);
		
		// Set new status = WAITING TAKE IN
		service.setIdserviceStatus(this.statusMap.get("WaitingTakeIn"));
		service.setServiceTime(new Timestamp(Calendar.getInstance().getTimeInMillis()));
		if (patient.getComment() != null) {
			service.setComment(patient.getComment());
		}
		if (patient.getDetails() != null){
			service.setDetails(patient.getDetails());
		}
		service.setIdPatient(this.idPatient);
		service.setNumClaim(0);
		service.setIdSite(this.idSite);
		
		saRepository.save(service);
	}

	public long getIdPatient() {
		return idPatient;
	}

	public void setIdPatient(long idPatient) {
		this.idPatient = idPatient;
	}

	public long getIdService() {
		return idService;
	}

	public void setIdService(long idService) {
		this.idService = idService;
	}
	
	public long getTrasanoCode() {
		return trasanoCode;
	}

	public void setTrasanoCode(long trasanoCode) {
		this.trasanoCode = trasanoCode;
	}

	public Integer getTagCode() {
		return tagCode;
	}

	public void setTagCode(Integer tagCode) {
		this.tagCode = tagCode;
	}

	public Timestamp getServiceTime() {
		return serviceTime;
	}

	public void setServiceTime(Timestamp serviceTime) {
		this.serviceTime = serviceTime;
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
	
	public Integer getNumClaim() {
		return numClaim;
	}

	public void setNumClaim(Integer numClaim) {
		this.numClaim = numClaim;
	}

	public long getLastClaimInMillis() {
		return lastClaimInMillis;
	}

	public void setLastClaimInMillis(long lastClaimInMillis) {
		this.lastClaimInMillis = lastClaimInMillis;
	}
	
	public void setLastClaimInMillis(ServiceClaimRepository sclaimRepository) {
		this.lastClaimInMillis = 0;
		List<Timestamp> listClaimTime = new ArrayList<Timestamp>();
		Iterable<ServiceClaim> iter = sclaimRepository.findAll();
		iter.forEach(item->{						
			if(item.getIdService() == this.idService){			
				listClaimTime.add(item.getTime());				
			}
		});	
		
		if (!listClaimTime.isEmpty()) {
			Collections.sort((List<Timestamp>) listClaimTime);
			Collections.reverse((List<Timestamp>) listClaimTime);
			
			// Get first element sorted into descending order
			this.lastClaimInMillis = listClaimTime.get(0).getTime();
		}		
	}

	public Driver getDriver(AmbulanceRepository ambulanceRepository, DriverRepository driverRepository) {
		Driver driver = new Driver();
		if (this.idAmbulance > 0) {
			Ambulance ambulance = new Ambulance();
			ambulance = ambulanceRepository.findOne(this.idAmbulance);	
			driver = driverRepository.findOne(ambulance.getIdDriver());
		}
		return driver;
	}
	
	public Site getSite(SiteRepository siteRepository) {
		Site site = new Site();
		site = siteRepository.findOne(this.idSite);
		return site;
	}
	
	public Ambulance getAmbulance(AmbulanceRepository ambulanceRepository) {
		Ambulance ambulance = new Ambulance();
		if (this.idAmbulance > 0) {
			ambulance = ambulanceRepository.findOne(this.idAmbulance);
		}
		return ambulance;
	}
	
	public Patient getPatient(PatientRepository patientRepository) {
		Patient patient = new Patient();
		patient = patientRepository.findOne(this.idPatient);
		return patient;
	}
	
	/**
	 * True if #idCodeError exists in #ErrorCodeRepository.
	 *
	 * @param	long idCodeError
	 * @param	errorCodeRepository ErrorCodeRepository
	 * @return	void
	 * 
	 */
	public boolean isCodeError (long idCodeError, ErrorCodeRepository errorCodeRepository) {
		boolean flag = false;
		ErrorCode errorCode = new ErrorCode();
		if (idCodeError > 0) {
			errorCode = errorCodeRepository.findOne(idCodeError);
			if (errorCode != null && errorCode.getId() > 0) {
				flag = true;
			}
		}
		return flag;
	}
	
	/**
	 * True if #lastClaimInMillis was ONE_HOUR before.
	 *
	 * @param	sclaimRepository ServiceClaimRepository
	 * @return	void
	 * 
	 */
	public boolean isLastClaimOut (ServiceClaimRepository sclaimRepository) {
		boolean flag = true;
		this.setLastClaimInMillis(sclaimRepository);
		if (this.lastClaimInMillis != 0) {
			Timestamp timeOfLastClaim = new Timestamp(this.lastClaimInMillis + ONE_HOUR);
			Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
			flag = currentTime.after(timeOfLastClaim);
		}
		
		return flag;
	}
	
	/**
	 * True if current time is ONE_HOUR after the service was requested and #lastClaimInMillis is true.
	 *
	 * @param	sclaimRepository ServiceClaimRepository
	 * @return	void
	 * 
	 */
	public boolean canClaim (ServiceClaimRepository sclaimRepository) {
		boolean flag = false;
		Timestamp currentTime = new Timestamp(Calendar.getInstance().getTimeInMillis());
		Timestamp timeOfRequestService = new Timestamp(this.serviceTime.getTime() + ONE_HOUR);
		
		if (currentTime.after(timeOfRequestService)) {
			flag = true && isLastClaimOut(sclaimRepository);
		}
		
		return flag;
	}

	@Override
	public String toString() {
		return "TrasanoService [idPatient=" + idPatient + ", idService=" + idService + ", idSite=" + idSite
				+ ", idAmbulance=" + idAmbulance + ", trasanoCode=" + trasanoCode + ", idServiceStatus="
				+ idServiceStatus + ", tagCode=" + tagCode + ", numClaim=" + numClaim + ", statusMap=" + statusMap
				+ "]";
	}
}
