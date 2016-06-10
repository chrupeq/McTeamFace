package com.validation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Readin {

	//static Scanner sc;

	static ValidationDataFromJDBC vd = new ValidationDataFromJDBC();
	
	private static List<int[]> intArrays;
	

	public static void main(String[] args) throws FileNotFoundException {
		readIn();
	}

	public static void readIn() throws FileNotFoundException {
		JDBCConnectionManager jdbc = new JDBCConnectionManager();
	//	jdbc.initiateDatabase();
		String s = "null";
		System.out.println(s);
//		intArrays = (ArrayList<int[]>) vd.getMarketsAndOperators();
//		// File file = new File("C:\\Users\\A00226084\\Desktop\\readin.txt");
//		File file2 = new File("C:\\Users\\A00226084\\Desktop\\readin2.txt");
//		// sc = new Scanner(file);
//		PrintWriter pw = new PrintWriter(file2);
//		for(int i = 0; i < intArrays.get(0).length; i ++) {
//			
//			pw.write("new Object[]{" + (intArrays.get(1)[i] + 1) + ", " +intArrays.get(0)[i] + "}, ");
//			pw.write("new Object[]{" + (intArrays.get(1)[i] - 1) + ", " +intArrays.get(0)[i] + "}, ");
//			pw.write("new Object[]{" + intArrays.get(1)[i] + ", " +(intArrays.get(0)[i] + 1) + "}, ");
//			pw.write("new Object[]{" + intArrays.get(1)[i] + ", " +(intArrays.get(0)[i] - 1) + "}, ");
//			pw.write("new Object[]{" + (intArrays.get(1)[i] + 1) + ", " +(intArrays.get(0)[i] + 1) + "}, ");
//			pw.write("new Object[]{" + (intArrays.get(1)[i] - 1) + ", " +(intArrays.get(0)[i] - 1) + "}, ");


//		}
//		pw.flush();
//		pw.close();

	}
}
