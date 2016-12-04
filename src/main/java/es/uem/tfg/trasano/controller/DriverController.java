/**
 * 
 */
package es.uem.tfg.trasano.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uem.tfg.trasano.entity.AmbulanceRepository;
import es.uem.tfg.trasano.entity.DriverRepository;
import es.uem.tfg.trasano.entity.PatientRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulance;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoricRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceStatusRepository;
import es.uem.tfg.trasano.entity.SiteRepository;
import es.uem.tfg.trasano.service.DriverService;
import es.uem.tfg.trasano.service.Response;

/**
 * @author rvallinot
 *
 */
@Controller
@RestController
@RequestMapping("/driver")
public class DriverController {

	private static final Logger logger = Logger.getLogger(DriverController.class.getName());
	
	@Autowired
	PatientRepository patientRepository;
	@Autowired
	ServiceAmbulanceRepository saRepository;
	@Autowired
	SiteRepository siteRepository;
	@Autowired
	AmbulanceRepository ambulanceRepository;
	@Autowired
	DriverRepository driverRepository;
	@Autowired
	ServiceStatusRepository statusRepository;
	@Autowired
	ServiceAmbulanceHistoricRepository saHisRepository;
	
	/**
	 * Returns a JSON with the name, surname, numAmbulance and numDriver if the driver
	 * exists.  
	 *
	 * @param		numDriver	String
	 * @param		numAmbulance	String
	 * @return     	response	JSON with error, name, surname, numAmbulance and numDriver. 
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	@ResponseBody
	public String login(@RequestParam(value = "numDriver", defaultValue = "") String numDriver,
			@RequestParam(value = "numAmbulance", defaultValue = "") String numAmbulance) {
		
		Response response = new Response();
		try {
			logger.info("Driver.Login.Request: numDriver = " + numDriver + ", numAmbulance = " + numAmbulance);
			response.setError("Error interno! Consulte con la central");

			DriverService driverService = new DriverService(numDriver.trim(), numAmbulance.trim(), 
					driverRepository, ambulanceRepository);
			logger.info("Driver.Login.DriverService = " + driverService.toString());

			if (driverService.isDriver()) {
				if (driverService.isAmbulance()) {
					response = new Response(driverService.getDriver(driverRepository));
				} else {
					response.setError("El número de ambulancia no está dado de alta en el sistema");
					logger.info("Driver.Login.error.numAmbulance = " + response.toString());
				}
			} else {
				response.setError("El conductor no está dado de alta en el sistema");
				logger.info("Driver.Login.TrasanoService.error.User = " + response.toString());
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.DriverController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Driver.Login.DriverService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
	
	/**
	 * Returns a JSON with the list of services for the #numAmbulance given. 
	 *
	 * @param		numDriver	String
	 * @param		numAmbulance	String
	 * @return     	response	JSON of services
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST)
	@ResponseBody
	public String service(@RequestParam(value = "numDriver", defaultValue = "") String numDriver,
			@RequestParam(value = "numAmbulance", defaultValue = "") String numAmbulance) {
		
		Response response = new Response();
		List<Response>listOfResponses = new ArrayList<>();
		try {
			logger.info("Driver.Service.Request: numDriver = " + numDriver + ", numAmbulance = " + numAmbulance);
			response.setError("Error interno! Consulte con la central");

			DriverService driverService = new DriverService(numDriver.trim(), numAmbulance.trim(), 
					driverRepository, ambulanceRepository);
			logger.info("Driver.Service.DriverService = " + driverService.toString());

			if (driverService.isDriver()) {
				if (driverService.isAmbulance()) {
					listOfResponses = driverService.setServices(saRepository, patientRepository, siteRepository);
				} else {
					response.setError("El número de ambulancia no está dado de alta en el sistema");
					logger.info("Driver.Service.error.numAmbulance = " + response.toString());
				}
			} else {
				response.setError("El conductor no está dado de alta en el sistema");
				logger.info("Driver.Service.TrasanoService.error.User = " + response.toString());
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.DriverController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			if (listOfResponses.size() == 0) {
				listOfResponses.add(response);
			}		
			logger.info("setServices.numOfServices = " + gson.toJson(listOfResponses));
			return gson.toJson(listOfResponses);
		}
	}
	
	/**
	 * Set an incidence for the service given. 
	 * <p>
	 * Error value is empty if the operation has been done successfully.
	 *
	 * @param		numDriver	String
	 * @param		numAmbulance	String
	 * @param		serviceId	String
	 * @param		incidence	String
	 * @return     	response	Response
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/incidence" }, method = RequestMethod.POST)
	@ResponseBody
	public String incidence(@RequestParam(value = "numDriver", defaultValue = "") String numDriver,
			@RequestParam(value = "numAmbulance", defaultValue = "") String numAmbulance,
			@RequestParam(value = "serviceId", defaultValue = "") String serviceId,
			@RequestParam(value = "incidence", defaultValue = "") String incidence) {
		
		Response response = new Response();
		try {
			logger.info("Driver.incidence.Request: numDriver = " + numDriver + ", numAmbulance = " + numAmbulance + 
					", serviceId = " + serviceId + ", incidence.length = " + incidence.length());
			response.setError("Error interno! Consulte con la central");
			
			DriverService driverService = new DriverService(numDriver.trim(), numAmbulance.trim(), 
					driverRepository, ambulanceRepository);
			logger.info("Driver.incidence.DriverService = " + driverService.toString());

			if (incidence.length() > 0) {
				if (driverService.isDriver()) {
					if (driverService.isAmbulance()) {
						if (!serviceId.isEmpty()) {
							ServiceAmbulance serviceAmbulance = saRepository.findOne(Long.valueOf(serviceId));
							serviceAmbulance.setIncidence(serviceAmbulance.getIncidence() + " | " +  incidence.trim());
							saRepository.save(serviceAmbulance);
							response = new Response();
						} else {
							response.setError("Error! No se encuentra el servicio indicado");
						}
					} else {
						response.setError("El número de ambulancia no está dado de alta en el sistema");
						logger.info("Driver.incidence.error.numAmbulance = " + response.toString());
					}
				} else {
				response.setError("El conductor no está dado de alta en el sistema");
				logger.info("Driver.incidence.TrasanoService.error.numDriver = " + response.toString());
				}
			} else {
				response.setError("La incidencia no puede estar vacía");
				logger.info("Driver.incidence.TrasanoService.error.Incidence = " + response.toString());
			}
		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.DriverController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Driver.incidence.DriverService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
	
	/**
	 * Set a new status for the service. Delete this service from #ServiceAmbulanceRepository and add one
	 * to #ServiceAmbulanceHistoricRepository.
	 * <p>
	 * Error value is empty if the operation has been done successfully.
	 *
	 * @param		numDriver	String
	 * @param		numAmbulance	String
	 * @param		serviceId	String
	 * @param		incidence	String
	 * @return     	response	Response
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/status" }, method = RequestMethod.POST)
	@ResponseBody
	public String status(@RequestParam(value = "numDriver", defaultValue = "") String numDriver,
			@RequestParam(value = "numAmbulance", defaultValue = "") String numAmbulance,
			@RequestParam(value = "serviceId", defaultValue = "") String serviceId,
			@RequestParam(value = "statusId", defaultValue = "") String statusId) {
		
		Response response = new Response();
		try {
			logger.info("Driver.status.Request: numDriver = " + numDriver + ", numAmbulance = " + numAmbulance + 
					", serviceId = " + serviceId + ", statusId = " + statusId);
			response.setError("Error interno! Consulte con la central");
			
			DriverService driverService = new DriverService(numDriver.trim(), numAmbulance.trim(), 
					driverRepository, ambulanceRepository);
			
			// Prepare service status and service id
			driverService.initIdServiceStatus(statusId);
			driverService.initIdService(serviceId);
			
			logger.info("Driver.status.DriverService = " + driverService.toString());

			response = driverService.setServiceStatus(statusRepository, saRepository, saHisRepository);

		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.DriverController: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Driver.status.DriverService.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}

}
