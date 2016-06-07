package com.ait.db.rest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.Event_cause;
import com.ait.db.model.Failure_class;
import com.ait.db.model.Mcc_mnc;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.User_equipment;


@Path("/network_entities")
public class NetworkEntityRestService {
	@EJB
	NetworkEntityDAO networkEntityDAO;
	@PersistenceContext
	private EntityManager entityManager;
	private NetworkEntityType networkEntityTypeEnum;
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get_by_id/{report_id}")
	public Response getNetworkEntityById(@PathParam("report_id") int report_id) {
		networkEntityTypeEnum = NetworkEntityType.BASE_DATA;
		Base_data baseData = (Base_data) networkEntityDAO.getNetworkEntityById(networkEntityTypeEnum, report_id);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm a z");
//		ObjectMapper objectMapper = new ObjectMapper();
	
		if (baseData != null) {
			return Response.status(200).entity(baseData).build();
		} else {
			return Response.status(404).build();
		}	
	}
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/get_all/{networkEntityType}")
	public Response getAllNetworkEntityEntiries(@PathParam("networkEntityType") String networkEntityType) {
		if(networkEntityType.equals(NetworkEntityType.BASE_DATA.getNetworkEntityType())) {
			networkEntityTypeEnum = NetworkEntityType.BASE_DATA;
		}
		List<? extends NetworkEntity> networkEntities = networkEntityDAO.getAllNetworkEntityEntries(networkEntityTypeEnum);
		return Response.status(200).entity(networkEntities).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/create_fc")
	public Response persistFailureClass(Failure_class failureClass) {
			networkEntityDAO.saveNetworkEntity(failureClass);
			return Response.status(201).entity(failureClass).build();
			
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/create_mcc_mnc")
	public Response persistMccMnc(Mcc_mnc mccMnc) {
		networkEntityDAO.saveNetworkEntity(mccMnc);
		return Response.status(201).entity(mccMnc).build();
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/create_ec")
	public Response persistEventCause(Event_cause eventCause) {
		networkEntityDAO.saveNetworkEntity(eventCause);
		return Response.status(201).entity(eventCause).build();
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/create_ue")
	public Response persistUserEqupment(User_equipment userEquipment) {
		networkEntityDAO.saveNetworkEntity(userEquipment);
		return Response.status(201).entity(userEquipment).build();
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/create_bd")
	public Response persistBaseData (Base_data baseData) {
		networkEntityDAO.saveNetworkEntity(baseData);
		return Response.status(201).entity(baseData).build();
	}

}
