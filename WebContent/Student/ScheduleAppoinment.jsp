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
<LINK href="../css/Calendar2.css" rel="stylesheet" type="text/css">

<script type='text/javascript' src='../../js/calendar.js'></script>
<script type='text/javascript' src='../../js/general.js'></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%

	String email = User.getUser().getEmail();	
	out.println(User.getUser().buildHeaderMenu("student"));

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
	
	String majors = "<select name='major' id='major' style='width:200px;'>";
	majors += "<option>--</option>";
	while(rs.next()) {
		majors += "<option value='" + rs.getString("MAJOR_ID") + "'>" + rs.getString("MAJOR_TEXT") + "</option>";
	}
	
	
	sql = "SELECT * FROM REASON";
	rs = Database.fetch(sql);
	
	String reasons = "<select name='reason' id='reason' style='width:200px;' class=''>";
	reasons += "<option>--</option>";
	while(rs.next()) {
		reasons += "<option value='" + rs.getString("REASON_ID") + "'>" + rs.getString("REASON_TEXT") + "</option>";
	}
%>
<div id='content' style=''>	
	<div id = 'validate'></div>
	<div class='title'>Schedule an Appointment</div>
	<form method ="post" action ="Confirmation.jsp" onsubmit='return validateAppt();'>
		<table >
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					Which advisor would you like to schedule an appointment with?	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div class="styled-select">
					   <% out.println(advisors); %>
					</div>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					Pick a Date (mm/dd/yyyy)
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<input type='text' name='date' id='date' style='width: 150px; height: 20px; ' placeholder='' onchange='getAvailableTimes();' />
					<img src='../img/calendar.gif' style='vertical-align: bottom;' id='cal' onmouseover='setup_cal("cal", "date");'/>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					Pick a Time
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div id='timeContainer' class='styled-select'>
						<select name='time' id='time' style='width: 200px;'>
							<option value=''>--</option>
						</select>
					</div>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					What do you want to schedule an appointment for?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div class="styled-select">
					   <% out.println(reasons); %>
					</div>	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					What is your current class standing?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div class="styled-select">
						<select name='standing' id='standing' style='width: 200px;'>
							<option value="0">--</option>
							<option value="1">Freshmen</option>
							<option value="2">Sophomore</option>
							<option value="3">Junior</option>
							<option value="4">Senior</option>
						</select>
					</div>
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td style='font-size: 12px;'>
					What is your current major?
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
					<div class="styled-select">
					   <% out.println(majors); %>
					</div>
				</td>
			</tr>
			<!-- 
			<tr style='background-color: transparent;'>
				<td style='vertical-align: bottom;'>
					<input type='checkbox' name='firstAppt' /> Check if this your first appointment with the WSU CS Department.
				</td>
			</tr>
			-->
			<tr style='background-color: transparent;'>
				<td>
					<input type='submit' name='submit' value='Submit' style='margin-left: 0px;'/>
					<input type='reset' name='reset' value='Reset' />
				</td>
			</tr>
		</table>
	</form>
</div>
</f:view>
</body>
</html>
