package servlet;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import com.hazelcast.config.*;
import com.hazelcast.core.*;
import com.hazelcast.client.config.ClientConfig;
import com.hazelcast.client.HazelcastClient;

/**
 * Servlet implementation class cart
 */
@WebServlet("/cart")
public class cart extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public cart() {
        super();
        // TODO Auto-generated constructor stub
    }

    @SuppressWarnings("deprecation")
	private HazelcastInstance getHConnection(){
    	ClientConfig clientConfig = new ClientConfig();
    	clientConfig.setLicenseKey("ENTERPRISE_HD#10Nodes#a6IO7KlwjbmNUAESkufVJ0F1HTr5y1411010191212016011910001119010");
        clientConfig.addAddress ("192.168.0.3:5701");
        HazelcastInstance client = HazelcastClient.newHazelcastClient(clientConfig);
        return client;
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("in cart servlet");
		String item = request.getParameter("food");
		HazelcastInstance instance;
		String user = "Abhinav";
		
		System.out.println("request.getParameter(food)  " + item);
/*		try{
		instance = getHConnection();
		IMap<String, String> map = instance.getMap("cart");
        System.out.println("Map Size: " + map.size() + "Food: " + map.get("Abhinav"));
		}
		catch (Exception e){		
		Config cfg = new Config ();
		cfg.setLicenseKey("ENTERPRISE_HD#100Nodes#a6IO7KlwjbmNUAESkufVJ0F1HTr5y1411010191212016011910001119010");
		instance = Hazelcast.newHazelcastInstance(cfg);*/
		ServletContext servletContext = request.getServletContext();
		instance = (HazelcastInstance)servletContext.getAttribute("hcastInstance");
		System.out.println("in cart servlet hazelcastinstance" + instance);
		
		MultiMap<String, String> cartMap = instance.getMultiMap("cart");
        cartMap.put(user, item);
        /*System.out.println ("mapCustomers.get() "+ cartMap.get("Abhinav"));*/
        System.out.println ("mapCustomers.size() "+ cartMap.size());
        int i= 0;
        for (String s: cartMap.get("Abhinav")){
/*        	Collection <String > values = cartMap.get( s );*/
        	System.out.println("item" + ++i +": " + s);
        }
		/*}*/
}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
