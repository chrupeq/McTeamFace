package com.ait.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class Mcc_mncFactory {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("ericsson_project");
	private EntityManager em = emf.createEntityManager();
	
	public int createMcc_mnc(int mcc, int mnc, String country, String operator){
		Mcc_mnc mcc_mnc = new Mcc_mnc(mcc, mnc, country, operator);
		EntityTransaction transaction = em.getTransaction();
		transaction.begin();
		em.persist(mcc_mnc);
		em.getTransaction().commit();
		return mcc_mnc.getMcc();
	}
	public ArrayList<Mcc_mnc> getAllMcc_mncDetails(){
		Query query = em.createQuery("SELECT c FROM Mcc_mnc c");
		Collection<Mcc_mnc> mcc_mncList = query.getResultList();
		return new ArrayList<Mcc_mnc>(mcc_mncList);
	}
	public void updateMcc_mnc(int mcc, String country, String operator){
		Mcc_mnc mcc_mnc = em.find(Mcc_mnc.class, mcc);
		em.getTransaction().begin();
		if(country != null){
			mcc_mnc.setCountry(country);
		}
		if(operator != null){
			mcc_mnc.setOperator(operator);
		}
		em.getTransaction().commit();
	}
	
	public void deleteMcc_mnc(int mcc){
		Mcc_mnc mcc_mnc = em.find(Mcc_mnc.class, mcc);
		em.getTransaction().begin();
		em.remove(mcc_mnc);
		em.getTransaction().commit();
	}
}
