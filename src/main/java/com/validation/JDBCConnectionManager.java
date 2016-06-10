package com.validation;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;
import com.ait.reader.ReadDataSetIntoMainMemory;

public class JDBCConnectionManager {

	Connection con;
	public Statement statement;

	public void initiateDatabase() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String url = "jdbc:mysql://groupproject.cdauegtl908j.eu-west-1.rds.amazonaws.com:3306/ericsson_project";
			con = DriverManager.getConnection(url, "grouproject", "grouproject2016");
			statement = con.createStatement();
		} catch (Exception e) {
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	public void closeDatabase() throws SQLException {
		con.close();
		con = null;
	}

	public static void getData(String path){
		Base_data[] networkEntityArray;
		try {
			networkEntityArray = (Base_data[]) ReadDataSetIntoMainMemory.readFileInFromHardDrive(path, 0);
			JDBCConnectionManager jdbc = new JDBCConnectionManager();
			jdbc.sendBaseDataToDB(networkEntityArray);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendBaseDataToDB(NetworkEntity[] networkEntityArray) {
		initiateDatabase();
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		for (int i = 0; i < networkEntityArray.length; i++) {
			Base_data b = (Base_data) networkEntityArray[i];
			try {
				Date d = new Date(b.getDate_time().getTimeInMillis());
				statement.executeUpdate("INSERT INTO base_data(date_time, event_id, failure_class, ue_type, market, operator, cell_id, duration, cause_code, ne_version, imsi, hier3_id, hier32_id, hier321_id)VALUES('" + d+ "',"
						+ b.getEvent_cause().getEvent_id() + "," + b.getFailure_class().getFailure_class() + ","
						+ b.getUser_equipment().getTac() + "," + b.getMcc_mnc().getMcc() + ","
						+ b.getMcc_mnc().getMnc() + "," + b.getCell_id() + "," + b.getDuration() + ","
						+ b.getEvent_cause().getCause_code() + ",'" + b.getNe_version() + "'," + b.getImsi() + ","
						+ b.getHier3_id() + "," + b.getHier32_id() + "," + b.getHier321_id() + ");");
			} catch (SQLException e) {
				System.out.println("Du bist eine failure.");
				e.printStackTrace();
			}
		}
	}
}
