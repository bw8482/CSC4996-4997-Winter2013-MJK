<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CS Scheduler Advisor</title>
</head>
<body>
	<f:view>

		<style>
h1 {
	color: #FFD700;
}
h3 {
    color: #FFD700;
}
</style>

		<div id="container" style="width: 1400px">

			<div id="header" style="background-color: #006633;font-size:20pt">
				<h1 style="margin-bottom: 0; text-align: center;">WSU Computer
					Science Scheduler</h1>
			</div>

			<div id="menu"
				style="background-color: #006633; height: 600px; width: 200px; float: left; font-color: #ccff00;">
				<h3>
					<b><em>Todays Appointments</em></b>
				</h3>
				<br> <br> <br> <br>
				<br><br> <br> <br> <br> <br> <br>
				
				<h3>
					<b><em>Advising Links</em></b>
				</h3>
				
				<br> <a href="http://www.wayne.edu/" 
					target="_blank">Wayne State University</a><br> 
					<a href="http://www.cs.wayne.edu/" target="_blank">Wayne
					State Computer Science</a><br>
					<a href="http://www.pipeline.wayne.edu/" target="_blank">Pipeline</a><br>

			</div>

			<div id="content"
				style="background-color: #C2FFC2 ; height: 600px; width: 1200px; float: left;">
				
				<h2>Welcome, This area may contain some basic instructions about how to use the CS Scheduler.</h2>
				<br> <br> <br> <br>
				<br><br> <br> <br> <br> <br> <br>
				<div id="buttons">
				   <center>
					<button type="button"
						style="color: #0000FF;background-color: #006633; font-size: 18pt; height: 100px; width: 300px"
						onclick="alert('View Appointments')"> <h3>View Appointments</h3></button>
					<br>
					<button type="button"
						style="color: #0000FF;background-color: #006633; font-size: 18pt; height: 100px; width: 300px"
						onclick= "alert('Administrators')"> <h3>Administrator</h3></button>
					</center>
				</div>
			</div>

			<div id="footer"
				style="background-color: #006633; clear: both; text-align: center;">
				Copyright © Wayne State University 2013</div>

		</div>


	</f:view>
</body>
</html>