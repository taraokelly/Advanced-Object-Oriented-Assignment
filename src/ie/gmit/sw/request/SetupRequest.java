package ie.gmit.sw.request;

import java.io.*;
import java.net.URL;
import java.util.*;

import ie.gmit.sw.document.*;

public class SetupRequest extends Request{
	
	/*
	 * This implementation of Request is optimized to create and populate the database.
	 */
	
	private ArrayList<Document> documents = new ArrayList<Document>();
	private ClassLoader loader = Thread.currentThread().getContextClassLoader();
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	private DocumentGenerator documentGenerator;
	
	public SetupRequest(String taskNumber, Integer size, Set<Integer> hashes) {
		super(taskNumber);
		documentGenerator = new DocumentGenerator(size,hashes);
	}

	public String doRequest() {
		
		for (File f : getResourceFolderFiles("resources/files")) {
			System.out.println("In file");
			String title = f.getName().substring(f.getName().lastIndexOf('/') + 1);
			File file = new File(loader.getResource("resources/files/" + title).getFile());
			try {
				documents.add(documentGenerator.generate(new FileInputStream(file), title));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			// add to db
		}
		return null;
	}
	
	private File[] getResourceFolderFiles (String folder) {
	    URL url = loader.getResource(folder);
	    String path = url.getPath();
	    return new File(path).listFiles();
	}

}
