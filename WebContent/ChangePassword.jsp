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
	User user = User.getUser();
	out.println(User.getUser().buildHeaderMenu(""));
%>

<div id='validate' style='padding: 5px; font-size: 11px;'>
<%
	try {
		if(request.getParameter("submit").equals("Register")) {
			boolean success = user.changePassword(request.getParameter("password"));		
			if(success) {
				
				String error = "<div class=''>You have succesfully changed your password for the WSU CSC Appointment Scheduler using email address " + request.getParameter("email") + "<br/><a href='Login.jsp'>Click here</a> to login with your new password.</div>";

				user.setError(error);
				out.println("<div class=''>You have succesfully changed your password for the WSU CSC Appointment Scheduler using email address " + request.getParameter("email") + "<br/><a href='Login.jsp'>Click here</a> to login with your new password.</div>");
			} else {
				out.println("<div class='error'>Unable to register an account.</div>");	
			}
		}
	} catch (Exception e) {
		try {
			if(e.getMessage().startsWith("Duplicate")) {
				out.println("<div class='error'>Error - an account with the email address provided already exists.</div>");
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
				<input type='submit' name='submit' value='Register'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</f:view>
</body>
</html>