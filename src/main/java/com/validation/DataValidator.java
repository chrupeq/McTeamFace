package com.validation;

import java.math.BigInteger;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;

import com.ait.db.model.Base_data;
import com.errorlogger.DatabaseErrorLogger;
import com.errorlogger.DateManipulator;

public class DataValidator {

	private static int[] eventIdList;
	private static int[] causeCodes;
	private static int[] failureClasses;
	private static int[] UETypes;
	private static List<int[]> marketsAndOperators;
	private static int[] MNCValues;
	private static int[] MCCValues;
	
	private static Object[][] arrayToBePersisted;

	private static StringBuilder errorBuilder = new StringBuilder();
	private static StringBuilder errorToString = new StringBuilder();

	private static int arrayLength;
	private static int rowCount = 2;
	private static String fileNameForErrorLogger;
	private static int errorCount;
	private static int counterForDatabaseEntries = 0;

	static DatabaseErrorLogger dataErrorLogger = new DatabaseErrorLogger("Validation Logger");

	public static void setUpDatabaseData() {

		ValidationDataFromJDBC databaseAccessor = new ValidationDataFromJDBC();
			try {
				eventIdList = databaseAccessor.getEventIdList();
			} catch (SQLException e) {
			System.out.println("SQL Exception on connection close.");
				e.printStackTrace();
			}
			
		causeCodes = databaseAccessor.getCauseCodes();
		failureClasses = databaseAccessor.getFailureClass();
		UETypes = databaseAccessor.getUETypes();
		marketsAndOperators = databaseAccessor.getMarketsAndOperators();

		MNCValues = marketsAndOperators.get(0);
		MCCValues = marketsAndOperators.get(1);

		new DataFormatter();
		
		try {
			databaseAccessor.closeDatabase();
		} catch (SQLException e) {
			System.out.println("Error closing the database connection...");
			e.printStackTrace();
		}

	}

	public static Base_data[] validateData(Object[][] tableValuesToValidate, String fileName) {
		arrayToBePersisted = new Object[tableValuesToValidate.length][tableValuesToValidate[0].length];
		setUpDatabaseData();
		arrayLength = tableValuesToValidate.length;
		fileNameForErrorLogger = fileName;

		for (int i = 0; i < tableValuesToValidate.length; i++) {

			if (!validateDateTime(tableValuesToValidate[i][0])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid date at column: 1 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateEventIdAndCauseCode(tableValuesToValidate[i][1], tableValuesToValidate[i][8])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid Event ID at column: 2 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateFailureClass(tableValuesToValidate[i][2])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid failure class at column: 3 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateUEType(tableValuesToValidate[i][3])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid UE Type at column: 4 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateMarketAndOperator(tableValuesToValidate[i][4], tableValuesToValidate[i][5])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid or unmatched data at column: 5 & 6 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateCellId(tableValuesToValidate[i][6])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid Cell ID at column: 7 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateDuration(tableValuesToValidate[i][7])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid call duration at column: 8 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateNEVersion(tableValuesToValidate[i][9])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime() + ": Invalid NEVersion at column: 10 Line: "
						+ i + "\r\n");
				errorCount++;
			}

			if (!validateIMSI(tableValuesToValidate[i][10])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid IMSI at column 11 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateHier3(tableValuesToValidate[i][11])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER3 at column: 12 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateHier32(tableValuesToValidate[i][12])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER32 at column: 13 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateHier321(tableValuesToValidate[i][13])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER321 at column: 14 Line: " + i + "\r\n");
				errorCount++;
			}

			rowCount++;

			if (errorBuilder.length() > 0) {
				logError(tableValuesToValidate[i], errorBuilder);
			}else{
				arrayToBePersisted[counterForDatabaseEntries] = tableValuesToValidate[i];
				counterForDatabaseEntries ++;
			}
		}
		
