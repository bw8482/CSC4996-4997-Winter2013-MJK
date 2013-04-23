<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="CSAppointmentSchedulerFaces.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
	<%
		
	User user=  new User();
	user.getUser();
	String headerMenu = user.buildHeaderMenu("student");
	out.println(headerMenu);
		
	String email = user.getEmail();
	
		try {
			if(request.getParameter("submit").equals("Cancel")) {
				Student.cancel(request.getParameter("appointment"));
			}
		} catch(Exception ex) {
			
		}
	%>
	
	<div id='content'>
	<div style='font-size: 12px; border-bottom: 1px solid #000; margin-bottom: 5px;'>Your Appointments</div>
	
	<%
		String output =Student.getAppointments(email);
		out.print(output);
	%>
	</div>
</f:view>
</body>
</html>