

import java.io.IOException;
import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.ibm.json.java.*;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
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
import org.bson.Document;
import static java.util.Arrays.asList;

/**
 * Servlet implementation class Update
 */
@WebServlet("/Update")
public class Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		String password = request.getParameter("update_password");
		//System.out.println(password);
		HttpSession session=request.getSession(); 
		String uname = (String)session.getAttribute("username"); 
		PrintWriter out = response.getWriter();
		//out.println(uname);
		MongoClientURI uri = new MongoClientURI("mongodb://root:123@ds047901.mongolab.com:47901/facecomm");
		MongoClient client = new MongoClient(uri);
		DB db = client.getDB(uri.getDatabase());
		DBCollection users = db.getCollection("Users");
		JSONObject params = new JSONObject();
		params.put("password", password);
		params.put("username", uname);
		params.put("email", uname);
		BasicDBObject user1 = new BasicDBObject(params);
		BasicDBObject searchQuery = new BasicDBObject().append("username",uname); 
		users.update(searchQuery, user1);
		out.println("<html>");
		out.println("<body bgcolor = '#81F781'>");
		out.println("password updated successfully");
		out.println("</body>");
		out.println("</html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
