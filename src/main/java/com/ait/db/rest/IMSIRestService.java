package com.ait.db.rest;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.swing.plaf.synth.SynthSpinnerUI;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import com.ait.db.data.DateParser;
import com.ait.db.data.IMSIDAO;
import com.ait.db.data.TopTenDAO;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.NetworkEntityType;
import com.ait.db.model.Base_data;
import com.ait.db.model.IMSIWithEventIDAndCauseCode;
import com.ait.db.model.IMSIWithValidFailureClasses;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;
import com.ait.imsiStats.IMSIStats;
import com.ait.imsiStats.IMSIStatsObjectFactory;
import com.ait.imsiStats.IMSIStatsProducer;
import com.fasterxml.jackson.databind.ObjectMapper;


@Path("/imsi")
@Stateless
@LocalBean
public class IMSIRestService {

	@EJB
	IMSIDAO IMSIDao;
	@EJB
	NetworkEntityDAO networkEntityDAO;
	@EJB
	TopTenDAO topTenDAO;
	DateParser dateParser;
	@PersistenceContext
	private EntityManager entityManager;
	
	@GET
	@Path("/get_unique")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getAllNetworkEntityEntiries() {
		try{
			List<BigInteger> IMSIList = IMSIDao.getAllUniqueIMSIs();
			return Response.status(200).entity(IMSIList).build();
		}catch(Exception e){
			System.out.println(e.getMessage());
			return Response.status(404).build();
		}
	}
	@GET
	@Path("/get_stats")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getIMSIStats(@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo) {
		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		System.out.println(dateOne);
		System.out.println(dateTwo);
		try {
			List<Base_data> baseDataList = IMSIDao.getAllBaseDataBetweenDates(dateOne, dateTwo);
			if(baseDataList.isEmpty()) {
				return Response.status(404).build();
			}
			IMSIStatsProducer imsiStatsProducer = new IMSIStatsProducer(baseDataList);
			List<IMSIStats> imsiStats = imsiStatsProducer.getListOfIMSIStatsObjects();
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(imsiStats);
			return Response.status(200).entity(imsiStats).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}	
	
	@GET
	@Path("/get_imsis_between_dates")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getImsisBetweenDates(@QueryParam("date1") String date1, @QueryParam("date2") 
		String date2){
		
		dateParser = new DateParser();
		date1 = dateParser.convertFromEuropeanToAmericanDateFormat(date1);
		date2 = dateParser.convertFromEuropeanToAmericanDateFormat(date2);
		
		try{
			List<IMSIWithValidFailureClasses> imsiList = IMSIDao.getIMSIsByDates(date1, date2);
		if(imsiList.isEmpty()) {
			return Response.status(404).build();
		}
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(imsiList);
		return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
	@GET
	@Path("/imsi_event_id/{imsi}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getImsisWithEventIDsAndCauseCodes(@PathParam("imsi") BigInteger imsi) {
		try {
			List<IMSIWithEventIDAndCauseCode> imsiList = IMSIDao.getIMSIsWithEventIDsAndCauseCodes(imsi);
			if(imsiList.isEmpty()) {
				return Response.status(404).build();
			} 
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(imsiList);
			return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			
			return Response.status(400).build();
		}
	}
	@GET
	@Path("/imsi_count_between_dates")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getIMSICountBetweenDates(@QueryParam("imsi") BigInteger imsi, 
			@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo) {
		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		
		try{
			List<Base_data> baseDataList = IMSIDao.getAllBaseDataByIMSIAndDate(imsi, dateOne, dateTwo);
		if(baseDataList.isEmpty()) {
			IMSIStats imsiStats = IMSIStatsObjectFactory.getIMSIStatsInstance(imsi, 0);
			List<IMSIStats> imsiList = new ArrayList<>();
			imsiList.add(imsiStats);
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(imsiList);
			System.out.println(jsonInString.length());
			return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		}
		IMSIStatsProducer imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		List<IMSIStats> imsiList = imsiStatsProducer.getListOfIMSIStatsObjectsWithFailuresBetweenDates();
		ObjectMapper mapper = new ObjectMapper();
		String jsonInString = mapper.writeValueAsString(imsiList);
		return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
	
	@GET
	@Path("/top10_market_operator_cellIdCombinations")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTopTenMarketOperatorCellIdCombinations(@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo){

		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		try {
			List<TopTenMarketOperatorCellIdCombinations> imsiList = topTenDAO.getTopTenMarketOperatorCellIdCombinationsWithFailures(dateOne, dateTwo);
			
			if(imsiList.isEmpty()) {
				return Response.status(404).build();
			} 
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(imsiList);
			return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
		
		
		
	
	}
}

