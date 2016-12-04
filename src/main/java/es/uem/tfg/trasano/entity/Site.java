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
@Table(name = "site")
public class Site {
	
	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="Headquarters")
	String headquarters;
	@Column(name="Department")
	String department;
	@Column(name="Street")
	String street;
	@Column(name="Locality")
	String locality;
	@Column(name="City")
	String city;
	@Column(name="PostalCode")
	String postalCode;
	@Column(name="tagCode")
	Integer tagCode;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getHeadquarters() {
		return headquarters;
	}
	public void setHeadquarters(String headquarters) {
		this.headquarters = headquarters;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
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
	public Integer getTagCode() {
		return tagCode;
	}
	public void setTagCode(Integer tagCode) {
		this.tagCode = tagCode;
	}
	@Override
	public String toString() {
		return "Site [id=" + id + ", headquarters=" + headquarters + ", department=" + department + ", street=" + street
				+ ", locality=" + locality + ", city=" + city + ", postalCode=" + postalCode + ", tagCode=" + tagCode
				+ "]";
	}	
}
