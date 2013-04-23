<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="java.util.Hashtable" %>
<%@ page import="java.util.Map" %>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Advisor" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />

<script type="text/javascript" src="../js/general.js"></script>

<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	String accessId = User.getUser().getAccessId();
	out.println(User.getUser().buildHeaderMenu("advisor"));
%>
<div id='content'>
	<div class='title'>
		Update Your Default Availability
	</div>
<%
	
	try {
		if(request.getParameter("submit").equals("Save")){
			Hashtable<String, String> availability = new Hashtable<String, String>();
			Map<String, String[]> parameters = request.getParameterMap();
			for(String parameter : parameters.keySet()) {
			    if(parameter.toLowerCase().contains(":")) {
			    	availability.put(parameter, request.getParameter(parameter) );
			    }
			}

			if((Advisor.updateAvailability(null, availability, accessId))) {
				out.println("<div class='success'>Successfully updated your availabilty.</div>");
			} else {
				out.println("<div class='error'>An error occured while updating your availabilty.</div>");
			}
		}
	} catch(Exception e) {
		//out.println("Exception: " + e.getMessage());
	}
	String form = Advisor.getAvailability(null, accessId);
	out.print(form);
%>
</div>
</f:view>
</body>
</html>