package com.fileuploader;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/fileTimer")
@Stateless
@LocalBean
public class FileTimerWS {

	@EJB
	private FileTimerDAO fileTimerDao;
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findFileTimerById(@PathParam("id") final int fileTimerId){
		final FileTimer fileTimer = fileTimerDao.getFileTimer(fileTimerId);
		return Response.status(200).entity(fileTimer).build();
	}

}
