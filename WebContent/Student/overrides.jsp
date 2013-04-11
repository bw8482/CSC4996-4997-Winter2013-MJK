<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Student" %>
<%@ page import ="CSAppointmentSchedulerFaces.Database" %>
<%@ page import ="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='../../js/general.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>

</head>
<body>
<%
Database.connect();
String sql = "SELECT * FROM OVERRIDES";
ResultSet rs = Database.fetch(sql);

User user = new User();
user.getUser();

String email = user.getEmail();

String overrides = "<select name='overrides' placeholder='Select an error' id='override' style='width:200px;'>";
overrides += "<option>--</option>";
while(rs.next()) {
	overrides += "<option value='" + rs.getString("ERROR_ID") + "'>" + rs.getString("ERROR_DESC") + "</option>";
}
%>
<center><h2>WSU Computer Science Departments Override Descriptions and Forms</h2></center>
<div style='font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'></div>
	<form method ="post" action ="">
		<table>
			<tr style='background-color: transparent;'>
				<td>
					Select the error that you received...	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<% out.println(overrides); %>
				</td>
			</tr>
			
	</table>
	</form>
<div id=content >
<div style='font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'>Please do the following to solve error:</div>
	
	<% 
		
		String output = Student.getOverrides(request.getParameter("override").value);
		out.print(output);
	%>
	
</div>


</body>
</html>