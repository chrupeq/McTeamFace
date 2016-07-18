package com.fileuploader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.bind.DatatypeConverter;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;
import com.ait.db.data.NetworkEntityDAO;
import com.ait.db.model.NetworkEntity;
import com.ait.db.model.NonBaseDataObjects;
import com.ait.reader.ReadDataSetIntoMainMemory;
import com.validation.JDBCConnectionManager;

/**
 * ExcelFileUploader class
 * This class is used for uploading the non-erroneous
 * data to the database. Also in this file is where the
 * file timer is ended as it is the last place in which
 * the data set gets uploaded to the database.
 *
 */
@Path("/upload")
@Stateless
@LocalBean
public class ExcelFileUploader extends JDBCConnectionManager {

	JDBCConnectionManager jdbc = new JDBCConnectionManager();
	
	ReadDataSetIntoMainMemory rdsimm = new ReadDataSetIntoMainMemory();
	
	FileTimer ft = new FileTimer();

	String fileName = "";
	
	private static int progressVariable = 0;

	@EJB
	private NetworkEntityDAO networkEntityDAO;
	@EJB
	private FileTimerDAO fileTimerDAO;

	@PersistenceContext
	private EntityManager entityManager;

	static ArrayList<Object[][]> sheetArray = new ArrayList<Object[][]>();
	static NetworkEntity[] eventCause;
	static NetworkEntity[] failureClass;
	static NetworkEntity[] userEquipment;
	static NetworkEntity[] mccMnc;
	static Object[] objectsToBePersisted = new Object[5];

	@POST
	@Path("/uploadfile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response uploadFile(@FormDataParam("data") String imageInBase64) throws IOException, JSONException {
		setProgressVariable(0);
		ft.setStartTime(new Date().toString());
		String[] imageArray = imageInBase64.split(",");
		String path = imageArray[2];
		fileName = imageArray[2];

		FileOutputStream fos = new FileOutputStream(new File(path));
		fos.write(DatatypeConverter.parseBase64Binary(imageArray[1].toString()));
		fos.close();

		setProgressVariable(5);
		
		sendToFileReader(path);

		return Response.status(200).build();
	}

	public void sendToFileReader(String path) throws IOException {
	
		sheetArray.clear();
		sheetArray = ReadDataSetIntoMainMemory.readFileInFromHardDrive(path);
		ExcelFileUploader.setProgressVariable(15);
		System.out.println("Excel file uploader: " + sheetArray.get(0).length);
		eventCause = NonBaseDataObjects.createEventCauseClass(sheetArray.get(1));
		ExcelFileUploader.setProgressVariable(20);
		objectsToBePersisted[4] = eventCause;
		failureClass = NonBaseDataObjects.createFailureClass(sheetArray.get(2));
		ExcelFileUploader.setProgressVariable(25);
		objectsToBePersisted[3] = failureClass;
		userEquipment = NonBaseDataObjects.createUserEquipment(sheetArray.get(3));
		ExcelFileUploader.setProgressVariable(30);
		objectsToBePersisted[2] = userEquipment;
		mccMnc = NonBaseDataObjects.createMccMncclass(sheetArray.get(4));
		ExcelFileUploader.setProgressVariable(35);
		objectsToBePersisted[1] = mccMnc;

		for (int i = 4; i > 0; i--) {

			String entity = networkEntityDAO.saveNetworkEntityArray((NetworkEntity[]) objectsToBePersisted[i]);
			
			switch(i){
			case 4:
				ExcelFileUploader.setProgressVariable(40);
				break;
			case 3:
				ExcelFileUploader.setProgressVariable(45);
				break;
			case 2:
				ExcelFileUploader.setProgressVariable(50);
				break;
			case 1:
				ExcelFileUploader.setProgressVariable(55);
			}

		}
		NetworkEntity[] baseData = ReadDataSetIntoMainMemory.passTheArrayToValidator(sheetArray.get(0), fileName);
		objectsToBePersisted[0] = baseData;
		String date = networkEntityDAO.saveNetworkEntityArray((NetworkEntity[]) objectsToBePersisted[0]);
		
		//settime
		ft.setEndTime(date);
		fileTimerDAO.update(ft);
	}
	
	@GET
	@Path("/progressvariable")
	public Response uploadFile() throws IOException, JSONException {
		
		return Response.status(200).entity(getProgressVariable()).build();
	}
	
	public static int getProgressVariable(){
		return progressVariable;
	}
	
	public static void setProgressVariable(int updateProgress){
		progressVariable = updateProgress;
	}
	
}
