package ie.gmit.sw.request;

import java.util.ArrayList;

import ie.gmit.sw.document.Document;

public class SetupRequest extends Request{
	
	/*
	 * This implementation of Request is optimized to create and populate the database.
	 */
	
	private Integer minHashLimit;
	private Integer shingleSize;
	
	private ArrayList<Document> documents = new ArrayList<Document>();
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	// private Jaccard jaccard = new Jaccard();
	// private ShingleGenerater shingle = MinHashedShingleGenerater();
	
	public SetupRequest(String taskNumber, Integer minHashLimit, Integer shingleSize) {
		super(taskNumber);
		this.minHashLimit = minHashLimit;
		this.shingleSize = shingleSize;
	}

	public String doRequest() {
		
		// read in files in resources/files
		// shingle and hash 
		// add all to db
		
		return null;
	}

}
