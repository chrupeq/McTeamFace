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
	
	@Column(name = "starttime")
	private String starttime;
	
	@Column(name = "endtime")
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

	public String getModel() {
		return endtime;
	}

	public void setModel(String endtime) {
		this.endtime = endtime;
	}

}
