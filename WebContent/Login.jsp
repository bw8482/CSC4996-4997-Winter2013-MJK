<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="css/General.css" rel="stylesheet" type="text/css">
<LINK href="css/Login.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<div id ='header'>
	<div id='header_wsu'>WSU</div>
	<div id='header_wsu2'>Wayne State University</div>
	<div id='header_cs'>Department of Computer Science - Appointment Scheduler</div>
</div>
<f:view>
	<h:form>
		<table>
			<tr>
				<td>Access ID</td>
				<td><h:inputText value="#{user.accessID}"></h:inputText></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><h:inputSecret value="#{user.password}"></h:inputSecret></td>
			</tr>
			<tr>
				<td></td>
				<td>Not a WSU student? <a href="Appointment.jsp">Click here</a> to schedule an appointment.</td>
			</tr>
			<tr>
				<td></td>
				<td><h:commandButton action="#{user.login}" value="Login"></h:commandButton></td>
			</tr>
			<tr>
				<td></td>
				<td><h:outputLabel style='border: 1px solid #FF4848; background-color: #FF7575; padding: 3px;' rendered="#{ldap.displayError == true}" value="#{ldap.error}"></h:outputLabel></td>
			</tr>
		</table>
	</h:form>
</f:view>
</body>
</html>