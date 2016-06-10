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
public class ValidatorTestPartOne extends ValidatorTestUtilities {


	Method validateEventIdAndCauseCode;
	Method validateFailureClassMethod;
	Method validateUETypesMethod;

	JDBCConnectionManager connectionManager;



	@Parameters
	public Object[] validEventIdParams() {
		return new Object[]{
			new Object[]{0, 4097}, new Object[]{1, 4097}, new Object[]{2, 4097}, new Object[]{3, 4097}, new Object[]{4, 4097}, new Object[]{5, 4097},
			new Object[]{0, 4098}, new Object[]{1, 4098}, new Object[]{2, 4098}, new Object[]{3, 4098}, new Object[]{0, 4106}, new Object[]{1, 4106}
		};
	}

	@Parameters
	public Object[] invalidEventIdParams() {
		return new Object[] { new Object[] { 0, 4096 }, new Object[] { 1, 4095 }, new Object[] { 2, 4099 },
				new Object[] { 3, 5000.0 }, new Object[] { 4, 4123.0 }, new Object[] { 5, 4124.0 }, new Object[] { 6, 4126 },
				new Object[] { 7, 4127 }, new Object[] {7, 4105 }, new Object[] {7, 4104 }, new Object[] {7, 4107 },
				new Object[] {7, 4109 }, new Object[] {7, 1 }, new Object[] {7, 12 }, new Object[] {7, 123 },
				new Object[] {7, 12345 }, new Object[] {7, 123456 }, new Object[] {7, -1 }, new Object[] {7, -12 },
				new Object[] {7, -123 }, new Object[] {7, -1234 }, new Object[] {7, 0 }, new Object[] {7, 0000 },
				new Object[] {7, 000 }, new Object[] {7, 00 }, new Object[] {7, "A000" }, new Object[] {7, "A4097" },
				new Object[] {7, 40970 }, new Object[] { 7, "04097" }, new Object[] {7, "A4097" }, new Object[] {7, "-.,&" }, new Object[]{7," "},
				new Object[]{7, "  "}, new Object[]{7, "   "}, new Object[]{7,"    "}, new Object[]{7,""} };
	}

	@Parameters
	public Object[] validFailureClassesParams() {
		return new Object[] { new Object[] { "0" }, new Object[] { 1 }, new Object[] { 2 }, new Object[] { 3 },
				new Object[] { 4 }, new Object[] { "(null)" } };
	}

	@Parameters
	public Object[] invalidFailureClassesParams() {
		return new Object[] { new Object[] { -1 }, new Object[] { -2 }, new Object[] { "00" }, new Object[] { "01" },
				new Object[] { "02" }, new Object[] { 20 }, new Object[] { 5 }, new Object[] { 6 },
				new Object[] { "-0" }, new Object[] { "0000" }, new Object[] { "001" }, new Object[] { "0001" },
				new Object[] { -0001 }, new Object[] { "A0022" }, new Object[] { "04097" }, new Object[] { ".,*7&" },
				new Object[] { "0(null)" }, new Object[] { "(null)0" }, new Object[] { "(nu0ll)" },
				new Object[] { "null" }, new Object[]{" "},
				new Object[]{"  "}, new Object[]{"   "}, new Object[]{"    "}, new Object[]{""} };
	}

