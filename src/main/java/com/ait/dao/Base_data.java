package com.ait.dao;

import java.math.BigInteger;
import java.sql.Date;

public class Base_data {

	int report_id;
	Date date_time;
	int cause_code;
	int event_id;
	int failure_class;
	int ue_type;
	int market;
	int operator;
	int cell_id;
	int duration;
	String ne_version;
	BigInteger imsi;
	BigInteger hier3_id;
	BigInteger hier32_id;
	BigInteger hier321_id;
	public int getReport_id() {
		return report_id;
	}
	public void setReport_id(int report_id) {
		this.report_id = report_id;
	}
	public Date getDate_time() {
		return date_time;
	}
	public void setDate_time(Date date_time) {
		this.date_time = date_time;
	}
	public int getCause_code() {
		return cause_code;
	}
	public void setCause_code(int cause_code) {
		this.cause_code = cause_code;
	}
	public int getEvent_id() {
		return event_id;
	}
	public void setEvent_id(int event_id) {
		this.event_id = event_id;
	}
	public int getFailure_class() {
		return failure_class;
	}
	public void setFailure_class(int failure_class) {
		this.failure_class = failure_class;
	}
	public int getUe_type() {
		return ue_type;
	}
	public void setUe_type(int ue_type) {
		this.ue_type = ue_type;
	}
	public int getMarket() {
		return market;
	}
	public void setMarket(int market) {
		this.market = market;
	}
	public int getOperator() {
		return operator;
	}
	public void setOperator(int operator) {
		this.operator = operator;
	}
	public int getCell_id() {
		return cell_id;
	}
	public void setCell_id(int cell_id) {
		this.cell_id = cell_id;
	}
	public int getDuration() {
		return duration;
	}
	public void setDuration(int duration) {
		this.duration = duration;
	}
	public String getNe_version() {
		return ne_version;
	}
	public void setNe_version(String ne_version) {
		this.ne_version = ne_version;
	}
	public BigInteger getImsi() {
		return imsi;
	}
	public void setImsi(BigInteger imsi) {
		this.imsi = imsi;
	}
	public BigInteger getHier3_id() {
		return hier3_id;
	}
	public void setHier3_id(BigInteger hier3_id) {
		this.hier3_id = hier3_id;
	}
	public BigInteger getHier32_id() {
		return hier32_id;
	}
	public void setHier32_id(BigInteger hier32_id) {
		this.hier32_id = hier32_id;
	}
	public BigInteger getHier321_id() {
		return hier321_id;
	}
	public void setHier321_id(BigInteger hier321_id) {
		this.hier321_id = hier321_id;
	}
	
	
}
