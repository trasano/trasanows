/**
 * 
 */
package es.uem.tfg.trasano.entity;

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
@Table(name = "serviceStatus")
public class ServiceStatus {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;

	@Column(name="Description")
	String description;
	
	public long getId() {
		return id;
	}
	public String getDescription() {
		return description;
	}
	@Override
	public String toString() {
		return "ServiceStatus [id=" + id + ", description=" + description + "]";
	}	
}
