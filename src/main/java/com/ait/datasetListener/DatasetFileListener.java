package com.ait.datasetListener;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import com.fileuploader.ExcelFileUploader;
 
@Stateless
@TransactionManagement( value=TransactionManagementType.BEAN)
public class DatasetFileListener {

	private WatchService watcher;
	private WatchKey key;
	private Path dir;
	final private ArrayList<Path> filesSent = new ArrayList<>();

	public void newFolderWatcher() {
		try {
			watcher = FileSystems.getDefault().newWatchService();
		} catch (IOException e) {
			System.out.println("Failed to init watcher");
			e.printStackTrace();
		}
	}

	public void setMonitoredDir(final String filePath) {

		dir = Paths.get(filePath);

		final WatchEvent.Kind<?>[] events = { StandardWatchEventKinds.ENTRY_CREATE };
		try {
			key = dir.register(watcher, events);
		} catch (IOException e) {
			System.out
					.println("Failed to register watcher to selected directory");
			e.printStackTrace();
		}
	}

	@Asynchronous
	
	public void startWatching(final ExcelFileUploader excelFileUploader) throws IOException {

		WatchKey key = null;
		//String fileName = null;

		Runtime.getRuntime().addShutdownHook(new Thread("shutdown watcher") {

			public void run() {
				try {
					watcher.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});

		while (true) {

			System.out.println("Waiting for watch event");
			System.out.println("Path being watched: " + dir.toString());

			try {
				key = watcher.take();
			} catch (InterruptedException e) {
				System.out.println("Watcher got interrupted");
				e.printStackTrace();
			} catch (ClosedWatchServiceException c) {
				System.out.println("Watchservice is closed exiting thread.");
				break;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (key != null) {

				for (final WatchEvent<?> event : key.pollEvents()) {
					final WatchEvent.Kind<?> kind = event.kind();

					if (kind == StandardWatchEventKinds.OVERFLOW) {
						continue;
					} else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
						System.out.println("File created");
						WatchEvent<Path> ev = (WatchEvent<Path>) event;
						final Path file = dir.resolve(ev.context());
						//File xlsFile = new File(file.toString());
						if (file.toString().endsWith(".xls")&&!filesSent.contains(file.getFileName())) {
							System.out.println(file.toString());
							try{
								excelFileUploader.sendToFileReader(file.toString());
								filesSent.add(file.getFileName());
							}catch(Exception e){
								
							}
							
						} else {
							System.out.println("The file is not dataset or already been uploaded!");
						}
					}

					// fileName = key.watchable().toString() + "\\"
					// + event.context().toString();
					/*
					 * if (extension.equals(ReaderTypes.EXCEL97)) {
					 * 
					 * log.info("File found: " + fileName);
					 * fileSystemMonitorInitializer.placeFactoryOrder( fileName,
					 * ReaderTypes.EXCEL97);
					 * 
					 * } else if (extension.equals(EXCEL_2007_FILE_EXTENSION)) {
					 * 
					 * log.info("File found: " + fileName);
					 * fileSystemMonitorInitializer.placeFactoryOrder( fileName,
					 * ReaderTypes.EXCEL2007);
					 * 
					 * } else {
					 * log.info("Unknown file format no action will be taken");
					 * }
					 */
					
					final boolean valid = key.reset();
					if (!valid) {
						System.out
								.println("Watcher key could not be reset stopping watchservice.");
						break;
					}
				}
			}
		}
	}
}
