package com.validation;

import java.sql.Connection;
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
			String url = "jdbc:mysql://localhost:3307/ericsson_project";
			con = DriverManager.getConnection(url, "root", "admin");
			statement = con.createStatement();
		} catch (Exception e) {
			System.out.println("Error: Failed to connect to database\n" + e.getMessage());
		}
	}

	public void closeDatabase() throws SQLException {
		con.close();
		con = null;
	}

	public void sendShitSomewhere(NetworkEntity[] networkEntityArray) {
		initiateDatabase();
		for (int i = 0; i < networkEntityArray.length; i++) {
			Base_data b = (Base_data) networkEntityArray[i];
			try {
				Date d = b.getDate_time();
				statement.executeUpdate("INSERT INTO base_data(date_time, cause_code, event_id, failure_class, ue_type, market, operator, cell_id, duration, ne_version, imsi, hier3_id, hier32_id, hier321_id)VALUES('" + b.getDate_time() + "',"
						+ b.getEvent_cause().getEvent_id() + "," + b.getFailure_class().getFailure_class() + ","
						+ b.getUser_equipment().getUser_equipment_type() + "," + b.getMcc_mnc().getMcc() + ","
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