	@Parameters
	public Object[] validUETypesParams() {
		return new Object[] { new Object[] { 100100 }, new Object[] { 100200 }, new Object[] { 100300 },
				new Object[] { 100400 }, new Object[] { 100500 }, new Object[] { 100600 }, new Object[] { 100700 },
				new Object[] { 100800 }, new Object[] { 100900 }, new Object[] { 101000 }, new Object[] { 101300 },
				new Object[] { 101500 }, new Object[] { 101600 }, new Object[] { 101700 }, new Object[] { 101710 },
				new Object[] { 101800 }, new Object[] { 102000 }, new Object[] { 102100 }, new Object[] { 102200 },
				new Object[] { 102300 }, new Object[] { 102400 }, new Object[] { 102500 }, new Object[] { 102600 },
				new Object[] { 102700 }, new Object[] { 102800 }, new Object[] { 102900 }, new Object[] { 103000 },
				new Object[] { 103100 }, new Object[] { 103200 }, new Object[] { 103300 }, new Object[] { 103500 },
				new Object[] { 103600 }, new Object[] { 103700 }, new Object[] { 103800 }, new Object[] { 103900 },
				new Object[] { 104100 }, new Object[] { 104200 }, new Object[] { 104400 }, new Object[] { 104500 },
				new Object[] { 104600 }, new Object[] { 104700 }, new Object[] { 104800 }, new Object[] { 105000 },
				new Object[] { 105200 }, new Object[] { 105300 }, new Object[] { 105400 }, new Object[] { 105500 },
				new Object[] { 105600 }, new Object[] { 105700 }, new Object[] { 105900 }, new Object[] { 106200 },
				new Object[] { 106400 }, new Object[] { 106500 }, new Object[] { 106600 }, new Object[] { 106700 },
				new Object[] { 106900 }, new Object[] { 107100 }, new Object[] { 107200 }, new Object[] { 107400 },
				new Object[] { 107600 }, new Object[] { 107700 }, new Object[] { 107800 }, new Object[] { 107900 },
				new Object[] { 108000 }, new Object[] { 108100 }, new Object[] { 108200 }, new Object[] { 108300 },
				new Object[] { 108400 }, new Object[] { 116000 }, new Object[] { 21060800 }, new Object[] { 33000153 },
				new Object[] { 33000253 }, new Object[] { 33000353 }, new Object[] { 33000453 },
				new Object[] { 33000553 }, new Object[] { 33000635 }, new Object[] { 33000753 },
				new Object[] { 33000853 }, new Object[] { 33000953 }, new Object[] { 33001053 },
				new Object[] { 33001195 }, new Object[] { 33001235 }, new Object[] { 33001295 },
				new Object[] { 33001453 }, new Object[] { 33001553 }, new Object[] { 33001635 },
				new Object[] { 33001695 }, new Object[] { 33001735 }, new Object[] { 33001835 },
				new Object[] { 33001953 }, new Object[] { 33002053 }, new Object[] { 33002135 },
				new Object[] { 33002195 }, new Object[] { 33002235 }, new Object[] { 33002295 },
				new Object[] { 33002353 }, new Object[] { 33002499 }, new Object[] { 33002535 },
				new Object[] { 33002635 } };
	}

