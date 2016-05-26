package com.validation;

import java.util.ArrayList;

import com.errorlogger.DatabaseErrorLogger;

public class DataValidator {

	private static ArrayList<Integer> eventIdList;
	private static ArrayList<Integer> causeCodes;
	private static ArrayList<Integer> failureClasses;
	private static ArrayList<Integer> UETypes;
	private static ArrayList<Integer> markets;
	private static ArrayList<Integer> operators;

	static DatabaseErrorLogger dataErrorLogger = new DatabaseErrorLogger("Validation Logger");

	public static void setUpDatabaseData() {

		ValidationDataFromJDBC databaseAccessor = new ValidationDataFromJDBC();
		eventIdList = databaseAccessor.getEventIdList();
		causeCodes = databaseAccessor.getCauseCodes();
		failureClasses = databaseAccessor.getFailureClass();
		UETypes = databaseAccessor.getUETypes();
		markets = databaseAccessor.getMarkets();
		operators = databaseAccessor.getOperators();

	}

	public static void validateData(Object[][] tableValuesToValidate, String fileName) {

		setUpDatabaseData();

		for (int i = 0; i < tableValuesToValidate.length; i++) {

			if (!validateDateTime(tableValuesToValidate[i][0])) {
				System.out.println(tableValuesToValidate.toString());
				logError(tableValuesToValidate[i], 1, fileName);
				continue;
			}

			if (!validateEventId(tableValuesToValidate[i][1])) {
				logError(tableValuesToValidate[i], 2, fileName);
				continue;
			}

			if (!validateFailureClass(tableValuesToValidate[i][2])) {
				logError(tableValuesToValidate[i], 3, fileName);
				continue;
			}

			if (!validateUEType(tableValuesToValidate[i][3])) {
				logError(tableValuesToValidate[i], 4, fileName);
				continue;
			}

			if (!validateMarket(tableValuesToValidate[i][4])) {
				logError(tableValuesToValidate[i], 5, fileName);
				continue;
			}

			if (!validateOperator(tableValuesToValidate[i][5])) {
				logError(tableValuesToValidate[i], 6, fileName);
				continue;
			}

			if (!validateCellId(tableValuesToValidate[i][6])) {
				logError(tableValuesToValidate[i], 7, fileName);
				continue;
			}

			if (!validateDuration(tableValuesToValidate[i][7])) {
				logError(tableValuesToValidate[i], 8, fileName);
				continue;
			}

			if (!validateCauseCode(tableValuesToValidate[i][8])) {
				logError(tableValuesToValidate[i], 9, fileName);
				continue;
			}

			if (!validateNEVersion(tableValuesToValidate[i][9])) {
				logError(tableValuesToValidate[i], 10, fileName);
				continue;
			}

			if (!validateIMSI(tableValuesToValidate[i][10])) {
				logError(tableValuesToValidate[i], 11, fileName);
				continue;
			}

			if (!validateHier3(tableValuesToValidate[i][11])) {
				logError(tableValuesToValidate[i], 12, fileName);
				continue;
			}

			if (!validateHier32(tableValuesToValidate[i][12])) {
				logError(tableValuesToValidate[i], 13, fileName);
				continue;
			}

			if (!validateHier321(tableValuesToValidate[i][13])) {
				logError(tableValuesToValidate[i], 14, fileName);
				continue;
			}
		}
	}

	private static boolean validateDateTime(Object dateTime) {
//		System.out.println(dateTime.toString());
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
		if (eventId instanceof Integer) {
			int eventIdValue = (Integer) eventId;
			if (eventIdList.contains(eventIdValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateFailureClass(Object failureClass) {
		if (failureClass instanceof Integer) {
			int failureClassValue = (Integer) failureClass;
			if (failureClasses.contains(failureClassValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateUEType(Object ueType) {
		if (ueType instanceof Integer) {
			int ueTypeValue = (Integer) ueType;
			if (UETypes.contains(ueTypeValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateMarket(Object market) {
		if (market instanceof Integer) {
			int marketValue = (Integer) market;
			if (markets.contains(marketValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateOperator(Object operator) {
		if (operator instanceof Integer) {
			int operatorValue = (Integer) operator;
			if (operators.contains(operatorValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateCellId(Object cellId) {
		if (cellId instanceof Integer) {
			int cellIdValue = (Integer) cellId;
			if (cellIdValue > 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateDuration(Object duration) {
		if (duration instanceof Integer) {
			int durationValue = (Integer) duration;
			if (durationValue > 0) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateCauseCode(Object causeCode) {
		if (causeCode instanceof Integer) {
			int causeCodeValue = (Integer) causeCode;
			if (causeCodes.contains(causeCodeValue)) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateNEVersion(Object NEVersion) {
		if (NEVersion instanceof String) {
			String NEVersionValue = (String) NEVersion;
			if (NEVersionValue.matches("^[0-9][0-9][A-Z]")) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateIMSI(Object IMSI) {
		if (IMSI instanceof Long) {
			Long IMSIValue = (Long) IMSI;
			System.out.println(String.valueOf(IMSIValue).length());
			if (IMSIValue > 0 && String.valueOf(IMSIValue).length() <= 15) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateHier3(Object hier3) {
		if (hier3 instanceof Long) {
			Long hier3Value = (Long) hier3;
			if (hier3Value > 0 && String.valueOf(hier3Value).length() == 19) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateHier32(Object hier32) {
		if (hier32 instanceof Long) {
			Long hier32Value = (Long) hier32;
			if (hier32Value > 0 && String.valueOf(hier32Value).length() == 19) {
				return true;
			}
		}
		return false;
	}

	private static boolean validateHier321(Object hier321) {
		if (hier321 instanceof Long) {
			Long hier321Value = (Long) hier321;
			if (hier321Value > 0 && String.valueOf(hier321Value).length() == 19) {
				return true;
			}
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

	private static void logError(Object[] erroneousEntry, int columnNumber, String fileName) {

		StringBuilder errorToString = new StringBuilder();

		for (Object o : erroneousEntry) {
			errorToString.append(o.toString() + " ");
		}
		System.out.println(errorToString.toString() + "test");
		dataErrorLogger.errorFound(errorToString.toString(), columnNumber, fileName);
	}
}
