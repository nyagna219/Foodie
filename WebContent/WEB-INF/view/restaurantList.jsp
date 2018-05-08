<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script src="http://code.jquery.com/jquery-1.10.1.min.js"></script>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script> 
<title>Insert title here</title>
</head>
<%-- <%@page import="utils.restaurant" %>
<%@page import="org.json.simple.*"%>
<%@page import="org.json.simple.parser.*"%>

<%
String restResp = (request.getAttribute("jsonString")).toString();
System.out.println("restaurantList.jsp:  " + request.getAttribute("jsonString"));
System.out.println(restResp);

/* JSONParser parser = new JSONParser();
JSONObject yelpResponse = null;
try {
  yelpResponse = (JSONObject) parser.parse(restResp);
} catch (ParseException pe) {
  System.out.println("Error: could not parse JSON response:");
  System.out.println(restResp);
  System.exit(1);
} */
/* 
JSONArray restArr = (JSONArray)yelpResponse.get("businesses");
JSONObject restObj = (JSONObject)restArr.get(1); */
%>
<%-- <table>
    <tr>  ${jsonString}
    </tr>
</table> --%>

${restaurant.name}
${restaurant.phone }
</body>
</html>