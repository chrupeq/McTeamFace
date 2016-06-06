package com.validator.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.validation.DataValidator;
import com.validation.JDBCConnectionManager;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import utilities.ValidatorTestUtilities;

@RunWith(JUnitParamsRunner.class)
public class ValidatorTestPartTwo extends ValidatorTestUtilities {

	Method validateCellId;
	Method validateDuration;
	Method validateCauseCode;
	Method validateNEVersion;
	Method validateIMSI;

	JDBCConnectionManager connectionManager;



	@Parameters
	public Object[] validCellIdParams() {
		return new Object[] { new Object[] { 1 }, new Object[] { 2 }, new Object[] { 5 }, new Object[] { 9 },
				new Object[] { 10 }, new Object[] { 99 }, new Object[] { 100 }, new Object[] { 101 },
				new Object[] { 150 }, new Object[] { 200 }, new Object[] { 999 }, new Object[] { 1000 },
				new Object[] { 1001 }, new Object[] { 1500 }, new Object[] { 2000 }, new Object[] { 3 },
				new Object[] { 7 }, new Object[] { 50 }, new Object[] { 25 }, new Object[] { 75 }, new Object[] { 125 },
				new Object[] { 175 }, new Object[] { 500 }, new Object[] { 750 }, new Object[] { 1250 },
				new Object[] { 1750 } };
	}

	@Parameters
	public Object[] invalidCellIdParams() {
		return new Object[] { new Object[] { -1 }, new Object[] { -12 }, new Object[] { -123 }, new Object[] { -1234 },
				new Object[] { 0 }, new Object[] { 00000 }, new Object[] { 0000 }, new Object[] { 000 },
				new Object[] { 00 }, new Object[] { -0 }, new Object[] { -00 }, new Object[] { -000 },
				new Object[] { -0000 }, new Object[] { "a" }, new Object[] { "abc" }, new Object[] { "ab" },
				new Object[] { "-o" }, new Object[] { ".,?" }, new Object[] { "?" }, new Object[] { ",?" } };
	}

	@Parameters
	public Object[] validDurationParams() {
		return new Object[] { new Object[] { 1 }, new Object[] { 2 }, new Object[] { 5 }, new Object[] { 9 },
				new Object[] { 10 }, new Object[] { 99 }, new Object[] { 100 }, new Object[] { 101 },
				new Object[] { 150 }, new Object[] { 200 }, new Object[] { 999 }, new Object[] { 1000 },
				new Object[] { 1001 }, new Object[] { 1500 }, new Object[] { 2000 }, new Object[] { 3 },
				new Object[] { 7 }, new Object[] { 50 }, new Object[] { 25 }, new Object[] { 75 }, new Object[] { 125 },
				new Object[] { 175 }, new Object[] { 500 }, new Object[] { 750 }, new Object[] { 1250 },
				new Object[] { 1750 } };
	}
	
	@Parameters
	public Object[] invalidDurationParams(){
		return new Object[] { new Object[] { -1 }, new Object[] { -12 }, new Object[] { -123 }, new Object[] { -1234 },
				new Object[] { 0 }, new Object[] { 00000 }, new Object[] { 0000 }, new Object[] { 000 },
				new Object[] { 00 }, new Object[] { -0 }, new Object[] { -00 }, new Object[] { -000 },
				new Object[] { -0000 }, new Object[] { "a" }, new Object[] { "abc" }, new Object[] { "ab" },
				new Object[] { "-o" }, new Object[] { ".,?" }, new Object[] { "?" }, new Object[] { ",?" } };
	}
	
	@Parameters
	public Object[] validCauseCodesParams(){
		return new Object[] {
			new Object[]{1}, new Object[]{2}, new Object[]{3}, new Object[]{4}, new Object[]{5}, new Object[]{6}, new Object[]{7}, new Object[]{8}, 
			new Object[]{9}, new Object[]{10}, new Object[]{11}, new Object[]{12}, new Object[]{13}, new Object[]{14}, new Object[]{15}, new Object[]{16}, 
			new Object[]{17}, new Object[]{18}, new Object[]{19}, new Object[]{20}, new Object[]{21}, new Object[]{22}, new Object[]{23}, new Object[]{24}, 
			new Object[]{25}, new Object[]{26}, new Object[]{27}, new Object[]{28}, new Object[]{29}, new Object[]{30}, new Object[]{31}, new Object[]{32}, 
			new Object[]{33}, new Object[]{"(null)"}, new Object[]{0}
		};
	}
	
	
	@Parameters
	public Object[] invalidCauseCodesParams(){
		return new Object[]{
				new Object[]{-2}, new Object[]{-1}, new Object[]{34}, new Object[]{35}, new Object[]{100}, new Object[]{",.()"}, new Object[]{"mn.,"}, 
				new Object[]{-35}, new Object[]{"A26"}, new Object[]{"2A6"}, new Object[]{"26A"}, new Object[]{"suns out, guns out"}
		};
	}

	@Parameters
	public Object[] validNEVersionParams(){
		return new Object[]{
				new Object[]{"11B"}, new Object[]{"12A"}, new Object[]{"13C"}, new Object[]{"14D"}, new Object[]{"15E"}, new Object[]{"16F"}, new Object[]{"17G"}, 
				new Object[]{"18H"}, new Object[]{"19I"}, new Object[]{"20J"}, new Object[]{"21K"}, new Object[]{"22L"}, new Object[]{"23M"}, new Object[]{"24N"}, 
				new Object[]{"25Y"}, new Object[]{"26Z"}
		};
	}
	
