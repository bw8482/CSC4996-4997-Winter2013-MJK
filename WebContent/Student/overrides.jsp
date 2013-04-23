<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
</head>
<body>
<%
	User user = new User();
    user = user.getUser();
	String headerMenu = user.buildHeaderMenu("student");
	out.println(headerMenu);
	%>

<center><h1>Registration Errors, Forms and Overrides</h1></center>

	
		<table class= 'tbl';>
		
		<th style= font-size:18px  bgcolor="#FF0000" height = "50" width = "20%"><div>
		Error Description
		</div></th>
		<th style= font-size:18px bgcolor="#FF0000" height = "50" width = "20%"><div>
		Error Reason
		</div></th>
		<th style= font-size:18px bgcolor="#FF0000" height = "50" width = "20%"><div>
		Error Solution
		</div></th>
		<th style= font-size:18px bgcolor="#FF0000" height = "50" width = "20%"><div>
		Forms to Complete
	
		
		<tr height = "30" style='background-color: #85FF85;'>
				<td>Pre-requisite and Test Score error</td>
				<td>You have not met the pre-requisites.</td>
				<td>REQUEST AN OVERRIDE: Override/Petition Form, Signed by instructor -</td>
				<td><a href="https://engineering.wayne.edu/pdf/cofeappeal.pdf">College of Engineering Appeal Form</a></td>
				
		</tr>
		
		<tr height = "30" style='background-color: #85FF85;'>
				<td>Level Restriction</td>
				<td>You currently do not meet the required class standing/number of credits for this course.</td>
				<td>Obtain instructor approval</td>
				<td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				
		</tr>
		
		<tr height = "30" style='background-color: #85FF85;'>
					<td>Written Consent needed</td>
					<td>Requires Department/ Instructor Approval.</td>
					<td>Complete Add form</td>
					<td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
					
		</tr>
	
		<tr height = "30" style='background-color: #85FF85;'>
				<td>TIME CONFLICT WITH "_____ "</td>
				<td>Students may not register for courses with overlapping times.</td>
				<td>NO OVERRIDES GRANTED. Pick a new section.</td>
				<td>No Form Required</td>
				
		</tr>
		<tr height = "30" style='background-color: #85FF85;'>
				<td>Repeat count exceeds 0</td>
				<td>You have already attempted this course once</td>
				<td>If it is your 2nd attempt: REQUEST AN OVERRIDE 3rd attempt: See advisor.</td>
				<td>No Form Required</td>
				
		</tr>
		<tr height = "30" style='background-color: #85FF85;'>
				   <td>CLOSED SECTION-X </td>
				   <td>No seats are available in the course</td>
				   <td>NO OVERRIDES GRANTED. Contact instructor.</td>
				   <td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				  
		</tr>
		<tr height = "30" style='background-color: #85FF85;'>
				<td>Co-requisite and Test error</td>
				<td>You are not signed up for all components of the course (i.e. lab and lecture).</td>
				<td>NO OVERRIDES GRANTED. Register for both components.</td>
				<td>No Form Required</td>
				
			    
		</tr>
			
	</table>
<p><h3>Click here to send override information to your advisor</h3></p>
   









  








</body>
</html>