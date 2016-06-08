package com.validation;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.User_equipment;
import com.fileuploader.ExcelFileUploader;

@Stateless
@LocalBean
public class SendValidatedInfoToDB {
	
	public void sendData(Object[][] dataToImport, int entryCounter) {
		NetworkEntityDAO ned = new NetworkEntityDAO();
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.UK);
		
		
		for(int i = 1; i < entryCounter; i ++){
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(dataToImport[i][0].toString()));
		} catch (ParseException e) {
			System.out.println("Parse exception in sendData!");
			e.printStackTrace();
		}
		Event_cause ev = new Event_cause();
				ev.setEvent_id(Integer.parseInt(dataToImport[i][1].toString()));
				ev.setCause_code(Integer.parseInt(dataToImport[i][8].toString()));
		Failure_class f = new Failure_class();
		f.setFailure_class(Integer.parseInt(dataToImport[i][2].toString()));
		User_equipment ue = new User_equipment();
		ue.setTac(Integer.parseInt(dataToImport[i][3].toString()));
		Mcc_mnc mcc = new Mcc_mnc();
		mcc.setMcc(Integer.parseInt(dataToImport[i][4].toString()));
		mcc.setMnc(Integer.parseInt(dataToImport[i][5].toString()));
		int cellId = Integer.parseInt(dataToImport[i][6].toString());
		int duration = Integer.parseInt(dataToImport[i][7].toString());
		String neversion = dataToImport[i][9].toString();
		BigInteger imsi = BigInteger.valueOf(Long.parseLong(dataToImport[i][10].toString()));
		BigInteger hier3_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][11].toString()));
		BigInteger hier32_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][12].toString()));
		BigInteger hier321_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][13].toString()));
		Base_data bd = new Base_data(c, ev, f, ue, mcc, cellId, duration, neversion, imsi, hier3_id, hier32_id, hier321_id);
		ExcelFileUploader eu = new ExcelFileUploader();
		eu.persistEntity(bd);
		}
	}
}
