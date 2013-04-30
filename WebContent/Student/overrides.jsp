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

	
		<table class= "tbl">
		
		<th><div>Description of Error</div></th>
		<th><div>Reason for Error</div></th>
		<th><div>Solution to Error</div></th>
		<th><div>Forms to Complete</div></th>
		
		<tr>
				<td style = "font-size: 14px;font-weight:bold; color :blue ;width:23%">Pre-requisite and Test Score error</td>
				<td style = "font-size: 13px;">You have not met the pre-requisites.</td>
				<td style = "font-size: 13px;">REQUEST AN OVERRIDE: Override/Petition Form, Signed by instructor </td>
				<td style = "font-weight:bold; color :blue ;width:20%"><a href="https://engineering.wayne.edu/pdf/cofeappeal.pdf">College of Engineering Appeal Form</a></td>
				
		</tr>
		
		<tr>
				<td style = "font-size: 14px;font-weight:bold; color :blue ;">Level Restriction</td>
				<td style = "font-size: 13px;">You currently do not meet the required class standing/number of credits for this course.</td>
				<td style = "font-size: 13px;">Obtain instructor approval</td>
				<td style = "font-size: 13px;"><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				
		</tr>
		
		<tr>
					<td style = "font-size: 14px;font-weight:bold; color :blue ;">Written Consent needed</td>
					<td style = "font-size: 13px;">Requires Department/ Instructor Approval.</td>
					<td style = "font-size: 13px;">Complete Add form</td>
					<td style = "font-size: 13px;"><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
					
		</tr>
	
		<tr>
				<td style = "font-size: 14px;font-weight:bold; color :blue ;">TIME CONFLICT WITH "_____ "</td>
				<td style = "font-size: 13px;">Students may not register for courses with overlapping times.</td>
				<td style = "font-size: 13px;">NO OVERRIDES GRANTED. Pick a new section.</td>
				<td style = "font-size: 13px;">No Form Required</td>
				
		</tr>
		<tr>
				<td style = "font-size: 14px;font-weight:bold; color :blue ;" >Repeat count exceeds 0</td>
				<td style = "font-size: 13px;">You have already attempted this course once</td>
				<td style = "font-size: 13px;">If it is your 2nd attempt: REQUEST AN OVERRIDE 3rd attempt: See advisor.</td>
				<td style = "font-size: 13px;">No Form Required</td>
				
		</tr>
		<tr>
				   <td style = "font-size: 14px;font-weight:bold; color :blue ;">CLOSED SECTION-X </td>
				   <td style = "font-size: 13px;">No seats are available in the course</td>
				   <td style = "font-size: 13px;">NO OVERRIDES GRANTED. Contact instructor.</td>
				   <td style = "font-size: 13px;"><a href="http://reg.wayne.edu/pdf-forms/add.pdf">WSU Student Add Form</a></td>
				  
		</tr>
		<tr>
				<td style = "font-size: 14px;font-weight:bold; color :blue ;">Co-requisite and Test error</td>
				<td style = "font-size: 13px;">You are not signed up for all components of the course (i.e. lab and lecture).</td>
				<td style = "font-size: 13px;">NO OVERRIDES GRANTED. Register for both components.</td>
				<td style = "font-size: 13px;">No Form Required</td>
				
		</tr>
			
	</table>

<%
	Database.connect();
	String sql = "SELECT * FROM ADVISOR";
	ResultSet rs = Database.fetch(sql);
	
	String advisors = "<select name='advisor' id='advisor' style='width:200px;'>";
	advisors += "<option>--</option>";
	while(rs.next()) {
		advisors += "<option value='" + rs.getString("ACCESS_ID") + "'>" + rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME") + "</option>";
	}
	
%>
<br>
<br>
<div align="center"; style='font-size: 18px;color :blue ;margin-bottom: 5px;'><em>Select an advisor to send an override</em></div>
	<form method ="post" action ="">
	<table align = "center">
	       <tr style='background-color: transparent;'>
				<td>
					<% out.println(advisors); %>
			
					<input type='submit' name='submit' value='Submit' />
				</td>
			</tr>
		</table>
	</form>
</div> 

<p><h2  align= "center">Click <a href="mailto:colleen.mckenney@wayne.edu?subject=CS Scheduler - Overide Request 
&body=Ms.Mckenney,%0A%0A%0ANOTE: All fields are mandatory to complete the override process%0A%0A%0AStudent Name: <%out.println(user.getFirstName()+" "+user.getLastName());%>%0A%0A
Banner Id:%0A%0AAccess Id: <%out.println(user.getAccessId());%>%0A%0AOverride Reason:%0A%0ACourse needed:%0A%0APre-requisite(s):%0A%0AGrade(s) received:
">Here</a></em> to send override information to the advisor</h2></p>



</body>
</html>