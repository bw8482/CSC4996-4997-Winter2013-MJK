<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Student" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="css/Header.css" rel="stylesheet" type="text/css">
<LINK href="css/General.css" rel="stylesheet" type="text/css">

<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="css/Login.css" rel="stylesheet" type="text/css">

<script type="text/javascript" src="../js/general.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	out.println(User.getUser().buildHeaderMenu(""));
%>

<div id='validate' style='padding: 8px; padding-bottom: 2px; font-size: 11px; color: red;'>
<%
	try {
		if(request.getParameter("submit").equals("Register")) {
			boolean success = Student.register(request.getParameter("email"), request.getParameter("firstName"), request.getParameter("lastName"), request.getParameter("password"));
			if(success) {
				out.println("<div class='success' style='width: 400px;'>You have succesfully registered an account for the WSU CSC Appointment Scheduler using email address " + request.getParameter("email") + "<br/><a href='Login.jsp'>Click here</a> to login and start using your account.</div>");
			} else {
				out.println("<div class='error' style='width: 300px;'>There seems to be an error - we were unable to register an account.</div>");	
			}
		}
	} catch (Exception e) {
		try {
			if(e.getMessage().startsWith("Duplicate")) {
				out.println("<div class='error' style='width: 300px;'>Error - an account with the email address provided already exists.</div>");
			}
		} catch(Exception e2) {
			
		}		
	}
%>
</div>
<div id='registerBox'>
	<form method='POST' onsubmit='return validateRegistration();'>
	<table>
		<tr>
			<td colspan='2' style='font-weight: bold; border-bottom: 1px solid #000; font-size: 11px;'>Register an Account</td>
		</tr>
		<tr>
			<td colspan='2' >
				Email Address<br/>
				<span style='font-size: 10px;'>
					Email address will be used to send confirmations and reminders and cannot be changed once registered.
				</span>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='text' name='email' id='email' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td>
				First Name
			</td>
			<td>
				Last Name
			</td>
		</tr>
		<tr>
			<td style='width: 200px;'>
				<input type='text' name='firstName' id='firstName' style='width: 200px;'/>
			</td>
			<td>
				<input type='text' name='lastName' id='lastName' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				--<br/>
				Password
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='password' name='password' id='password' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				Confirm Password
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='password' name='confirmPassword' id='confirmPassword' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='submit' name='submit' value='Register' style='margin-left: 0px;'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</f:view>
</body>
</html>