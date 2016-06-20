package com.ait.db.model;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;

@Stateless
@LocalBean
public class NonBaseDataObjects {
	
	static Failure_class[] arrayOfFailureClasses;
	static Event_cause[] arrayOfEventCause;
	static Mcc_mnc[] arrayOfMccMncClasses;
	static User_equipment[] arrayOfUserEquipment;
	
	public static Failure_class[] createFailureClass(Object[][] array){
		arrayOfFailureClasses = new Failure_class[array.length];
		for(int i=1; i < array.length; i++){
			Failure_class fc = new Failure_class();
			fc.setDescription(array[i][1].toString());
			fc.setFailure_class(Integer.parseInt(array[i][0].toString()));
			arrayOfFailureClasses[i] = fc;
		}
		return arrayOfFailureClasses;
	}
	
	public static Event_cause[] createEventCauseClass(Object[][] array){
		arrayOfEventCause = new Event_cause[array.length];
		for(int i = 1; i< array.length; i++){
			Event_cause ec = new Event_cause();
			ec.setCause_code(Integer.parseInt(array[i][0].toString()));
			ec.setEvent_id(Integer.parseInt(array[i][1].toString()));
			ec.setDescription(array[i][2].toString());
			arrayOfEventCause[i] = ec;
		}
		return arrayOfEventCause;
	}
	
	public static Mcc_mnc[] createMccMncclass(Object[][] array){
		arrayOfMccMncClasses = new Mcc_mnc[array.length];
		for(int i=1; i < array.length; i++){
			Mcc_mnc mcc_mnc = new Mcc_mnc();
			mcc_mnc.setMcc(Integer.parseInt(array[i][0].toString()));
			mcc_mnc.setMnc(Integer.parseInt(array[i][1].toString()));
			mcc_mnc.setCountry(array[i][2].toString());
			mcc_mnc.setOperator(array[i][3].toString());
			arrayOfMccMncClasses[i] = mcc_mnc;
		}
		return arrayOfMccMncClasses;
	}
	
	public static User_equipment[] createUserEquipment(Object[][] array){
		arrayOfUserEquipment = new User_equipment[array.length];
		System.out.println("Num rows: " + array.length);
		System.out.println("Num columns: " + array[0].length);
		for(int i=1; i < array.length; i++){
			User_equipment ue = new User_equipment();
			ue.setTac(Integer.parseInt(array[i][0].toString()));
			ue.setMarketing_name(array[i][1].toString());
			ue.setManufacturer(array[i][2].toString());
			ue.setAccess_capability(array[i][3].toString());
			ue.setModel(array[i][4].toString());
			ue.setVendor_name(array[i][5].toString());
			ue.setUser_equipment_type(array[i][6].toString());
			ue.setOperating_system(array[i][7].toString());
			ue.setInput_mode(array[i][8].toString());
			
			arrayOfUserEquipment[i] = ue;
		}
		return arrayOfUserEquipment;
	}

}
