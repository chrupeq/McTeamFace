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

import com.ait.db.data.DrillDownDAO;
import com.ait.db.model.Base_data;

@Path("/drill_down")
@Stateless
@LocalBean
public class DrillDownRestService {
	
	@EJB
	DrillDownDAO drillDownDao;
	
	@GET
	@Path("/failure_event_desc")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getFailureEventDescRest(@QueryParam("eventId") int eventId, @QueryParam("causeCode") int causeCode, @QueryParam("tacNumber") int tacNumber) {
		try{
			List<Base_data> baseDataList = drillDownDao.getFailureEventDesc(eventId, causeCode, tacNumber);
			return Response.status(200).entity(baseDataList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	
	@GET
	@Path("/imsi_desc")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getModelOfPhoneDescRest(@QueryParam("imsi") BigInteger imsi) {
		try{
			List<Base_data> baseDataList = drillDownDao.getModelOfPhoneDesc(imsi);
			return Response.status(200).entity(baseDataList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}

}
