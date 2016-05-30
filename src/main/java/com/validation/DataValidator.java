package com.validation;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;

import com.errorlogger.DatabaseErrorLogger;
import com.errorlogger.DateManipulator;

public class DataValidator {

	private static ArrayList<Integer> eventIdList;
	private static ArrayList<Integer> causeCodes;
	private static ArrayList<Integer> failureClasses;
	private static ArrayList<Integer> UETypes;
	private static HashMap<Integer, Integer> marketsAndOperators;

	private static DataFormatter formatter;
	private static StringBuilder errorBuilder = new StringBuilder();
	private static StringBuilder errorToString = new StringBuilder();

	private static int arrayLength;
	private static int rowCount = 2;
	private static String fileNameForErrorLogger;

	static DatabaseErrorLogger dataErrorLogger = new DatabaseErrorLogger("Validation Logger");

	public static void setUpDatabaseData() {

		ValidationDataFromJDBC databaseAccessor = new ValidationDataFromJDBC();
		eventIdList = databaseAccessor.getEventIdList();
		causeCodes = databaseAccessor.getCauseCodes();
		failureClasses = databaseAccessor.getFailureClass();
		UETypes = databaseAccessor.getUETypes();
		marketsAndOperators = databaseAccessor.getMarketsAndOperators();

		formatter = new DataFormatter();

	}

	public static void validateData(Object[][] tableValuesToValidate, String fileName) {

		setUpDatabaseData();

		arrayLength = tableValuesToValidate.length;
		fileNameForErrorLogger = fileName;

		for (int i = 1; i < tableValuesToValidate.length; i++) {

			if (!validateDateTime(tableValuesToValidate[i][0])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid date at column: 1 Line: " + i + "\n");
			}

			if (!validateEventId(tableValuesToValidate[i][1])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid Event ID at column: 2 Line: " + i + "\n");
			}

			if (!validateFailureClass(tableValuesToValidate[i][2])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid failure class at column: 3 Line: " + i + "\n");
			}

			if (!validateUEType(tableValuesToValidate[i][3])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid UE Type at column: 4 Line: " + i + "\n");
			}

			if (!validateMarketAndOperator(tableValuesToValidate[i][4], tableValuesToValidate[i][5])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid or unmatched data at column: 5 & 6 Line: " + i + "\n");
			}

			if (!validateCellId(tableValuesToValidate[i][6])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid Cell ID at column: 7 Line: " + i + "\n");
			}

			if (!validateDuration(tableValuesToValidate[i][7])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime()
						+ ": Invalid call duration at column: 8 Line: " + i + "\n");
			}

			if (!validateCauseCode(tableValuesToValidate[i][8])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime() + ": Invalid Cause Code at column: 9 Line: "
						+ i + "\n");
			}

			if (!validateNEVersion(tableValuesToValidate[i][9])) {
				errorBuilder.append(DateManipulator.getCurrentDateAndTime() + ": Invalid NEVersion at column: 10 Line: "
						+ i + "\n");
			}

			if (!validateIMSI(tableValuesToValidate[i][10])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid IMSI at column 11 Line: " + i + "\n");
			}

			if (!validateHier3(tableValuesToValidate[i][11])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER3 at column: 12 Line: " + i + "\n");
			}

			if (!validateHier32(tableValuesToValidate[i][12])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER32 at column: 13 Line: " + i + "\n");
			}

			if (!validateHier321(tableValuesToValidate[i][13])) {
				errorBuilder.append(
						DateManipulator.getCurrentDateAndTime() + ": Invalid HIER321 at column: 14 Line: " + i + "\n");
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
				int day = Integer.parseInt(brokenUpDates[0]);
				int year = Integer.parseInt(brokenUpDates[2]);

				if (brokenUpDates[1].matches("0[1-9]|1[0-2]")) {
					if (day <= 28 && day > 0) {
						return true;
					} else if (day <= 30 && day > 0 && brokenUpDates[1].matches("04|06|09|11")) {
						return true;
					} else if (day <= 31 && brokenUpDates[1].matches("01|03|05|07|08|12")) {
						return true;
					} else if (isLeapYear(year) && brokenUpDates[1].equals("02") && day <= 29 && day > 0) {
						return true;
					} else {
						return false;
					}
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
			if (eventIdList.contains(intValue)) {
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
			if (failureClasses.contains(intValue)) {
				return true;
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
			if (UETypes.contains(intValue)) {
				return true;
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
			for (int key : marketsAndOperators.keySet()) {
				if (marketIntValue == key && operatorIntValue == marketsAndOperators.get(key))
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
			if (causeCodes.contains(intValue)) {
				return true;
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

		for (int i = 0; i < erroneousEntry.length; i ++) {
			errorToString.append("[" + (i + 1) + "]" + erroneousEntry[i].toString() + " ");
		}
		errorToString.append("\n");
		errorToString.append("----------------------\n");
		errorToString.append(errorBuilder.toString() + "\n");
		errorToString.append("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");

		if (rowCount == arrayLength)
			dataErrorLogger.errorsFound(errorToString.toString(), errorToString, fileNameForErrorLogger);

		errorBuilder.setLength(0);
	}
}
