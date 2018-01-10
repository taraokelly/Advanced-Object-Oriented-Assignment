package ie.gmit.sw;

import ie.gmit.sw.request.*;
import ie.gmit.sw.response.*;
import ie.gmit.sw.request.consumer.*;
import ie.gmit.sw.document.DocumentSimilarity;
import ie.gmit.sw.document.JaccardHashes;
import ie.gmit.sw.document.db.*;

import java.util.*;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet("/UploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*2, // 2MB. The file size in bytes after which the file will be temporarily stored on disk. The default size is 0 bytes.
                 maxFileSize=1024*1024*10,      // 10MB. The maximum size allowed for uploaded files, in bytes
                 maxRequestSize=1024*1024*50)   // 50MB. he maximum size allowed for a multipart/form-data request, in bytes.
public class ServiceHandler extends HttpServlet {
	//An Asynchronous Message Facade
	private Queue<Requestable> inqueue = new LinkedList<Requestable>();
	private Map<String, Responsator> outqueue = new LinkedHashMap<String, Responsator>();
	//Start Consumer thread & pass in queues.
	private Thread consumer = new Thread(new Consumer(inqueue, outqueue));
	private JaccardHashes hashes;
	private DatabaseHandler db;
	
	private String DB_PATH = null;
	//private String DB_POPULATION_DIR = null;
	private Integer SHINGLE_SIZE = null;
	private long jobNumber = 0;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext(); 
		// Reads the value from the context-params in web.xml.
		DB_PATH = ctx.getInitParameter("DB_PATH"); 
		SHINGLE_SIZE = Integer.parseInt(ctx.getInitParameter("SHINGLE_SIZE"));
		//DB_POPULATION_DIR = ctx.getInitParameter("DB_POPULATION_DIR");
		// Set up.
		hashes = JaccardHashes.getInstance(ctx.getInitParameter("MINHASHES"));
		db = DatabaseHandler.getInstance(DB_PATH);
		if (!consumer.isAlive()){
			consumer.start();
			/*
			 * Removing population method as Tomcat can't find resource folder.
			 * 
			Request r = new SetupRequest("T" + jobNumber, SHINGLE_SIZE, hashes.getHashes(), db, DB_POPULATION_DIR);
			jobNumber++;
			inqueue.offer(r);
			*/
		}
	}
	
	// HTTP Methods.
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter(); 
		// Check if parameter exists
        if (req.getParameterMap().containsKey("txtTitle")) {
        	String title = req.getParameter("txtTitle");
    		String taskNumber = req.getParameter("frmTaskNumber");
    		int counter = 1;
    		Responsator result = outqueue.get(taskNumber);
    		// Check if form details already submitted
    		if (taskNumber == null){
    			//Add job to in-queue
    			Part part = req.getPart("txtDocument");
    			taskNumber = new String("T" + jobNumber);
    			jobNumber++;
    			Request r = new ComparisonRequest(taskNumber, new InputStreamReader(part.getInputStream()), title, SHINGLE_SIZE, hashes.getHashes(), db);
    			inqueue.offer(r);
    			printLoadingPage(out, title, taskNumber, counter);	
    		}else{
    			//Check out-queue for finished job with the given taskNumber
    			if(result !=null)
    			{	
    				if(result.getResultStatus().equals("200"))
    					printComparisonResultPage(out, title, taskNumber, (ComparisonResponse) result);	
    				else
    					printErrorPage(out, result.getResultStatus());
    			} else{
    				if (req.getParameter("counter") != null){
    					counter = Integer.parseInt(req.getParameter("counter"));
    					counter++;
    				}
    				printLoadingPage(out, title, taskNumber, counter);	
    			}
    		}
        }else{
        	printErrorPage(out,"Form Resubmission");
        }
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
 	}
	
	// Helper Methods

	public void printHeader(PrintWriter out){
		out.print("<html><head><title>A JEE Application for Measuring Document Similarity</title>");
		out.print("<style>table {font-family: arial, sans-serif;border-collapse: collapse;width: 100%;}td, th {border: 1px solid #dddddd;");
		out.print("text-align: left;padding: 8px;}tr:nth-child(even) {background-color: #dddddd;}h1{color:#990000;} h3{color:grey;} .wrapper{margin: 0 auto; width:50%; min-width: 600px; margin-top: 50px; text-align:center;} html{font-size:18px;}</style>");
		out.print("</head>");		
		out.print("<body>");
	}
	public void printFooter(PrintWriter out){
		out.print("</body>");	
		out.print("</html>");	
	}
	public void printScript(PrintWriter out){
		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");
	}
	public void printLoadingPage(PrintWriter out, String title, String taskNumber, Integer counter){
		printHeader(out);
		out.print("<div class=\"wrapper\">");
		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Document being compared: " + title + "</H3>");
		out.print("<form name=\"frmRequestDetails\" action=\"doProcess\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("<input name=\"counter\" type=\"hidden\" value=\"" + counter + "\">");
		out.print("</form>");	
		out.print("</div>");
		printFooter(out);
		printScript(out);
	}
	public void printComparisonResultPage(PrintWriter out, String title, String taskNumber, ComparisonResponse result){
		printHeader(out);
		out.print("<div class=\"wrapper\">");
		out.print("<H1>Request processed for Job#: " + taskNumber + "</H1>");
		out.print("<H3>Title: " + title + "</H3>");
		out.print("<table><tr><th>Title</th><th>Similarity</th></tr>");
		for(DocumentSimilarity r: result.getResults()){
			out.print("<tr><td>" + r.getTitle() +"</td><td>" + r.getSimilarity() +"</td></tr>");
		}
		out.print("</table>");
		out.print("<br><br><a class=\"button\" href=\"index.jsp\">Make Another Query</a>");
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"txtTitle\" type=\"hidden\" value=\"" + title + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</div>");
		printFooter(out);
	}
	public void printErrorPage(PrintWriter out, String error){
		printHeader(out);
		out.print("<div class=\"wrapper\">");
		out.print("<H1>Error</H1>");	
		out.print("<H3>" + error + "</H3>");	
		out.print("<br><br><a class=\"button\" href=\"index.jsp\">Make Another Query</a>");
		out.print("</div>");
		printFooter(out);
	}
}