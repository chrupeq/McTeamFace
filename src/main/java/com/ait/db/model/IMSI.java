package com.ait.db.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="base_data")
@Stateless
@LocalBean
public class IMSI {
	
	@Id
	@Column(name="imsi")
	private Long imsiNumber;
	
	public Long getImsiNumber(){
		return imsiNumber;
	}
}
