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
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	User user;
	user = User.getUser();
	String headerMenu = user.buildHeaderMenu("");
	out.println(headerMenu);
%>
<div id='loginBox'>
	<h:form>
		<table>
			<tr>
				<td>Access ID</td>
				<td><h:inputText value="#{user.accessId}"></h:inputText></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><h:inputSecret value="#{user.password}"></h:inputSecret></td>
			</tr>
			<tr>
				<td></td>
				<td>Not a WSU student? <a href="Register.jsp">Click here</a> to register to use the CSC Appointment Scheduler.</td>
			</tr>
			<tr>
				<td></td>
				<td><h:commandButton action="#{user.authorized}" value="Login"></h:commandButton></td>
			</tr>
			<tr>
				<td></td>
				<td style='padding: 1px; padding-top: 5px;'><h:outputLabel style='font-weight: bold; color: red;' rendered="#{user.displayError == true}" value="#{user.error}"></h:outputLabel></td>
			</tr>
		</table>
	</h:form>
</div>
</f:view>
</body>
</html>