	@Parameters
	public Object[] invalidNEVersionParams(){
		return new Object[]{
			new Object[]{"111B"}, new Object[]{"1B"}, new Object[]{"0B"}, new Object[]{"11BB"}, new Object[]{"11BBB"}, new Object[]{"11"}, new Object[]{"1B"}, 
			new Object[]{"1BB"}, new Object[]{"1BBB"}, new Object[]{"-11B"}, new Object[]{"11AB"}, new Object[]{"11-B"}, new Object[]{"11@"}, new Object[]{"1-1B"}, 
			new Object[]{"10-1B"}, new Object[]{"11 B"}, new Object[]{"1 1B"}, new Object[]{" 11B"}, new Object[]{"11B "}   
		};
	}
	
	@Parameters
	public Object[] validIMSIParams(){
		return new Object[]{
			new Object[]{new Long(344930000000011l)}, new Object[]{new Long(344930000000011l)}, new Object[]{new Long(34493000000001l)},
			new Object[]{new Long(3449300000000l)}, new Object[]{new Long(344930000000l)}, new Object[]{new Long(34493000000l)}, 
			new Object[]{new Long(3449300000l)}, new Object[]{new Long(34493000l)}, new Object[]{new Long(3449300l)}, new Object[]{new Long(34493)},
			new Object[]{new Long(3)}, new Object[]{new Long(344)}, new Object[]{new Long(34)}
		};
	}
	
	@Parameters
	public Object[] invalidIMSIParams(){
		return new Object[]{
			new Object[]{new Long(1234123412341232l)}, new Object[]{new Long(34493000000001111l)}, new Object[]{new Long("344930A000000011l")}, 
			new Object[]{new Long("AAAABBNBNJJNBNM")}, new Object[]{new Long("./;'[")}, new Object[]{new Long("3449300090IInIl")}, new Object[]{new Long("000000000000000l")},    
		};
	}
	@Before
	public void setUp() {
		try {
			validateCellId = DataValidator.class.getDeclaredMethod("validateCellId", Object.class);
			validateDuration = DataValidator.class.getDeclaredMethod("validateDuration", Object.class);
			validateCauseCode = DataValidator.class.getDeclaredMethod("validateCauseCode", Object.class);
			validateNEVersion = DataValidator.class.getDeclaredMethod("validateNEVersion", Object.class);
			validateIMSI = DataValidator.class.getDeclaredMethod("validateIMSI", Object.class);
			validateCellId.setAccessible(true);
			validateDuration.setAccessible(true);
			validateCauseCode.setAccessible(true);
			validateNEVersion.setAccessible(true);
			validateIMSI.setAccessible(true);

			DataValidator.setUpDatabaseData();

		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Method may not exist. Set up method in ValidatorTest.");
			e.printStackTrace();
		}
	}

	@Test
	@Parameters(method = "validCellIdParams")
	public void testValidCellId(Object cellId)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean areCellIdsValid = (boolean) validateCellId.invoke(DataValidator.class, cellId);
		assertTrue(areCellIdsValid);
	}

	@Test
	@Parameters(method = "invalidCellIdParams")
	public void testInvalidCellId(Object cellId)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean areCellIdsValid = (boolean) validateCellId.invoke(DataValidator.class, cellId);
		assertFalse(areCellIdsValid);
	}
	
	@Test
	@Parameters(method = "validDurationParams")
	public void testValidDuration(Object duration) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isDurationValid = (boolean) validateDuration.invoke(DataValidator.class, duration);
		assertTrue(isDurationValid);
	}
	
	@Test
	@Parameters(method = "invalidDurationParams")
	public void testInvalidDuration(Object duration) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isDurationValid = (boolean) validateDuration.invoke(DataValidator.class, duration);
		assertFalse(isDurationValid);
	}
	
	@Test
	@Parameters(method = "validCauseCodesParams")
	public void testValidCauseCodes(Object causeCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isCauseCodeValid = (boolean) validateCauseCode.invoke(DataValidator.class, causeCode);
		assertTrue(isCauseCodeValid);
	}
	
	@Test
	@Parameters(method = "invalidCauseCodesParams")
	public void testInvalidCauseCodes(Object causeCode) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isCauseCodeValid = (boolean) validateCauseCode.invoke(DataValidator.class, causeCode);
		assertFalse(isCauseCodeValid);
	}
	
	@Test
	@Parameters(method = "validNEVersionParams")
	public void testValidNEVersions(Object NEVersion) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isNEVersionValid = (boolean) validateNEVersion.invoke(DataValidator.class, NEVersion);
		assertTrue(isNEVersionValid);
	}
	
	@Test
	@Parameters(method = "invalidNEVersionParams")
	public void testInvalidNEVersions(Object NEVersion) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isNEVersionValid = (boolean) validateNEVersion.invoke(DataValidator.class, NEVersion);
		assertFalse(isNEVersionValid);
	}
	
	@Test
	@Parameters(method = "validIMSIParams")
	public void testValidIMSI(Object IMSI) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isIMSIValid = (boolean) validateIMSI.invoke(DataValidator.class, IMSI);
		assertTrue(isIMSIValid);
	}
	
	@Test
	@Parameters(method = "invalidIMSIParams")
	public void testInvalidIMSI(Object IMSI) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		boolean isIMSIValid = (boolean) validateIMSI.invoke(DataValidator.class, IMSI);
		assertFalse(isIMSIValid);
	}
}
