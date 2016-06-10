package com.fileuploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.eclipse.persistence.sessions.factories.SessionFactory;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.db.model.NetworkEntity;
import com.ait.reader.ReadDataSetIntoMainMemory;
import com.validation.JDBCConnectionManager;

@Path("/upload")
@Stateless
@LocalBean
public class ExcelFileUploader extends JDBCConnectionManager{

JDBCConnectionManager jdbc = new JDBCConnectionManager();

	@EJB
	private NetworkEntityDAO networkEntityDAO;

	@PersistenceContext
	private EntityManager entityManager;

	static NetworkEntity[] networkEntityArray;

	@POST
	@Path("/uploadfile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response uploadFile(@FormDataParam("data") String imageInBase64) throws IOException, JSONException {
		
		String[] imageArray = imageInBase64.split(",");

		String path = "C:\\Users\\A00226084\\Desktop\\temp.xls";

		FileOutputStream fos = new FileOutputStream(new File(path));
		fos.write(DatatypeConverter.parseBase64Binary(imageArray[1].toString()));
		fos.close();

		try {
			Thread.sleep(3000);
			System.out.println("Sleeping complete...");
		} catch (InterruptedException e) {
			System.out.println("Threading failed...");
			e.printStackTrace();
		}
		sendToFileReader(path);
		
		return Response.status(200).build();
	}
	
	public void sendToFileReader(String path) throws IOException {

		
		for (int i = 4; i >= 1; i--) {
			
			networkEntityArray = ReadDataSetIntoMainMemory.readFileInFromHardDrive(path, i);
			
			
				networkEntityDAO.saveNetworkEntityArray(networkEntityArray);
					
			}
		networkEntityArray = ReadDataSetIntoMainMemory.readFileInFromHardDrive(path, 0);
			jdbc.sendBaseDataToDB(networkEntityArray);
		}
	}
