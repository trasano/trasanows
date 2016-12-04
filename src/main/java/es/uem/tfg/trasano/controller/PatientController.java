/**
 * 
 */
package es.uem.tfg.trasano.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uem.tfg.trasano.entity.Ambulance;
import es.uem.tfg.trasano.entity.AmbulanceRepository;
import es.uem.tfg.trasano.entity.Driver;
import es.uem.tfg.trasano.entity.DriverRepository;
import es.uem.tfg.trasano.entity.ErrorCodeRepository;
import es.uem.tfg.trasano.entity.Patient;
import es.uem.tfg.trasano.entity.PatientRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoricRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceCanceledRepository;
import es.uem.tfg.trasano.entity.ServiceClaimRepository;
import es.uem.tfg.trasano.entity.ServiceStatusRepository;
import es.uem.tfg.trasano.entity.Site;
import es.uem.tfg.trasano.entity.SiteRepository;
import es.uem.tfg.trasano.service.Response;
import es.uem.tfg.trasano.service.TrasanoService;

/**
 * @author rvallinot
 *
 */
@Controller
@RestController
@RequestMapping("/patient")
public class PatientController {
	
	private static final Logger logger = Logger.getLogger(PatientController.class.getName());
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ServiceAmbulanceRepository saRepository;
	@Autowired
	ServiceCanceledRepository scanceledRepository;
	@Autowired
	SiteRepository siteRepository;
	@Autowired
	AmbulanceRepository ambulanceRepository;
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	ServiceStatusRepository statusRepository;	
	@Autowired
	ServiceClaimRepository sclaimRepository;
	@Autowired
	ErrorCodeRepository errorCodeRepository;
	@Autowired
	ServiceAmbulanceHistoricRepository saHisRepository;

