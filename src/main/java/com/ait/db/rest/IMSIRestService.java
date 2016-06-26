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

import com.ait.db.data.IMSIDAO;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSI;
import com.ait.db.model.NetworkEntity;

@Path("/imsi")
@Stateless
@LocalBean
public class IMSIRestService {

	@EJB
	IMSIDAO IMSIDao;
	@EJB
	NetworkEntityDAO networkEntityDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	@Path("/get_unique")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNetworkEntityEntiries() {
		try{
			List<IMSI> IMSIList = IMSIDao.getAllUniqueIMSIs();
			return Response.status(200).entity(IMSIList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	@GET
	@Path("/get_stats")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getIMSIStats() {
		try {
			List<? extends NetworkEntity> baseDataList = networkEntityDAO.getAllNetworkEntityEntries(NetworkEntityType.BASE_DATA);
			if(baseDataList.isEmpty()) {
				return Response.status(404).build();
			}
			return Response.status(200).entity(baseDataList).build();
		} catch(Exception e) {
			return Response.status(404).build();
		}
	}	
	
//	@GET
//	@Path("/get_imsis_between_dates")
//	@Produces({MediaType.APPLICATION_JSON})
//	PUBLIC
}
