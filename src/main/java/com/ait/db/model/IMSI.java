package com.ait.db.model;

import java.math.BigDecimal;
import java.util.Map;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

public class IMSI {
	
	private Long imsiNumber;
	
	public Long getImsiNumber(){
		return imsiNumber;
	}
}