	@Parameters
	public Object[] invalidUETypesParams() {
		return new Object[] { new Object[] { 100101 }, new Object[] { 100099 }, new Object[] { 100201 },
				new Object[] { 100199 }, new Object[] { 100301 }, new Object[] { 100299 }, new Object[] { 100401 },
				new Object[] { 100399 }, new Object[] { 100501 }, new Object[] { 100499 }, new Object[] { 100601 },
				new Object[] { 100599 }, new Object[] { 100701 }, new Object[] { 100699 }, new Object[] { 100801 },
				new Object[] { 100799 }, new Object[] { 100901 }, new Object[] { 100899 }, new Object[] { 101001 },
				new Object[] { 100999 }, new Object[] { 101301 }, new Object[] { 101299 }, new Object[] { 101501 },
				new Object[] { 101499 }, new Object[] { 101601 }, new Object[] { 101599 }, new Object[] { 101701 },
				new Object[] { 101699 }, new Object[] { 101711 }, new Object[] { 101709 }, new Object[] { 101801 },
				new Object[] { 101799 }, new Object[] { 102001 }, new Object[] { 101999 }, new Object[] { 102101 },
				new Object[] { 102099 }, new Object[] { 102201 }, new Object[] { 102199 }, new Object[] { 102301 },
				new Object[] { 102299 }, new Object[] { 102401 }, new Object[] { 102399 }, new Object[] { 102501 },
				new Object[] { 102499 }, new Object[] { 102601 }, new Object[] { 102599 }, new Object[] { 102701 },
				new Object[] { 102699 }, new Object[] { 102801 }, new Object[] { 102799 }, new Object[] { 102901 },
				new Object[] { 102899 }, new Object[] { 103001 }, new Object[] { 102999 }, new Object[] { 103101 },
				new Object[] { 103099 }, new Object[] { 103201 }, new Object[] { 103199 }, new Object[] { 103301 },
				new Object[] { 103299 }, new Object[] { 103501 }, new Object[] { 103499 }, new Object[] { 103601 },
				new Object[] { 103599 }, new Object[] { 103701 }, new Object[] { 103699 }, new Object[] { 103801 },
				new Object[] { 103799 }, new Object[] { 103901 }, new Object[] { 103899 }, new Object[] { 104101 },
				new Object[] { 104099 }, new Object[] { 104201 }, new Object[] { 104199 }, new Object[] { 104401 },
				new Object[] { 104399 }, new Object[] { 104501 }, new Object[] { 104499 }, new Object[] { 104601 },
				new Object[] { 104599 }, new Object[] { 104701 }, new Object[] { 104699 }, new Object[] { 104801 },
				new Object[] { 104799 }, new Object[] { 105001 }, new Object[] { 104999 }, new Object[] { 105201 },
				new Object[] { 105199 }, new Object[] { 105301 }, new Object[] { 105299 }, new Object[] { 105401 },
				new Object[] { 105399 }, new Object[] { 105501 }, new Object[] { 105499 }, new Object[] { 105601 },
				new Object[] { 105599 }, new Object[] { 105701 }, new Object[] { 105699 }, new Object[] { 105901 },
				new Object[] { 105899 }, new Object[] { 106201 }, new Object[] { 106199 }, new Object[] { 106401 },
				new Object[] { 106399 }, new Object[] { 106501 }, new Object[] { 106499 }, new Object[] { 106601 },
				new Object[] { 106599 }, new Object[] { 106701 }, new Object[] { 106699 }, new Object[] { 106901 },
				new Object[] { 106899 }, new Object[] { 107101 }, new Object[] { 107099 }, new Object[] { 107201 },
				new Object[] { 107199 }, new Object[] { 107401 }, new Object[] { 107399 }, new Object[] { 107601 },
				new Object[] { 107599 }, new Object[] { 107701 }, new Object[] { 107699 }, new Object[] { 107801 },
				new Object[] { 107799 }, new Object[] { 107901 }, new Object[] { 107899 }, new Object[] { 108001 },
				new Object[] { 107999 }, new Object[] { 108101 }, new Object[] { 108099 }, new Object[] { 108201 },
				new Object[] { 108199 }, new Object[] { 108301 }, new Object[] { 108299 }, new Object[] { 108401 },
				new Object[] { 108399 }, new Object[] { 116001 }, new Object[] { 115999 }, new Object[] { 21060801 },
				new Object[] { 21060799 }, new Object[] { 33000154 }, new Object[] { 33000152 },
				new Object[] { 33000254 }, new Object[] { 33000252 }, new Object[] { 33000354 },
				new Object[] { 33000352 }, new Object[] { 33000454 }, new Object[] { 33000452 },
				new Object[] { 33000554 }, new Object[] { 33000552 }, new Object[] { 33000636 },
				new Object[] { 33000634 }, new Object[] { 33000754 }, new Object[] { 33000752 },
				new Object[] { 33000854 }, new Object[] { 33000852 }, new Object[] { 33000954 },
				new Object[] { 33000952 }, new Object[] { 33001054 }, new Object[] { 33001052 },
				new Object[] { 33001196 }, new Object[] { 33001194 }, new Object[] { 33001236 },
				new Object[] { 33001234 }, new Object[] { 33001296 }, new Object[] { 33001294 },
				new Object[] { 33001454 }, new Object[] { 33001452 }, new Object[] { 33001554 },
				new Object[] { 33001552 }, new Object[] { 33001636 }, new Object[] { 33001634 },
				new Object[] { 33001696 }, new Object[] { 33001694 }, new Object[] { 33001736 },
				new Object[] { 33001734 }, new Object[] { 33001836 }, new Object[] { 33001834 },
				new Object[] { 33001954 }, new Object[] { 33001952 }, new Object[] { 33002054 },
				new Object[] { 33002052 }, new Object[] { 33002136 }, new Object[] { 33002134 },
				new Object[] { 33002196 }, new Object[] { 33002194 }, new Object[] { 33002236 },
				new Object[] { 33002234 }, new Object[] { 33002296 }, new Object[] { 33002294 },
				new Object[] { 33002354 }, new Object[] { 33002352 }, new Object[] { 33002500 },
				new Object[] { 33002498 }, new Object[] { 33002536 }, new Object[] { 33002534 },
				new Object[] { 33002636 }, new Object[] { 33002634 }, new Object[] { "033002534" },
				new Object[] { ",.lkkd" }, new Object[] { -1 }, new Object[] { -2 }, new Object[] { "-000" },
				new Object[] { "001" }, new Object[] {}, new Object[] { "testme" }, new Object[] { "3300sds2634" },
				new Object[] { 2000000 }, new Object[] { new Date() }, new Object[]{" "},
				new Object[]{"  "}, new Object[]{"   "}, new Object[]{"    "}, new Object[]{""} };
	}
	
