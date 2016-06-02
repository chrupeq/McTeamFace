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



@Path("/users")
@Stateless
@LocalBean
public class UserWS {

	@EJB
	private UsersDAO userDao;
	

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAllUsers() {
		List<User> users=userDao.getAllUsers();
		return Response.status(200).entity(users).build();
	}
	
	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findWineById(@PathParam("id") int id) {
		User user = userDao.getUser(id);
		return Response.status(200).entity(user).build();

	}
	
	

}
