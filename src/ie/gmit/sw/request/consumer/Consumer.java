package ie.gmit.sw.request.consumer;

import java.util.*;
import java.util.concurrent.*;

import ie.gmit.sw.request.*;
import ie.gmit.sw.request.control.RequestControl;

public class Consumer implements Runnable {
	
	/*
	 * This runnable class is a worker thread that launches a fixed pool of stateless session threads that
	 * polls the in-queue, processes the available requests and adds the response to the out-queue.
	 */
	
	private volatile Queue<Requestable> inqueue;
	private Map<String, String> outqueue;
	private ExecutorService executor = Executors.newFixedThreadPool(50);
	
	
	public Consumer(Queue<Requestable> in, Map<String, String> out, Integer minHashLimit){
        this.inqueue = in;
		this.outqueue = out;
    }
	
	@Override 
	public void run() {
		
		while(true){

			Requestable r = inqueue.poll();

            if(r != null){
            	
            	executor.submit(new Runnable() {
            		
				    @Override 
				    public void run() {
				    	
				    	/*
				    	 * Chain of command pattern - executor of the command does not need to know anything at all about what the command is, 
				    	 * what context information it needs on or what it does. All of that is encapsulated in the command. This pattern also promotes 
				    	 * loose coupling. Another object of type Requestable can be easily be add and used in the hierarchy.
				    	 */
				    	
				    	RequestControl rc = new RequestControl();
				    	rc.setCommand(r);
				    	String result = rc.exeute();
		                outqueue.put(r.getTaskNumber(), result);
				    }
				});
            }
        }
	}
}
