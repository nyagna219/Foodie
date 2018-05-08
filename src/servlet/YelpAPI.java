package servlet;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import utils.restaurant;

import com.foodie.TwoStepOAuth;

@WebServlet("/YelpAPI")
public class YelpAPI extends HttpServlet{
		private static final long serialVersionUID = 1L;
	       
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	    public YelpAPI() {
	        super();
	        // TODO Auto-generated constructor stub
	    }
	    private static final String API_HOST = "api.yelp.com";
	    private static final int SEARCH_LIMIT = 10;
	    private static final String SEARCH_PATH = "/v2/search";
	    private static final String BUSINESS_PATH = "/v2/business";

        private static final String CONSUMER_KEY = "8pneVaSrYhxPOO2DcTWbRA";
        private static final String CONSUMER_SECRET = "kpTCI161fC60nRUgWFy4wRiVwJU";
        private static final String TOKEN = "MiWj5zLG9iQKlA_063ANYWVsOBDnS8-j";
        private static final String TOKEN_SECRET = "0ByV6tAebGfYdm7R5On2892VEJ0";
        OAuthService service;
        Token accessToken;

        public YelpAPI(String consumerKey, String consumerSecret, String token, String tokenSecret) {

        	this.service =
                new ServiceBuilder().provider(TwoStepOAuth.class).apiKey(consumerKey)
                    .apiSecret(consumerSecret).build();
            this.accessToken = new Token(token, tokenSecret);
          }
        public String searchForBusinessesByLocation(String term, String location) {
            OAuthRequest request = createOAuthRequest(SEARCH_PATH);
            request.addQuerystringParameter("term", term);
            request.addQuerystringParameter("location", location);
            request.addQuerystringParameter("limit", String.valueOf(SEARCH_LIMIT));
            return sendRequestAndGetResponse(request);
          }

        public String searchByBusinessId(String businessID) {
            OAuthRequest request = createOAuthRequest(BUSINESS_PATH + "/" + businessID);
            return sendRequestAndGetResponse(request);
          }

        private OAuthRequest createOAuthRequest(String path) {
            OAuthRequest request = new OAuthRequest(Verb.GET, "https://" + API_HOST + path);
            return request;
          }
        
        private String sendRequestAndGetResponse(OAuthRequest request) {
            System.out.println("Querying " + request.getCompleteUrl() + " ...");
            System.out.println("Hi");
            this.service.signRequest(this.accessToken, request);
            Response response = request.send();
            return response.getBody();
          }

        private static String queryAPI(YelpAPI yelpApi,String term, String location) {
            String searchResponseJSON =
                    yelpApi.searchForBusinessesByLocation(term, location);
            return searchResponseJSON;

/*                JSONParser parser = new JSONParser();
                JSONObject response = null;
                try {
                  response = (JSONObject) parser.parse(searchResponseJSON);
                } catch (ParseException pe) {
                  System.out.println("Error: could not parse JSON response:");
                  System.out.println(searchResponseJSON);
                  System.exit(1);
                }
                return response;*/
/*
                try (FileWriter file = new FileWriter("C:/Users/Prashant/Desktop/file1.txt")) {
            		file.write(searchResponseJSON.toString());
            		System.out.println("Successfully Copied JSON Object to File...");
 //           		System.out.println("\nJSON Object: " + searchResponseJSON);
            	} catch (IOException e) {
            		// TODO Auto-generated catch block
            		e.printStackTrace();
            	}
                JSONArray businesses = (JSONArray) response.get("businesses");
                for(int i=0;i<businesses.size();i++)
                {
                JSONObject firstBusiness = (JSONObject) businesses.get(i);
                String firstBusinessID = firstBusiness.get("id").toString();
                System.out.println(String.format(
                    "%s businesses found, querying business info for the top result \"%s\" ...",
                    businesses.size(), firstBusinessID));

                // Select the first business and display business details
                String businessResponseJSON = yelpApi.searchByBusinessId(firstBusinessID.toString());
                System.out.println(String.format("Result for business \"%s\" found:", firstBusinessID));
                System.out.println(businessResponseJSON);
                }*/
          }



		/**
		 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			String search = request.getParameter("search");
	        String location = request.getParameter("location");

	        System.out.println (location);
	        YelpAPI yelpApi = new YelpAPI(CONSUMER_KEY, CONSUMER_SECRET, TOKEN, TOKEN_SECRET);
	        String queryResponse = queryAPI(yelpApi, search,location);
	        
	        StringBuffer restaurantData = new StringBuffer();
	        restaurantData.append(queryResponse);

	        response.setContentType("text/html");
	        System.out.println(restaurantData.toString());
	        response.getWriter().write(restaurantData.toString());
/*	        JSONParser parser = new JSONParser();
            JSONObject yelpResponse = null;
            try {
              yelpResponse = (JSONObject) parser.parse(queryResponse);
            } catch (ParseException pe) {
              System.out.println("Error: could not parse JSON response:");
              System.out.println(queryResponse);
              System.exit(1);
            }

	        JSONArray restArr = (JSONArray)yelpResponse.get("businesses");
	        JSONObject restObj = (JSONObject)restArr.get(1);
	        System.out.println ("restaurant name " + restObj.get("id").toString());
	        System.out.println ("restaurant phone " + restObj.get("phone").toString());
	        
	        restaurant rest = new restaurant(restObj.get("id").toString(), restObj.get("phone").toString());
	        System.out.println ("queryResponse = " +queryResponse);
	        request.setAttribute("jsonString", queryResponse);
	        System.out.println("request.getParameter(jsonString)= "+ request.getAttribute("jsonString"));
	        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/view/restaurantList.jsp");
	        dispatcher.forward(request, response);
	        response.setContentType("application/json");
	        request.setAttribute ("restaurant", rest);
	        RequestDispatcher dispatcher = this.getServletContext().getRequestDispatcher("/WEB-INF/view/restaurantList.jsp");
	        dispatcher.forward(request, response);*/

		}

		/**
		 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
		 */
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			// TODO Auto-generated method stub
			 doGet(request, response);
		}


}