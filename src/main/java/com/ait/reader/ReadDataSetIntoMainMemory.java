package com.ait.reader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;
import com.validation.DataValidator;

/**
 * @author A00236958
 * In this class, the excel file is read from a specified
 * location on the hard drive and then validated for errors.
 */
public class ReadDataSetIntoMainMemory {
	
	@EJB
	private NetworkEntityDAO networkEntityDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	private NetworkEntityType networkEntityTypeEnum;

	private static SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy hh:mm");
	private static ArrayList<Object[][]> arrayListOfSheets = new ArrayList<Object[][]>();
	private static String fileName = "C:\\Users\\A00226084\\Downloads\\AIT Group Project - Sample Dataset.xls";
//	private static String fileName2 = "C:\\Users\\Garrett\\Documents\\manualTest.xls";
	private static String makeFileNameForErrorLog = "ErrorLog";
	static Base_data[] base_data;
	static Failure_class[] failure_class;
	static Event_cause[] event_cause;
	static Mcc_mnc[] mcc_mnc;
	static User_equipment[] user_equipment;

//	public static void main(String[] args) throws Exception {
//		readFileInFromHardDrive("C:\\Users\\A00226084\\Desktop\\tempdsf.xls");
//	}

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
	public static NetworkEntity[] readFileInFromHardDrive(final String fileName, int sheetValidationNumber)
			throws IOException {

		FileInputStream fos = new FileInputStream(new File(fileName));
		
		Workbook dataSetWorkbook = null;
		try {
			dataSetWorkbook = WorkbookFactory.create(fos);
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		for (int sheetNumber = 0; sheetNumber <= 4; sheetNumber++) {
			final Object[][] sheet = convertDataSetSheetIntoObjectArray(dataSetWorkbook, sheetNumber);

			arrayListOfSheets.add(sheet);
		}
		
		
		switch(sheetValidationNumber){
		case 0: 
			base_data = passTheArrayToValidator(arrayListOfSheets.get(0), makeFileNameForErrorLog);
			return base_data;
			break;
		case 1:
			failure_class = NonBaseDataObjects.createFailureClass(arrayListOfSheets.get(1));
			return failure_class;
			break;
		case 2:
			event_cause = NonBaseDataObjects.createEventCauseClass(arrayListOfSheets.get(2));
			return event_cause;
			break;
		case 3:
			mcc_mnc = NonBaseDataObjects.createMccMncclass(arrayListOfSheets.get(3));
			return mcc_mnc;
			break;
		case 4:
			user_equipment = NonBaseDataObjects.createUserEquipmentclass(arrayListOfSheets.get(4));
			return user_equipment;
			break;
		}
		
		fos.close();
		
		return null;
//		return arrayListOfSheets;
		
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
	public static Base_data[] passTheArrayToValidator(final Object[][] sheet,
			final String makeFileNameForErrorLog) {
			Base_data[] bdArray = DataValidator.validateData(sheet, makeFileNameForErrorLog);
			return bdArray;
	}
}