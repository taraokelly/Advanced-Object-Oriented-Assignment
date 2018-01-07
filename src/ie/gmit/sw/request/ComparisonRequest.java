package ie.gmit.sw.request;

import java.io.InputStream;

import ie.gmit.sw.document.Document;

public class ComparisonRequest extends Request {
	
	/*
	 * This implementation of Request is concerned with adding a single doc to the database and comparing it with the existing ones.
	 */
	
	private String title;
	private Integer minHashLimit;
	private Integer shingleSize;
	
	// private ShingleGenerater shingle = MinHashedShingleGenerater();
	private Document document;
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	// private Jaccard jaccard = new Jaccard();
	
	public ComparisonRequest(String taskNumber, InputStream is, String title, Integer minHashLimit, Integer shingleSize) { // pass in lock
		super(taskNumber);
		// document = new Document(shingle.generate(is), title);
		this.title = title;
	}

	public String doRequest() {
		// String comparisonResult = jaccard.compare(document, title, db.getAllDocuments());
		
		// acquire lock
		// String dbResult = db.add(document) - return db result?
		// release lock
		
		//return result; - return response obj with both results? ( with chain of command - nah )
		return null;
	}
	
}
