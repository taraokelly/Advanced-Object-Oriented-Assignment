package ie.gmit.sw.request.consumer;

import java.util.*;
import java.util.concurrent.*;

import ie.gmit.sw.request.*;
import ie.gmit.sw.request.control.RequestControl;
import ie.gmit.sw.response.Responsator;

public class Consumer implements Runnable  {
	
	/*
	 * This runnable class is a worker thread that launches a fixed pool of stateless session threads that
	 * polls the in-queue, processes the available requests and adds the response to the out-queue.
	 */
	
	private volatile Queue<Requestable> inqueue;
	private Map<String, Responsator> outqueue;
	private ExecutorService executor = Executors.newFixedThreadPool(25);
	private volatile Boolean shutdown;
	
	
	public Consumer(Queue<Requestable> in, Map<String, Responsator> out, Boolean shutdown){
        this.inqueue = in;
		this.outqueue = out;
		this.shutdown = shutdown;
	
		/*Runtime.getRuntime().addShutdownHook(new Thread() {
		    public void run() {
		        executor.shutdown();
		    }
		});*/
	}
	
	@Override 
	public void run() {
		//new Thread(() -> {if(shutdown)executor.shutdown();}).start();
		while(!shutdown){

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
				    	Responsator result = (Responsator) rc.exeute();
		                outqueue.put(r.getTaskNumber(), result);
				    }
				});
            }
        }
		executor.shutdown();
		shutdown = null;
	}
}
