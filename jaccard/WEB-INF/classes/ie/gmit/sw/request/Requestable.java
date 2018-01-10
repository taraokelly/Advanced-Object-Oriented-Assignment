package ie.gmit.sw.request;

import ie.gmit.sw.response.Responsator;

public interface Requestable {
	public Responsator doRequest();
	public String getTaskNumber();
}
