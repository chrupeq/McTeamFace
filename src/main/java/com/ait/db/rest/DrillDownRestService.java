package com.ait.db.rest;

import java.math.BigInteger;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.DateParser;
import com.ait.db.data.DrillDownDAO;
import com.ait.db.model.Base_data;

@Path("/drill_down")
@Stateless
@LocalBean
public class DrillDownRestService {
	
	DateParser dateParser;
	
	@EJB
	DrillDownDAO drillDownDao;
	
	@GET
	@Path("/failure_event_desc")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getFailureEventDescRest(@QueryParam("eventId")final int eventId, @QueryParam("causeCode")final int causeCode, @QueryParam("tacNumber")final int tacNumber) {
		try{
			final List<Base_data> baseDataList = drillDownDao.getFailureEventDesc(eventId, causeCode, tacNumber);
			return Response.status(200).entity(baseDataList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	
	@GET
	@Path("/imsi_duration_percent")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModelOfPhoneDescRest(@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo, @QueryParam("duration1") final Long duration1, @QueryParam("duration2") final Long duration2) {
		
		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		try{
			final List<Base_data> baseDataList = drillDownDao.getFailureDurationsBetweenDates(dateOne, dateTwo, duration1, duration2);
			return Response.status(200).entity(baseDataList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}

}

