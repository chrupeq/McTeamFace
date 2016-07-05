package com.fileuploader;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
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
	public Response findFileTimerById(@PathParam("id") final int id){
		FileTimer fileTimer = fileTimerDao.getFileTimer(id);
		return Response.status(200).entity(fileTimer).build();
	}
	
	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateFileTimer(final FileTimer fileTimer, @PathParam("id") final int id) {
		fileTimer.setId(id);
		fileTimerDao.update(fileTimer);
		return Response.status(200).entity(fileTimer).build();
	}
}
