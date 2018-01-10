package ie.gmit.sw.request;

import java.io.*;
import java.net.URL;
import java.util.*;

import ie.gmit.sw.document.*;
import ie.gmit.sw.document.db.DatabaseHandler;
import ie.gmit.sw.response.Responsator;
import ie.gmit.sw.response.SetupResponse;

public class SetupRequest extends Request{
	
	/*
	 * This implementation of Request is optimized to create and populate the database.
	 */
	
	private ArrayList<Document> documents = new ArrayList<Document>();
	private ClassLoader loader = Thread.currentThread().getContextClassLoader();
	private DatabaseHandler db;
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	private DocumentGenerator documentGenerator;
	private String status = "200";
	private String dir;
	
	public SetupRequest(String taskNumber, Integer size, Set<Integer> hashes, DatabaseHandler db, String dir) {
		super(taskNumber);
		this.db = db;
		this.dir = dir;
		documentGenerator = new DocumentGenerator(size,hashes);
	}

	public Responsator doRequest() {
		for (File f : getResourceFolderFiles(dir)) {
			String title = f.getName().substring(f.getName().lastIndexOf('/') + 1);
			File file = new File(loader.getResource(dir + title).getFile());
			try {
				documents.add(documentGenerator.generate(new InputStreamReader(new FileInputStream(file)), title));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				status = "500 Internal Server Error";
			}
		}
		if(documents.size()>=1){
			db.addAllDocuments(documents);
		}
		return new SetupResponse(status);
	}
	
	private File[] getResourceFolderFiles (String folder) {
	    URL url = loader.getResource(folder);
	    String path = url.getPath();
	    return new File(path).listFiles();
	}

}
