/**
 * 
 */
package es.uem.tfg.trasano.entity;

import java.math.BigDecimal;

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
@Table(name = "patient")
public class Patient {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="NumSegSoc")
	long numSegSoc;
	@Column(name="NumHistory")
	long numHistory;
	@Column(name="DNI")
	String dni;
	@Column(name="Name")
	String name;
	@Column(name="Surname")
	String surname;
	@Column(name="Street")
	String street;
	@Column(name="Locality")
	String locality;
	@Column(name="City")
	String city;
	@Column(name="PostalCode")
	String postalCode;
	@Column(name="Telephone1")
	String telephone1;
	@Column(name="Telephone2")
	String telephone2;
	@Column(name="Comment")
	String comment;
	@Column(name="Details")
	String details;
	@Column(name="Latitude")
	BigDecimal latitude;
	@Column(name="Longitude")
	BigDecimal longitude;


	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getNumSegSoc() {
		return numSegSoc;
	}
	public void setNumSegSoc(long numSegSoc) {
		this.numSegSoc = numSegSoc;
	}
	public long getNumHistory() {
		return numHistory;
	}
	public void setNumHistory(long numHistory) {
		this.numHistory = numHistory;
	}
	public String getDni() {
		return dni;
	}
	public void setDni(String dni) {
		this.dni = dni;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getLocality() {
		return locality;
	}
	public void setLocality(String locality) {
		this.locality = locality;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
	public String getTelephone1() {
		return telephone1;
	}
	public void setTelephone1(String telephone1) {
		this.telephone1 = telephone1;
	}
	public String getTelephone2() {
		return telephone2;
	}
	public void setTelephone2(String telephone2) {
		this.telephone2 = telephone2;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
	}
	public String getPatienHome(){
		String patientHome = this.street;
		if (!this.locality.isEmpty()){
			patientHome = patientHome + ", " + this.locality;
		}
		if (!this.city.isEmpty()){
			patientHome = patientHome + ", " + this.city;
		}
		if (!this.postalCode.isEmpty()){
			patientHome = patientHome + ", " + this.postalCode;
		}
		return patientHome;
	}
	@Override
	public String toString() {
		return "Patient [id=" + id + ", numSegSoc=" + numSegSoc + ", numHistory=" + numHistory + ", dni=" + dni
				+ ", name=" + name + ", surname=" + surname + ", street=" + street + ", locality=" + locality
				+ ", city=" + city + ", postalCode=" + postalCode + ", telephone1=" + telephone1 + ", telephone2="
				+ telephone2 + ", comment=" + comment + ", details=" + details + ", latitude=" + latitude
				+ ", longitude=" + longitude + "]";
	}

}
