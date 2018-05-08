package servlet;

/*import java.io.FileNotFoundException;*/
import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;

//import java.sql.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*import com.hazelcast.core.*;
import com.hazelcast.config.*;
import java.util.concurrent.ConcurrentMap;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;*/



import database.userAccount;
import utils.*;
import database.conn.*;

/**
 * Servlet implementation class login
 */
@WebServlet("/login")
public class login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public login() {
        super();
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
 
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMeStr = request.getParameter("rememberMe");
        boolean remember= "Y".equals(rememberMeStr);
 
         
        userAccount user = null;
        boolean hasError = false;
        String errorString = null;
        
/*        try{  
        	Class.forName("com.mysql.cj.jdbc.Driver");  
        	Connection con=DriverManager.getConnection(  
        	"jdbc:mysql://localhost:3306/foodie?useSSL=false","root","root123");  
        	//here sonoo is database name, root is username and password  
        	Statement stmt=con.createStatement();  
        	ResultSet rs=stmt.executeQuery("select * from user_account");  
        	while(rs.next())
        	{
        		String uname = rs.getString(1);
        		String pword = rs.getString(2);
        		if (uname.equals(userName) && pword.equals(password)){
        			response.setContentType("text/html");
        			System.out.println ("User Found");
        			PrintWriter out = response.getWriter();
        			out.println("<h1>Welcome</h1>");
        		}
        			        	System.out.println(rs.getInt(1)+"  "+rs.getString(2)+"  "+rs.getString(3));
        	}
        	con.close();  
        	}catch(Exception e){ System.out.println(e);} */ 
 
        if (userName == null || password == null
                 || userName.length() == 0 || password.length() == 0) {
            hasError = true;
            errorString = "Required username and password!";
        } else {
            Connection conn = myUtils.getStoredConnection(request);
            if (conn == null)
              {
            	try{
            	conn = ConnectionUtils.getConnection();	
            	}
            	catch (Exception e){
            		System.out.println ("Connection failed");
            	}
            	System.out.println ("conn: " +conn);
              }
            try {
              
                user = DBUtils.findUser(conn, userName, password);
                 
                if (user == null) {
                    hasError = true;
                    errorString = "User Name or password invalid";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                hasError = true;
                errorString = e.getMessage();
            }
        }
        
        // If error, forward to /WEB-INF/views/login.jsp
        if (hasError) {
            user = new userAccount();
            user.setUserName(userName);
            user.setPassword(password);
             
        
            // Store information in request attribute, before forward.
            request.setAttribute("errorString", errorString);
            request.setAttribute("user", user) ;
 
/*            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            out.println ("<h1> connection failed </h1>");
            // Forward to /WEB-INF/views/login.jsp
*/            RequestDispatcher dispatcher
            = this.getServletContext().getRequestDispatcher("/WEB-INF/view/loginView.jsp");
 
            dispatcher.forward(request, response);
        }
     
        // If no error
        // Store user information in Session
        // And redirect to userInfo page.
        else {
            HttpSession session = request.getSession();
            myUtils.storeLoginedUser(session, user);
             
             // If user checked "Remember me".
            if(remember)  {
                myUtils.storeUserCookie(response,user);
            }
    
            // Else delete cookie.
            else  {
                myUtils.deleteUserCookie(response);
            }                       
 /*              try {
                
                HazelcastInstance instance = Hazelcast.newHazelcastInstance (new Config());
                ConcurrentMap<Integer, String> accountMap = instance.getMap("userMap");
                accountMap.put(1, "Abhinav");
                System.out.println ("kjhkljljkjl");
                System.out.println (accountMap.get(1));
            	}
               catch (Exception e){
               	System.out.println ("exception");
               }*/
            // Redirect to userInfo page.
            response.sendRedirect(request.getContextPath() + "/userInfo");
            
/*            if (hazelcastConn.instance == null){
               hazelcastConn.hazelcastInit();
            }
            */
/*            
            System.out.println ("hazelcast instance " + hazelcastConn.instance);*/
          
/*
            Map <Integer, userAccount> accountMap = instance.getMap("accountMap");
            accountMap.put(1, user);

            
            System.out.println (((userAccount)accountMap.get(1)).getUserName());
            System.out.println (((userAccount)accountMap.get(1)).getPassword());

            
            ClientConfig clientConfig = new ClientConfig();
            clientConfig.addAddress("192.168.0.3:5701");
            HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
            IMap<Integer, String> map = client.getMap("userMap");
            System.out.println("Map Size:" + map.size());
            
*/
        }
    }
 
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
 
}
