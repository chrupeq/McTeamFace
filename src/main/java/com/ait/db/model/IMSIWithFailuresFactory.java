package com.ait.db.model;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class IMSIWithFailuresFactory {
	
	private static List<IMSIWithValidFailureClasses> imsisWithValidFailureClassesList;
	
	public static List<IMSIWithValidFailureClasses> getImsiFailureClassList(List<Base_data> baseDataList) {
		Calendar dateTime;
		int failureClass;
		BigInteger imsi;
		imsisWithValidFailureClassesList = new ArrayList<>();
		for(Base_data baseData : baseDataList) {
			if(baseData.getFailure_class() == null) {
				continue;
			}
				dateTime = baseData.getDate_time();
				failureClass = baseData.getFailure_class().getFailure_class();
				imsi = baseData.getImsi();
				IMSIWithValidFailureClasses imsiWithValidFailureClasses 
					= new IMSIWithValidFailureClasses(dateTime, failureClass, imsi);
				imsisWithValidFailureClassesList.add(imsiWithValidFailureClasses);
			}
		return imsisWithValidFailureClassesList;
		}

}
