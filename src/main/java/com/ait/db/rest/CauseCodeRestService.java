package com.ait.db.rest;


import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.CauseCodeDAO;
import com.fasterxml.jackson.databind.ObjectMapper;

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
			final List<Integer> causeCodes = causeCodeDAO.getUniqueCauseCodes();
			if(causeCodes.isEmpty()){
				return Response.status(404).build();
			}
			return Response.status(200).entity(causeCodes).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}	
	}
	@GET
	@Path("/get_by_cause_code")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAffectedIMSIsByCauseCode(@QueryParam("cause_code") final int causeCode){
		try{
			final List<BigInteger> affectedIMSIs = causeCodeDAO.getAffectedIMSIs(causeCode);
			if(affectedIMSIs.isEmpty()){
				return Response.status(404).build();
			}
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(affectedIMSIs);
			return Response.status(200).entity(affectedIMSIs).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
}
