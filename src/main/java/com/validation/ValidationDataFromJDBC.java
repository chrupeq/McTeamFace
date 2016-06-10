package com.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Class for returning data from the database for cross-checking purposes with incoming datasets
 */

public class ValidationDataFromJDBC extends JDBCConnectionManager{
	
	private ResultSet resultsGatherer;
	private int[] eventsList;
	private int[] causeCodes;
	private int[] failureClass;
	private int[] userEquipment;
	private int[] MCCValues;
	private int[] MNCValues;
	private List<int[]> MNCMCCHolder;
	
	private static int counter = 0;
	
	private static int rowCount;
	
	/*
	 * Returns all event IDs from the database for use in validating event IDs in the dataset
	 */
	
	public int[] getEventIdList() throws SQLException{
		initiateDatabase();
		try {
			resultsGatherer = statement.executeQuery("SELECT event_id FROM event_cause");
			
			resultsGatherer.last();
			rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			eventsList = new int[rowCount];
			while(resultsGatherer.next()){
				System.out.println(resultsGatherer.getInt("event_id"));
				eventsList[counter] = resultsGatherer.getInt("event_id");
				counter ++;
			}
			resultsGatherer.close();
			counter = 0;
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for event_id list.");
			e.printStackTrace();
		}
	//	closeDatabase();
		return eventsList;
	}
	
	/*
	 * Returns all cause codes from the database for use in validating cause codes in the dataset
	 */
	
	public int[] getCauseCodes(){
		
		try {
			resultsGatherer = statement.executeQuery("SELECT cause_code FROM event_cause");
			
			resultsGatherer.last();
			rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			
			causeCodes = new int[rowCount];
			
			while(resultsGatherer.next()){
				causeCodes[counter] = resultsGatherer.getInt("cause_code");
				counter ++;
			}
			
			counter = 0;
		} catch (SQLException e) {
			System.out.println("Error in executing query for cause_codes list.");
			e.printStackTrace();
		}
		
		return causeCodes;
	}
	
	/*
	 * Returns all failure classes from the database for use in validating failure classes in the dataset
	 */
	
	public int[] getFailureClass(){
		
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT failure_class FROM failure_class");
			
			resultsGatherer.last();
			rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			
			failureClass = new int[rowCount];
			while(resultsGatherer.next()){
				failureClass[counter] = resultsGatherer.getInt("failure_class");
				counter ++;
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for failure_class list.");
			e.printStackTrace();
		}
		counter = 0;
		return failureClass;
	}
	
	/*
	 * Returns all user equipment types from the database for use in validating user equipment types in the dataset
	 */
	//comment
	public int[] getUETypes(){
	
		try {
			resultsGatherer = statement.executeQuery("SELECT tac FROM user_equipment");
			
			resultsGatherer.last();
			rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			
			userEquipment = new int[rowCount];
			
			while(resultsGatherer.next()){
				userEquipment[counter] = resultsGatherer.getInt("tac");
				counter ++;
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for tac list.");
			e.printStackTrace();
		}
		counter = 0;
		return userEquipment;
	}
	
	/*
	 * Returns all mobile country codes and mobile network codes from the database and stores them in seperate arrays for use in validating data in the dataset
	 * Both of these must exist together in the database or the data is considered invalid
	 */
	
	public List<int[]> getMarketsAndOperators(){
		initiateDatabase();
		MNCMCCHolder = new ArrayList<int[]>();
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT mcc, mnc FROM mcc_mnc");
			resultsGatherer.last();
			int rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			MCCValues = new int[rowCount];
			MNCValues = new int[rowCount];
			while(resultsGatherer.next()){
				MCCValues[counter] = resultsGatherer.getInt("mcc"); 
				MNCValues[counter] = resultsGatherer.getInt("mnc");
				counter ++;
			}
			MNCMCCHolder.add(MCCValues);
			MNCMCCHolder.add(MNCValues);
			counter = 0;
			rowCount = 0;
		} catch (SQLException e) {
			System.out.println("Error in executing query for mcc list.");
			e.printStackTrace();
		}
		
		return MNCMCCHolder;
	}
}
