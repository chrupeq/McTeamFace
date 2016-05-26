package com.ait.gui;

public class Admin extends Users {
	private String username;
	private String password;
	
	private Admin(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public static Admin accessorMethod(String username, String password) {
		return new Admin(username, password);
		
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
	
	@Override
	public String getPassword() {
		return this.password;
	}

}
