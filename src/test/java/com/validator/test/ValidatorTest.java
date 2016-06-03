package com.validator.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.validation.DataValidator;
import com.validation.JDBCConnectionManager;
import com.validation.ValidationDataFromJDBC;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import utilities.ValidatorTestUtilities;

@RunWith(JUnitParamsRunner.class)
public class ValidatorTest extends ValidatorTestUtilities {

	Method validateDateTimeMethod;
	Method validateEventIdMethod;
	Method validateFailureClassMethod;
	Method validateUETypesMethod;
	Method validateMarketAndOperator;
	JDBCConnectionManager connectionManager;

	@Parameters
	public Object[] validDateParams() {
		return new Object[] { new Object[] { "01/01/2016 00:00" }, new Object[] { "02/01/2015 00:01" },
				new Object[] { "15/01/2014 12:00" }, new Object[] { "30/01/2013 00:58" },
				new Object[] { "31/01/2012 00:59" }, new Object[] { "01/02/2011 01:00" },
				new Object[] { "02/02/2010 01:01" }, new Object[] { "14/02/2009 01:30" },
				new Object[] { "28/02/2008 01:58" }, new Object[] { "29/02/2012 01:59" },
				new Object[] { "01/03/2010 02:00" }, new Object[] { "02/03/2007 02:01" },
				new Object[] { "15/03/2006 02:29" }, new Object[] { "30/03/2005 02:58" },
				new Object[] { "31/03/2004 02:59" }, new Object[] { "01/04/2003 03:00" },
				new Object[] { "02/04/2002 03:01" }, new Object[] { "15/04/2001 03:31" },
				new Object[] { "29/04/2000 03:58" }, new Object[] { "30/04/2001 03:59" },
				new Object[] { "01/05/2000 04:00" }, new Object[] { "02/05/1999 04:01" },
				new Object[] { "15/05/1998 04:28" }, new Object[] { "30/05/1997 04:58" },
				new Object[] { "31/05/1996 04:59" }, new Object[] { "01/06/1995 05:00" },
				new Object[] { "02/06/1994 05:01" }, new Object[] { "15/06/1993 05:30" },
				new Object[] { "29/06/1992 05:59" }, new Object[] { "30/06/1991 06:00" },
				new Object[] { "01/07/1990 06:01" }, new Object[] { "02/07/2015 06:02" },
				new Object[] { "14/07/2015 06:32" }, new Object[] { "30/07/2014 06:59" },
				new Object[] { "31/07/2013 07:00" }, new Object[] { "01/08/2012 07:01" },
				new Object[] { "02/08/2011 07:02" }, new Object[] { "15/08/2010 07:30" },
				new Object[] { "30/08/2009 07:59" }, new Object[] { "31/08/2008 08:00" },
				new Object[] { "01/09/2007 08:01" }, new Object[] { "02/09/2006 08:02" },
				new Object[] { "15/09/2005 08:30" }, new Object[] { "29/09/2004 08:59" },
				new Object[] { "30/09/2003 09:00" }, new Object[] { "01/10/2002 09:01" },
				new Object[] { "02/10/2001 09:02" }, new Object[] { "15/10/2000 09:27" },
				new Object[] { "30/10/1999 09:59" }, new Object[] { "31/10/1998 10:00" },
				new Object[] { "01/11/1997 10:01" }, new Object[] { "02/11/1996 10:02" },
				new Object[] { "15/11/1995 10:33" }, new Object[] { "29/11/1994 10:59" },
				new Object[] { "30/11/1993 11:00" }, new Object[] { "01/12/1992 11:01" },
				new Object[] { "02/12/1991 11:02" }, new Object[] { "15/12/1990 11:30" },
				new Object[] { "30/12/2015 11:59" }, new Object[] { "31/12/2015 12:00" } };
	}

