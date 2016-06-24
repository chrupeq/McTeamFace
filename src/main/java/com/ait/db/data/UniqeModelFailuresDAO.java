package com.ait.db.data;

	import java.util.List;

	import javax.ejb.LocalBean;
	import javax.ejb.Stateless;
	import javax.persistence.EntityManager;
	import javax.persistence.PersistenceContext;
	import javax.persistence.Query;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.ait.db.model.Base_data;
import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
public class UniqeModelFailuresDAO {

		@PersistenceContext
	    private EntityManager entityManager;
		private Query query;
		
		
		public List<Base_data> getAllUniqueModels(int tacNumber) {
			query = entityManager.createQuery("SELECT b FROM Base_data b WHERE ue_type = " + tacNumber);
			List<Base_data> distinctModelFailures = query.getResultList();
			return distinctModelFailures;
	    }
		
	}

