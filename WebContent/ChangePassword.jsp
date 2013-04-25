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
	
	try {
		if(request.getParameter("submit").equals("Change")) {
			boolean success = user.changePassword(request.getParameter("password"));		
			if(success) {
				String error = "<div class='success' style='width: 400px;'>You have succesfully changed your password for the WSU CSC Appointment Scheduler.<br/><a href='Login.jsp'>Click</a> here to login with your new password.</div>";
				out.println(error);
			} else {
				out.println("<div class='error'>Unable to change your password.</div>");	
			}
		} else {
			out.println("<div class='error'>Unable to change your password.</div>");	
			out.println("Error not found.");

		}
	} catch (Exception e) {

	}
%>

<div id='validate' style='margin: 8px; color: red; font-weight: bold; font-size: 11px;'>
</div>
<div id='registerBox'>
	<form method='POST' action='ChangePassword.jsp' onsubmit='return validateChangePassword();'>
	<table>
		<tr>
			<td colspan='2' style='font-weight: bold; border-bottom: 1px solid #000; font-size: 11px;'>
				Change Your Password
			</td>
		</tr>
		<tr>
			<td colspan='2' >
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
				<input type='submit' name='submit' value='Change' style='margin-left: 0px;'/> 			
				<input type='submit' name='submit' value='Cancel' onclick='window.location="Login.jsp"; return false;'/>
			</td>
		</tr>
		
	</table>
	</form>
</div>
</f:view>
</body>
</html>