	@Parameters
	public Object[] invalidDateParams() {
		return new Object[] { new Object[] { "32/01/2016 00:00" }, new Object[] { "-01/01/2015 00:01" },
				new Object[] { "33/01/2014 12:00" }, new Object[] { "AA/01/2013 00:58" },
				new Object[] { "313/01/2012 00:59" }, new Object[] { "1/02/2011 01:00" },
				new Object[] { "02/13/2010 01:01" }, new Object[] { "14/14/2009 01:30" },
				new Object[] { "1/02/2008 01:58" }, new Object[] { "29/-01/2012 01:59" },
				new Object[] { "01/-1/2010 02:00" }, new Object[] { "02/012/2007 02:01" },
				new Object[] { "15/031/2006 02:29" }, new Object[] { "30/0/2005 02:58" },
				new Object[] { "31/3/2004 02:59" }, new Object[] { "01/04/20031 03:00" },
				new Object[] { "02/04/200 03:01" }, new Object[] { "15/04/20A0 03:31" },
				new Object[] { "29/04/-2000 03:58" }, new Object[] { "30/04/-001 03:59" },
				new Object[] { "01/05/-1 04:00" }, new Object[] { "02/05/-999 04:01" },
				new Object[] { "15/05/1998 -01:28" }, new Object[] { "30/05/1997 -1:58" },
				new Object[] { "31/05/1996 -13:59" }, new Object[] { "01/06/1995 -3:00" },
				new Object[] { "02/06/1994 24:01" }, new Object[] { "15/06/1993 25:30" },
				new Object[] { "29/06/1992 05:60" }, new Object[] { "30/06/1991 06:61" },
				new Object[] { "01/07/1990 06:-1" }, new Object[] { "02/07/2016 06:-2" },
				new Object[] { "14/07/2015 06:AA" }, new Object[] { "30/07/2014 AA:59" },
				new Object[] { "31/07/2013 A7:00" }, new Object[] { "01/08/2012 07:A1" },
				new Object[] { "02/08/2011 07:021" }, new Object[] { "15/08/2010 07:3" },
				new Object[] { "30/08/200907:59" }, new Object[] { "31/08/2008  08:00" },
				new Object[] { "01/09/ 2007 08:01" }, new Object[] { "02/09 /2006 08:02" },
				new Object[] { "15 /09/2005 08:30" }, new Object[] { "29/09/2004 08:59:00" },
				new Object[] { "29/02/2015 09:00" }, new Object[] { "30/02/2015 09:01" },
				new Object[] { "31/09/2001 09:02" }, new Object[] { "31/04/2000 09:27" },
				new Object[] { "31/06/1999 09:59" }, new Object[] { "31/11/1998 10:00" },
				new Object[] { "32/11/1997 10:01" }, new Object[] { "32/09/1996 10:02" },
				new Object[] { "32/04/1995 10:33" }, new Object[] { "32/06/1994 10:59" },
				new Object[] { "29/02/1900 16:00" }, new Object[] { getDatesInTheFuture(1) },
				new Object[] { getDatesInTheFuture(2) }, new Object[] { getDatesInTheFuture(3) },
				new Object[] { getDatesInTheFuture(4) }, new Object[] { getDatesInTheFuture(5) } };
	}

	@Parameters
	public Object[] validEventIdParams() {
		return new Object[] { new Object[] { 4097 }, new Object[] { 4098 }, new Object[] { 4125 },
				new Object[] { 4106 } };
	}

