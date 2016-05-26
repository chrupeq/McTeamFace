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
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;


public class SimpleExcelReaderExample {

	 public static void main(String[] args) throws Exception
	    {
		 try {
			    POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream("C:\\Users\\A00236958\\Documents\\AIT Group Project - Sample Dataset.xls"));
			    HSSFWorkbook wb = new HSSFWorkbook(fs);
			    HSSFSheet sheet = wb.getSheetAt(0);
			    HSSFRow row;
			    HSSFCell cell;
			    
			    //some craic
			    
			    int rows; // No of rows
			    rows = sheet.getPhysicalNumberOfRows();

			    int cols = 0; // No of columns
			    int tmp = 0;

			    // This trick ensures that we get the data properly even if it doesn't start from first few rows
			    for(int i = 0; i < 10 || i < rows; i++) {
			        row = sheet.getRow(i);
			        if(row != null) {
			            tmp = sheet.getRow(i).getPhysicalNumberOfCells();
			            if(tmp > cols) cols = tmp;
			        }
			    }

			    for(int r = 0; r < rows; r++) {
			        row = sheet.getRow(r);
			        if(row != null) {
			            for(int c = 0; c < cols; c++) {
			                cell = row.getCell(c);
			                if(cell != null) {
			                    // Your code here
			                	System.out.print(cell+"\t");
			                }
			            }
			        }
			        System.out.println("\n");
			    }
			} catch(Exception ioe) {
			    ioe.printStackTrace();
			}
	    }
}