	/**
	 * Returns a JSON with the name, surname and address of a patient if the patient
	 * exists.  
	 *
	 * @param		dni	String
	 * @param		numss	String
	 * @return     	response	JSON with error, name, surname 
	 * 							address
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam(value = "dni", defaultValue = "") String dni,
			@RequestParam(value = "numss", defaultValue = "") String numss) {
		
		Response response = new Response();
		try {
			logger.info("LOGIN.Request: dni = " + dni + ", numss = " + numss);
			response.setError("Consulte en el mostrador");

			TrasanoService trService = new TrasanoService(dni, patientRepository);
			logger.info("LOGIN.TrasanoService = " + trService.toString());

			if (trService.isPatient()) {
				if (trService.allowUser(numss, patientRepository)) {
					Patient patient = trService.getPatient(patientRepository);
					response = new Response(patient);
				} else {
					response.setError("Número de la seguridad social no válido");
					logger.info("LOGIN.error.SegSocial = " + response.toString());
				}
			} else {
				response.setError("El usuario no esta dado de alta en el sistema");
				logger.info("LOGIN.TrasanoService.error.User = " + response.toString());
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.PatientController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("LOGIN.TrasanoService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}	
	
	/**
	 * Return JSON for an service ambulance closed with the dni and tagcode given.  
	 *
	 * @param		dni	String
	 * @param		tagcode	String
	 * @return     	response	JSON with error, nameDriver, surnameDriver, numAmbulance, 
	 * 							serviceTime.
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/close" }, method = RequestMethod.POST)
	@ResponseBody
	public String close(@RequestParam(value = "dni", defaultValue = "") String dni,
			@RequestParam(value = "tagcode", defaultValue = "-1") String tagCode) {
		Response response = new Response();
		try {
			logger.info("CLOSE.Request: dni = " + dni + ", tagcode = " + tagCode);
			response.setError("Consulte en el mostrador");

			TrasanoService trService = new TrasanoService(dni, patientRepository, saRepository, statusRepository);
			logger.info("CLOSE.TrasanoService: " + trService.toString());

			if (trService.isPatient()) {
				if (trService.hasService() && !trService.isServiceOrdered()) {
					// MEJORAR con HashMAP
					trService.setTrasanoCode(2);
					// ----------------------------
					Integer intTagCode = (Integer.parseInt(tagCode.trim()));
					if  ((intTagCode > 0) && (intTagCode == trService.getSite(siteRepository).getTagCode())) {
						trService.setTagCode(intTagCode);
						trService.setSiteByTagCode(siteRepository);

						if (trService.getIdSite() > 0) {
							Site site = new Site();
							site = trService.getSite(siteRepository);
							trService.closeService(saRepository, patientRepository);
							response = new Response(trService, site);
						} else {
							response.setError("Ubicación de la etiqueta desnocida");
						}					
					} else {
						response.setError("El código de la etiqueta es incorrecto");
					}					
				} else {
					response.setError(
							"La ambulancia ya ha sido solicitada: " + 
									trService.getServiceTime().toString());
				}
			} else {
				response.setError("El paciente no dispone de servicio de ambulancia");
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.PatientController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("CLOSE.TrasanoService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}

	/**
	 * Return JSON for an service ambulance claimed by the dni.
	 * <p>
	 * Reason of the claimed can be given.  
	 *
	 * @param		dni	String
	 * @param		reason	String
	 * @return     	response	JSON with error, numClaims, lastClaimInMillis.
	 * 
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/claim" }, method = RequestMethod.POST)
	@ResponseBody
	public String claim(@RequestParam(value = "dni", defaultValue = "") String dni,
			@RequestParam(value = "reason", defaultValue = "") String reason) {
		Response response = new Response();
		try {

			logger.info("CLAIM.Request: dni = " + dni + ", reason = " + reason);
			response.setError("Consulte en el mostrador");

			TrasanoService trService = new TrasanoService(dni, patientRepository, saRepository, statusRepository);

			logger.info("CLAIM.TrasanoService: " + trService.toString());

			if (trService.isPatient() && trService.hasService()) {
				
				// MEJORAR con HashMAP
				trService.setTrasanoCode(4);
				// --------------------------
				
				if (trService.canClaim(sclaimRepository)) {
					trService.claimService(reason.trim(), saRepository, sclaimRepository);

					Site site = new Site();
					site = trService.getSite(siteRepository);

					logger.debug("CLAIM.site: " + site.toString());
					logger.debug("CLAIM.TrasanoService: " + trService.toString());

					response = new Response(trService, site);
				} else {
					response.setError("Debe pasar una hora antes de poder reclamar");
				}				
			} else {
				response.setError("El paciente no dispone de servicio de ambulancia");
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.PatientController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("CLAIM.TrasanoService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}

	/**
	 * Return JSON for an service ambulance canceled with the dni and code.
	 * <p>
	 * Reason of the cancelation can be given. 
	 * Delete this service from the table of services and it to the historical services. 
	 *
	 * @param		dni	String
	 * @param		code	String
	 * @param		reason	String
	 * @return     	response	JSON with error, numClaims, lastClaimInMillis.
	 * 
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/cancel" }, method = RequestMethod.POST)
	@ResponseBody
	public String cancel(@RequestParam(value = "dni", defaultValue = "") String dni,
			@RequestParam(value = "code", defaultValue = "") String code,
			@RequestParam(value = "reason", defaultValue = "") String reason) {

		Response response = new Response();
		try {
			logger.info("CANCEL.Request: dni = " + dni);
			response.setError("Consulte en el mostrador");

			TrasanoService trService = new TrasanoService(dni, patientRepository, saRepository, statusRepository);
			logger.info("CANCEL.TrasanoService: " + trService.toString());

			if (trService.isPatient() && trService.hasService()) {
				if (trService.isCodeError(Integer.parseInt(code), errorCodeRepository)){
					// MEJORAR con HashMAP
					trService.setTrasanoCode(3);
					// --------------------------
					
					trService.cancelService(code.trim(), reason.trim(), 
							saRepository, saHisRepository, scanceledRepository);

					Site site = new Site();
					site = trService.getSite(siteRepository);

					logger.info("CANCEL.Site: " + site.toString());

					response = new Response(trService, site);
				} else {
					response.setError("Código de error no válido");
				}
			} else {
				response.setError("El paciente no dispone de servicio de ambulancia");
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.PatientController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("CANCEL.TrasanoService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}

	/**
	 * Return JSON for an service ambulance by a dni given
	 * <p>
	 * Reason of the cancelation can be given. 
	 * Delete this service from the table of services and it to the historical services. 
	 *
	 * @param		dni	String
	 * @return     	response	JSON with error, numAmbulance, numClaims, lastClaimInMillis,
	 * 							driverName, DriverSurname, companyAmbulance, serviceTime, patientHome 
	 * 							and information about the location of the hospital.
	 * 
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/info" }, method = RequestMethod.POST)
	@ResponseBody
	public String info(@RequestParam(value = "dni", defaultValue = "") String dni) {

		logger.info("INFO.Request: dni = " + dni);
		Response response = new Response();
		response.setError("Consulte en el mostrador");
		try {
			TrasanoService trService = new TrasanoService(dni, patientRepository, saRepository, statusRepository);
			logger.info("INFO.TrasanoService: " + trService.toString());

			if (trService.isPatient() && trService.hasService()) {
				// MEJORAR con HashMAP
				trService.setTrasanoCode(1);
				// --------------------------
				trService.setLastClaimInMillis(sclaimRepository);
				
				Site site = new Site();
				site = trService.getSite(siteRepository);

				Ambulance ambulance = new Ambulance();
				ambulance = trService.getAmbulance(ambulanceRepository);

				Driver driver = new Driver();
				driver = trService.getDriver(ambulanceRepository, driverRepository);
				
				Patient patient = new Patient();
				patient = trService.getPatient(patientRepository);

				response = new Response(ambulance, site, driver, patient, trService.getServiceTime());
				response.setNumClaim(trService.getNumClaim());
				response.setLastClaimInMillis(String.valueOf(trService.getLastClaimInMillis()));
			} else {
				response.setError("El paciente no dispone de servicio de ambulancia");
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.PatientController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("INFO.TrasanoService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
}
