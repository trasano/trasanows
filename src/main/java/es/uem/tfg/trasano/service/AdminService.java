/**
 * 
 */
package es.uem.tfg.trasano.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import es.uem.tfg.trasano.entity.CancelReason;
import es.uem.tfg.trasano.entity.CancelReasonRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulance;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceCanceled;
import es.uem.tfg.trasano.entity.ServiceCanceledRepository;

/**
 * @author rvallinot
 *
 */
@Service
@Repository
public class AdminService {
	/* Keys of Hashamp of services claimed  */
	private static Integer  NO_CLAIM = 0;
	private static Integer  ONE_CLAIM = 1;
	private static Integer  TWO_CLAIMS = 2;
	private static Integer  THREE_CLAIM = 3;
	private static Integer  FOUR_CLAIMS = 4;
	
	// Services
	private long numOfServices;
	private long numAmbulancesWorking;
	private long numOfPatients;
	private long numOfServicesClaimed;
	private long numServicesWaiting;
	private long numServicesInTransint;
	
	// Claim
	private Integer numClaimFlag = 0;
	private HashMap<Integer, Integer> highestNumOfClaim;
	private HashMap<Integer, Integer> hmServicesClaimed;
	
	//Cancel
	private long numOfCanceled;
	private float avgNumClaim;
	private HashMap<Integer, Integer> hmCancelReason;
	
	/**
	 * Initialize the attribute #hmServicesClaimed.
	 * <p>
	 * Usually one service is not claimed more than 3 times.
	 *
	 * @param 	void
	 * @return	void
	 */
	private void initHMServicesClaimed () {
		this.hmServicesClaimed = new HashMap<Integer, Integer>();
		hmServicesClaimed.put(NO_CLAIM, 0);
		hmServicesClaimed.put(ONE_CLAIM, 0);
		hmServicesClaimed.put(TWO_CLAIMS, 0);
		hmServicesClaimed.put(THREE_CLAIM, 0);
		hmServicesClaimed.put(FOUR_CLAIMS, 0);
	}
	
	/**
	 * Initialize the attribute #hmCancelReason from the repository given.
	 *
	 * @param 	cancelReasonRepository	Repository of services canceled.
	 * @return	void
	 */
	private void initHMServicesCanceled (CancelReasonRepository cancelReasonRepository) {
		this.hmCancelReason = new HashMap<Integer, Integer>();
		Iterable<CancelReason> iter = cancelReasonRepository.findAll();
		iter.forEach(item->{
			this.hmCancelReason.put((int)item.getId(), 0);
		});

	}
	
	/**
	 * Initialize all attributes of #AdminService class.
	 * @param 	void
	 * @return	void
	 */
	private void initAdminService() {
		// Service
		this.numOfServices = 0;
		this.numAmbulancesWorking = 0;
		this.numOfPatients = 0;
		this.numOfServicesClaimed = 0;
		this.numServicesWaiting = 0;
		this.numServicesInTransint = 0;
		
		// Claim
		this.numClaimFlag = 0;
		this.highestNumOfClaim = new HashMap<Integer, Integer>();
		this.initHMServicesClaimed();
	}
	
	/**
	 * Initialize attributes used for canceled services.
	 * 
	 * @param	cancelReasonRepository	Repository of canceled reason.
	 * @return	void
	 */
	private void initAdminService(CancelReasonRepository cancelReasonRepository) {
		//Cancel
		this.numOfCanceled = 0;
		this.avgNumClaim = 0;
		this.initHMServicesCanceled(cancelReasonRepository);	
	}

	/**
	 * Update Hashmap #hmServicesClaimed of services claimed.
	 * 
	 * @param	item	#ServiceAmbulance class.
	 * @return	void
	 */
	private void updateHMServicesClaimed (ServiceAmbulance item) {
		Integer counter = 0;
		if (item.getNumClaim() > 3) {
			counter = this.hmServicesClaimed.get(4);
			counter++;
			this.hmServicesClaimed.put(4, counter);
		} else {
			counter = this.hmServicesClaimed.get(item.getNumClaim());
			counter++;
			this.hmServicesClaimed.put((int) item.getNumClaim(), counter);
		}
	}
	
