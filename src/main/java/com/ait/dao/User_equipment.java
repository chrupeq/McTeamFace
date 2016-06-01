package com.ait.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class User_equipment {
	@Id
	private int tac;
	private String marketing_name;
	private String manufacturer;
	private String access_capability;
	private String model;
	
	public User_equipment() {}
	
	public User_equipment(int tac, String marketing_name, String manufacturer,
			String access_capability, String model) {
		this.tac = tac;
		this.marketing_name = marketing_name;
		this.manufacturer = manufacturer;
		this.access_capability = access_capability;
		this.model = model;
	}



	public int getTac() {
		return tac;
	}
	public void setTac(int tac) {
		this.tac = tac;
	}
	public String getMarketing_name() {
		return marketing_name;
	}
	public void setMarketing_name(String marketing_name) {
		this.marketing_name = marketing_name;
	}
	public String getManufacturer() {
		return manufacturer;
	}
	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}
	public String getAccess_capability() {
		return access_capability;
	}
	public void setAccess_capability(String access_capability) {
		this.access_capability = access_capability;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
}
