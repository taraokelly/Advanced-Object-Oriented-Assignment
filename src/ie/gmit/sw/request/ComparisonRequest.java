package ie.gmit.sw.request;

import java.io.InputStream;
import java.util.Set;

import ie.gmit.sw.document.Document;
import ie.gmit.sw.document.DocumentGenerator;

public class ComparisonRequest extends Request {
	
	/*
	 * This implementation of Request is concerned with adding a single doc to the database and comparing it with the existing ones.
	 */
	
	private DocumentGenerator documentGenerator;
	private Document document;
	// private DatabaseProxyHandler db = new DatabaseProxyHandler();
	// private Jaccard jaccard = new Jaccard();
	
	public ComparisonRequest(String taskNumber, InputStream is, String title, Integer size, Set<Integer> hashes) { // pass in lock
		super(taskNumber);
		documentGenerator = new DocumentGenerator(size,hashes);
		document = documentGenerator.generate(is, title);
	}

	public String doRequest() {
		//jaccard.compare(documents, db.getAllDocuments());
		//jaccard.getResults().forEach(item->System.out.println(item.getTitle() + ": " + item.getSimilarity()));
		
		// acquire lock
		// String dbResult = db.add(document) - return db result?
		// release lock
		
		//return result; - return response obj with both results? ( with chain of command - nah )
		return null;
	}
	
}
