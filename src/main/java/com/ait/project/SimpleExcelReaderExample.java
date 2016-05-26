package com.ait.project;

//LS I commented them out as eclipse said they unused

/*import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;*/

/*import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;*/
import java.io.File;
import java.io.FileInputStream;
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




public class SimpleExcelReaderExample {

	public static void main(String[] args) throws Exception {
		Object[] array = readFileInFromHardDrive();
		int size = array.length;
		System.out.println(size);
		//System.out.println(array[7]);
		System.out.println(Arrays.asList(array).contains(null));
	}

	public static Object[] readFileInFromHardDrive() {
		String fileName = "C:\\Users\\A00236958\\Documents\\AIT Group Project - Sample Dataset.xls";
		Object[] arrayOfArrays = new Object[5];
		
		try {

//			POIFSFileSystem dataSetExcelFile;
//
//			dataSetExcelFile = new POIFSFileSystem(
//					new FileInputStream(fileName));
//
//			HSSFWorkbook dataSetWorkbook = new HSSFWorkbook(dataSetExcelFile);
			Workbook dataSetWorkbook = WorkbookFactory.create(new File(fileName));
			

			// getSheetAt(0) gets the first sheet in the excel file!
			for (int i = 0; i <= 4; i++) {
				Object[][] array = readInTheData(dataSetWorkbook, i);
				System.out.println(array.toString());
				arrayOfArrays[i] = array;
				
				passTheArrayToValidator(array, fileName);
			}
		
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		 return arrayOfArrays;
		
	}

	
	public static Object[][] readInTheData(Workbook dataSetWorkbook, int i) {
		Sheet sheet = dataSetWorkbook.getSheetAt(i);
		Row row;
		Cell cell;
		DataFormatter formatter = new DataFormatter();

		int rows; // No of rows
		rows = sheet.getPhysicalNumberOfRows();
		

		int columns = 0; // No of columns
		int numOfDataCellsPerRow = 0;

		// This trick ensures that we get the data properly even if it
		// doesn't start from first few rows
		for (int j = 0; j < 10 || j < rows; j++) {
			row = sheet.getRow(j);
			if (row != null) {
				numOfDataCellsPerRow = sheet.getRow(j)
						.getPhysicalNumberOfCells();
				if (numOfDataCellsPerRow > columns) {
					columns = numOfDataCellsPerRow;
				}
			}
		}
		
		Object[][] sheetObject = new Object[rows][columns];

		for (int r = 1; r < rows; r++) {
			row = sheet.getRow(r);
			if (row != null) {
				for (int c = 0; c < columns; c++) {
					
					cell = row.getCell(c);
					if(c!=0)
					cell.setCellType(Cell.CELL_TYPE_STRING);
					if (cell != null) {					
						System.out.print(cell + "\t");
						sheetObject[r][c] = cell;
					}
				}
			}
			System.out.println("\n");
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		
		return sheetObject;
	}

	
	public static void passTheArrayToValidator(Object[][] array, String filename) {
		//DataValidator.validData(array, filename);
		System.out.println("WE ARE IN!!!");
		
		
		
	}
}