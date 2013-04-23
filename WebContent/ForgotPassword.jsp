<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.Password" %>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page language="java" import="java.util.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="css/Header.css" rel="stylesheet" type="text/css">
<LINK href="css/General.css" rel="stylesheet" type="text/css">
<LINK href="css/Login.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript'>
	function validateRegistration() {
		
		var div = document.getElementById("validate");
		
		var error = "";
		var email = document.getElementById("email").value;
		
		if(email == "") {
			error += "Enter an Email address.<br/>"
		}
		if(error != "") {
			error = "<div class='error' style=''>" + error + "</div>";
			div.innerHTML = error;
			return false;
		}
		
		return true;
	}
</script>

<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
User user = new User();
String headerMenu = user.buildHeaderMenu("");
out.println(headerMenu);
%>

<div id='registerBox'>
	<form method='POST' onsubmit='return validateRegistration();'>
	<table>
		<tr>
			<td colspan='2' style='font-weight: bold; border-bottom: 1px solid #000; font-size: 11px;'>Forgot your Password?</td>
		</tr>
		<tr>
			<td colspan='2' >
				Email Address<br/>
				<span style='font-size: 12px;'>
					Please enter your email address to request your password.
				</span>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='text' name='email' id='email' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='submit' name='submit' value='Submit'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>

<div id='validate' style='padding: 5px; font-size: 11px;'>
<%
	try {
		if(request.getParameter("submit").equals("Submit")) {
			user.setEmail(request.getParameter("email"));
			boolean success=user.forgotPassword();
			if(success) {
				out.println("<div class=''>You have succesfully changed your password." + request.getParameter("email") + "<br/><a href='Login.jsp'>Click here</a> to login and start using your account.</div>");
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

</f:view>
</body>
</html>