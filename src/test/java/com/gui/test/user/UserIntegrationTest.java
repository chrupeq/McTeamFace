package com.gui.test.user;

import static org.junit.Assert.*;
import java.util.List;
import javax.ejb.EJB;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import com.ait.db.rest.JaxRsActivator;
import com.ait.gui.User;
import com.ait.gui.UserWS;
import com.ait.gui.UsersDAO;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(Arquillian.class)
public class UserIntegrationTest {

	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap.create(JavaArchive.class, "Test.jar")
				.addClasses(UsersDAO.class, User.class, JaxRsActivator.class, UserWS.class)
				.addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");

	}

	@EJB
	private UserWS userWS;

	@EJB
	private UsersDAO userDAO;
	
	@Test
	public void testAAFindAllUsers() {
		assertEquals(userWS.findAllUsers().toString(), 200, userWS.findAllUsers().getStatus());
	}
	
	@Test
	public void testABFindUserByID() {
		assertEquals(userWS.findUserById(1).toString(), 200, userWS.findUserById(1).getStatus());
	}
	
//	@Test
//	public void testACSaveUser() {
//		User stadium = new User();
//		stadium.setName("StadiumTest");
//		stadium.setYear("2002");
//		stadium.setTenant("TeamName");
//		stadium.setCity("CityName");
//		stadium.setCountry("Ireland");
//		stadium.setSport("sour");
//		stadium.setCapacity("arquilllian");
//		stadium.setSeated("Athlone");
//		assertEquals(201, stadiumWS.saveStadium(stadium).getStatus());
//	}

}
