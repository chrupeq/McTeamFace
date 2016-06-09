package com.fileuploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.reader.ReadDataSetIntoMainMemory;

@Path("/upload")
@Stateless
@LocalBean
public class ExcelFileUploader {

	@EJB
	private NetworkEntityDAO networkEntityDAO;
	
	@PersistenceContext
	private EntityManager entityManager;
	
	static Base_data[] bdArray;
	
	@POST
	@Path("/uploadfile")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response uploadFile(
	        @FormDataParam("data") String imageInBase64) throws IOException, JSONException
	         {
		System.out.println("here");
		System.out.println("String: " + imageInBase64);

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
	
	public void sendToFileReader(String path) throws IOException{
		
			bdArray = ReadDataSetIntoMainMemory.readFileInFromHardDrive(path);
			System.out.println(bdArray.length);
			for(Base_data bda : bdArray){
				System.out.println(bda.getNe_version());
		networkEntityDAO.saveNetworkEntity(bda);
			
			}
	
	}
	
}
