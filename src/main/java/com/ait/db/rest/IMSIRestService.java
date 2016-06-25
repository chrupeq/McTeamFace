package com.ait.db.rest;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

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

import com.ait.db.data.IMSIDAO;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSI;
import com.ait.db.model.NetworkEntity;
import com.ait.imsiStats.IMSIStats;
import com.ait.imsiStats.IMSIStatsProducer;

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
			IMSIStatsProducer imsiStatsProducer = new IMSIStatsProducer(baseDataList);
			List<IMSIStats> imsiStats = imsiStatsProducer.getListOfIMSIStatsObjects();
			return Response.status(200).entity(imsiStats).build();
		} catch(Exception e) {
			return Response.status(404).build();
		}
	}	
	
	@GET
	@Path("/get_imsis_between_dates")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getImsisBetweenDates(@QueryParam("date1") String date1, @QueryParam("date2") String date2){

		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.UK);
		String firstDate = date1.substring(6,10) + "/" + date1.substring(3, 5) + "/" + date1.substring(0,2) + " " + date1.substring(11,16);
		String secondDate = date2.substring(6,10) + "/" + date2.substring(3, 5) + "/" + date2.substring(0,2) + " " + date2.substring(11,16);
		System.out.println(firstDate);
		System.out.println(secondDate);
		try{
		System.out.println(sdf.parse(firstDate));
		System.out.println(sdf.parse(secondDate));
		Calendar c = Calendar.getInstance();
		c.setTime(sdf.parse(firstDate));
		Calendar c2 = Calendar.getInstance();
		c2.setTime(sdf.parse(firstDate));
		
		Date date = new Date(c.getTimeInMillis());
		Date date3 = new Date(c2.getTimeInMillis());

		List<Base_data> imsiList = IMSIDao.getIMSIsByDates(date, date3);
		if(imsiList.isEmpty()) {
			return Response.status(404).build();
		}
		return Response.status(200).entity(imsiList).build();
		}catch(Exception e){
			return Response.status(400).build();
		}
	}
}
