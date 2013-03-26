<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Advisor" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	User user;
	user = User.getUser();
	String headerMenu = user.buildHeaderMenu("advisor");
	out.println(headerMenu);
%>
<div id='content'>
<%

	String date = null;
	try {
		date = request.getParameter("date");
	} catch(Exception ex) {
		date = null;
	} finally {
		if(date == null){
			date = "today";
		}
	}
	
	//Database.connect();

	String output = Advisor.getAppointments(date);
	out.println(output);

%>
</div>
</f:view>
</body>
</html>