	/**
	 * Update Hashmap #hmCancelReason of canceled reason.
	 * 
	 * @param	item	#ServiceCanceled class.
	 * @return	void
	 */
	private void updateHMCanceledReason (ServiceCanceled item) {
		Integer counter = this.hmCancelReason.get(item.getReasonCode());
		counter++;
		this.hmCancelReason.put((int) item.getReasonCode(), counter);
	}
	
	/**
	 * Initialize all statistical counters: #numOfServices, #numOfServicesClaimed,
	 * #highestNumOfClaim, #numServicesWaiting, #numServicesInTransint, 
	 * #numAmbulancesWorking, #numAmbulancesWorking, #hmServicesClaimed.
	 * 
	 * @param	saRepository	#ServiceAmbulanceRepository class.
	 * @return	void
	 */
	private void setAllCounters(ServiceAmbulanceRepository saRepository) {
		this.numOfServices = saRepository.count();
		Set<Integer> setNumAmbulances = new HashSet<Integer>();
		Set<Integer> setNumPatients = new HashSet<Integer>();
		Iterable<ServiceAmbulance> iter = saRepository.findAll();
		iter.forEach(item->{
			// Get id of an ambulance on service
			if(item.getIdAmbulance() > 0 && !setNumAmbulances.contains(item.getIdAmbulance())){			
				setNumAmbulances.add((int) item.getIdAmbulance());				
			}
			// Get id of a patient using a service
			if(item.getIdPatient() > 0 && !setNumPatients.contains(item.getIdPatient())){			
				setNumPatients.add((int) item.getIdPatient());				
			}
			
			// Order services by number of claims
			this.updateHMServicesClaimed(item);
			
			// Get id of claimed services
			if(item.getNumClaim() > 0){	
				// Set number of services claimed
				this.numOfServicesClaimed++;
				
				// Set service id with more number of claims
				if (item.getNumClaim() >= this.numClaimFlag) {
					this.numClaimFlag = item.getNumClaim();
					this.highestNumOfClaim.put((int) item.getId(), item.getNumClaim());
				}
			}
			// Get number of services waiting for ambulance
			if(item.getIdserviceStatus() == 6 || item.getIdserviceStatus() == 1){			
				this.numServicesWaiting++;				
			}
			// Get number of services in transit
			if(item.getIdserviceStatus() == 2){			
				this.numServicesInTransint++;				
			}
		});
		this.numAmbulancesWorking = setNumAmbulances.size();
		this.numOfPatients = setNumPatients.size();
	}
	
	/**
	 * Initialize canceled statistical counters: #numOfCanceled, #avgNumClaim,
	 * #hmCancelReason.
	 * 
	 * @param	scanceledRepository	#ServiceCanceledRepository class.
	 * @return	void
	 */
	private void setCanceledCounters(ServiceCanceledRepository scanceledRepository) {
		this.numOfCanceled = scanceledRepository.count();
		Iterable<ServiceCanceled> iter = scanceledRepository.findAll();
		iter.forEach(item->{
			this.avgNumClaim= this.avgNumClaim + item.getNumClaim();
			this.updateHMCanceledReason(item);
		});
		this.avgNumClaim = this.avgNumClaim / this.numOfCanceled;
	}
	
	/**
	 * Constructor of AdminService class.
	 * <p> 
	 * Initialize: #numOfServices, #numAmbulancesWorking, #numOfPatients
	 * #numOfServicesClaimed, #numServicesWaiting, #numServicesInTransint.
	 * 
	 * @param	void
	 * @return	void
	 */
	public AdminService() {
		this.numOfServices = 0;
		this.numAmbulancesWorking = 0;
		this.numOfPatients = 0;
		this.numOfServicesClaimed = 0;
		this.numServicesWaiting = 0;
		this.numServicesInTransint = 0;
	}
	
	/**
	 * Constructor of AdminService class. Using methods:
	 * #initAdminService, #setAllCounters.
	 * <p> 
	 * Initialize: #numOfServices, #numAmbulancesWorking, #numOfPatients
	 * #numOfServicesClaimed, #numServicesWaiting, #numServicesInTransint.
	 * 
	 * @param	saRepository #ServiceAmbulanceRepository class.
	 * @return	void
	 */
	public AdminService(ServiceAmbulanceRepository saRepository) {
		this.initAdminService();
		this.setAllCounters(saRepository);
	}
	