		Base_data[] bdArray = sendInformationToTheDataLayer(arrayToBePersisted);
		return bdArray;
	}

	private static boolean validateDateTime(Object dateTime) {
		if (dateTime instanceof String) {
			String dateTimeValue = (String) dateTime;
			if (dateTimeValue.matches("\\d{2,2}/\\d{2,2}/\\d{4,4}\\s\\d{2,2}:\\d{2,2}$")) {
				String[] brokenUpDates = dateTimeValue.substring(0, 10).split("/");
				String time = dateTimeValue.substring(11);
				int day = Integer.parseInt(brokenUpDates[0]);
				int year = Integer.parseInt(brokenUpDates[2]);
				try {
					if (DateManipulator.checkThatDateIsNotInTheFuture(dateTime.toString())) {

						if (brokenUpDates[1].matches("0[1-9]|1[0-2]") && time
								.matches("(0[0-9]|1[0-9]|2[0-3]):(0[0-9]|1[0-9]|2[0-9]|3[0-9]|4[0-9]|5[0-9])$")) {
							if (day <= 28 && day > 0) {
								return true;
							} else if (day <= 30 && day > 0 && brokenUpDates[1].matches("04|06|09|11")) {
								return true;
							} else if (day <= 31 && brokenUpDates[1].matches("01|03|05|07|08|10|12")) {
								return true;
							} else if (isLeapYear(year) && brokenUpDates[1].equals("02") && day <= 29 && day > 0) {
								return true;
							} else {
								return false;
							}
						}
					}
				} catch (ParseException e) {
					System.out.println("parse exception!");
					e.printStackTrace();
				}
			}
		}
		return false;
	}

	private static boolean validateEventIdAndCauseCode(Object eventId, Object causeCode) {
		int causeCodeValue;
		try {
			if(eventId.toString().startsWith("0") && !eventId.toString().matches("^[1-9][1-9][1-9][1-9]$")){
				return false;
			}
			int intValue = Integer.parseInt(eventId.toString());
			if (causeCode.toString().equals("(null)") && intValue == 4099) {
				for(int i = 0; i < eventIdList.length; i ++){
					if (eventIdList[i] == intValue) {
						return true;
					}
					}
			}else{
			if(causeCode.toString().equals("0")){
				 causeCodeValue = 0;
			}else{
			 causeCodeValue = Integer.parseInt(causeCode.toString());
			}
			for(int i = 0; i < causeCodes.length; i ++){
			if (causeCodes[i] == causeCodeValue && eventIdList[i] == intValue) {
				return true;
			}
			}
		}
			} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating EventID.");
			e.printStackTrace();
			return false;
		}
		return false;
	}

	private static boolean validateFailureClass(Object failureClass) {
		try {
			if (!failureClass.toString().matches("^[0-4]$") && !failureClass.toString().equals("(null)")){
				return false;
			}
			if(failureClass.toString().equals("(null)")){
				return true;
			}
			int intValue = Integer.parseInt(failureClass.toString());
			for(int i = 0; i < failureClasses.length; i ++){
			if (failureClasses[i] == intValue) {
				return true;
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating failureClass.");
			return false;
		}

		return false;
	}

	private static boolean validateUEType(Object ueType) {
		
		if(!ueType.toString().matches("^[0-9]{1,45}$")){
			return false;
		}
		
		try {
			int intValue = Integer.parseInt(ueType.toString());
			for(int i = 0; i < UETypes.length; i ++){
			if (UETypes[i] == intValue) {
				return true;
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating UEType.");
			return false;
		}
		return false;
	}

	private static boolean validateMarketAndOperator(Object market, Object operator) {
		try {
			int marketIntValue = Integer.parseInt(market.toString());
			int operatorIntValue = Integer.parseInt(operator.toString());
			
			for (int i = 0; i < MNCValues.length; i++) {
				if (marketIntValue == MNCValues[i] && operatorIntValue == MCCValues[i])
					return true;
			}

		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating marketsAndOperators.");
		}

		return false;
	}

	private static boolean validateCellId(Object cellId) {
		try {
			int intValue = Integer.parseInt(cellId.toString());
			if (intValue > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating cellId.");
		}

		return false;
	}

	private static boolean validateDuration(Object duration) {
		try {
			int intValue = Integer.parseInt(duration.toString());
			if (intValue > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating duration.");
		}
		return false;
	}

	private static boolean validateNEVersion(Object NEVersion) {
		if (NEVersion.toString().matches("^[0-9][0-9][A-Z]")) {
			return true;
		}
		return false;
	}

	private static boolean validateIMSI(Object IMSI) {
		try {
			Long longValue = Long.parseLong(IMSI.toString());
			if (longValue > 0 && IMSI.toString().length() <= 15) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating IMSI.");
		}
		return false;
	}

	private static boolean validateHier3(Object hier3) {
		try {
			if(hier3.toString().matches("[0-9]+")) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating hier3.");
		}
		return false;
	}

	private static boolean validateHier32(Object hier32) {
		try {
			if(hier32.toString().matches("[0-9]+")) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating hier32.");
		}
		return false;
	}

	private static boolean validateHier321(Object hier321) {
		try {
			if(hier321.toString().matches("[0-9]+")) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered while validating hier321.");
		}
		return false;
	}

	public static boolean isLeapYear(int year) {
		if ((year % 4 == 0) && year % 100 != 0) {
			return true;
		} else if ((year % 4 == 0) && (year % 100 == 0) && (year % 400 == 0)) {
			return true;
		} else {
			return false;
		}
	}

	private static void logError(Object[] erroneousEntry, StringBuilder errorBuilder) {

		for (int i = 0; i < erroneousEntry.length; i++) {
			errorToString.append("[" + (i + 1) + "]" + erroneousEntry[i].toString() + " ");
		}
		errorToString.append("\r\n");
		errorToString.append("----------------------\r\n");
		errorToString.append(errorBuilder.toString() + "\r\n");
		errorToString.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\r\n");

		if (rowCount == arrayLength)
			dataErrorLogger.errorsFound(errorToString.toString(), errorToString, fileNameForErrorLogger, errorCount);

		errorBuilder.setLength(0);
	}
	
	private static Base_data[] sendInformationToTheDataLayer(Object[][] arrayToBePersisted){
		SendValidatedInfoToDB db = new SendValidatedInfoToDB();
		Base_data[] bdArray = db.sendData(arrayToBePersisted, counterForDatabaseEntries);
		counterForDatabaseEntries = 0;
		return bdArray;
	}
}
