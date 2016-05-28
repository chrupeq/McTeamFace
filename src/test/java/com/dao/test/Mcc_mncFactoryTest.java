package com.dao.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.ait.dao.Mcc_mnc;
import com.ait.dao.Mcc_mncFactory;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class Mcc_mncFactoryTest {
	
	Mcc_mncFactory mcc_mncFactory = new Mcc_mncFactory();
	ArrayList<Mcc_mnc> mcc_mncArrayList;

	@Test
	public void test1createMcc_mnc() {
		int mccId = mcc_mncFactory.createMcc_mnc(100, 120, "Poland", "Vodafone");
		assertEquals(100, mccId);
		mccId = mcc_mncFactory.createMcc_mnc(101, 121, "Ireland", "Meteor");
		assertEquals(101, mccId);
		mccId = mcc_mncFactory.createMcc_mnc(102, 122, "England", "3G");
		assertEquals(102, mccId);
	}
	
	@Test
	public void test2updateMcc_mnc() {
		mcc_mncFactory.updateMcc_mnc(100, null, null);
		mcc_mncFactory.updateMcc_mnc(101, "German", null);
		mcc_mncFactory.updateMcc_mnc(102, "Russia", "O2");		
		mcc_mncArrayList = mcc_mncFactory.getAllMcc_mncDetails();
		assertEquals(mcc_mncArrayList.get(0).getCountry(),"Poland");
		assertEquals(mcc_mncArrayList.get(0).getOperator(),"Vodafone");
		assertEquals(mcc_mncArrayList.get(1).getCountry(),"German");
		assertEquals(mcc_mncArrayList.get(1).getOperator(),"Meteor");
		assertEquals(mcc_mncArrayList.get(2).getCountry(),"Russia");
		assertEquals(mcc_mncArrayList.get(2).getOperator(),"O2");
	}
	
	@Test
	public void test3deleteMcc_mnc() {
		mcc_mncArrayList = mcc_mncFactory.getAllMcc_mncDetails();
		assertEquals(mcc_mncArrayList.size(),3);
		mcc_mncFactory.deleteMcc_mnc(100);
		mcc_mncArrayList = mcc_mncFactory.getAllMcc_mncDetails();
		assertEquals(mcc_mncArrayList.size(),2);
		mcc_mncFactory.deleteMcc_mnc(102);
		mcc_mncFactory.deleteMcc_mnc(101);
		mcc_mncArrayList = mcc_mncFactory.getAllMcc_mncDetails();
		assertEquals(mcc_mncArrayList.isEmpty(),true);	
	}

}
