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
	out.println("'<div align = 'right' style = 'font-size: 13px; '><b>Welcome, " + user.getName() + "</b>&nbsp&nbsp;</div>");
%>

<center><h1>WSU Computer Science - Forms and Overrides</h1></center>

<div style='font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'></div>
	
		<table>
		<th>
		Error Description
		</th>
		<tr style='background-color: transparent;'>
				<td>Pre-requisite and Test Score error</td>
				<td>Level Restriction<td>
				<td>Written Consent needed<td>
			    <td>TIME CONFLICT WITH "_____ "<td>
			    <td>Repeat count exceeds 0<td>
			    <td>CLOSED SECTION-X <td>
			    <td>Co-requisite and Test error<td>
					
				</td>
		</tr>
		<th>
		Error Description
		</th>
		<tr style='background-color: transparent;'>
				<td>
					Error Reason
				</td>
		</tr>
		<th>
		Error Description
		</th>
		<tr style='background-color: transparent;'>
				<td>
					Error Solution
				</td>
		</tr>
		<th>
		Error Description
		</th>
		<tr style='background-color: transparent;'>
				<td>
					Forms to Complete
				</td>
		</tr>
		<tr style='background-color: transparent;'>
				<td>
					
				</td>
		</tr>
		<tr style='background-color: transparent;'>
				<td>
					
				</td>
		</tr>
			<tr style='background-color: transparent;'>
				<td>
					
	
				</td>
			</tr>
			<tr style='background-color: transparent;'>
				<td>
				   Something	
				</td>
			</tr>
			
	</table>



</body>
</html>