	@Parameters
	public Object[] invalidEventIdParams() {
		return new Object[] { new Object[] { 4096 }, new Object[] { 4095 }, new Object[] { 4099 },
				new Object[] { 5000.0 }, new Object[] { 4123.0 }, new Object[] { 4124.0 }, new Object[] { 4126 },
				new Object[] { 4127 }, new Object[] { 4105 }, new Object[] { 4104 }, new Object[] { 4107 },
				new Object[] { 4109 }, new Object[] { 1 }, new Object[] { 12 }, new Object[] { 123 },
				new Object[] { 12345 }, new Object[] { 123456 }, new Object[] { -1 }, new Object[] { -12 },
				new Object[] { -123 }, new Object[] { -1234 }, new Object[] { 0 }, new Object[] { 0000 },
				new Object[] { 000 }, new Object[] { 00 }, new Object[] { "A000" }, new Object[] { "A4097" },
				new Object[] { 40970 }, new Object[] { "04097" }, new Object[] { "A4097" }, new Object[] { "-.,&" } };
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
				new Object[] { "null" } };
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
				new Object[] { 33002636 }, new Object[] { 33002634 }, new Object[] {"033002534"},
				new Object[]{",.lkkd"}, new Object[]{-1}, new Object[]{-2}, new Object[]{"-000"}, new Object[]{"001"}, 
				new Object[]{}, new Object[]{"testme"}, new Object[]{"3300sds2634"}, new Object[]{2000000}, new Object[]{new Date()} 
		};
	}

	@Parameters
	public Object[] validMarketAndOperatorParams(){
		return new Object[]{
				new Object[]{238, 1}, new Object[]{238, 2}, new Object[]{238, 3}, new Object[]{240, 1}, new Object[]{240, 2}, new Object[]{240, 3},
				new Object[]{240, 20}, new Object[]{240, 21}, new Object[]{302, 36}, new Object[]{302, 37}, new Object[]{302, 62}, new Object[]{302, 63}, 
				new Object[]{310, 10}, new Object[]{310, 12}, new Object[]{310, 13}, new Object[]{310, 540}, new Object[]{310, 550}, new Object[]{310, 560}, 
				new Object[]{310, 570}, new Object[]{310, 580}, new Object[]{310, 590}, new Object[]{340, 1}, new Object[]{340, 2}, new Object[]{340, 3}, 
				new Object[]{344, 30}, new Object[]{344, 920}, new Object[]{344, 930}, new Object[]{405, 0}, new Object[]{405, 1}, new Object[]{405, 3}, 
				new Object[]{405, 4}, new Object[]{405, 5}, new Object[]{440, 9}, new Object[]{440, 10}, new Object[]{440, 11}, new Object[]{505, 62}, 
				new Object[]{505, 68}, new Object[]{505, 71}, new Object[]{505, 72}, new Object[]{505, 88}, new Object[]{505, 90}, new Object[]{0, 0} 	
		};
	}
	
	@Before
	public void setUp() {
		try {
			validateDateTimeMethod = DataValidator.class.getDeclaredMethod("validateDateTime", Object.class);
			validateEventIdMethod = DataValidator.class.getDeclaredMethod("validateEventId", Object.class);
			validateFailureClassMethod = DataValidator.class.getDeclaredMethod("validateFailureClass", Object.class);
			validateUETypesMethod = DataValidator.class.getDeclaredMethod("validateUEType", Object.class);
			validateMarketAndOperator = DataValidator.class.getDeclaredMethod("validateMarketAndOperator", Object.class, Object.class);
			validateDateTimeMethod.setAccessible(true);
			validateEventIdMethod.setAccessible(true);
			validateFailureClassMethod.setAccessible(true);
			validateUETypesMethod.setAccessible(true);
			validateMarketAndOperator.setAccessible(true);
			
			

		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Method may not exist. Set up method in ValidatorTest.");
			e.printStackTrace();
		}
	}

	@Test
	@Parameters(method = "validDateParams")
	public void testValidDates(Object dateAndTime)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isDateValid = (boolean) validateDateTimeMethod.invoke(DataValidator.class, dateAndTime);
		assertTrue(isDateValid);
	}

	@Test
	@Parameters(method = "invalidDateParams")
	public void testInvalidDates(Object dateAndTime)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isDateValid = (boolean) validateDateTimeMethod.invoke(DataValidator.class, dateAndTime);
		assertFalse(isDateValid);
	}

	@Test
	@Parameters(method = "validEventIdParams")
	public void testValidEventIds(Object eventId)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isEventValid = (boolean) validateEventIdMethod.invoke(DataValidator.class, eventId);
		assertTrue(isEventValid);
	}

	@Test
	@Parameters(method = "invalidEventIdParams")
	public void testInvalidEventIds(Object eventId)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isEventValid = (boolean) validateEventIdMethod.invoke(DataValidator.class, eventId);
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
		assertFalse(isUETypeValid);
	}

	@Test
	@Parameters(method = "invalidUETypesParams")
	public void testInvalidUETypes(Object UEType) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean isUETypeValid = (boolean) validateUETypesMethod.invoke(DataValidator.class, UEType);
		assertFalse(isUETypeValid);
	}
	
	@Test
	@Parameters(method = "validMarketAndOperatorParams")
	public void testValidMarketAndOperator(Object market, Object operator) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean areMarketsAndOperatorsValid = (boolean) validateMarketAndOperator.invoke(DataValidator.class, market, operator);
		assertFalse(areMarketsAndOperatorsValid);
	}
}
