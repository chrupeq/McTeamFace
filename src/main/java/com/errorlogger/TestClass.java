package com.errorlogger;

public class TestClass {
	
	public static void main(String[] args){
		
		DatabaseErrorLogger db = new DatabaseErrorLogger("logger");
		
		db.errorFound("something", 4, "something");
	}
}
