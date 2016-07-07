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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.ait.db.model.Base_data;
import com.ait.db.model.UniqueFailures;
import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
public class UniqeModelFailuresDAO {

		@PersistenceContext
	    private EntityManager entityManager;
		private Query query;
		
		private DateParser dateParser;
		
		
		public List<Base_data> getAllUniqueModels(int tacNumber) {
			
			query = entityManager.createQuery("SELECT b FROM Base_data b WHERE ue_type=" + tacNumber + " GROUP BY event_id, cause_code");
			
			List<Base_data> distinctModelFailures1 = query.getResultList();
			
			query = entityManager.createQuery("SELECT COUNT(*) FROM Base_data b WHERE ue_type=" + tacNumber + " GROUP BY event_id, cause_code");
			List<Long> distinctModelFailures2 = query.getResultList();
			for(int i = 0; i < distinctModelFailures1.size(); i ++){
				distinctModelFailures1.get(i).setHier3_id(BigInteger.valueOf(distinctModelFailures2.get(i)));
			}
			return distinctModelFailures1;
	    }
		
public List<Base_data> getAllUniqueModelsBetweenDates(int tacNumber, String date1, String date2) {
	
	dateParser = new DateParser();
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	Calendar[] calendarArray = dateParser.parseStringsToCalendarObjects(simpleDateFormat, date1, date2);
			
			query = entityManager.createQuery("SELECT b FROM Base_data b WHERE ue_type=" + tacNumber + " AND date_time BETWEEN :date1 AND :date2");
			query.setParameter("date1", calendarArray[0])
			.setParameter("date2", calendarArray[1]);
	
			List<Base_data> distinctStuff = query.getResultList();
			List<Base_data> distinctModelFailures1 = new ArrayList<>();
		Base_data b = new Base_data();
			query = entityManager.createQuery("SELECT COUNT(*) FROM Base_data b WHERE ue_type=" + tacNumber + " AND date_time BETWEEN :date1 AND :date2");
			query.setParameter("date1", calendarArray[0])
			.setParameter("date2", calendarArray[1]);
			List<Long> distinctModelFailures2 = query.getResultList();
			b.setHier3_id(BigInteger.valueOf(distinctModelFailures2.get(0)));
			b.setUser_equipment(distinctStuff.get(0).getUser_equipment());
			distinctModelFailures1.add(b);
			return distinctModelFailures1;
	    }
		
	}

