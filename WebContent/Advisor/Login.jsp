<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="css/Header.css" rel="stylesheet" type="text/css">
<LINK href="css/General.css" rel="stylesheet" type="text/css">
<LINK href="css/Login.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script type='text/javascript'>
	window.onload = function() {
		resize();
	};
	
	window.onresize = function() {
		resize();
	};
	
	function resize(){
		var w = 0;var h = 0;
		if(!window.innerWidth){
		    if(!(document.documentElement.clientWidth == 0)){
		        w = document.documentElement.clientWidth;h = document.documentElement.clientHeight;
		    } else{
		        w = document.body.clientWidth;h = document.body.clientHeight;
		    }
		} else {
		    w = window.innerWidth;h = window.innerHeight;
		}
		
		var loginBox = document.getElementById("loginBox");
		loginBox.style.position = 'fixed';
		loginBox.style.left = (w / 2) - (450 / 2) + 'px';
		//return {width:w,height:h};
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

<div id='loginBox'>
	<h:form>
		<table>
			<tr>
				<td colspan='2'>
					<h:outputLabel style='font-weight: bold; font-size: 12px; color: red;' rendered="#{user.displayError == true}" value="#{user.error}"></h:outputLabel>
				</td>
			</tr>
			<tr>
				<td><img src='img/user.png'/> WSU Access ID or Email</td>
				<td><img src='img/password.png'/> Password</td>
			</tr>
			<tr>
				<td><h:inputText value="#{user.accessId}" style=''></h:inputText></td>
				<td><h:inputSecret value="#{user.password}"></h:inputSecret></td>
			</tr>
			<tr>
				<td colspan='2' style='font-size: 10px;'>Not a WSU Student? <a href='Register.jsp'>Click here</a> to register an account to use <br/>the CSC Appointment Scheduler.</td>
			</tr>
			<tr>
				<td></td>
				<td style='text-align: right;'><h:commandButton action="#{user.authorized}" value="Login"></h:commandButton></td>
			</tr>
		</table>
	</h:form>
</div>
</f:view>
</body>
</html>