<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="f"  uri="http://java.sun.com/jsf/core"%>
<%@ taglib prefix="h"  uri="http://java.sun.com/jsf/html"%>
<%@ page import="CSAppointmentSchedulerFaces.Database" %>
<%@ page import="java.sql.ResultSet" %>
<%
	try {
		String date = request.getParameter("date");
		String advisor = request.getParameter("advisor");
		
		String 	sql = "";
		sql += " SELECT  	TIME ";
		sql += "	FROM    ADVISOR_AVAILABILITY AV";
		sql += "	WHERE   ADVISOR_ACCESS_ID = '" + advisor + "'";
		sql += "		    AND DATE IS NULL";
		sql += "	        AND AVAILABLE = 1";
		sql += "		    AND NOT EXISTS (";
		sql += "	            SELECT 1 ";
		sql += "	            FROM    APPOINTMENT A";
		sql += "	            WHERE   A.ADVISOR_ACCESS_ID = AV.ADVISOR_ACCESS_ID";
		sql += "	                    AND A.APPT_TIME = AV.TIME";
		sql += "	                    AND A.APPT_DATE = '" + date + "'";
		sql += "                 	AND CANCELLED = 0";
		sql += "       	)";
		sql += "       	AND NOT EXISTS (";
		sql += "           SELECT 1 ";
		sql += "            FROM    ADVISOR_AVAILABILITY AV2";
		sql += "            WHERE   AV2.ADVISOR_ACCESS_ID = AV.ADVISOR_ACCESS_ID";
		sql += "                    AND AV2.TIME = AV.TIME";
		sql += "	                   AND AV2.DATE = '" + date + "'";
		sql += "                    AND AVAILABLE = 0";
		sql += "   		)";
		
		Database.connect();
		ResultSet rs = Database.fetch(sql);
		
		String select = "<select name='time'>";
		while(rs.next()) {
			select += "<option value='" + rs.getString("TIME") + "'>" + rs.getString("TIME") + "</option>";
		}
		
		select += "</select>";
		
		out.println(select);
	} catch(Exception ex) {
		out.println(ex);
	}
%>