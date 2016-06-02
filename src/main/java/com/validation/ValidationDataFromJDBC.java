package com.validation;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ValidationDataFromJDBC extends JDBCConnectionManager{
	
	private ResultSet resultsGatherer;
	private ArrayList<Integer> eventsList = new ArrayList<Integer>();
	private ArrayList<Integer> causeCodes = new ArrayList<Integer>();
	private ArrayList<Integer> failureClass = new ArrayList<Integer>();
	private ArrayList<Integer> userEquipment = new ArrayList<Integer>();
	private ArrayList<Integer> operators = new ArrayList<Integer>();
	int[] MCCValues;
	int[] MNCValues;
	List<int[]> MNCMCCHolder;
	
	public ArrayList<Integer> getEventIdList(){
		initiateDatabase();
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT(event_id) FROM event_cause");
			
			resultsGatherer.next();
			
			while(resultsGatherer.next()){
				eventsList.add(resultsGatherer.getInt("event_id"));
				
			}
			resultsGatherer.close();
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for event_id list.");
			e.printStackTrace();
		}
		
		return eventsList;
	}
	
	public ArrayList<Integer> getCauseCodes(){
		
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT cause_code FROM event_cause");
			
			while(resultsGatherer.next()){
				causeCodes.add(resultsGatherer.getInt("cause_code"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for cause_codes list.");
			e.printStackTrace();
		}
		
		return causeCodes;
	}
	
	public ArrayList<Integer> getFailureClass(){
	
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT failure_class FROM failure_class");
			
			while(resultsGatherer.next()){
				failureClass.add(resultsGatherer.getInt("failure_class"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for failure_class list.");
			e.printStackTrace();
		}
		
		return failureClass;
	}
	
	public ArrayList<Integer> getUETypes(){
	
		try {
			resultsGatherer = statement.executeQuery("SELECT tac FROM user_equipment");
			
			while(resultsGatherer.next()){
				userEquipment.add(resultsGatherer.getInt("tac"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for tac list.");
			e.printStackTrace();
		}
		
		return userEquipment;
	}
	
	public List<int[]> getMarketsAndOperators(){
	
		int counter = 0;
		MNCMCCHolder = new ArrayList<int[]>();
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT mcc, mnc FROM mcc_mnc");
			resultsGatherer.last();
			int rowCount = resultsGatherer.getRow();
			resultsGatherer.beforeFirst();
			resultsGatherer.next();
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
		} catch (SQLException e) {
			System.out.println("Error in executing query for mcc list.");
			e.printStackTrace();
		}
		
		return MNCMCCHolder;
	}
	
	public ArrayList<Integer> getOperators(){
		
		try {
			resultsGatherer = statement.executeQuery("SELECT DISTINCT mnc FROM mcc_mnc");
			
			while(resultsGatherer.next()){
				operators.add(resultsGatherer.getInt("mnc"));
			}
			
		} catch (SQLException e) {
			System.out.println("Error in executing query for operators list.");
			e.printStackTrace();
		}
		
		return operators;
	}
}
