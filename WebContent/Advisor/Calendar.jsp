<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%!
public int nullIntconv(String inv) {   
    int conv = 0; 
    try {
        conv = Integer.parseInt(inv);
    } catch(Exception e){
    	
    } finally {
    	
    }
    
    return conv;
}
%>
<% 
int iYear = nullIntconv(request.getParameter("iYear"));
int iMonth = nullIntconv(request.getParameter("iMonth"));
int iDay = nullIntconv(request.getParameter("iDay"));
String method = "";
try {
	method = request.getParameter("method");
} catch( Exception e) {
	method = "availability";
} finally {
	if(method == null) {
		method = "availability";
	}
}

Calendar ca = new GregorianCalendar();
int iTDay = ca.get(Calendar.DATE);
int iTYear = ca.get(Calendar.YEAR);
int iTMonth = ca.get(Calendar.MONTH);

if(iYear == 0) {
     iYear = iTYear;
}
if(iMonth == 0) {
	iMonth = iTMonth;
}
if(iDay == 0 && iMonth == iTMonth) {
    iDay = iTDay;
} else if(iDay == 0) {
	iDay = 1;
}

GregorianCalendar cal = new GregorianCalendar (iYear, iMonth, 1); 

int days = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
int weekStartDay = cal.get(Calendar.DAY_OF_WEEK);

cal = new GregorianCalendar (iYear, iMonth, days); 
int iTotalweeks = cal.get(Calendar.WEEK_OF_MONTH);