	@Before
	public void setUp() {
		try {
			
			validateEventIdAndCauseCode = DataValidator.class.getDeclaredMethod("validateEventIdAndCauseCode", Object.class, Object.class);
			validateFailureClassMethod = DataValidator.class.getDeclaredMethod("validateFailureClass", Object.class);
			validateUETypesMethod = DataValidator.class.getDeclaredMethod("validateUEType", Object.class);
			validateEventIdAndCauseCode.setAccessible(true);
			validateFailureClassMethod.setAccessible(true);
			validateUETypesMethod.setAccessible(true);
			
			DataValidator.setUpDatabaseData();

		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Method may not exist. Set up method in ValidatorTest.");
			e.printStackTrace();
		}
	}

	@Test
	@Parameters(method = "validEventIdParams")
	public void testValidEventIds(Object eventId, Object causeCode)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isEventValid = (boolean) validateEventIdAndCauseCode.invoke(DataValidator.class, causeCode, eventId);
		assertTrue(isEventValid);
	}

	@Test
	@Parameters(method = "invalidEventIdParams")
	public void testInvalidEventIds(Object eventId, Object causeCode)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isEventValid = (boolean) validateEventIdAndCauseCode.invoke(DataValidator.class, causeCode, eventId);
		assertFalse(isEventValid);
	}

	@Test
	@Parameters(method = "validFailureClassesParams")
	public void testValidFailureClasses(Object failureClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isFailureClassValid = (boolean) validateFailureClassMethod.invoke(DataValidator.class, failureClass);
		assertTrue(isFailureClassValid);
	}

	@Test
	@Parameters(method = "invalidFailureClassesParams")
	public void testInvalidFailureClasses(Object failureClass)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isFailureClassValid = (boolean) validateFailureClassMethod.invoke(DataValidator.class, failureClass);
		assertFalse(isFailureClassValid);
	}

	@Test
	@Parameters(method = "validUETypesParams")
	public void testValidUETypes(Object UEType)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isUETypeValid = (boolean) validateUETypesMethod.invoke(DataValidator.class, UEType);
		assertTrue(isUETypeValid);
	}

	@Test
	@Parameters(method = "invalidUETypesParams")
	public void testInvalidUETypes(Object UEType)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isUETypeValid = (boolean) validateUETypesMethod.invoke(DataValidator.class, UEType);
		assertFalse(isUETypeValid);
	}

}
