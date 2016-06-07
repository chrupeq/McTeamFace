package com.ait.db.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table(name = "user_equipment")
public class User_equipment implements Serializable, NetworkEntity{
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private int tac;
	@Column(nullable = false)
	private String marketing_name;
	@Column(nullable = false)
	private String manufacturer;
	@Column(nullable = false)
	private String access_capability;
	@Column(nullable = false)
	private String model;
	@Column(nullable = false)
	private String vendor_name;
	private String user_equipment_type;
	private String operating_system;
	private String input_mode;
	@OneToMany(mappedBy="user_equipment")
	private List<Base_data> baseData;
	
	public User_equipment() {}
	
	public User_equipment(int tac, String marketing_name, String manufacturer,
			String access_capability, String model, String vendor_name,
			String user_equipment_type, String operating_system,
			String input_mode) {
		super();
		this.tac = tac;
		this.marketing_name = marketing_name;
		this.manufacturer = manufacturer;
		this.access_capability = access_capability;
		this.model = model;
		this.vendor_name = vendor_name;
		this.user_equipment_type = user_equipment_type;
		this.operating_system = operating_system;
		this.input_mode = input_mode;
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

	public String getUser_equipment_type() {
		return user_equipment_type;
	}

	public void setUser_equipment_type(String user_equipment_type) {
		this.user_equipment_type = user_equipment_type;
	}

	public String getOperating_system() {
		return operating_system;
	}

	public void setOperating_system(String operating_system) {
		this.operating_system = operating_system;
	}

	public String getInput_mode() {
		return input_mode;
	}

	public void setInput_mode(String input_mode) {
		this.input_mode = input_mode;
	}

	public String getVendor_name() {
		return vendor_name;
	}

	public void setVendor_name(String vendor_name) {
		this.vendor_name = vendor_name;
	}
	
	
}
