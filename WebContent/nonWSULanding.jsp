<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f" uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h" uri="http://java.sun.com/jsf/html"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>CS Scheduler</title>
	<script type="text/javascript">
					function check()

					{

						var varname = document.getElementById('fname').value;
						var varlname = document.getElementById('lname').value;
						
						var alphaExp = /^[a-zA-Z]+$/;

						if (varname == '')

						{

							alert('Student first name is required');

						}
						if (varlname == '')

						{

							alert('Student last name is required');

						}

				

						if (document.getElementById('reason').value == '')

						{

							alert('Reason is required');

						}

				
						if (document.getElementById('email').value == '')

						{

							alert('Email is required');

						}
					}
				</script>
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

			<div id="header" style="background-color: #006633; font-size: 20pt">
				<h1 style="margin-bottom: 0; text-align: center;">WSU Computer
					Science Scheduler</h1>
			</div>

			<div id="menu"
				style="background-color: #006633; height: 600px; width: 200px; float: left; font-color: #ccff00;">
				<h3>
					<b><em>WSU Links</em></b>
				</h3>
				<br> <a href="http://www.wayne.edu/" accesskey="W"
					target="_blank">Wayne State University</a><br> <a
					href="http://www.cs.wayne.edu/" accesskey="S" target="_blank">Wayne
					State Computer Science</a><br>

			</div>

			<div id="content"
				style="background-color: #C2FFC2; height: 600px; width: 1200px; float: left;">

				<h2>Welcome, This area may contain general information and
					policy about WSU Computer Science scheduling.</h2>
				<br> 


					<center>
						<h2 style="font-size: x-large">WSU Advising Appointment Form</h2>
					</center>

					<hr>

					<br>

						<form>

							<table>

								<tr>

									<th><label for="name" id="fname">First Name</label></th>

									<th>:</th>

									<td><input type="text" name="name" id="fname"></td>
								</tr>
                                 
                                <tr>

									<th><label for="name" id="lname">Last Name</label></th>

									<th>:</th>

									<td><input type="text" name="name" id="lname"></td>
								</tr>

							

								<tr>

									<th><label for="Reason">Reason</label></th>

									<th>:</th>

									<td><textarea name="reason" id="reason"></textarea></td>

								</tr>


								<tr>

									<th><label for="email">E-Mail</label></th>

									<th>:</th>
									<td><input type="text" name="email" id="email"></td>

								</tr>

								<tr>

									<th><input type="reset" name="name"></th>

									<th>:</th>

									<td><input type="submit" name="name" onclick="check()"></td>

								</tr>
							</table>

						</form>

					
			</div>

			<div id="footer"
				style="background-color: #006633; clear: both; text-align: center;">
				Copyright © Wayne State University 2013</div>

		</div>


	</f:view>
</body>
</html>