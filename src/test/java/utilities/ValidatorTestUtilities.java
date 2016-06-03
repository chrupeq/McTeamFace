package utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

import com.validation.JDBCConnectionManager;

public class ValidatorTestUtilities {

	private static DateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	
	public static String getDatesInTheFuture(int timeToGet){
		
		Calendar currentDateAndTime = Calendar.getInstance();

		
		switch(timeToGet){
		case 1:
			currentDateAndTime.add(Calendar.MINUTE, 2);
			break;
		case 2:
			currentDateAndTime.add(Calendar.HOUR, 1);
			break;
		case 3:
			currentDateAndTime.add(Calendar.YEAR, 1);
			break;
		case 4:
			currentDateAndTime.add(Calendar.MONTH, 1);
			break;
		case 5:
			currentDateAndTime.add(Calendar.DATE, 1);
			break;
		}
		
		return dateFormatter.format(currentDateAndTime.getTime());
	}
	
}
