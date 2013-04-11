<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="CSAppointmentSchedulerFaces.Student" %>
<%@ page import ="CSAppointmentSchedulerFaces.Database" %>
<%@ page import ="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<script type='text/javascript' src='../../js/general.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%

User user = new User();
user.getUser();
String headerMenu = user.buildHeaderMenu("");
out.println(headerMenu);

String email = user.getEmail();



	
	Database.connect();
	String sql = "SELECT * FROM ADVISOR";
	ResultSet rs = Database.fetch(sql);
	
	String advisors = "<select name='advisor' id='advisor' onchange='getAvailableTimes()' style='width:200px;'>";
	advisors += "<option>--</option>";
	while(rs.next()) {
		advisors += "<option value='" + rs.getString("ACCESS_ID") + "'>" + rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME") + "</option>";
	}
	
	sql = "SELECT * FROM MAJOR";
	rs = Database.fetch(sql);
	
	String majors = "<select name='major' style='width:200px;'>";
	majors += "<option>--</option>";
	while(rs.next()) {
		majors += "<option value='" + rs.getString("MAJOR_ID") + "'>" + rs.getString("MAJOR_TEXT") + "</option>";
	}
	
	sql = "SELECT * FROM REASON";
	rs = Database.fetch(sql);
	
	String reasons = "<select name='reason' style='width:200px;'>";
	reasons += "<option>--</option>";
	while(rs.next()) {
		reasons += "<option value='" + rs.getString("REASON_ID") + "'>" + rs.getString("REASON_TEXT") + "</option>";
	}
%>
<div id='content' style='padding-right: 50px;'>	
	<%
	try {
		if(request.getParameter("submit").equals("Submit")) {
			boolean success = Student.scheduleAppointment(email, request.getParameter("advisor"), request.getParameter("date"), request.getParameter("time"), request.getParameter("reason"), request.getParameter("standing"), request.getParameter("standing"), "1");
			if(success) {
				out.println("<div class='success'>You have succesfully scheduled an appointment.</div>");
			} else {
				out.println("<div class='error'>There was an error while scheduling your appointment.</div>");	
			}
		}
	} catch (Exception ex) {
		try {
			if(ex.getMessage().startsWith("Duplicate")) {
				out.println("<div class='error'>Error - you already have an appointment scheduled on " + request.getParameter("date") + ".</div>");
			}
		} catch(Exception e) {
			
		}
	}
	%>
	<div style='font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'>Schedule an Appointment</div>
	<form method ="post" action ="">
		<table>
			<tr style='background-color: transparent;'>
				<td>
					Which advisor would you like to schedule an appointment with?	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<% out.println(advisors); %>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					Pick a Date	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<input type='text' name='date' id='date' value='' placeholder='yyyy-mm-dd' onchange='getAvailableTimes()'/>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					Pick a Time	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div id='timeContainer'>
						<select name='time' style='width: 200px;'>
							<option value=''>--</option>
						</select>
					</div>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					What do you want to schedule an appointment for?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<% out.println(reasons); %>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					What is your current class standing?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<select name='standing' style='width: 200px;'>
						<option value="0">--</option>
						<option value="1">Freshmen</option>
						<option value="2">Sophomore</option>
						<option value="3">Junior</option>
						<option value="4">Senior</option>
						<option value="5">Undecided</option>
					</select>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					What is your current major?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<% out.println(majors); %>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='vertical-align: bottom;'>
					<input type='checkbox' name='firstAppt' /> Check if this your first appointment with the WSU CS Department.
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<input type='submit' name='submit' value='Submit' />
					<input type='reset' name='reset' value='Reset' />
				</td>
			</tr>
		</table>
	</form>
</div>
</f:view>
</body>
</html>