<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="CSAppointmentSchedulerFaces.FormatterFactory" %>
<%@ page import ="java.sql.ResultSet" %>
<%@ page import ="CSAppointmentSchedulerFaces.Database" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />

<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript" src="../js/general.js"></script>
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
	<%
		String accessId = User.getUser().getAccessId();
		out.println(User.getUser().buildHeaderMenu("advisor"));
		Database.connect();
		String sql = "";
		String message = "";
		try {
			if(request.getParameter("submit").equals("Delete")) {
				sql = "DELETE FROM REASON WHERE REASON_ID = " + request.getParameter("id");
			} else if(request.getParameter("submit").equals("Save")) {
				sql = "UPDATE REASON SET REASON_TEXT = '" + request.getParameter("text").replace("'", "''") + "', REASON_FEEDBACK = '" + request.getParameter("feedback").replace("'", "''") + "' WHERE REASON_ID = " + request.getParameter("id");
			} else if(request.getParameter("submit").equals("Add")) {
				sql  = "INSERT INTO REASON ";
				sql += "(REASON_TEXT, REASON_FEEDBACK) ";
				sql += "VALUES ('" + request.getParameter("text").replace("'", "''") + "', '" + request.getParameter("feedback").replace("'", "''") +"')";
			}
			
			if(Database.execute(sql)) {
				message = ("<div class='success' style=''>Successfully saved changes.</div>");			
			} else {
				message = ("<div class='error' style=''>Unable to save changes.</div>");
			}
		} catch(Exception ex) {
			//out.println("<div class='error'>" + ex.toString() + "</div>");
		}
	%>
	<div id='content'>
		<div class='title'>Update Reasons Available for Appointments</div>
		<%
			out.print(message);
			sql = "SELECT * FROM REASON";
			ResultSet rs = Database.fetch(sql);
			
			String output = "<table>";
			output += ("<form method='POST'>");
			
			output += ("<tr><td colspan='2'>To add a new reason, enter the short description / feedback for the reason below and click on 'Add'.</td></tr>");
			output += ("<tr>");
			output += ("<td style='vertical-align: bottom; border: 0px solid red;'><input type='text' name='text' style='width: 502px;' value=''/></td>");
			output += ("<tr></tr>");
			output += ("<td style='vertical-align: bottom; border: 0px solid black;'><textarea name='feedback' style='height: 40px; width: 500px; border: 1px solid #000;'></textarea></td>");
			output += ("<tr></tr>");
			output += ("<td style='text-align: right;'><input type='submit' name='submit' value='Add'/> <input type='hidden' name='id' value='' /></td>");
			output += ("</tr>");
			
			output += ("</form>");
			output += ("<tr><td colspan='2'><a href='#' onclick='document.getElementById(\"reasons\").style.display=\"block\";'>Click here</a> to view available reasons that students can choose from. <br/>Edit the description and/or feedback and click on 'Save'. <br/>Click on 'Delete' to remove the reason from the available selections.</td></tr>");
			output += ("</table>");
			
			output += ("<div id='reasons' style='display: none;'/>");
			output += ("<table>");
			while(rs.next()) {
				output += ("<form method='POST'>");
				
				output += ("<tr>");
				output += ("<td style='vertical-align: bottom; border: 0px solid red;'><input type='text' name='text' style='width: 502px;' value='" + FormatterFactory.format(rs.getString("REASON_TEXT")) + "'/></td>");
				output += ("<tr></tr>");
				output += ("<td style='vertical-align: bottom; border: 0px solid black;'><textarea name='feedback' style='height: 40px; width: 500px; border: 1px solid #000;'>" + FormatterFactory.format(rs.getString("REASON_FEEDBACK")) + "</textarea></td>");
				output += ("<tr></tr>");
				output += ("<td style='text-align: right;'><input type='submit' name='submit' value='Delete'/> <input type='submit' name='submit' value='Save' /><input type='hidden' name='id' value='" + rs.getString("REASON_ID") +"' /></td>");
				output += ("</tr>");
				
				output += ("</form>");
			}
			
			output += ("</div>");
			output += "</table>";
			out.println(output);
		
		%>
	</div>
</f:view>
</body>
</html>