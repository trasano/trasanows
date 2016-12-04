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
@Table(name = "driver")
public class Driver {

	@Id @GeneratedValue(strategy=GenerationType.AUTO) 
	long id;
	@Column(name="Name")
	String name;
	@Column(name="Surname")
	String surname;
	@Column(name="NumDriver")
	long numdriver;
	
	public Driver() {
		super();
		this.id = -1;
		this.name = "";
		this.surname = "";
		this.numdriver = -1;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public long getNumdriver() {
		return numdriver;
	}
	public void setNumdriver(long numdriver) {
		this.numdriver = numdriver;
	}
	@Override
	public String toString() {
		return "Driver [id=" + id + ", name=" + name + ", surname=" + surname + ", numdriver=" + numdriver + "]";
	}
}
