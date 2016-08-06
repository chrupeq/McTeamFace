package com.ait.datasetListener;

import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

import com.fileuploader.ExcelFileUploader;

@Startup  
@Singleton  
@Asynchronous
//@DependsOn("ExcelPropertiesLoader")  
public class FileSystemMonitorInitializer {  
     
//    @EJB  
//    private ExcelPropertiesLoader excelPropertiesLoader;  
	@EJB
    private ExcelFileUploader excelFileUploader;
    
    @EJB
    private DatasetFileListener fileSystemMonitor;  
     
    @PostConstruct  
    public void init() throws IOException{  
         
        fileSystemMonitor.newFolderWatcher();  
        fileSystemMonitor.setMonitoredDir("C:/jboss-as-7.1.1.Final/bin/dataSets");  
        fileSystemMonitor.startWatching(excelFileUploader);  
         
    }  
}  