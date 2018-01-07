package ie.gmit.sw.request.control;

import ie.gmit.sw.control.Control;
import ie.gmit.sw.request.Requestable;

public class RequestControl implements Control {
	
	/*
	 * This implementation of the Control interface is specialized to handle the client requests.
	 */
	
	private Requestable command;
	
	public void setCommand(Object command){
		this.command = (Requestable) command;
	}
	public String exeute(){
		return command.doRequest();
	} 
}
