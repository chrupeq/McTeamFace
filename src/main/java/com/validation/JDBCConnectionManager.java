package com.validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class JDBCConnectionManager {

	Connection con;
	Statement statement;
	
	public void initiateDatabase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://localhost:3307/ericsson_project";
			con = DriverManager.getConnection(url, "root", "admin");
			statement = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	
}
