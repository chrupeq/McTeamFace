package com.ait.db.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.UniqeModelFailuresDAO;
import com.ait.db.model.Base_data;

@Stateless
@LocalBean
@Path("/unique_model_failures")
public class UniqueModelFailuresRestService {


	@EJB
	UniqeModelFailuresDAO UniqueModelFailuresDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{ue_type}")
	public Response getAllUniqueDataForModel(@PathParam("ue_type") int tacNumber) {
		try{
			System.out.println(tacNumber);
			List<Base_data> modelList = UniqueModelFailuresDao.getAllUniqueModels(tacNumber);
			if(modelList.isEmpty()) {
				return Response.status(404).build();
			}
			return Response.status(200).entity(modelList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
		
	}
	
}
