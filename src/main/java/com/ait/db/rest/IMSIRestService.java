package com.ait.db.rest;

import java.math.BigInteger;
import java.util.ArrayList;
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
import com.ait.db.data.IMSIDAO;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.data.TopTenDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.Failure_class;
import com.ait.db.model.IMSIHelperObject;
import com.ait.db.model.IMSIWithEventIDAndCauseCode;
import com.ait.db.model.IMSIWithValidFailureClasses;
import com.ait.db.model.TopTenIMSIForFailures;
import com.ait.db.model.TopTenMarketOperatorCellIdCombinations;
import com.ait.db.model.UniqueEventCauseFailureClass;
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
			final List<BigInteger> IMSIList = IMSIDao.getAllUniqueIMSIs();
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
			final List<IMSIStats> imsiStats = IMSIDao.getIMSIStatistics(dateOne, dateTwo);
			if(imsiStats.isEmpty()) {
				return Response.status(404).build();
			}
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(imsiStats);
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
			final List<IMSIWithValidFailureClasses> imsiList = IMSIDao.getIMSIsByDates(date1, date2);
		if(imsiList.isEmpty()) {
			return Response.status(404).build();
		}
		final ObjectMapper mapper = new ObjectMapper();
		final String jsonInString = mapper.writeValueAsString(imsiList);
		return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
	
	@GET
	@Path("/imsi_event_id/{imsi}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getImsisWithEventIDsAndCauseCodes(@PathParam("imsi")final BigInteger imsi) {
		try {
			final List<IMSIWithEventIDAndCauseCode> imsiList = IMSIDao.getIMSIsWithEventIDsAndCauseCodes(imsi);
			if(imsiList.isEmpty()) {
				return Response.status(404).build();
			} 
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(imsiList);
			return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			
			return Response.status(400).build();
		}
	}
	
	@GET
	@Path("/imsi_count_between_dates")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getIMSICountBetweenDates(@QueryParam("imsi")final BigInteger imsi, 
			@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo) {
		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		
		try{
			final List<Base_data> baseDataList = IMSIDao.getAllBaseDataByIMSIAndDate(imsi, dateOne, dateTwo);
		if(baseDataList.isEmpty()) {
			final IMSIStats imsiStats = IMSIStatsObjectFactory.getIMSIStatsInstance(imsi, 0);
			final List<IMSIStats> imsiList = new ArrayList<>();
			imsiList.add(imsiStats);
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(imsiList);
			System.out.println(jsonInString.length());
			return Response.status(200).entity(imsiList).header("Content-Length", jsonInString.length()).build();
		}
		final IMSIStatsProducer imsiStatsProducer = new IMSIStatsProducer(baseDataList);
		final List<IMSIStats> imsiList = imsiStatsProducer.getListOfIMSIStatsObjectsWithFailuresBetweenDates();
		final ObjectMapper mapper = new ObjectMapper();
		final String jsonInString = mapper.writeValueAsString(imsiList);
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
			final List<TopTenMarketOperatorCellIdCombinations> topTenMarketOperatorCellIdList = topTenDAO.getTopTenMarketOperatorCellIdCombinationsWithFailures(dateOne, dateTwo);
			
			if(topTenMarketOperatorCellIdList.isEmpty()) {
				return Response.status(404).build();
			} 
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(topTenMarketOperatorCellIdList);
			return Response.status(200).entity(topTenMarketOperatorCellIdList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
	}

	@GET
	@Path("/top10_IMSI")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getTopTenIMSI(@QueryParam("dateOne") String dateOne, @QueryParam("dateTwo") String dateTwo){

		dateParser = new DateParser();
		dateOne = dateParser.convertFromEuropeanToAmericanDateFormat(dateOne);
		dateTwo = dateParser.convertFromEuropeanToAmericanDateFormat(dateTwo);
		try {
			final List<TopTenIMSIForFailures> topTenIMSIList = topTenDAO.getTopTenIMSIForFailures(dateOne, dateTwo);
			
			if(topTenIMSIList.isEmpty()) {
				return Response.status(404).build();
			} 
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(topTenIMSIList);
			return Response.status(200).entity(topTenIMSIList).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e) {
			e.printStackTrace();
			return Response.status(400).build();
		}
		
	}
	
	@GET
	@Path("/unique_failure_class")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUniqueFailureClasses(){
		
		try {
			final List<Failure_class> failureClassList = IMSIDao.getFailureClass();
			return Response.status(200).entity(failureClassList).build();
		} catch (Exception e) {
			System.out.println("Failed to get failures...new?");
			return Response.status(400).build();
		}

	}
	
	@GET
	@Path("/imsis_for_failure_class")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getIMSIForFailureClasses(@QueryParam("failure_class") final String failureClass){
		try {
			final List<Base_data> failureClassList = IMSIDao.getIMSIsForFailureClass(failureClass);
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(failureClassList);
			return Response.status(200).entity(failureClassList).header("Content-Length", jsonInString.length()).build();
		} catch (Exception e) {
			System.out.println("Failed to get failures...?");
			return Response.status(400).build();
		}

	}
	
	@GET
	@Path("/unique_causeCode_failureClass_Description")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(@QueryParam("imsi") final BigInteger imsi){
		try{
			final List<UniqueEventCauseFailureClass> causeCodeFailureClassDescriptList = IMSIDao.getUniqueCauseCodeAndDescriptionForFailureClassForIMSI(imsi);
			if(causeCodeFailureClassDescriptList.isEmpty()){
				return Response.status(404).build();
			}
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(causeCodeFailureClassDescriptList);
			return Response.status(200).entity(causeCodeFailureClassDescriptList).header("Content-Length", jsonInString.length()).build();
		}catch(Exception e){
			System.out.println("Failed to get failures...?");
			return Response.status(400).build();
		}
	}
	@GET
	@Path("/get_per_failure_class")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getUniqueIMSIsPerFailureClass(@QueryParam("failure_class")final int failureClass){
		try{
			final List<IMSIHelperObject> IMSIsPerFailureClass = IMSIDao.getAffectedIMSIsPerFailureClass(failureClass);
			if(IMSIsPerFailureClass.isEmpty()){
				return Response.status(404).build();
			}
			final ObjectMapper mapper = new ObjectMapper();
			final String jsonInString = mapper.writeValueAsString(IMSIsPerFailureClass);
			return Response.status(200).entity(IMSIsPerFailureClass).header("Content-Length", jsonInString.length()).build();
		} catch(Exception e){
			e.printStackTrace();
			return Response.status(400).build();
		}
	}
	
}

