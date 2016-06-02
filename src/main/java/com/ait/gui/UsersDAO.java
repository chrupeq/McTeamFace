package com.ait.gui;

import java.util.List;



import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;



@Stateless
@LocalBean
public class UsersDAO {

    @PersistenceContext
    private EntityManager em;
    
	public List<User> getAllUsers() {
    	Query query=em.createQuery("SELECT u FROM User u");
        return query.getResultList();
    }
		
	public User getUser(int id ) {
        return em.find(User.class, id);
    }
	
	
	
	
	
	
      
}
