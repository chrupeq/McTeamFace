package com.compositeKeyEqualsMethod.test;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;

import com.ait.db.model.Event_causeKey;
import com.ait.db.model.Mcc_mncKey;

import junit.framework.Assert;

public class CompositePKClassesObjectEqualityTest {
	Mcc_mncKey mccMncKeyOne;
	Mcc_mncKey mccMncKeyTwo;
	Mcc_mncKey mccMncKeyThree;
	Mcc_mncKey mccMncKeyFour;
	Event_causeKey eventCauseKeyOne;
	Event_causeKey eventCauseKeyTwo;
	Event_causeKey eventCauseKeyThree;
	Event_causeKey eventCauseKeyFour;
	
	@Before
	public void initialize() {
		mccMncKeyOne = new Mcc_mncKey();
		mccMncKeyOne.setMcc(238);
		mccMncKeyOne.setMnc(1);
		mccMncKeyTwo = new Mcc_mncKey();
		mccMncKeyTwo.setMcc(238);
		mccMncKeyTwo.setMnc(1);
		mccMncKeyThree = new Mcc_mncKey();
		mccMncKeyThree.setMcc(238);
		mccMncKeyThree.setMnc(2);
		mccMncKeyFour = new Mcc_mncKey();
		mccMncKeyFour.setMcc(238);
		mccMncKeyFour.setMnc(1);
		eventCauseKeyFour = new Event_causeKey();
		eventCauseKeyFour.setCause_code(0);
		eventCauseKeyFour.setEvent_id(4097);
		eventCauseKeyOne = new Event_causeKey();
		eventCauseKeyOne.setCause_code(0);
		eventCauseKeyOne.setEvent_id(4097);
		eventCauseKeyTwo = new Event_causeKey();
		eventCauseKeyTwo.setCause_code(0);
		eventCauseKeyTwo.setEvent_id(4097);
		eventCauseKeyThree = new Event_causeKey();
		eventCauseKeyThree.setCause_code(1);
		eventCauseKeyThree.setEvent_id(4097);
	}
	
	@Test
	public void testThatTwoObjectsAreEqual() {
		assertTrue(mccMncKeyOne.equals(mccMncKeyTwo));
		assertFalse(mccMncKeyOne.equals(null));
		assertEquals(mccMncKeyOne.hashCode(), mccMncKeyTwo.hashCode());
		assertTrue(eventCauseKeyOne.equals(eventCauseKeyTwo));
		assertFalse(eventCauseKeyOne.equals(null));
		assertEquals(eventCauseKeyOne.hashCode(), eventCauseKeyTwo.hashCode());
		
	}
	@Test
	public void testThatTwoObjectsAreNotEqual() {
		assertFalse(mccMncKeyOne.equals(eventCauseKeyThree));
		assertNotSame(mccMncKeyOne.hashCode(), mccMncKeyThree.hashCode());
		assertFalse(eventCauseKeyOne.equals(eventCauseKeyThree));
		assertNotSame(eventCauseKeyOne.hashCode(), eventCauseKeyThree.hashCode());
	}
	@Test
	public void testIfOverrideIsReflexive() {
		assertTrue(mccMncKeyOne.equals(mccMncKeyOne));
		assertEquals(mccMncKeyOne.hashCode(), mccMncKeyTwo.hashCode());
		assertTrue(eventCauseKeyOne.equals(eventCauseKeyOne));
		assertEquals(eventCauseKeyOne.hashCode(), eventCauseKeyTwo.hashCode());
	}
	@Test
	public void testIfOverrideIsSymmetric() {
		assertTrue(mccMncKeyOne.equals(mccMncKeyTwo));
		assertTrue(mccMncKeyTwo.equals(mccMncKeyOne));
		assertTrue(eventCauseKeyOne.equals(eventCauseKeyTwo));
		assertTrue(eventCauseKeyTwo.equals(eventCauseKeyOne));
	}
	@Test
	public void testIfOverrideIsTransitive() {
		assertTrue(mccMncKeyOne.equals(mccMncKeyTwo));
		assertTrue(mccMncKeyTwo.equals(mccMncKeyFour));
		assertTrue(mccMncKeyOne.equals(mccMncKeyFour));
		assertTrue(eventCauseKeyOne.equals(eventCauseKeyTwo));
		assertTrue(eventCauseKeyTwo.equals(eventCauseKeyFour));
		assertTrue(eventCauseKeyOne.equals(eventCauseKeyFour));
	}
	

}
