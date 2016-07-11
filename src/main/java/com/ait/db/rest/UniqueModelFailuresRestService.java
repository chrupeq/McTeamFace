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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.DateParser;
import com.ait.db.data.UniqeModelFailuresDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.UniqueFailures;
import com.ait.imsiStats.IMSIStats;
import com.ait.imsiStats.IMSIStatsProducer;
import com.fasterxml.jackson.databind.ObjectMapper;

@Stateless
@LocalBean
@Path("/unique_model_failures")
public class UniqueModelFailuresRestService {


	@EJB
	UniqeModelFailuresDAO UniqueModelFailuresDao;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	private DateParser dateParser;
	
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
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(modelList);
			return Response.status(200).entity(modelList).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
		
	}
	
	@GET
	@Path("/date_time_query")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUniqueModelsBetweenDates(@QueryParam("tacNumber") int tacNumber, @QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo) {
		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		
		try {
			List<Base_data> baseDataList = UniqueModelFailuresDao.getAllUniqueModelsBetweenDates(tacNumber, dateOne, dateTwo);
			if(baseDataList.isEmpty()) {
				return Response.status(404).build();
			}
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(baseDataList);
			return Response.status(200).entity(baseDataList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}	
	
}
