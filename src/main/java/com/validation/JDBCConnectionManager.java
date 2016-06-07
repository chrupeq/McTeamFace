package com.validation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCConnectionManager {

	Connection con;
	Statement statement;
	
	public void initiateDatabase()
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			String url="jdbc:mysql://groupproject.cdauegtl908j.eu-west-1.rds.amazonaws.com:3306/ericsson_project";
			con = DriverManager.getConnection(url, "grouproject", "grouproject2016");
			statement = con.createStatement();
		}
		catch(Exception e)
		{
			System.out.println("Error: Failed to connect to database\n"+e.getMessage());
		}
	}
	
	public void closeDatabase() throws SQLException{
		con.close();
		con = null;
	}
}
