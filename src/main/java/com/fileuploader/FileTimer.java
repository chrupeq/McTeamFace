package com.fileuploader;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * File Timer Class
 * For creating File Timer objects, used in the
 * process of timing how long it takes a file to 
 * upload to the database
 *
 */
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

	public void setId(final int id) {
		this.id = id;
	}

	public String getStartTime() {
		return starttime;
	}

	public void setStartTime(final String starttime) {
		this.starttime = starttime;
	}

	public String getEndTime() {
		return endtime;
	}

	public void setEndTime(final String endtime) {
		this.endtime = endtime;
	}

}
