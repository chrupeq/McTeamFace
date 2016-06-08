package com.ait.reader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.validation.DataValidator;

/**
 * @author A00236958
 * In this class, the excel file is read from a specified
 * location on the hard drive and then validated for errors.
 */
public class ReadDataSetIntoMainMemory {

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	private static ArrayList<Object[][]> arrayListOfSheets = new ArrayList<Object[][]>();
	private static String fileName = "C:\\Users\\A00226084\\Downloads\\AIT Group Project - Sample Dataset.xls";
//	private static String fileName2 = "C:\\Users\\Garrett\\Documents\\manualTest.xls";
	private static String makeFileNameForErrorLog = "ErrorLog";

	public static void main(String[] args) throws Exception {
		readFileInFromHardDrive(fileName);
	}

	/**
	 * Reads in the excel file from a specified location on the hard drive.
	 * Passes on the excel file to the readInTheData method, and then to the
	 * passTheArrayToValidator method, then finally returns an ArrayList of the
	 * excel sheets.
	 * 
	 * @param fileName
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static ArrayList<Object[][]> readFileInFromHardDrive(final String fileName)
			throws InvalidFormatException, IOException {

		final Workbook dataSetWorkbook = WorkbookFactory.create(new File(fileName));

		for (int sheetNumber = 0; sheetNumber <= 0; sheetNumber++) {
			final Object[][] sheet = convertDataSetSheetIntoObjectArray(dataSetWorkbook, sheetNumber);

			arrayListOfSheets.add(sheet);
		}
		
		passTheArrayToValidator(arrayListOfSheets.get(0), makeFileNameForErrorLog);
		return arrayListOfSheets;
	}

	/**
	 * The Workbook and sheetNumber are passed into this method for converting
	 * the sheet into a 2D object array filled with cells from the sheet. This
	 * is essential for the validator to check the Workbook for errors.
	 * 
	 * @param dataSetWorkbook
	 * @param sheetNumber
	 * @return
	 */
	public static Object[][] convertDataSetSheetIntoObjectArray(final Workbook dataSetWorkbook, final int sheetNumber) {
		final Sheet sheet = dataSetWorkbook.getSheetAt(sheetNumber);

		Row row;
		Cell cell;
		Cell dateCell;

		int rows;
		rows = sheet.getPhysicalNumberOfRows();

		int columns = 0;
		int numOfDataCellsPerRow = 0;

		// This for loop ensures that we get the data properly
		// even if the data doesn't start from first few rows
		for (int j = 0; j < 10 || j < rows; j++) {
			row = sheet.getRow(j);
			if (row != null) {
				numOfDataCellsPerRow = sheet.getRow(j).getPhysicalNumberOfCells();
				if (numOfDataCellsPerRow > columns) {
					columns = numOfDataCellsPerRow;
				}
			}
		}

		Object[][] sheetObject = new Object[rows][columns];
		for (int r = 1; r < rows; r++) {
			row = sheet.getRow(r);
			for (int c = 0; c < columns; c++) {
				if (sheetNumber == 0 && c == 0) {
					dateCell = row.getCell(c);
					sheetObject[r][c] = dateFormatter.format(dateCell.getDateCellValue());
				} else {
					cell = row.getCell(c);
					cell.setCellType(Cell.CELL_TYPE_STRING);
					sheetObject[r][c] = (String) cell.toString();
				}
			}
		}
		return sheetObject;
	}

	/**
	 * Here the ArrayList of sheets are passed to the DataValidator class where
	 * the sheets will be validated for errors.
	 * 
	 * @param objects
	 * @param filename
	 */
	public static void passTheArrayToValidator(final Object[][] sheet,
			final String makeFileNameForErrorLog) {
			DataValidator.validateData(sheet, makeFileNameForErrorLog);
	}
}