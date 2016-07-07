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

import com.ait.db.data.CauseCodeDAO;

@Path("/cause_codes")
@Stateless
@LocalBean
public class CauseCodeRestService {
	
	@EJB
	CauseCodeDAO causeCodeDAO;
	@PersistenceContext
	private EntityManager entityManager;
	
	
	@GET
	@Path("/get_unique")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUniqueCauseCodesFromBaseDataTable(){
		try{
			List<Integer> causeCodes = causeCodeDAO.getUniqueCauseCodes();
			if(causeCodes.isEmpty()){
				return Response.status(404).build();
			}
			return Response.status(200).entity(causeCodes).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}	
	}
}
