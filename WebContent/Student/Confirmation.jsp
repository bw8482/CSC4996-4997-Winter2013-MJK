<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="java.sql.ResultSet" %>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<%@ page import ="CSAppointmentSchedulerFaces.Student" %>
<%@ page import ="CSAppointmentSchedulerFaces.Database"%>
<%@ page import ="CSAppointmentSchedulerFaces.FormatterFactory" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />

<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	String email = User.getUser().getEmail();	
	out.println(User.getUser().buildHeaderMenu("student"));
	
	Database.connect();
%>
<div id='content'>	
	<%
	
	try {
		out.println(request.getParameter("submit"));
		if(request.getParameter("submit").equals("Submit")) 
		{
			boolean success = Student.scheduleAppointment(email, request.getParameter("advisor"), request.getParameter("date"), request.getParameter("time"), request.getParameter("reason"), request.getParameter("standing"), request.getParameter("standing"), "1");
			if(success) {
				out.println("<div class='success'>You have succesfully scheduled an appointment.</div>");
				String sql = "SELECT * FROM ADVISOR, REASON WHERE ACCESS_ID = '" + 
					request.getParameter("advisor") + "' AND REASON_ID = " + request.getParameter("reason");
				out.println(sql);
				ResultSet rs = Database.fetch(sql);
				out.println("<div style='margin-top: 5px;'>");
				
				out.println("Your appointment is scheduled on <b>" + FormatterFactory.dateFormat(FormatterFactory.dateFormat2(request.getParameter("date"))));
				
				if(rs.next()) {
					out.println("</b> with " + rs.getString("FIRST_NAME") + " " + rs.getString("LAST_NAME") + ".");
					
					out.println("<br/>");
					out.println("--");
					out.println("<br/>");
					out.println("Per the advisor's policy: you can <u>only</u> cancel the appointment <b>" + 
						rs.getString("CANCEL_WINDOW") + "</b> hours before the scheduled date for the appointment. You are only permitted to cancel a total of <b>" + 
						rs.getString("CANCEL_ALLOWED") + "</b> appointments or miss a total of <b>" + rs.getString("CANCEL_ALLOWED") + 
						"</b> appointments before your eligibilty to schedule an appointment is <u>revoked</u>.");
					
					out.println("<br/>");
					out.println("<br/>");
					if(!rs.getString("REASON_FEEDBACK").isEmpty()) {
						out.println("Note: " + rs.getString("REASON_TEXT") + " - " + rs.getString("REASON_FEEDBACK"));
					}

				}
				
				out.println("<br/>");
				out.println("<br/>");
				out.println("For more information regarding your appointment and for future references, refer to your appointments which can be access by clicking on 'View Your Appointments' in the menu above or by clicking <a href='Appointments.jsp'>here</a>.");
				
				out.println("</div>");
				
				
			} else {
				out.println("<div class='error'>There was an error while scheduling your appointment.</div>");	
			}
		}
	} catch (Exception ex) {
		try {
			if(ex.getMessage().startsWith("Duplicate")) {
				out.println("<div class='error'>Error - you already have an appointment scheduled on " + request.getParameter("date") + ".</div>");
			}
		} catch(Exception e) {
			
		}
	}
	%>
</div>
</f:view>
</body>
</html>