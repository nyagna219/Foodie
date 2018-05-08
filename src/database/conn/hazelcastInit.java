package database.conn;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hazelcast.config.*;
import com.hazelcast.core.*;

public class hazelcastInit implements ServletContextListener{
/*	public static HazelcastInstance instance = null;
	
	public hazelcastConn(){
		
	}
	
	public static void hazelcastInit(){
		Config cfg = new Config ();
		cfg.setLicenseKey("ENTERPRISE_HD#100Nodes#a6IO7KlwjbmNUAESkufVJ0F1HTr5y1411010191212016011910001119010");
		instance = Hazelcast.newHazelcastInstance(cfg);
	}*/

	    public void contextInitialized(ServletContextEvent sce) {
	        //application is being deployed
	        //register the "global" object here
	    	Config cfg = new Config ();
			cfg.setLicenseKey("ENTERPRISE_HD#100Nodes#a6IO7KlwjbmNUAESkufVJ0F1HTr5y1411010191212016011910001119010");
			HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);

			ServletContext sc = sce.getServletContext();
	        sc.setAttribute("hcastInstance", instance);
	    }
	    public void contextDestroyed(ServletContextEvent sce) {
	        //application is being undeployed
	    }
	}
