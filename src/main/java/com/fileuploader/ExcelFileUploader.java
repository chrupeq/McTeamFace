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

@Path("/upload")
@Stateless
@LocalBean
public class ExcelFileUploader extends JDBCConnectionManager {

	JDBCConnectionManager jdbc = new JDBCConnectionManager();
	
	ReadDataSetIntoMainMemory rdsimm = new ReadDataSetIntoMainMemory();
	
	FileTimer ft = new FileTimer();

	@EJB
	private NetworkEntityDAO networkEntityDAO;
	@EJB
	private FileTimerDAO fileTimerDAO;

	@PersistenceContext
	private EntityManager entityManager;

	static ArrayList<Object[][]> sheetArray;
	static NetworkEntity[] eventCause;
	static NetworkEntity[] failureClass;
	static NetworkEntity[] userEquipment;
	static NetworkEntity[] mccMnc;
	static Object[] objectsToBePersisted = new Object[5];

	@POST
	@Path("/uploadfile")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response uploadFile(@FormDataParam("data") String imageInBase64) throws IOException, JSONException {
		ft.setStartTime(new Date().toString());
		String[] imageArray = imageInBase64.split(",");

		String path = "temp.xls";

		FileOutputStream fos = new FileOutputStream(new File(path));
		fos.write(DatatypeConverter.parseBase64Binary(imageArray[1].toString()));
		fos.close();

		sendToFileReader(path);

		return Response.status(200).entity("hello").build();
	}

	public void sendToFileReader(String path) throws IOException {
		sheetArray = ReadDataSetIntoMainMemory.readFileInFromHardDrive(path);
		eventCause = NonBaseDataObjects.createEventCauseClass(sheetArray.get(1));
		objectsToBePersisted[4] = eventCause;
		failureClass = NonBaseDataObjects.createFailureClass(sheetArray.get(2));
		objectsToBePersisted[3] = failureClass;
		userEquipment = NonBaseDataObjects.createUserEquipment(sheetArray.get(3));
		objectsToBePersisted[2] = userEquipment;
		mccMnc = NonBaseDataObjects.createMccMncclass(sheetArray.get(4));
		objectsToBePersisted[1] = mccMnc;

		for (int i = 4; i > 0; i--) {

			String entity = networkEntityDAO.saveNetworkEntityArray((NetworkEntity[]) objectsToBePersisted[i]);

		}
		NetworkEntity[] baseData = ReadDataSetIntoMainMemory.passTheArrayToValidator(sheetArray.get(0), "testname");
		objectsToBePersisted[0] = baseData;
		String date = networkEntityDAO.saveNetworkEntityArray((NetworkEntity[]) objectsToBePersisted[0]);
		
		ft.setEndTime(date);
		fileTimerDAO.update(ft);
	}
}
