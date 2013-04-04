<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.Database" %>
<%@ page import="java.sql.ResultSet" %>
<script type="text/javascript" src="js/general.js"></script>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>

<%

	try {
		out.println("You selected " + request.getParameter("reason"));
	} catch (Exception ex) {
		
	}
	Database.connect();
	String sql = "SELECT * FROM REASON";
	ResultSet rs = Database.fetch(sql);
	
	String reasons = "<select name='reason'>";
	while(rs.next()) {
		reasons += "<option value='" + rs.getString("REASON_ID") + "'>" + rs.getString("REASON_TEXT") + "</option>";
		
	}
	
	reasons += "</select>";
	
	out.println("Hello world");
	out.println("<form>");
	out.println(reasons);
	out.println("Date <input type='text' id='date' value='2013-4-3'/>");
	out.println("Advisor <input type='advisor' id='advisor' value='ef2558'/>");

	out.println("<div id='timeContainer'><select><option>--</option></select></div>");
	out.println("<input type='submit' name='submit' value='Save'/>");
	
	
	out.println("</form>");
	out.println("<button onclick='getAvailableTimes(); return false;'>Click me</button>");
%>

</f:view>
</body>
</html>