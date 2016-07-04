package com.validation;

import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.User_equipment;
import com.fileuploader.ExcelFileUploader;

@Stateless
@LocalBean
public class SendValidatedInfoToDB {
	
	@EJB
	private NetworkEntityDAO networkEntityDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private Base_data[] bdArray;
	
	public Base_data[] sendData(Object[][] dataToImport, int entryCounter) {
		bdArray = new Base_data[entryCounter];
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy HH:mm", Locale.UK);
		
		for(int i = 0; i < entryCounter; i ++){
		Calendar c = Calendar.getInstance();
		
		Base_data bd = new Base_data();
		try {
			c.setTime(sdf.parse(dataToImport[i][0].toString()));
		} catch (ParseException e) {
			System.out.println("Parse exception in sendData!");
			e.printStackTrace();
		}
		Event_cause ev = new Event_cause();
				ev.setEvent_id(Integer.parseInt(dataToImport[i][1].toString()));
				Failure_class f;
		if(dataToImport[i][2].toString().equals("(null)")){
			f = new Failure_class();
		}else{
			f = new Failure_class();
			f.setFailure_class(Integer.parseInt(dataToImport[i][2].toString()));
		}
		
		User_equipment ue = new User_equipment();
		ue.setTac(Integer.parseInt(dataToImport[i][3].toString()));
		Mcc_mnc mcc = new Mcc_mnc();
		mcc.setMcc(Integer.parseInt(dataToImport[i][4].toString()));
		mcc.setMnc(Integer.parseInt(dataToImport[i][5].toString()));
		
		int cellId = Integer.parseInt(dataToImport[i][6].toString());
		int duration = Integer.parseInt(dataToImport[i][7].toString());
		if(dataToImport[i][8].toString().equals("(null)")){
				ev.setCause_code(-1);
		}else{
		ev.setCause_code(Integer.parseInt(dataToImport[i][8].toString()));
		}
		String neversion = dataToImport[i][9].toString();
		BigInteger imsi = BigInteger.valueOf(Long.parseLong(dataToImport[i][10].toString()));
		BigInteger hier3_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][11].toString()));
		BigInteger hier32_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][12].toString()));
		BigInteger hier321_id = BigInteger.valueOf(Long.parseLong(dataToImport[i][13].toString()));
		bd.setCell_id(cellId);
		bd.setDate_time(c);
		bd.setDuration(duration);
		bd.setEvent_cause(ev);
		bd.setFailure_class(f);
		bd.setHier321_id(hier321_id);
		bd.setHier32_id(hier32_id);
		bd.setHier3_id(hier3_id);
		bd.setImsi(imsi);
		bd.setMcc_mnc(mcc);
		bd.setNe_version(neversion);
		bd.setUser_equipment(ue);
		bdArray[i] = bd;
		}
		return bdArray;
	}
}