	/**
	 * Constructor of AdminService class. Using methods:
	 * #initAdminService, #setCanceledCounters.
	 * <p> 
	 * Initialize: #numOfServices, #numAmbulancesWorking, #numOfPatients
	 * #numOfServicesClaimed, #numServicesWaiting, #numServicesInTransint,
	 * #numClaimFlag, #highestNumOfClaim, #hmCancelReason. 
	 * 
	 * @param	scanceledRepository #ServiceCanceledRepository class.
	 * @return	void
	 */
	public AdminService(ServiceCanceledRepository scanceledRepository) {
		this.initAdminService();
		this.setCanceledCounters(scanceledRepository);
	}
	
	/**
	 * Constructor of AdminService class. Using methods:
	 * #initAdminService, #setCanceledCounters.
	 * <p> 
	 * Initialize: #numOfServices, #numAmbulancesWorking, #numOfPatients
	 * #numOfServicesClaimed, #numServicesWaiting, #numServicesInTransint,
	 * #numClaimFlag, #highestNumOfClaim, #hmCancelReason. 
	 * 
	 * @param	scanceledRepository #ServiceCanceledRepository class.
	 * @param	cancelReasonRepository #CancelReasonRepository class.
	 * @return	void
	 */
	public AdminService(ServiceCanceledRepository scanceledRepository, 
			CancelReasonRepository cancelReasonRepository) {
		this.initAdminService(cancelReasonRepository);
		this.setCanceledCounters(scanceledRepository);
	}

	public long getNumOfServices() {
		return numOfServices;
	}

	public void setNumOfServices(long numOfServices) {
		this.numOfServices = numOfServices;
	}

	public long getNumAmbulancesWorking() {
		return numAmbulancesWorking;
	}

	public void setNumAmbulancesWorking(long numAmbulancesWorking) {
		this.numAmbulancesWorking = numAmbulancesWorking;
	}

	public long getNumOfPatients() {
		return numOfPatients;
	}

	public void setNumOfPatients(long numOfPatients) {
		this.numOfPatients = numOfPatients;
	}

	public long getNumOfServicesClaimed() {
		return numOfServicesClaimed;
	}

	public void setNumOfServicesClaimed(long numOfServicesClaimed) {
		this.numOfServicesClaimed = numOfServicesClaimed;
	}

	public long getNumServicesWaiting() {
		return numServicesWaiting;
	}

	public void setNumServicesWaiting(long numServicesWaiting) {
		this.numServicesWaiting = numServicesWaiting;
	}

	public long getNumServicesInTransint() {
		return numServicesInTransint;
	}

	public void setNumServicesInTransint(long numServicesInTransint) {
		this.numServicesInTransint = numServicesInTransint;
	}

	public HashMap<Integer, Integer> getHighestNumOfClaim() {
		return highestNumOfClaim;
	}

	public void setHighestNumOfClaim(HashMap<Integer, Integer> highestNumOfClaim) {
		this.highestNumOfClaim = highestNumOfClaim;
	}

	public HashMap<Integer, Integer> getHmServicesClaimed() {
		return hmServicesClaimed;
	}

	public void setHmServicesClaimed(HashMap<Integer, Integer> hmServicesClaimed) {
		this.hmServicesClaimed = hmServicesClaimed;
	}

	public Integer getNumClaimFlag() {
		return numClaimFlag;
	}

	public void setNumClaimFlag(Integer numClaimFlag) {
		this.numClaimFlag = numClaimFlag;
	}

	public long getNumOfCanceled() {
		return numOfCanceled;
	}

	public void setNumOfCanceled(long numOfCanceled) {
		this.numOfCanceled = numOfCanceled;
	}

	public float getAvgNumClaim() {
		return avgNumClaim;
	}

	public void setAvgNumClaim(float avgNumClaim) {
		this.avgNumClaim = avgNumClaim;
	}

	public HashMap<Integer, Integer> getHmCancelReason() {
		return hmCancelReason;
	}

	public void setHmCancelReason(HashMap<Integer, Integer> hmCancelReason) {
		this.hmCancelReason = hmCancelReason;
	}

}
