package com.ait.db.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.OneToMany;

@Entity
@Table(name="failure_class")
public class Failure_class implements Serializable, NetworkEntity {
	
	private static final long serialVersionUID = 1L;
	@Id
	@Column(columnDefinition = "INT UNSIGNED", nullable = false)
	private int failure_class;
	@Column(nullable = false)
	private String description;
	@OneToMany(mappedBy="failure_class")
	List<Base_data> baseDataList;
	
	public Failure_class() {}
	
	public Failure_class(int failure_class, String description) {
		this.failure_class = failure_class;
		this.description = description;
	}

	public int getFailure_class() {
		return failure_class;
	}
	public void setFailure_class(int failure_class) {
		this.failure_class = failure_class;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	
}
