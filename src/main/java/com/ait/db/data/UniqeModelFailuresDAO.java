package com.ait.db.data;

import java.math.BigInteger;

import java.util.ArrayList;
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
		
	}

