//package com.validator.test;
//
//import static org.junit.Assert.*;
//
//import org.junit.Test;
//
//import com.validation.DataValidator;
//
//import junitparams.Parameters;
//
//public class ValidatorTest {
//
//	@Parameters
//	public Object[] validateDateTestParams(){
//		return new Object[]{
//				new Object[]{"01/01/2016 00:00", 4097, 1, 21060800, 344, 930, 4, 1000, 13, "11B", new Long(344930000000011l), new Long(4809532081614990000l), new Long(8226896360947470000l), new Long(1150444940909480000l)},
//				new Object[]{"02/01/2015 00:01"},
//				new Object[]{"15/01/2014 12:00"},
//				new Object[]{"30/01/2013 00:58"},
//				new Object[]{"31/01/2012 00:59"},
//				new Object[]{"01/02/2011 01:00"},
//				new Object[]{"02/02/2010 01:01"},
//				new Object[]{"14/02/2009 01:30"},
//				new Object[]{"28/02/2008 01:58"},
//				new Object[]{"29/02/2012 01:59"}
//		};
//	}
//	
//	@Test
//	@Parameters(method = "validDateTestParams")
//	public void testValidDates(Object[] testData) {
//		assertTrue(DataValidator.validateData(testData, "testFile"));
//	}
//
//}
