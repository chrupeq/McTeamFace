package com.ait.db.rest;

import java.util.List;

import javax.ejb.EJB;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
	@Path("/{networkEntityType}/{id}")
	public Response getNetworkEntityById(@PathParam("networkEntityType")final String networkEntityType,@PathParam("id")final int report_id) {
		try{
			networkEntityTypeEnum = getNetworkEntityType(networkEntityType);
			final NetworkEntity networkEntity = networkEntityDAO.getNetworkEntityById(networkEntityTypeEnum, report_id);
			if(networkEntity.equals(null)){
				return Response.status(404).build();
			}
			return Response.status(200).entity(networkEntity).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{networkEntityType}/{id1}/{id2}")
	public Response getNetworkEntityById(@PathParam("networkEntityType") final String networkEntityType,@PathParam("id1") final int id1,@PathParam("id2") final int id2) {
		try{
			networkEntityTypeEnum = getNetworkEntityType(networkEntityType);
			final NetworkEntity networkEntity = networkEntityDAO.getNetworkEntityById(networkEntityTypeEnum, id1,id2);
			if(networkEntity.equals(null)){
				return Response.status(404).build();
			}
			return Response.status(200).entity(networkEntity).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	
	@GET
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/{networkEntityType}")
	public Response getAllNetworkEntityEntiries(@PathParam("networkEntityType")final String networkEntityType) {
		System.out.println(networkEntityType);
		try{
			networkEntityTypeEnum = getNetworkEntityType(networkEntityType);
			final List<? extends NetworkEntity> networkEntities = networkEntityDAO.getAllNetworkEntityEntries(networkEntityTypeEnum);
			return Response.status(200).entity(networkEntities).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
		
	}
	@DELETE
	@Path("/{networkEntityType}/{id}")
	public Response deleteBase_data(@PathParam("networkEntityType")final String networkEntityType, @PathParam("id")final int networkEntityId) {
		try{
			networkEntityTypeEnum = getNetworkEntityType(networkEntityType);
			networkEntityDAO.deleteNetworkEntity(networkEntityDAO.getNetworkEntityById(networkEntityTypeEnum, networkEntityId));
			return Response.status(204).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	@DELETE
	@Path("/{networkEntityType}/{id1}/{id2}")
	public Response deleteBase_data(@PathParam("networkEntityType")final String networkEntityType, @PathParam("id1") final int id1, @PathParam("id2") final int id2) {
		try{
			networkEntityTypeEnum = getNetworkEntityType(networkEntityType);
			networkEntityDAO.deleteNetworkEntity(networkEntityDAO.getNetworkEntityById(networkEntityTypeEnum, id1,id2));
			return Response.status(204).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	
//	@POST
//	@Consumes({MediaType.APPLICATION_JSON})
//	@Produces({MediaType.APPLICATION_JSON})
//	@Path("/{networkEntityType}")
//	public Response saveNetworkEntity(@PathParam("networkEntityType") String networkEntityType,NetworkEntity networkEntityTypeClass) {
//		if(networkEntityType.equals(NetworkEntityType.BASE_DATA.getNetworkEntityType())) {
//			networkEntityDAO.saveNetworkEntity(networkEntityTypeClass);
//		}else if(networkEntityType.equals(NetworkEntityType.EVENT_CAUSE.getNetworkEntityType())){
//			networkEntityDAO.saveNetworkEntity((Event_cause)networkEntityTypeClass);
//		}else if(networkEntityType.equals(NetworkEntityType.FAILURE_CLASS.getNetworkEntityType())){
//			networkEntityTypeEnum = NetworkEntityType.FAILURE_CLASS;
//		}else if(networkEntityType.equals(NetworkEntityType.MCC_MNC.getNetworkEntityType())){
//			networkEntityTypeEnum = NetworkEntityType.MCC_MNC;
//		}else if(networkEntityType.equals(NetworkEntityType.USER_EQUIPMENT.getNetworkEntityType())){
//			networkEntityTypeEnum = NetworkEntityType.USER_EQUIPMENT;
//		}else {
//			return Response.status(404).build();
//		}
//		return Response.status(201).entity(networkEntityType).build();
//	}
	
	public NetworkEntityType getNetworkEntityType(final String networkEntity) {
		if(networkEntity.equals(NetworkEntityType.BASE_DATA.getNetworkEntityType())) {
			return NetworkEntityType.BASE_DATA;
		}else if(networkEntity.equals(NetworkEntityType.EVENT_CAUSE.getNetworkEntityType())){
			return NetworkEntityType.EVENT_CAUSE;
		}else if(networkEntity.equals(NetworkEntityType.FAILURE_CLASS.getNetworkEntityType())){
			return NetworkEntityType.FAILURE_CLASS;
		}else if(networkEntity.equals(NetworkEntityType.MCC_MNC.getNetworkEntityType())){
			return NetworkEntityType.MCC_MNC;
		}else if(networkEntity.equals(NetworkEntityType.USER_EQUIPMENT.getNetworkEntityType())){
			return NetworkEntityType.USER_EQUIPMENT;
		}else{
			return NetworkEntityType.BASE_DATA;
		}
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/failure_class")
	public Response persistFailureClass(final Failure_class failureClass) {
			networkEntityDAO.saveNetworkEntity(failureClass);
			return Response.status(201).entity(failureClass).build();
			
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/failure_class/{id}")
	public Response updateFailureClass(final Failure_class failureClass) {
			networkEntityDAO.updateNetworkEntity(failureClass);
			return Response.status(200).entity(failureClass).build();
			
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/mcc_mnc")
	public Response persistMccMnc(final Mcc_mnc mccMnc) {
		networkEntityDAO.saveNetworkEntity(mccMnc);
		return Response.status(201).entity(mccMnc).build();
	}
	
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/mcc_mnc/{id1}/{id2}")
	public Response updateMccMnc(final Mcc_mnc mccMnc) {
		networkEntityDAO.updateNetworkEntity(mccMnc);
		return Response.status(200).entity(mccMnc).build();
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event_cause")
	public Response persistEventCause(final Event_cause eventCause) {
		networkEntityDAO.saveNetworkEntity(eventCause);
		return Response.status(201).entity(eventCause).build();
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/event_cause/{id1}/{id2}")
	public Response updateEventCause(final Event_cause eventCause) {
		networkEntityDAO.updateNetworkEntity(eventCause);
		return Response.status(200).entity(eventCause).build();
	}
	
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user_equipment")
	public Response persistUserEqupment(final User_equipment userEquipment) {
		networkEntityDAO.saveNetworkEntity(userEquipment);
		return Response.status(201).entity(userEquipment).build();
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/user_equipment/{id}")
	public Response updateUserEqupment(final User_equipment userEquipment) {
		networkEntityDAO.updateNetworkEntity(userEquipment);
		return Response.status(200).entity(userEquipment).build();
	}
	@POST
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/base_data")
	public Response persistBaseData (final Base_data baseData) {
		networkEntityDAO.saveNetworkEntity(baseData);
		return Response.status(201).entity(baseData).build();
	}
	@PUT
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	@Path("/base_data/{id}")
	public Response updateBaseData (final Base_data baseData) {
		networkEntityDAO.updateNetworkEntity(baseData);
		return Response.status(200).entity(baseData).build();
	}

}


