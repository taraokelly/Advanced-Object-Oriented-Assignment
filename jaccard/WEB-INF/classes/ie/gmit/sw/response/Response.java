package ie.gmit.sw.response;

public abstract class Response implements Responsator {
	
	private String resultStatus;
	
	public Response(String resultStatus) {
		super();
		this.resultStatus = resultStatus;
	}

	public String getResultStatus() {
		return resultStatus;
	}

}
