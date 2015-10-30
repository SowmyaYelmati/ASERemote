

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession; 
import com.ibm.json.java.*;
import com.ibm.json.java.JSON;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.*;
import com.mongodb.operation.BaseWriteOperation.*;
import org.bson.json.*;
import java.net.UnknownHostException;

/**
 * Servlet implementation class Register
 */
//@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MongoException, DuplicateKeyException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		PrintWriter out = response.getWriter();
		String uname = request.getParameter("username2");
        String password = request.getParameter("password2");
        MongoClientURI uri = new MongoClientURI("mongodb://root:123@ds047901.mongolab.com:47901/facecomm");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("Users");
		BasicDBObject query = new BasicDBObject("username", uname).append("password", password);
		DBCursor docs = users.find(query);
		if(docs.hasNext())
		{
			out.println("login successful");
			//out.println("<a href=”http://localhost:9080/example/update”> update password </a>");
			//out.println("<a href=”http://localhost:8080/HelloWorld/test”> Hello World Servlet </a>");
			HttpSession session=request.getSession(); 
			session.setAttribute("username",uname); 
			out.println("<html>");
			out.println("<body bgcolor = '#81F781'>");
			out.println("<form action='update' method='get'>");
			out.println("new password : <input type = 'password' name='update_password'>");
			out.println("<input type = 'submit' value='update password'>");
	        out.println("</form>");   
	        
	        out.println("<form action='delete' method='get'>");
			//out.println("new password : <input type = 'password' name='update_password'>");
			out.println("<input type = 'submit' value='delete account'>");
	        out.println("</form>");
	        out.println("</body>");
	        out.println("</html>");
	    }
			
		
		else{
			out.println("login unsuccessful");
		}
		//List<DBObject> userarray = docs.toArray();
		//if( docs.hasNext() )
			//BasicDBObject user1;
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "POST");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
		response.setHeader("Access-Control-Max-Age", "86400");
        
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, MongoException, DuplicateKeyException, UnknownHostException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
         StringBuilder buffer = new StringBuilder();
         BufferedReader reader = request.getReader();
         String uname = request.getParameter("username");
         String password = request.getParameter("password");
         String email = request.getParameter("email");
         System.out.println(uname+" "+password+" "+email);
         /*String line;
         while((line=reader.readLine())!=null){
        	 buffer.append(line);
         }
         String data = buffer.toString();
         System.out.println(data);
         data = "{\"p\":\"N\",\"c\":\"W\"}";*/
         //System.out.println(data);
         //JSONObject params = (JSONObject)JSON.parse(data);
         JSONObject params = new JSONObject();
         params.put("username", uname);
         params.put("password", password);
         BasicDBObject user1 = new BasicDBObject(params);
         for(Object key:params.keySet().toArray()){
         user1.put(key.toString(), params.get(key));}
        // System.out.println(user1.toJson());
         MongoClientURI uri = new MongoClientURI("mongodb://vikas_new:vikas_new@ds035014.mongolab.com:35014/vikas2");
 		MongoClient client = new MongoClient(uri);
 		DB db = client.getDB(uri.getDatabase());
 		DBCollection users = db.getCollection("users");
 		users.insert(user1);
 		response.setHeader("Access-Control-Allow-Origin", "*");
 		response.setHeader("Access-Control-Allow-Methods", "POST");
 		response.setHeader("Access-Control-Allow-Headers", "Content-Type");
 		response.setHeader("Access-Control-Max-Age", "86400");
         
         
		}

}

