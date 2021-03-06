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
import com.ait.db.model.User;

/**
 * User Web Service class
 *
 */
@Path("/users")
@Stateless
@LocalBean
public class UserWS {

	@EJB
	private UsersDAO userDao;

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getAllUsers() {
		final List<User> users = userDao.getAllUsers();
		return Response.status(200).entity(users).build();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	public Response findAllUsers() {
		final List<User> users = userDao.getAllUsers();
		return Response.status(200).entity(users).build();
	}

	@GET
	@Produces({ MediaType.APPLICATION_JSON })
	@Path("/{id}")
	public Response findUserById(@PathParam("id") final int userId) {
	
		final User user = userDao.getUser(userId);
		//Response response = Response.status(200).entity(user).header("Content-Length", user.toString().length()).build();
		System.out.println(user.getFirstName());
		return Response.status(200).entity(user).header("Content-Length", 100).build();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON })
	@Consumes("application/json")
	public Response saveUser(final User user) {
		userDao.save(user);
		return Response.status(201).entity(user).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response updateUser(final User user, @PathParam("id") final int userId) {
		user.setId(userId);
		userDao.update(user);
		return Response.status(200).entity(user).build();
	}

	@DELETE
	@Path("/{id}")
	public Response deleteUser(@PathParam("id") final int userId) {
		userDao.delete(userId);
		return Response.status(204).build();
	}
	
	@POST
	@Path("/logintime/{newlogindateandtime}/{username}")
	public Response updateLogin(@PathParam("newlogindateandtime") final String loginTime, @PathParam("username") final String username) {
		System.out.println(loginTime + " " + username);
		userDao.updateLastLogin(username, loginTime);
		return Response.status(204).build();
	}

}
