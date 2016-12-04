/**
 * 
 */
package es.uem.tfg.trasano.entity;

import org.springframework.data.repository.CrudRepository;

/**
 * @author rvallinot
 *
 */
public interface PatientRepository extends CrudRepository<Patient, Long> {
	
	// Custom Querys
	/*List<Patient> findPatientByDNI(Integer dni);
	List<Patient> findPatientByNumSegSocial(Integer NumSegSoc);
	List<Patient> findPatientByNumHistory(Integer numHistory);*/

}
