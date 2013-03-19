<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Computer Science Scheduler</title>
</head>
<body>
	<div id ='header'>
	<div id='header_wsu'>WSU</div>
	<div id='header_wsu2'>Wayne State University</div>
	<div id='header_cs'>Department of Computer Science - Appointment Scheduler</div>
	</div>
	<div style='font-size: 10px;'>
	<h1> Welcome to the Wayne State University - Department of Computer Science Appointment Scheduler. Today is </h1>
	</div>
	<div class='info'>To schedule or view an appointment, please login.</div>
<f:view>
	<h:form>
		<h:panelGrid columns= "2">
		<h:outputLabel value="Access ID"></h:outputLabel>
		<h:inputText value="#{user.accessID}"/>
		<h:outputLabel value="Password"></h:outputLabel>
		<h:inputSecret value="#{user.password}"></h:inputSecret>
		
		<h:commandButton action="#{user.login}" value="Login"></h:commandButton>
		
		</h:panelGrid>
		
		<h1> Not a WSU student? </h1>
		</h:form>
	
</f:view>
</body>
</html>


