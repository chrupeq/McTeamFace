package com.ait.gui;

import java.util.List;


import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.EJB;
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

/**
 * @author A00236944
 *
 */
@Path("/users")
@Stateless
@LocalBean
public class UserWS {

	@EJB
	private UsersDAO userDao;
	

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAllUsers() {
		List<User> users=userDao.getAllUsers();
		return Response.status(200).entity(users).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findUserById(@PathParam("id") int id) {
		User user = userDao.getUser(id);
		return Response.status(200).entity(user).build();
	}
	
	@PUT
	@Produces({ MediaType.APPLICATION_JSON })
	public Response saveStadium(final User user) {
		userDao.save(user);
		return Response.status(201).entity(user).build();
	}

	@POST
	@Path("/{id}")
	@Consumes("application/json")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateStadium(final User user, @PathParam("id") final int userId) {
		user.setId(userId);
		userDao.update(user);
		return Response.status(200).entity(user).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteStadium(@PathParam("id") final int userId) {
		userDao.delete(userId);
		return Response.status(204).build();
	}
	
}
