package ie.gmit.sw.request;

public class SetupRequest extends Request{
	
	/*
	 * This implementation of Request is optimized to create and populate the database.
	 */
	
	public SetupRequest(String taskNumber) {
		super(taskNumber);
	}

	public String doRequest() {
		return null;
	}

}
