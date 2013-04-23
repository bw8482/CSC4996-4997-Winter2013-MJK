<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Password" %>
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

		var password = document.getElementById("password").value;
		var confirmPassword = document.getElementById("confirmPassword").value;
		

		if(password == "") {
			error += "Enter a password.<br/>"
		}
		

		if(password != confirmPassword) {
			error += "Your passwords do not match.<br/>"
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
String email = user.getUser().getEmail();
String headerMenu = user.buildHeaderMenu("");
out.println(headerMenu);
%>

<div id='validate' style='padding: 5px; font-size: 11px;'>
<%
{
			out.println("Hey you are" + user.getEmail());
			user.setPassword(request.getParameter("password"));
			/** if(success) {
			out.println("<div class=''>You have succesfully changed your password for the WSU CSC Appointment Scheduler using email address " + user.getEmail() + "<br/><a href='Login.jsp'>Click here to continue to your account</a>.</div>");
			} else {
				out.println("<div class='error'>Password cannot be changed.</div>");	
			} */
		

	}
%>
</div>
<div id='registerBox'>
	<form method='POST' onsubmit='return validateRegistration();'>
	<table>
		<tr>
			<td colspan='2' style='font-weight: bold; border-bottom: 1px solid #000; font-size: 11px;'> Please Change your Password</td>
		</tr>
		<tr>
			<td colspan='2' >
				<br/>
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
				<input type='submit' name='submit' value='Change Password'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</f:view>
</body>
</html>