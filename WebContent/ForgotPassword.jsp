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

<script type="text/javascript" src="../js/general.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	User user = User.getUser();
	String headerMenu = user.buildHeaderMenu("");
	out.println(headerMenu);
%>
<div id='validate' style='padding: 5px; padding-bottom: 2px; font-size: 11px;'>
<%
	try {
		if(request.getParameter("submit").equals("Submit")) {
			user.setEmail(request.getParameter("email"));
			boolean success=user.forgotPassword();
			if(success) {
				out.println("<div class='success' style='width: 500px;'>You have succesfully changed your password for account " + request.getParameter("email") + "<br/>Your new temporary password has been emailedd to you. <a href='Login.jsp'>Click here</a> to login and start using your account.</div>");
			} else {
				out.println("<div class='error'>There seems to be an error - we were unable to change your password.</div>");	
			}
		}
	} catch (Exception e) {
		try {
			if(e.getMessage().startsWith("Duplicate")) {
				out.println("<div class='error'>There seems to be an error - we were unable to change your password.</div>");
			}
		} catch(Exception e2) {
			
		}		
	}
%>
</div>
<div id='registerBox'>
	<form method='POST' onsubmit='return validateForgotPassword();'>
	<table>
		<tr>
			<td colspan='2' style='font-weight: bold; border-bottom: 1px solid #000; font-size: 11px;'>Forgot your Password?</td>
		</tr>
		<tr>
			<td colspan='2' >
				<span style='font-size: 11px;'>
					Please enter your email address to request your password. <br> If you are a <b>Wayne State Student</b>, please <a href="https://webmail.wayne.edu/am/cgi/pw_reset">click here</a> to change your password.
					<br/>
					<br/>
				</span>
				Email Address
			</td>
		</tr>
		<tr>
			<td colspan='2' >
				<input type='text' name='email' id='email' style='width: 200px;'/>
			</td>
		</tr>
		<tr>
			<td colspan='2' style='''>
				<input type='submit' name='submit' value='Submit' style='margin-left: 0px;'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</f:view>
</body>
</html>