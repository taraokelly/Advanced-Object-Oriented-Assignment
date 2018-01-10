package ie.gmit.sw.request;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Set;

import ie.gmit.sw.document.*;
import ie.gmit.sw.document.db.DatabaseHandler;
import ie.gmit.sw.response.ComparisonResponse;
import ie.gmit.sw.response.Responsator;

public class ComparisonRequest extends Request {
	
	/*
	 * This implementation of Request is concerned with adding a single doc to the database and comparing it with the existing ones.
	 */
	private DocumentGenerator documentGenerator;
	private Document document;
	private DatabaseHandler db;
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	private Jaccard jaccard = new Jaccard();
	private String status = "200";
	
	public ComparisonRequest(String taskNumber, InputStreamReader is, String title, Integer size, Set<Integer> hashes, DatabaseHandler db) { // pass in lock
		super(taskNumber);
		this.db = db;
		documentGenerator = new DocumentGenerator(size,hashes);
		document = documentGenerator.generate(is, title);
	}

	public Responsator doRequest() {
		List<Document> documents = db.getAllDocuments();
		if(!documents.isEmpty())
			jaccard.compare(document, documents);
		else
			status = "404 Files Not Found";
		db.addDocument(document);
		return new ComparisonResponse(status, jaccard.getResults());
	}
	
}
