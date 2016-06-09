package com.fileuploader;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.imageio.ImageIO;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;

import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.Base_data;
import com.ait.reader.ReadDataSetIntoMainMemory;

import sun.misc.BASE64Decoder;

@Path("/upload")
@Stateless
@LocalBean
public class ExcelFileUploader {

	@EJB
	private NetworkEntityDAO networkEntityDAO;
	
	@POST
	@Path("/uploadfile")
	@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Response uploadFile(
	        @FormDataParam("data") String imageInBase64) throws IOException, JSONException
	         {
		System.out.println(imageInBase64);

		String[] imageArray = imageInBase64.split(",");
		
		 String path = "temp.xls";
		       
		            Files.write(Paths.get(path), DatatypeConverter.parseBase64Binary(imageArray[1].toString()));
		       
		            System.out.println("Fle upload error!");
		        
		        sendToFileReader(path);
		return Response.status(200).build();
	}
	
	public static void sendToFileReader(String path) throws IOException{
		
			ReadDataSetIntoMainMemory.readFileInFromHardDrive(path);
	
	}
	
	public void persistEntity(Base_data bd){
		networkEntityDAO.saveNetworkEntity(bd);
	}
}
