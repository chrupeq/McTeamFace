package com.ait.db.model;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.ait.db.data.DateParser;

public class IMSIWithFailuresFactory {
	
	private static List<IMSIWithValidFailureClasses> imsisWithValidFailureClassesList;
	private static DateParser dateParser;
	
	public static List<IMSIWithValidFailureClasses> getImsiFailureClassList(List<Base_data> baseDataList) {
		int reportId;
		Calendar dateTime;
		int failureClass;
		BigInteger imsi;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		imsisWithValidFailureClassesList = new ArrayList<>();
		
		for(Base_data baseData : baseDataList) {
			if(baseData.getFailure_class() == null) {
				continue;
			}
			dateParser = new DateParser();
			reportId = baseData.getReport_id();	
			dateTime = baseData.getDate_time();
			String dateTimeAsString = dateParser.parseCalendarToString(dateTime, simpleDateFormat);
			failureClass = baseData.getFailure_class().getFailure_class();
			imsi = baseData.getImsi();
			IMSIWithValidFailureClasses imsiWithValidFailureClasses 
					= new IMSIWithValidFailureClasses(reportId, dateTimeAsString, failureClass, imsi);
				imsisWithValidFailureClassesList.add(imsiWithValidFailureClasses);
			}
		return imsisWithValidFailureClassesList;
		}

}
