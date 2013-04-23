<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Advisor" %>
<%@ page import="java.util.Hashtable" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />

<script type="text/javascript" src="../js/general.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	String accessId = User.getUser().getAccessId();
	out.println(User.getUser().buildHeaderMenu("advisor"));

%>
<div id='content'>
	<%
		try {
			if(request.getParameter("submit").equals("Save")) {
				if(Advisor.updatePersonalInfo(accessId, request.getParameter("location"), request.getParameter("phone"), request.getParameter("cancelAllowed"), request.getParameter("noShowAllowed"), request.getParameter("cancelWindow"))) {
					out.println("<div class='success'>Successfully updated your personal information.</div>");
				} else {
					out.println("<div class='error'>Unable to update your personal information.</div>");
				}
			}
			
		} catch(Exception ex) {

		}
	
		Hashtable<String, String> info = Advisor.getPersonalInfo(accessId);
	%>
	<div class='title'>
		Update Your Personal Information & Settings	
	</div>
	<form>
	<table style=''>
		<tr>
			<td style='font-size: 10px;'>
				The following information will be provided to students after they schedule an appointment.
			</td>
		</tr>
		<tr>
			<td>
				First Name<br/>
				<input disabled=disabled type='text' name='' value='<% out.println(info.get("FIRST_NAME")); %>'/>
			</td>
		</tr>
		<tr>
			<td>
				Last Name<br/>
				<input disabled=disabled type='text' name='' value='<% out.println(info.get("LAST_NAME")); %>'/>
			</td>
		</tr>
		<tr>
			<td>
				Email<br/>
				<input disabled=disabled type='text' name='email' value='<% out.println(info.get("ADVISOR_EMAIL")); %>'/>
			</td>
		</tr>		
		<tr>
			<td>
				Location<br/>
				<input style='width: 300px;' type='text' name='location' value='<% out.println(info.get("LOCATION")); %>'/>
			</td>
		</tr>
		<tr>
			<td>
				Phone Number<br/>
				<span style='font-size: 10px'>Format: xxxxxxxxxx<br/></span>
				<input type='text' name='phone' value='<% out.println(info.get("PHONE")); %>' size='10'/>
			</td>
		</tr>
		<tr>
			<td>
				Allow students to cancel an appointment <input style='width: 30px;' type='text' name='cancelWindow' value='<% out.println(info.get("CANCEL_WINDOW")); %>'/> hours 
				before their appointment.
			</td>
		</tr>
		<tr>
			<td>
				Allow students to cancel a max of <input style='width: 30px;' type='text' name='cancelAllowed' value='<% out.println(info.get("CANCEL_ALLOWED")); %>'/> appointments 
				before disabling their ability to schedule an appointment.
			</td>
		</tr>
		<tr>
			<td>
				Allow students to miss a max of <input style='width: 30px;' type='text' name='noShowAllowed' value='<% out.println(info.get("NO_SHOW_ALLOWED")); %>'/> appointments 
				before disabling their ability to schedule an appointment.
			</td>
		</tr>
		<tr>
			<td>
				<input type='submit' value='Save' name='submit'/>
			</td>
		</tr>
	</table>
	</form>
</div>
</f:view>
</body>
</html>