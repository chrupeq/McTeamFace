package com.ait.db.model;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
public class Base_data implements Serializable, NetworkEntity {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "INT UNSIGNED")
	private int report_id;
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date_time;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns(
		{@JoinColumn(name="cause_code", referencedColumnName="cause_code", nullable = true),
		@JoinColumn(name="event_id", referencedColumnName="event_id")}
		)
	private Event_cause event_cause;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="failure_class", referencedColumnName="failure_class", nullable = true)
	private Failure_class failure_class;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="ue_type", referencedColumnName="tac")
	private User_equipment user_equipment;
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumns( 
		{@JoinColumn(name="market", referencedColumnName="mcc"),
		@JoinColumn(name="operator", referencedColumnName="mnc")}
			)
	private Mcc_mnc mcc_mnc;
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private int cell_id;
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private int duration;
	@Column(nullable = false)
	private String ne_version;
	@Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
	private BigInteger imsi;
	@Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
	private BigInteger hier3_id;
	@Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
	private BigInteger hier32_id;
	@Column(columnDefinition = "BIGINT UNSIGNED", nullable = false)
	private BigInteger hier321_id;
	
	public Base_data() {}
	


	public Base_data(Calendar date_time,
			Event_cause event_cause, Failure_class failure_class,
			User_equipment user_equipment, Mcc_mnc mcc_mnc, int cell_id,
			int duration, String ne_version, BigInteger imsi,
			BigInteger hier3_id, BigInteger hier32_id, BigInteger hier321_id) {
		super();
		this.date_time = date_time;
		this.event_cause = event_cause;
		this.failure_class = failure_class;
		this.user_equipment = user_equipment;
		this.mcc_mnc = mcc_mnc;
		this.cell_id = cell_id;
		this.duration = duration;
		this.ne_version = ne_version;
		this.imsi = imsi;
		this.hier3_id = hier3_id;
		this.hier32_id = hier32_id;
		this.hier321_id = hier321_id;
	}

	public Event_cause getEvent_cause() {
		return event_cause;
	}

	public void setEvent_cause(Event_cause event_cause) {
		this.event_cause = event_cause;
	}

	public Failure_class getFailure_class() {
		return failure_class;
	}

	public void setFailure_class(Failure_class failureClass) {
		this.failure_class = failureClass;
	}

	public User_equipment getUser_equipment() {
		return user_equipment;
	}

	public void setUser_equipment(User_equipment userEquipment) {
		this.user_equipment = userEquipment;
	}

	public Mcc_mnc getMcc_mnc() {
		return mcc_mnc;
	}

	public void setMcc_mnc(Mcc_mnc mccMnc) {
		this.mcc_mnc = mccMnc;
	}

	public int getReport_id() {
		return report_id;
	}
	public Calendar getDate_time() {
		return date_time;
	}
	public void setDate_time(Calendar date_time) {
		this.date_time = date_time;
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
