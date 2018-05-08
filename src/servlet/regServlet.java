package servlet;

import java.io.IOException;
import java.sql.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBUtils;
import utils.myUtils;
import database.conn.ConnectionUtils;

/**
 * Servlet implementation class regServlet
 */
@WebServlet("/regServlet")
public class regServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public regServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String name = request.getParameter("fullName");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String address = request.getParameter("address");
/*		
		System.out.println ("username" + name);
		System.out.println ("email" + email);
		System.out.println ("password" + password);*/

		Connection conn = myUtils.getStoredConnection (request);
		if (conn == null){
			try{
				conn = ConnectionUtils.getConnection ();
			}
			catch (Exception e){
				System.out.println ("Connnection failed");
				return;
			}
		}
		try {
			if (DBUtils.findUser(conn, email) != null){
				System.out.println ("User exists");   // to be changed. This has to be printed on screen instead of console
				return;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    String query = " insert into user_account(name, email, password, address)" + " values (?, ?, ?, ?)";
	    try {
	    PreparedStatement preparedStmt = conn.prepareStatement(query);
	      preparedStmt.setString (1, name);
	      preparedStmt.setString (2, email);
	      preparedStmt.setString (3, password);
	      preparedStmt.setString (4, address);

	      // execute the preparedstatement
	      preparedStmt.execute();
	      response.sendRedirect(request.getContextPath() + "/userInfo");
	      conn.close();
	    }
	    catch (Exception e){
	    	System.out.println("Exception");
	    }
	    }
	}

