package com.validator.test;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.validation.DataValidator;
import com.validation.JDBCConnectionManager;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import utilities.ValidatorTestUtilities;

@RunWith(JUnitParamsRunner.class)
public class ValidatorTestPartThree extends ValidatorTestUtilities{

	Method validateMarketAndOperator;
	
	JDBCConnectionManager connectionManager;
	
	@Parameters
	public Object[] validMarketAndOperatorParams() {
		return new Object[] { new Object[] { 238, 1 }, new Object[] { 238, 2 }, new Object[] { 238, 3 },
				new Object[] { 240, 1 }, new Object[] { 240, 2 }, new Object[] { 240, 3 }, new Object[] { 240, 20 },
				new Object[] { 240, 21 }, new Object[] { 302, 36 }, new Object[] { 302, 37 }, new Object[] { 302, 62 },
				new Object[] { 302, 63 }, new Object[] { 310, 10 }, new Object[] { 310, 12 }, new Object[] { 310, 13 },
				new Object[] { 310, 540 }, new Object[] { 310, 550 }, new Object[] { 310, 560 },
				new Object[] { 310, 570 }, new Object[] { 310, 580 }, new Object[] { 310, 590 },
				new Object[] { 340, 1 }, new Object[] { 340, 2 }, new Object[] { 340, 3 }, new Object[] { 344, 30 },
				new Object[] { 344, 920 }, new Object[] { 344, 930 }, new Object[] { 405, 0 }, new Object[] { 405, 1 },
				new Object[] { 405, 3 }, new Object[] { 405, 4 }, new Object[] { 405, 5 }, new Object[] { 440, 9 },
				new Object[] { 440, 10 }, new Object[] { 440, 11 }, new Object[] { 505, 62 }, new Object[] { 505, 68 },
				new Object[] { 505, 71 }, new Object[] { 505, 72 }, new Object[] { 505, 88 }, new Object[] { 505, 90 },
				new Object[] { 0, 0 } };
	}

