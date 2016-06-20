package com.fileuploader;

import java.io.IOException;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.media.multipart.FormDataParam;
import org.json.JSONException;

public interface ExcelFileUploaderIF {

	Response uploadFile(String imageInBase64) throws IOException, JSONException;

}