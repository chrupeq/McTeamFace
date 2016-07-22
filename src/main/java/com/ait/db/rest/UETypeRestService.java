package com.ait.db.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.UETypeDAO;
import com.ait.db.model.User_equipment;

@Stateless
@LocalBean
@Path("/unique_model")
public class UETypeRestService {

	@EJB
	UETypeDAO UETypeDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllModels() {
		try{
			final List<User_equipment> modelList = UETypeDao.getAllUniqueModels();
			return Response.status(200).entity(modelList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
		
	}
	
}
