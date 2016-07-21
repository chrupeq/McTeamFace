package com.ait.db.data;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ait.db.model.Base_data;

@Stateless
@LocalBean
public class UniqeModelFailuresDAO {

		@PersistenceContext
	    private EntityManager entityManager;
		private Query query;
		
		private DateParser dateParser;
		
		
		public List<Base_data> getAllUniqueModels(final int tacNumber) {
			
			query = entityManager.createQuery("SELECT b FROM Base_data b WHERE ue_type=" + tacNumber + " GROUP BY event_id, cause_code");
			
			final List<Base_data> distinctModelFailures1 = query.getResultList();
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM Base_data b WHERE ue_type=" + tacNumber + " GROUP BY event_id, cause_code");
			final List<Long> distinctModelFailures2 = query.getResultList();
			for(int i = 0; i < distinctModelFailures1.size(); i ++){
				distinctModelFailures1.get(i).setHier3_id(BigInteger.valueOf(distinctModelFailures2.get(i)));
			}
			return distinctModelFailures1;
	    }
		
public List<Base_data> getAllUniqueModelsBetweenDates(final int tacNumber, final String date1, final String date2) {
	
	dateParser = new DateParser();
	final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	final Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
			
			query = entityManager.createQuery("SELECT b FROM Base_data b WHERE ue_type=" + tacNumber + " AND date_time BETWEEN :date1 AND :date2");
			query.setParameter("date1", calendarArray[0])
			.setParameter("date2", calendarArray[1]);
	
			final List<Base_data> distinctStuff = query.getResultList();
			final List<Base_data> distinctModelFailures1 = new ArrayList<>();
		final Base_data baseDataObject = new Base_data();
			query = entityManager.createQuery("SELECT COUNT(*) FROM Base_data b WHERE ue_type=" + tacNumber + " AND date_time BETWEEN :date1 AND :date2");
			query.setParameter("date1", calendarArray[0])
			.setParameter("date2", calendarArray[1]);
			final List<Long> distinctModelFailures2 = query.getResultList();
			baseDataObject.setHier3_id(BigInteger.valueOf(distinctModelFailures2.get(0)));
			baseDataObject.setUser_equipment(distinctStuff.get(0).getUser_equipment());
			distinctModelFailures1.add(baseDataObject);
			System.out.println(distinctModelFailures1.size());
			return distinctModelFailures1;
	    }
		
	}

