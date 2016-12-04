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
@Table(name = "cancelreason")
public class CancelReason {
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="Description")
	String description;
	
	public CancelReason() {
		super();
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "CancelReason [id=" + id + ", description=" + description + "]";
	}	
}
