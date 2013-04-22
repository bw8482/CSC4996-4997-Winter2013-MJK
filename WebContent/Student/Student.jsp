<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import ="CSAppointmentSchedulerFaces.User" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="//wayne.edu/global/css/global-v2.css" rel="stylesheet" type="text/css" media="all" />
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CSC Appointment Scheduler</title>
</head>
<body>
<f:view>
<%
	User user = new User();
	user = User.getUser();
	String headerMenu = user.buildHeaderMenu("student");
	out.println(headerMenu);
	
	int attnd = user.getAttendance(user.getEmail());
	if(user.getAttendance(user.getEmail()) > 0){
		 /*  alert(user.getName() + "  ,you have canceled " + attnd + " times. " ); */
	}
%>

<div id='content'>
	Welcome,  to the <b style='color:#254117;'>Wayne State University</b> Computer Science Appointment Scheduler<br>
	
</div>
</f:view>
</body>
</html>