package com.ait.project;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.validation.DataValidator;

public class SimpleExcelReaderExample {

	static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	static ArrayList<Object[][]> arrays = new ArrayList<Object[][]>();

	public static void main(String[] args) throws Exception {
		arrays = readFileInFromHardDrive();
		
		// System.out.println(array[7]);
	}

	public static ArrayList<Object[][]> readFileInFromHardDrive() {
		String fileName = "C:\\Users\\A00226084\\Downloads\\AIT Group Project - Sample Dataset.xls";
		String makeFileName = "testfile";

		try {

			Workbook dataSetWorkbook = WorkbookFactory.create(new File(fileName));

			for (int i = 0; i <= 4; i++) {
				Object[][] array = readInTheData(dataSetWorkbook, i);
				System.out.println(array.toString());
				arrays.add(array);

			}
			passTheArrayToValidator(arrays, makeFileName);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return arrays;

	}

	public static Object[][] readInTheData(Workbook dataSetWorkbook, int i) {
		Sheet sheet = dataSetWorkbook.getSheetAt(i);

		Row row;
		Cell cell;
		Cell dateCell;

		int rows; // No of rows
		rows = sheet.getPhysicalNumberOfRows();

		int columns = 0; // No of columns
		int numOfDataCellsPerRow = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
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
		String date = "";
		for (int r = 1; r < rows; r++) {
			row = sheet.getRow(r);
			if (row != null) {
				for (int c = 0; c < columns; c++) {
					if (i == 0) {
						if (c == 0) {
							dateCell = row.getCell(c);

							sheetObject[r][c] = dateFormatter.format(dateCell.getDateCellValue());
						} else {
							cell = row.getCell(c);
							cell.setCellType(Cell.CELL_TYPE_STRING);
							sheetObject[r][c] = cell;
						}
					} else {
						cell = row.getCell(c);
						cell.setCellType(Cell.CELL_TYPE_STRING);
						sheetObject[r][c] = cell;
					}
				}
			}
			System.out.println("\n");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");

		return sheetObject;
	}

	public static void passTheArrayToValidator(ArrayList<Object[][]> arrays, String filename) {

		for (Object[][] o : arrays) {
			DataValidator.validateData(o, filename);
		}
		System.out.println("WE ARE IN!!!");

	}
}