	@Parameters
	public Object[] invalidMarketAndOperatorParams() {
		return new Object[] { new Object[] { 2, 238 }, new Object[] { 0, 238 }, new Object[] { 1, 239 },
				new Object[] { 1, 237 }, new Object[] { 2, 239 }, new Object[] { 0, 237 }, new Object[] { 3, 238 },
				new Object[] { 1, 238 }, new Object[] { 2, 239 }, new Object[] { 2, 237 }, new Object[] { 3, 239 },
				new Object[] { 1, 237 }, new Object[] { 4, 238 }, new Object[] { 2, 238 }, new Object[] { 3, 239 },
				new Object[] { 3, 237 }, new Object[] { 4, 239 }, new Object[] { 2, 237 }, new Object[] { 2, 240 },
				new Object[] { 0, 240 }, new Object[] { 1, 241 }, new Object[] { 1, 239 }, new Object[] { 2, 241 },
				new Object[] { 0, 239 }, new Object[] { 3, 240 }, new Object[] { 1, 240 }, new Object[] { 2, 241 },
				new Object[] { 2, 239 }, new Object[] { 3, 241 }, new Object[] { 1, 239 }, new Object[] { 4, 240 },
				new Object[] { 2, 240 }, new Object[] { 3, 241 }, new Object[] { 3, 239 }, new Object[] { 4, 241 },
				new Object[] { 2, 239 }, new Object[] { 21, 240 }, new Object[] { 19, 240 }, new Object[] { 20, 241 },
				new Object[] { 20, 239 }, new Object[] { 21, 241 }, new Object[] { 19, 239 }, new Object[] { 22, 240 },
				new Object[] { 20, 240 }, new Object[] { 21, 241 }, new Object[] { 21, 239 }, new Object[] { 22, 241 },
				new Object[] { 20, 239 }, new Object[] { 37, 302 }, new Object[] { 35, 302 }, new Object[] { 36, 303 },
				new Object[] { 36, 301 }, new Object[] { 37, 303 }, new Object[] { 35, 301 }, new Object[] { 38, 302 },
				new Object[] { 36, 302 }, new Object[] { 37, 303 }, new Object[] { 37, 301 }, new Object[] { 38, 303 },
				new Object[] { 36, 301 }, new Object[] { 63, 302 }, new Object[] { 61, 302 }, new Object[] { 62, 303 },
				new Object[] { 62, 301 }, new Object[] { 63, 303 }, new Object[] { 61, 301 }, new Object[] { 64, 302 },
				new Object[] { 62, 302 }, new Object[] { 63, 303 }, new Object[] { 63, 301 }, new Object[] { 64, 303 },
				new Object[] { 62, 301 }, new Object[] { 11, 310 }, new Object[] { 9, 310 }, new Object[] { 10, 311 },
				new Object[] { 10, 309 }, new Object[] { 11, 311 }, new Object[] { 9, 309 }, new Object[] { 13, 310 },
				new Object[] { 11, 310 }, new Object[] { 12, 311 }, new Object[] { 12, 309 }, new Object[] { 13, 311 },
				new Object[] { 11, 309 }, new Object[] { 14, 310 }, new Object[] { 12, 310 }, new Object[] { 13, 311 },
				new Object[] { 13, 309 }, new Object[] { 14, 311 }, new Object[] { 12, 309 }, new Object[] { 541, 310 },
				new Object[] { 539, 310 }, new Object[] { 540, 311 }, new Object[] { 540, 309 },
				new Object[] { 541, 311 }, new Object[] { 539, 309 }, new Object[] { 551, 310 },
				new Object[] { 549, 310 }, new Object[] { 550, 311 }, new Object[] { 550, 309 },
				new Object[] { 551, 311 }, new Object[] { 549, 309 }, new Object[] { 561, 310 },
				new Object[] { 559, 310 }, new Object[] { 560, 311 }, new Object[] { 560, 309 },
				new Object[] { 561, 311 }, new Object[] { 559, 309 }, new Object[] { 571, 310 },
				new Object[] { 569, 310 }, new Object[] { 570, 311 }, new Object[] { 570, 309 },
				new Object[] { 571, 311 }, new Object[] { 569, 309 }, new Object[] { 581, 310 },
				new Object[] { 579, 310 }, new Object[] { 580, 311 }, new Object[] { 580, 309 },
				new Object[] { 581, 311 }, new Object[] { 579, 309 }, new Object[] { 591, 310 },
				new Object[] { 589, 310 }, new Object[] { 590, 311 }, new Object[] { 590, 309 },
				new Object[] { 591, 311 }, new Object[] { 589, 309 }, new Object[] { 2, 340 }, new Object[] { 0, 340 },
				new Object[] { 1, 341 }, new Object[] { 1, 339 }, new Object[] { 2, 341 }, new Object[] { 0, 339 },
				new Object[] { 3, 340 }, new Object[] { 1, 340 }, new Object[] { 2, 341 }, new Object[] { 2, 339 },
				new Object[] { 3, 341 }, new Object[] { 1, 339 }, new Object[] { 4, 340 }, new Object[] { 2, 340 },
				new Object[] { 3, 341 }, new Object[] { 3, 339 }, new Object[] { 4, 341 }, new Object[] { 2, 339 },
				new Object[] { 31, 344 }, new Object[] { 29, 344 }, new Object[] { 30, 345 }, new Object[] { 30, 343 },
				new Object[] { 31, 345 }, new Object[] { 29, 343 }, new Object[] { 921, 344 },
				new Object[] { 919, 344 }, new Object[] { 920, 345 }, new Object[] { 920, 343 },
				new Object[] { 921, 345 }, new Object[] { 919, 343 }, new Object[] { 931, 344 },
				new Object[] { 929, 344 }, new Object[] { 930, 345 }, new Object[] { 930, 343 },
				new Object[] { 931, 345 }, new Object[] { 929, 343 }, new Object[] { 1, 405 }, new Object[] { -1, 405 },
				new Object[] { 0, 406 }, new Object[] { 0, 404 }, new Object[] { 1, 406 }, new Object[] { -1, 404 },
				new Object[] { 2, 405 }, new Object[] { 0, 405 }, new Object[] { 1, 406 }, new Object[] { 1, 404 },
				new Object[] { 2, 406 }, new Object[] { 0, 404 }, new Object[] { 4, 405 }, new Object[] { 2, 405 },
				new Object[] { 3, 406 }, new Object[] { 3, 404 }, new Object[] { 4, 406 }, new Object[] { 2, 404 },
				new Object[] { 5, 405 }, new Object[] { 3, 405 }, new Object[] { 4, 406 }, new Object[] { 4, 404 },
				new Object[] { 5, 406 }, new Object[] { 3, 404 }, new Object[] { 6, 405 }, new Object[] { 4, 405 },
				new Object[] { 5, 406 }, new Object[] { 5, 404 }, new Object[] { 6, 406 }, new Object[] { 4, 404 },
				new Object[] { 10, 440 }, new Object[] { 8, 440 }, new Object[] { 9, 441 }, new Object[] { 9, 439 },
				new Object[] { 10, 441 }, new Object[] { 8, 439 }, new Object[] { 11, 440 }, new Object[] { 9, 440 },
				new Object[] { 10, 441 }, new Object[] { 10, 439 }, new Object[] { 11, 441 }, new Object[] { 9, 439 },
				new Object[] { 12, 440 }, new Object[] { 10, 440 }, new Object[] { 11, 441 }, new Object[] { 11, 439 },
				new Object[] { 12, 441 }, new Object[] { 10, 439 }, new Object[] { 63, 505 }, new Object[] { 61, 505 },
				new Object[] { 62, 506 }, new Object[] { 62, 504 }, new Object[] { 63, 506 }, new Object[] { 61, 504 },
				new Object[] { 69, 505 }, new Object[] { 67, 505 }, new Object[] { 68, 506 }, new Object[] { 68, 504 },
				new Object[] { 69, 506 }, new Object[] { 67, 504 }, new Object[] { 72, 505 }, new Object[] { 70, 505 },
				new Object[] { 71, 506 }, new Object[] { 71, 504 }, new Object[] { 72, 506 }, new Object[] { 70, 504 },
				new Object[] { 73, 505 }, new Object[] { 71, 505 }, new Object[] { 72, 506 }, new Object[] { 72, 504 },
				new Object[] { 73, 506 }, new Object[] { 71, 504 }, new Object[] { 89, 505 }, new Object[] { 87, 505 },
				new Object[] { 88, 506 }, new Object[] { 88, 504 }, new Object[] { 89, 506 }, new Object[] { 87, 504 },
				new Object[] { 91, 505 }, new Object[] { 89, 505 }, new Object[] { 90, 506 }, new Object[] { 90, 504 },
				new Object[] { 91, 506 }, new Object[] { 89, 504 }, new Object[] { 1, 0 }, new Object[] { -1, 0 },
				new Object[] { 0, 1 }, new Object[] { 0, -1 }, new Object[] { 1, 1 }, new Object[] { -1, -1 },
				new Object[] { "090", "0506" }, new Object[] { 505, 00 }, new Object[] { "0", "01" },
				new Object[] { 0240, 01 }, new Object[] { "ask", "ab" }, new Object[] { ".,&", "^2" },
				new Object[] { 1, 90 }, new Object[] { 12, 90 }, new Object[] { 123, 90 }, new Object[] { 12345, 90 },
				new Object[] { 123456, 90 }, new Object[] { 505, 1 }, new Object[] { 505, 12 },
				new Object[] { 505, 123 }, new Object[] { 505, 1234 }, new Object[]{" ", " "},
				new Object[]{"", ""},
				new Object[]{"  ", "  "}, new Object[]{"   ", "   "}, new Object[]{"    ", "    "} };
	}
	
	@Before
	public void setUp() throws NoSuchMethodException, SecurityException{
		validateMarketAndOperator = DataValidator.class.getDeclaredMethod("validateMarketAndOperator", Object.class,
				Object.class);
		validateMarketAndOperator.setAccessible(true);
		
		DataValidator.setUpDatabaseData();
	}

	@Test
	@Parameters(method = "validMarketAndOperatorParams")
	public void testValidMarketAndOperator(Object market, Object operator)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean areMarketsAndOperatorsValid = (boolean) validateMarketAndOperator.invoke(DataValidator.class, market,
				operator);
		assertTrue(areMarketsAndOperatorsValid);
	}

	@Test
	@Parameters(method = "invalidMarketAndOperatorParams")
	public void testInvalidMarketAndOperator(Object market, Object operator)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, SQLException {
		boolean areMarketsAndOperatorsValid = (boolean) validateMarketAndOperator.invoke(DataValidator.class, market,
				operator);
		assertFalse(areMarketsAndOperatorsValid);
	}
}
