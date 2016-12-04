/**
 * 
 */
package es.uem.tfg.trasano.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;

import es.uem.tfg.trasano.entity.CancelReasonRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceHistoricRepository;
import es.uem.tfg.trasano.entity.ServiceAmbulanceRepository;
import es.uem.tfg.trasano.entity.ServiceCanceledRepository;
import es.uem.tfg.trasano.service.AdminService;
import es.uem.tfg.trasano.service.Response;

/**
 * Controller which responses POST request for the next url: /admin/cancel, 
 * /admin/claim, /admin/service
 * 
 * @author rvallinot
 *
 */
@Controller
@RestController
@RequestMapping("/admin")
public class AdminController {

	private static final Logger logger = Logger.getLogger(AdminController.class.getName());
	
	@Autowired
	ServiceAmbulanceRepository saRepository;
	@Autowired
	ServiceAmbulanceHistoricRepository saHisRepository;
	@Autowired
	ServiceCanceledRepository scanceledRepository;
	@Autowired
	CancelReasonRepository cancelReasonRepository;
	
	/**
	 * Returns a JSON with the statics of canceled services.
	 *
	 * @param		void
	 * @return     	response	JSON with error, numOfCanceled, avgNumClaim 
	 * 							servicesCanceledSorted (JSON)
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/cancel" }, method = RequestMethod.POST)
	@ResponseBody
	public String cancel() {
		
		Response response = new Response();
		try {
			logger.info("Admin.Claim.Request");
			response.setError("Error interno! Consulte con el departamento de sistemas");

			AdminService adminService = new AdminService(scanceledRepository, cancelReasonRepository);
			response = new Response(adminService);

		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.AdminController.Claim: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Admin.Claim.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
	
	/**
	 * Returns a JSON with the statics of claimed services.
	 *
	 * @param		void
	 * @return     	response	JSON with error, nunOfServices, numOfPatients 
	 * 							numOfServicesClaimed, servicesMoreClaimed (JSON),
	 * 							servicesClaimedSorted (JSON)
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/claim" }, method = RequestMethod.POST)
	@ResponseBody
	public String claim() {
		Response response = new Response();
		try {
			logger.info("Admin.Claim.Request");
			response.setError("Error interno! Consulte con el departamento de sistemas");

			AdminService adminService = new AdminService(saRepository);
			response = new Response(adminService);

		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.AdminController.Claim: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Admin.Claim.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
	
	/**
	 * Returns a JSON with the service statics.
	 *
	 * @param		void
	 * @return     	response	JSON with error, nunOfServices, numAmbulancesWorking
	 * 							numOfPatients, numOfServicesClaimed, numServicesWaiting,
	 * 							numServicesInTransit
	 * @exception	java.lang.Exception
	 */
	@SuppressWarnings("finally")
	@RequestMapping(value = { "/service" }, method = RequestMethod.POST)
	@ResponseBody
	public String service() {	
		Response response = new Response();
		try {
			logger.info("Admin.Service.Request");
			response.setError("Error interno! Consulte con el departamento de sistemas");

			AdminService adminService = new AdminService(saRepository);
			response = new Response(adminService);

		} catch (Exception e) {
			System.out.println("es.uem.tfg.trasano.AdminController.Response: " + e.toString());
		} finally {
			Gson gson = new Gson();
			logger.info("Admin.Service.response = " + gson.toJson(response));
			return gson.toJson(response);
		}
	}
}


