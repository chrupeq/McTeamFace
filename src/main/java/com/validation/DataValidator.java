package com.validation;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

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

	private static DataFormatter formatter;
	private static StringBuilder errorBuilder = new StringBuilder();
	private static StringBuilder errorToString = new StringBuilder();

	private static int arrayLength;
	private static int rowCount = 2;
	private static String fileNameForErrorLogger;
	private static int errorCount;

	static DatabaseErrorLogger dataErrorLogger = new DatabaseErrorLogger("Validation Logger");

	public static void setUpDatabaseData() {

		ValidationDataFromJDBC databaseAccessor = new ValidationDataFromJDBC();
		eventIdList = databaseAccessor.getEventIdList();
		causeCodes = databaseAccessor.getCauseCodes();
		failureClasses = databaseAccessor.getFailureClass();
		UETypes = databaseAccessor.getUETypes();
		marketsAndOperators = databaseAccessor.getMarketsAndOperators();

		MNCValues = marketsAndOperators.get(0);
		MCCValues = marketsAndOperators.get(1);

		formatter = new DataFormatter();

	}

	public static void validateData(Object[][] tableValuesToValidate, String fileName) {

		setUpDatabaseData();

		arrayLength = tableValuesToValidate.length;
		fileNameForErrorLogger = fileName;

		for (int i = 1; i < tableValuesToValidate.length; i++) {

			if (!validateDateTime(tableValuesToValidate[i][0])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid date at column: 1 Line: " + i + "\r\n");
				errorCount++;
			}

			if (!validateEventId(tableValuesToValidate[i][1])) {
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

			if (!validateCauseCode(tableValuesToValidate[i][8])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime() + ": Invalid Cause Code at column: 9 Line: "
						+ i + "\r\n");
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
			}
		}
	}

	private static boolean validateDateTime(Object dateTime) {
		// System.out.println(dateTime.toString());
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

	private static boolean validateEventId(Object eventId) {
		Cell c = (Cell) eventId;
		String eventIdValue = formatter.formatCellValue(c);
		try {
			int intValue = Integer.parseInt(eventIdValue);
			
			for(int i = 0; i < eventIdList.length; i ++)
			if (eventIdList[i] == intValue) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateFailureClass(Object failureClass) {
		Cell c = (Cell) failureClass;
		String failureClassValue = formatter.formatCellValue(c);
		try {
			if (failureClass.toString().equals("(null)")) {
				return true;
			}
			int intValue = Integer.parseInt(failureClassValue);
			for(int i = 0; i < failureClasses.length; i ++){
			if (failureClasses[i] == intValue) {
				return true;
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}

		return false;
	}

	private static boolean validateUEType(Object ueType) {
		Cell c = (Cell) ueType;
		String ueTypeValue = formatter.formatCellValue(c);
		try {
			int intValue = Integer.parseInt(ueTypeValue);
			for(int i = 0; i < UETypes.length; i ++){
			if (UETypes[i] == intValue) {
				return true;
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateMarketAndOperator(Object market, Object operator) {
		Cell c = (Cell) market;
		Cell o = (Cell) operator;
		String marketValue = formatter.formatCellValue(c);
		String operatorValue = formatter.formatCellValue(o);
		try {
			int marketIntValue = Integer.parseInt(marketValue);
			int operatorIntValue = Integer.parseInt(operatorValue);
			for (int i = 0; i < MNCValues.length; i++) {
				if (marketIntValue == MNCValues[i] && operatorIntValue == MCCValues[i])
					return true;
			}

		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}

		return false;
	}

	private static boolean validateCellId(Object cellId) {
		Cell c = (Cell) cellId;
		String cellIdValue = formatter.formatCellValue(c);
		try {
			int intValue = Integer.parseInt(cellIdValue);
			if (intValue > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}

		return false;
	}

	private static boolean validateDuration(Object duration) {
		Cell c = (Cell) duration;
		String durationValue = formatter.formatCellValue(c);
		try {
			int intValue = Integer.parseInt(durationValue);
			if (intValue > 0) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateCauseCode(Object causeCode) {
		Cell c = (Cell) causeCode;
		String causeCodeValue = formatter.formatCellValue(c);
		try {
			if (causeCodeValue.equals("(null)")) {
				return true;
			}
			int intValue = Integer.parseInt(causeCodeValue);
			for(int i = 0; i < causeCodes.length; i ++){
			if (causeCodes[i] == intValue) {
				return true;
			}
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateNEVersion(Object NEVersion) {
		Cell c = (Cell) NEVersion;
		String NEVersionValue = formatter.formatCellValue(c);
		if (NEVersionValue.matches("^[0-9][0-9][A-Z]")) {
			return true;
		}
		return false;
	}

	private static boolean validateIMSI(Object IMSI) {
		Cell c = (Cell) IMSI;
		String IMSIValue = formatter.formatCellValue(c);
		try {
			Long longValue = Long.parseLong(IMSIValue);
			if (longValue > 0 && IMSIValue.length() <= 15) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateHier3(Object hier3) {
		Cell c = (Cell) hier3;
		String hier3Value = formatter.formatCellValue(c);
		try {
			Long longValue = Long.parseLong(hier3Value);
			if (longValue > 0 && hier3Value.length() == 19) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateHier32(Object hier32) {
		Cell c = (Cell) hier32;
		String hier32Value = formatter.formatCellValue(c);
		try {
			Long longValue = Long.parseLong(hier32Value);
			if (longValue > 0 && hier32Value.length() == 19) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
		}
		return false;
	}

	private static boolean validateHier321(Object hier321) {
		Cell c = (Cell) hier321;
		String hier321Value = formatter.formatCellValue(c);
		try {
			Long longValue = Long.parseLong(hier321Value);
			if (longValue > 0 && hier321Value.length() == 19) {
				return true;
			}
		} catch (Exception e) {
			System.out.println("Invalid datatype encountered.");
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
}
