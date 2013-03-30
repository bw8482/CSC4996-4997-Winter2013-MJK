<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="CSAppointmentSchedulerFaces.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript">
// Popup window code
function newPopup(url) {
	popupWindow = window.open(
		url,'popUpWindow','height=700,width=900,left=10,top=10,resizable=yes,scrollbars=yes,toolbar=yes,menubar=no,location=no,directories=no,status=yes')
}
</script>

<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	User user;
	user = User.getUser();
	String headerMenu = user.buildHeaderMenu("student");
	out.println(headerMenu);
	
	try {
		if(request.getParameter("submit").equals("Submit")) {
			boolean success = Student.scheduleAppointment(request.getParameter("advisor"), request.getParameter("date"), request.getParameter("time"), request.getParameter("reason"), request.getParameter("major"));
		}
	} catch (Exception e) {
		
	}
%>
<div id='content'>
	Welcome to the <b style='color:#254117;'>Wayne State University</b> Computer Science Appointment Scheduler.
	
	<div style='font-weight: bold; font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'>Schedule an Appointments</div>
	<form method ="post" action ="">
		<div style='margin-top: 5px;'>
			<label>Which Advisor would you like to schedule an appointment with?<br>
			<select name="advisordropdown">
				<option value="s1">Select One</option>
				<option value="ef2558">Colleen McKenney</option>
				<option value="ef2558">John Brown</option>
				<option value="ef2558">Kim French</option>
			</select>
			</label>
		</div>
		
		<div style='margin-top: 5px;'>
			<label>Pick a Day<br>
			<input type="date"  name='date'placeholder ="yyyy-mm-dd" />
			</label>
		</div>
		
		<div style='margin-top: 5px;'>
			<label>Pick a Time<br>
			<input type ="time" name='time' placeholder ="hh:mm:ss" />
			</label>
		</div>
		
		<div style='margin-top: 5px;'>
			<label>Select a reason for this appointment<br>
			<select name="reasondropdown" placeholder ="Reason">
				<option value="s2">Select One</option>
				<option value="academicdifficulty">Academic Difficulty</option>
				<option value="sapappeal">SAP Appeal-(Bring SAP forms filled out ready to be signed)</option>
				<option value="courseplanning">Course Schedule Planning</option>
				<option value="graduationaudit">Graduation Audit</option>
				<option value="generalinformation">General Information</option>
				<option value="prospectivestudent">Prospective Non WSU student-(Bring transcripts)</option>
			</select>
			</label>
		</div>
		
		<div style='margin-top: 5px;'>
			<label>What is your current class standing?<br>
			<select name="standingdropdown">
			    <option value="s4">Select One</option>
				<option value="freshman">Freshman</option>
				<option value="sophamore-cs">Sophomore</option>
				<option value="junior">Junior</option>
				<option value="senior">Senior</option>
			</select>
			</label>
		</div>
		<div style='margin-top: 5px;'>
			<label>What is you current major?<br>
			<select name="majordropdown">
			    <option value="s3">Select One</option>
				<option value="bs-cs">BS - Computer Science</option>
				<option value="ba-cs">BA - Computer Science</option>
				<option value="ba-ist">BA - Information Systems Technology</option>
				<option value="ba-ist-imse">BA - Information Systems Technology (IMSE)</option>
			</select>
			</label>
		</div>
			
		<div style='margin-top: 5px;'>
			<input type="checkbox" name="firsttime" value="false">Is your first time making an appointment?
		</div>
		<div>
			<input type="submit" name= "submit" value="Submit"/>
			<input type="reset" value ="Clear" />
		</div>

	</form>
	<a href="JavaScript:newPopup('http://localhost:8080/CSC4996-4997-Winter2013-MJK/Student/overrides.html');">Override Information and Forms</a>    
</div>
</f:view>
</body>
</html>