String monthString = new DateFormatSymbols().getMonths()[iMonth];
String prevMonth = "";
String nextMonth = "";
String months[] = {"Jan", "Feb", "Mar", "Apr", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

if(iMonth == 0) {
	prevMonth = months[11];
} else {
	prevMonth = months[iMonth - 1];
}

if(iMonth == 11) {
	nextMonth = months[0];
} else {
	nextMonth = months[iMonth + 1];
}
  
%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="CSAppointmentSchedulerFaces.User" %>
<%@ page import="CSAppointmentSchedulerFaces.Advisor" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<LINK href="../css/General.css" rel="stylesheet" type="text/css">
<LINK href="../css/Header.css" rel="stylesheet" type="text/css">
<LINK href="../css/Table.css" rel="stylesheet" type="text/css">
<LINK href="../css/Calendar.css" rel="stylesheet" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<script>
var year = <%=iYear%>;
var month = <%=iMonth%>;
function changeCal(){
  document.calendar.submit();
}
function prevMonth() {
	if(document.getElementById("iMonth").value == "0") {
		month = 11;
		year = year - 1;
	} else {
		month = month - 1;
	}
	
	document.getElementById("iMonth").value = month;
	document.getElementById("iYear").value = year;
	document.getElementById("iDay").value = 0;
	changeCal();
}
function nextMonth(){
	if(document.getElementById("iMonth").value == "11") {
		month = 0;
		year = year + 1;
	} else {
		month = month + 1;
	}
	
	document.getElementById("iMonth").value = month;
	document.getElementById("iYear").value = year;
	document.getElementById("iDay").value = 0;
	changeCal();
}
function selectDay(day, method){
	document.getElementById("iDay").value = day;
	document.getElementById("method").value = method ? method : "availability";
	changeCal();
}
</script>
<title>CSC Appointment Scheduler</title>
</head>
<body>
<%
	User user;
	user = User.getUser();
	String headerMenu = user.buildHeaderMenu("advisor");
	out.println(headerMenu);
%>
<div id='content'>
	<div id='calendar'>
		<form name='calendar'>
		<input type='hidden' name='iYear' id='iYear' value='<%=iYear%>'>
		<input type='hidden' name='iMonth' id='iMonth' value='<%=iMonth%>'>
		<input type='hidden' name='iDay' id='iDay' value='<%=iDay%>'>
		<input type='hidden' name='method' id='method' value='<%=method%>'>
		<table class='calendar'>
	      	<tr class='months'>
	      		<td onclick='prevMonth();' colspan='1' style='text-align: left; cursor: pointer; text-decoration: underline;'><%=prevMonth%></td>
	      		<td colspan='5' style='text-align: center; font-size: 13px;'>
	      			<%=monthString%> <%=iYear%>
	      			<!-- <span style='display: block; font-size: 10px; text-decoration: underline; font-weight: bold; cursor: pointer;'>Today</span> -->
	      		</td>
	      		<td onclick='nextMonth();' colspan='1' style='text-align: right; cursor: pointer; text-decoration: underline;'><%=nextMonth%></td>
	      	</tr>
	        <tr class='weekday'>
	          <td>Sun</td>
	          <td>Mon</td>
	          <td>Tue</td>
	          <td>Wed</td>
	          <td>Thu</td>
	          <td>Fri</td>
	          <td>Sat</td>
	        </tr>
<%
	        int cnt = 1;
	        for(int i=1; i <= iTotalweeks; i++) {
	        	out.print("<tr class='days'>"); 
	            for(int j=1;j<=7;j++) {
	                if(cnt < weekStartDay || (cnt - weekStartDay + 1) > days) {
	                	out.print("<td class='noday'>&nbsp;</td>"); 
	                }
	                else {
	                	String className;
	                	if(iDay == (cnt - weekStartDay + 1)) {
	                		className = "selectedDay";
	                	} else {
	                		className = "monthDay";
	                	}
	                	
	                	String availabilityBtn = "<div onclick='selectDay(" +  (cnt - weekStartDay + 1) + ", \"availability\");' class='availability_btn' title='Update availability for day'></div>";
	                	String appointmentBtn = "<div onclick='selectDay(" +  (cnt - weekStartDay + 1) + ", \"appointment\");' class='appointment_btn' title='View appointments / send reminders for day'></div>";
	                	String dayText = "<div class='day_text'>" +  (cnt - weekStartDay + 1) + "</div>";
	                	String td = "<td class='" + className + "' id='day_" + (cnt - weekStartDay + 1) + "'>";
	                	td += "<div class='container'>";
		                	td += availabilityBtn;
		                	td += appointmentBtn;
		                	td += dayText;
		                	td += "</td>";
	                	td += "</div>";
	                	out.println(td);
	                }
	                cnt++;
	              }
	            out.print("</tr>");
	        }
%>
	    </table>
	    </form>
	    <div>
	    	<table>
	    		<tr>
	    			<td><div class='availability_btn'>&nbsp;</div></td>
	    			<td>Update availability for day</td>
	    		</tr>
	    		<tr>
	    			<td><div class='appointment_btn'>&nbsp;</div></td>
	    			<td>View appointments / send reminders for day</td>
	    		</tr>
	    	</table>
	    </div>
	    
    	<%
	    	if(method != null) {
	    		String date = null;
				try {
					date = iYear + "-" + (iMonth + 1) + "-" + iDay;
				} catch(Exception ex) {
					date = null;
				} 
				
			
				if(method.equals("appointment")) {
	    			out.println("<div style='position: relative; border-top: 2px solid #e4e4e4; width: 807px; padding-top: 5px; padding-bottom: 5px; font-weight: bold;'>");
	    			out.println("Appointments on " + monthString + " " + iDay + ", " + iYear);
	    			out.println("<button onclick='alert();' style='position: absolute; top: 0px; right: 0px;'>Send Reminders</button>");
	    			out.println("</div>");

	    			String output = Advisor.getAppointments(date);
	    			out.println(output);
	    		} else if(method.equals("availability")) {
	    			/*
	    			* To Do: When updating availabilty on another month...bug
	    			*/
	    			//out.println("<div style='position: relative; border-top: 2px solid #e4e4e4; width: 807px; padding-top: 5px; padding-bottom: 5px; font-weight: bold;'>");
	    			//out.println("Update Availability for " + monthString + " " + iDay + ", " + iYear);
	    			//out.println("</div>");
	    					
	    			try {
	    				if(request.getParameter("submit").equals("Save")){
	    					Hashtable<String, String> availability = new Hashtable<String, String>();
	    					Map<String, String[]> parameters = request.getParameterMap();
	    					for(String parameter : parameters.keySet()) {
	    					    if(parameter.toLowerCase().contains(":")) {
	    					    	availability.put(parameter, request.getParameter(parameter) );
	    					    }
	    					}

	    					if((Advisor.updateAvailability(date, availability))) {
	    						out.println("<div class='success'>Successfully updated your availabilty.</div>");
	    					} else {
	    						out.println("<div class='error'>An error occured while updating your availabilty.</div>");
	    					}
	    				}
	    			} catch(Exception e) {
	    				//out.println("Exception: " + e.getMessage());
	    				//out.println(request.getParameter("submit"));
	    			}
	    			String form = Advisor.getAvailability(date);
	    			out.print(form);		
	    		}
	    	}   	
    	%>
	</div>
</div>
</body>
</html>