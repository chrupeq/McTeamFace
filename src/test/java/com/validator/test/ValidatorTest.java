package com.validator.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.validation.DataValidator;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;

@RunWith(JUnitParamsRunner.class)
public class ValidatorTest extends ValidatorTestUtilities {

	Method validateDateTime;
	
	@Parameters
	public Object[] validateDateTestValidParams(){
		return new Object[]{
				new Object[]{"01/01/2016 00:00"}, new Object[]{"02/01/2015 00:01"}, new Object[]{"15/01/2014 12:00"}, new Object[]{"30/01/2013 00:58"}, 
				new Object[]{"31/01/2012 00:59"}, new Object[]{"01/02/2011 01:00"}, new Object[]{"02/02/2010 01:01"}, new Object[]{"14/02/2009 01:30"},
				new Object[]{"28/02/2008 01:58"}, new Object[]{"29/02/2012 01:59"}, new Object[]{"01/03/2010 02:00"}, new Object[]{"02/03/2007 02:01"},
				new Object[]{"15/03/2006 02:29"}, new Object[]{"30/03/2005 02:58"}, new Object[]{"31/03/2004 02:59"}, new Object[]{"01/04/2003 03:00"},
				new Object[]{"02/04/2002 03:01"}, new Object[]{"15/04/2001 03:31"}, new Object[]{"29/04/2000 03:58"}, new Object[]{"30/04/2001 03:59"},
				new Object[]{"01/05/2000 04:00"}, new Object[]{"02/05/1999 04:01"}, new Object[]{"15/05/1998 04:28"}, new Object[]{"30/05/1997 04:58"},
				new Object[]{"31/05/1996 04:59"}, new Object[]{"01/06/1995 05:00"}, new Object[]{"02/06/1994 05:01"}, new Object[]{"15/06/1993 05:30"},
				new Object[]{"29/06/1992 05:59"}, new Object[]{"30/06/1991 06:00"}, new Object[]{"01/07/1990 06:01"}, new Object[]{"02/07/2015 06:02"},
				new Object[]{"14/07/2015 06:32"}, new Object[]{"30/07/2014 06:59"}, new Object[]{"31/07/2013 07:00"}, new Object[]{"01/08/2012 07:01"},
				new Object[]{"02/08/2011 07:02"}, new Object[]{"15/08/2010 07:30"}, new Object[]{"30/08/2009 07:59"}, new Object[]{"31/08/2008 08:00"},
				new Object[]{"01/09/2007 08:01"}, new Object[]{"02/09/2006 08:02"}, new Object[]{"15/09/2005 08:30"}, new Object[]{"29/09/2004 08:59"},
				new Object[]{"30/09/2003 09:00"}, new Object[]{"01/10/2002 09:01"}, new Object[]{"02/10/2001 09:02"}, new Object[]{"15/10/2000 09:27"},
				new Object[]{"30/10/1999 09:59"}, new Object[]{"31/10/1998 10:00"}, new Object[]{"01/11/1997 10:01"}, new Object[]{"02/11/1996 10:02"},
				new Object[]{"15/11/1995 10:33"}, new Object[]{"29/11/1994 10:59"}, new Object[]{"30/11/1993 11:00"}, new Object[]{"01/12/1992 11:01"},
				new Object[]{"02/12/1991 11:02"}, new Object[]{"15/12/1990 11:30"}, new Object[]{"30/12/2015 11:59"}, new Object[]{"31/12/2015 12:00"}
		};
	}
	
	@Parameters
	public Object[] validateDateTestInvalidParams(){
		return new Object[]{
				new Object[]{"32/01/2016 00:00"}, new Object[]{"-01/01/2015 00:01"}, new Object[]{"33/01/2014 12:00"}, new Object[]{"AA/01/2013 00:58"}, 
				new Object[]{"313/01/2012 00:59"}, new Object[]{"1/02/2011 01:00"}, new Object[]{"02/13/2010 01:01"}, new Object[]{"14/14/2009 01:30"},
				new Object[]{"1/02/2008 01:58"}, new Object[]{"29/-01/2012 01:59"}, new Object[]{"01/-1/2010 02:00"}, new Object[]{"02/012/2007 02:01"},
				new Object[]{"15/031/2006 02:29"}, new Object[]{"30/0/2005 02:58"}, new Object[]{"31/3/2004 02:59"}, new Object[]{"01/04/20031 03:00"},
				new Object[]{"02/04/200 03:01"}, new Object[]{"15/04/20A0 03:31"}, new Object[]{"29/04/-2000 03:58"}, new Object[]{"30/04/-001 03:59"},
				new Object[]{"01/05/-1 04:00"}, new Object[]{"02/05/-999 04:01"}, new Object[]{"15/05/1998 -01:28"}, new Object[]{"30/05/1997 -1:58"},
				new Object[]{"31/05/1996 -13:59"}, new Object[]{"01/06/1995 -3:00"}, new Object[]{"02/06/1994 24:01"}, new Object[]{"15/06/1993 25:30"},
				new Object[]{"29/06/1992 05:60"}, new Object[]{"30/06/1991 06:61"}, new Object[]{"01/07/1990 06:-1"}, new Object[]{"02/07/2016 06:-2"},
				new Object[]{"14/07/2015 06:AA"}, new Object[]{"30/07/2014 AA:59"}, new Object[]{"31/07/2013 A7:00"}, new Object[]{"01/08/2012 07:A1"},
				new Object[]{"02/08/2011 07:021"}, new Object[]{"15/08/2010 07:3"}, new Object[]{"30/08/200907:59"}, new Object[]{"31/08/2008  08:00"},
				new Object[]{"01/09/ 2007 08:01"}, new Object[]{"02/09 /2006 08:02"}, new Object[]{"15 /09/2005 08:30"}, new Object[]{"29/09/2004 08:59:00"},
				new Object[]{"29/02/2015 09:00"}, new Object[]{"30/02/2015 09:01"}, new Object[]{"31/09/2001 09:02"}, new Object[]{"31/04/2000 09:27"},
				new Object[]{"31/06/1999 09:59"}, new Object[]{"31/11/1998 10:00"}, new Object[]{"32/11/1997 10:01"}, new Object[]{"32/09/1996 10:02"},
				new Object[]{"32/04/1995 10:33"}, new Object[]{"32/06/1994 10:59"}, new Object[]{getDatesInTheFuture(1)}, new Object[]{getDatesInTheFuture(2)},
				new Object[]{getDatesInTheFuture(3)}, new Object[]{getDatesInTheFuture(4)}, new Object[]{getDatesInTheFuture(5)}
		};
	}
	
	@Before
	public void setUp(){
		try {
		validateDateTime = DataValidator.class.getDeclaredMethod("validateDateTime", Object.class);
		validateDateTime.setAccessible(true);
		} catch (NoSuchMethodException | SecurityException e) {
			System.out.println("Method may not exist. Set up method in ValidatorTest.");
			e.printStackTrace();
		}
	}
	
	@Test
	@Parameters(method = "validateDateTestValidParams")
	public void testValidDates(Object dateAndTime) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean isDateValid = (boolean) validateDateTime.invoke(DataValidator.class, dateAndTime);
		assertTrue((Boolean) isDateValid);
	}
	
	@Test
	@Parameters(method = "validateDateTestInvalidParams")
	public void testInvalidDates(Object dateAndTime) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		boolean isDateValid = (boolean) validateDateTime.invoke(DataValidator.class, dateAndTime);
		assertFalse((Boolean) isDateValid);
	}

}
