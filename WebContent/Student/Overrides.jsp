<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.Database" %>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="java.sql.ResultSet" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
</head>
<body>
<%
	User user;
    user = User.getUser();
	String headerMenu = user.buildHeaderMenu("student");
	out.println(headerMenu);
%>

	<div id='content'>
		<table class= "tbl" style='margin: 5px;'>
		
		<th><div style=''>Description of Error</div></th>
		<th><div>Reason for Error</div></th>
		<th><div>Solution to Error</div></th>
		<th><div>Forms to Complete</div></th>
		
		<tr>
		<td style = "font-red; color :red;">Pre-requisite and Test Score error</td>
		<td>You have not met the pre-requisites.</td>
		<td>REQUEST AN OVERRIDE: Override/Petition Form, Signed by instructor </td>
		<td><a href="https://engineering.wayne.edu/pdf/cofeappeal.pdf">College of Engineering Appeal Form</a></td>
				
		</tr>
		
		<tr>
		<td style = "color :red ;">Level Restriction</td>
		<td>You currently do not meet the required class standing/number of credits for this course.</td>
		<td>Obtain instructor approval</td>
		<td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				
		</tr>
		
		<tr>
			<td style = "color :red ;">Written Consent needed</td>
			<td>Requires Department/ Instructor Approval.</td>
			<td>Complete Add form</td>
			<td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
					
		</tr>
		
		<tr>
		<td style = "color :red ;">TIME CONFLICT WITH "_____ "</td>
		<td>Students may not register for courses with overlapping times.</td>
		<td>NO OVERRIDES GRANTED. Pick a new section.</td>
		<td>No Form Required</td>
				
		</tr>
		<tr>
		<td style = "color :red;" >Repeat count exceeds 0</td>
		<td>You have already attempted this course once</td>
		<td>If it is your 2nd attempt: REQUEST AN OVERRIDE 3rd attempt: See advisor.</td>
		<td>No Form Required</td>
				
		</tr>
		<tr>
		<td style = "color :red ;">CLOSED SECTION-X </td>
		<td>No seats are available in the course</td>
		<td>NO OVERRIDES GRANTED. Contact instructor.</td>
		<td><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				  
		</tr>
		<tr>
		<td style = "color :red ;">Co-requisite and Test error</td>
		<td>You are not signed up for all components of the course (i.e. lab and lecture).</td>
		<td>NO OVERRIDES GRANTED. Register for both components.</td>
		<td>No Form Required</td>
				
		</tr>
			
	</table>
	<div style='font-weight: bold; padding: 5px;'>
		Click <a href="mailto:colleen.mckenney@wayne.edu?subject=CS Scheduler - Overide Request 
		&body=Ms. Mckenney,%0A%0A%0ANOTE: All fields are mandatory to complete the override process%0A%0A%0AStudent Name: <%out.println(user.getFirstName()+" "+user.getLastName());%>%0A%0A
		Banner Id:%0A%0AAccess Id: <%out.println(user.getAccessId());%>%0A%0AOverride Reason:%0A%0ACourse needed:%0A%0APre-requisite(s):%0A%0AGrade(s) received:">
		Here</a></em> to send override information to the advisor
	</div>
</div>

</body>
</html>