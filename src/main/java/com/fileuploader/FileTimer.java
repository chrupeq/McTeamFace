package com.fileuploader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table(name = "filetimer")
public class FileTimer {
	
	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "start_time")
	private String starttime;
	
	@Column(name = "end_time")
	private String endtime;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStartTime() {
		return starttime;
	}

	public void setStartTime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndTime() {
		return endtime;
	}

	public void setEndTime(String endtime) {
		this.endtime = endtime;
	}

}
