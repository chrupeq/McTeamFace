package com.ait.db.data;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.ait.db.model.Base_data;
import com.ait.db.model.CompositePK;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Event_causeKey;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.Mcc_mncKey;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
public class NetworkEntityDAO {
	
	@PersistenceContext
    private EntityManager entityManager;
	List<NetworkEntity> listOfNetworkEntities;
	private Query query;
	
	public List<? extends NetworkEntity> getAllNetworkEntityEntries(NetworkEntityType networkEntityType) {
		Query query = getTheRetrieveAllEntriesQuery(networkEntityType);
		List<Object> databaseEntities = query.getResultList();
		return getTheListOfNetworkEntities(databaseEntities);
    }
	public NetworkEntityType getNetworkEntityType(NetworkEntity networkEntity) {
		return NetworkEntityTypeEnumFactory.getNetworkEntityTypeEnum(networkEntity);
	}
	private Query getTheRetrieveAllEntriesQuery(NetworkEntityType networkEntityType) {
		
		if (networkEntityType.equals(NetworkEntityType.BASE_DATA)) {
			query = entityManager.createQuery("SELECT b FROM Base_data b");
		}
		if(networkEntityType.equals(NetworkEntityType.MCC_MNC)) {
			query = entityManager.createQuery("SELECT m FROM Mcc_mnc m");
		}
		if(networkEntityType.equals(NetworkEntityType.EVENT_CAUSE)) {
			query = entityManager.createQuery("SELECT e FROM Event_cause e");
		}
		if(networkEntityType.equals(NetworkEntityType.FAILURE_CLASS)) {
			query = entityManager.createQuery("SELECT f FROM Failure_class f");
		}
		if(networkEntityType.equals(NetworkEntityType.USER_EQUIPMENT)) {
			query = entityManager.createQuery("SELECT q FROM User_equipment q");
		}
		return query;
	}
	public void saveNetworkEntity(NetworkEntity networkEntity) {
		System.out.println("in save");
		entityManager.persist(networkEntity);
	}
	public void updateNetworkEntity(NetworkEntity networkEntity) {
		entityManager.merge(networkEntity);
	}
	public void deleteNetworkEntity(NetworkEntity networkEntity) {
		entityManager.remove(entityManager.contains(networkEntity)? networkEntity : entityManager.merge(networkEntity));
	}
	public NetworkEntity getNetworkEntityById(NetworkEntityType networkEntityType, Object...idParameters) {
		if(networkEntityType.equals(NetworkEntityType.BASE_DATA)) {
			return entityManager.find(Base_data.class, idParameters[0]);
		}
		if(networkEntityType.equals(NetworkEntityType.MCC_MNC)) {
			Mcc_mncKey mccMncKey = (Mcc_mncKey) PrimaryKeyFactory.getPrimaryKeyEntity(CompositePrimaryKeyType.MCC_MNC_KEY, idParameters);
			return entityManager.find(Mcc_mnc.class, mccMncKey);
		}
		if(networkEntityType.equals(NetworkEntityType.EVENT_CAUSE)) {
			Event_causeKey eventCauseKey = (Event_causeKey) PrimaryKeyFactory.getPrimaryKeyEntity(CompositePrimaryKeyType.EVENT_CAUSE_KEY, idParameters);
			return entityManager.find(Event_cause.class, eventCauseKey);
		}
		if(networkEntityType.equals(NetworkEntityType.FAILURE_CLASS)) {
			return entityManager.find(Failure_class.class, idParameters[0]);
		}
		if(networkEntityType.equals(NetworkEntityType.USER_EQUIPMENT)) {
			return entityManager.find(User_equipment.class, idParameters[0]);
		}
		return null;
	}
	private List<NetworkEntity> getTheListOfNetworkEntities(List<Object> databaseEntities) {
		listOfNetworkEntities = new ArrayList<NetworkEntity>();
		for (Object entry : databaseEntities) {
			if(entry instanceof NetworkEntity) {
				listOfNetworkEntities.add((NetworkEntity) entry);
			}
		}
		return listOfNetworkEntities;
	}
	static class PrimaryKeyFactory {
		public static CompositePK getPrimaryKeyEntity(CompositePrimaryKeyType compositePrimaryKeyType, Object...params) {
			if(compositePrimaryKeyType.equals(CompositePrimaryKeyType.MCC_MNC_KEY)) {
				Mcc_mncKey mccMncKey = new Mcc_mncKey();
				mccMncKey.setMcc((Integer) params[0]);
				mccMncKey.setMnc((Integer) params[1]);
				return mccMncKey;
			}
			if(compositePrimaryKeyType.equals(CompositePrimaryKeyType.EVENT_CAUSE_KEY)) {
				Event_causeKey eventCauseKey = new Event_causeKey();
				eventCauseKey.setCause_code((Integer) params[0]);
				eventCauseKey.setEvent_id((Integer) params[1]);
				return eventCauseKey;
			}
			return null;
		}
	}
	public static class NetworkEntityTypeEnumFactory {
		public static NetworkEntityType getNetworkEntityTypeEnum(NetworkEntity networkEntity) {
			if(networkEntity instanceof Base_data) {
				return NetworkEntityType.BASE_DATA;
			} if(networkEntity instanceof Mcc_mnc) {
				return NetworkEntityType.MCC_MNC;
			} if(networkEntity instanceof Event_cause) {
				return NetworkEntityType.EVENT_CAUSE;
			} if(networkEntity instanceof Failure_class) {
				return NetworkEntityType.FAILURE_CLASS;
			} if(networkEntity instanceof User_equipment) {
				return NetworkEntityType.USER_EQUIPMENT;
			}
			return null;
		}
	}
}
