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
@Table(name = "ambulance")
public class Ambulance {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="idDriver")
	long idDriver;
	@Column(name="NumAmbulance")
	Integer numAmbulance;
	@Column(name="Company")
	String company;
	@Column(name="Capacity")
	Integer capacity;
	
	public Ambulance() {
		super();
		this.id = -1;
		this.idDriver = -1;
		this.numAmbulance = 0;
		this.company = "";
		this.capacity = 0;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getIdDriver() {
		return idDriver;
	}
	public void setIdDriver(long idDriver) {
		this.idDriver = idDriver;
	}
	public Integer getNumAmbulance() {
		return numAmbulance;
	}
	public void setNumAmbulance(Integer numAmbulance) {
		this.numAmbulance = numAmbulance;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public Integer getCapacity() {
		return capacity;
	}
	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}
	@Override
	public String toString() {
		return "Ambulance [id=" + id + ", idDriver=" + idDriver + ", numAmbulance=" + numAmbulance + ", company="
				+ company + ", capacity=" + capacity + "]";
	}
}
