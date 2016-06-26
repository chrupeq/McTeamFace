package com.ait.db.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Stateless
@LocalBean
@Table(name="sessions")
public class Session {

	@Id
	@Column(name = "username")
	String username;
	
	@Column(name = "session_id")
	String sessionId;
	
	@Column(name = "user_level")
	String userLevel;

	public Session(){
		
	}
	
	public Session(String sessionId, String userLevel, String username){
		this.sessionId = sessionId;
		this.userLevel = userLevel;
		this.username = username;
	}
	
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getUser_level() {
		return userLevel;
	}

	public void setUser_level(String user_level) {
		this.userLevel = user_level;
	}
}
