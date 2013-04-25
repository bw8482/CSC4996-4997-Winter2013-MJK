package CSAppointmentSchedulerFaces;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Hashtable;

import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Advisor {
	
	public static String timesArrayMilitary[] = {"08", "09", "10", "11", "12", "13", "14", "15"};
	public static int timesArray[] = {8, 9, 10, 11, 12, 1, 2, 3};
	
	public static String getAppointments(String date, String accessId) throws ClassNotFoundException, SQLException, ParseException {
		Database.connect();
		String hiddenDate = date;
		String dateNiceFormat;
		String tbl = "";
		
		boolean showTitle = false;
		if(date.equals("today") || date == null) {
			date = "= DATE_ADD(CURDATE(), INTERVAL 0 DAY)";
			dateNiceFormat = "Today";
		} else if(date.equals("tomorrow")){
			date = "= DATE_ADD(CURDATE(), INTERVAL 1 DAY)";
			dateNiceFormat = "tomorrow";
		} else if(date.equals("week")){
			//date = "> DATE_SUB(NOW(), INTERVAL 1 WEEK)";
			date = "BETWEEN DATE_ADD(CURDATE(), INTERVAL(1-DAYOFWEEK(CURDATE())) DAY) AND DATE_ADD(CURDATE(), INTERVAL(7-DAYOFWEEK(CURDATE())) DAY)";
			dateNiceFormat = "This Week";
		} else {
			dateNiceFormat = "on " + FormatterFactory.dateFormat(date);
			date = "= '" + date + "'";
			showTitle = true;
		}
		
		String title = "Appointments " + dateNiceFormat;
		
		tbl += ("<div style='text-align: right;'>");
		tbl += ("<button onclick='checkAll(); return false;'>Check All</button>");
		tbl += ("<button onclick='unCheckAll(); return false;'>Un-Check All</button>");
		tbl += ("</div>");
		tbl += ("<form name='appointments' style='position: relative'>");

		tbl += ("<div style='margin-top: 10px; border-bottom: 1px solid #000; font-weight: normal; margin-bottom: 5px; position: relative;'>");
		
		if(showTitle) { tbl += title; } else { tbl += "&nbsp;";}
		
		tbl += ("<input type='submit' name='submit' value='Send Reminders' style='position: absolute; right: 0px; top: -7px; width: 100px;'/>");
		tbl += ("<input type='submit' name='submit' value='Cancel' style='position: absolute; right: 101px; top: -7px; width: 80px;'/>");
		tbl += ("<input type='submit' name='submit' value='Mark No Show' style='position: absolute; right: 182px; top: -7px; width: 110px;'/>");
		//tbl += ("<input type='submit' name='submit' value='Check All' style='position: absolute; right: 283px; top: -7px; width: 100px;'/>");
		//tbl += ("<input type='submit' name='submit' value='Un-Check All' style='position: absolute; right: 401px; top: -7px; width: 100px;'/>");

		
		tbl += ("</div>");
		
		tbl += ("<table class='tbl'>");
		tbl += ("<th style='width: 30px;'><div>&nbsp;</div></th>");
		tbl += ("<th style='width: 130px;'><div>Email</div></th>");
		tbl += ("<th style='width: 130px;'><div>Name</div></th>");
		tbl += ("<th style='width: 120px;'><div>Major</div></th>");
		tbl += ("<th style='width: 60px;'><div>Standing</div></th>");
		tbl += ("<th style='width: 120px;'><div>Date</div></th>");
		tbl += ("<th style='width: 60px;'><div>Time</div></th>");
		tbl += ("<th style='width: 100px;'><div>Reason</div></th>");
		tbl += ("<th style='width: 120px;'><div>System Comments</div></th>");
		//tbl += ("<th style='width: 30px;'><div style='width: 150px;'>Additional Comments</div></th>");
		
		
		String sql = "SELECT";
		sql += " APPT_ID,";
		sql += " STUDENT_EMAIL,";
		sql += " REASON_TEXT,";
		sql += " APPT_DATE,";
		sql += " APPT_TIME,";
		sql += " MAJOR_TEXT,";
		sql += " FIRST_NAME,";
		sql += " LAST_NAME,";
		sql += " CURRENT_STANDING,";
		sql += " CANCELLED,";
		sql += " ATTENDANCE";
		sql += " FROM APPOINTMENT A";
		sql += " LEFT JOIN REASON R ON R.REASON_ID = A.REASON";
		sql += " LEFT JOIN MAJOR M ON M.MAJOR_ID = A.MAJOR";
		sql += " LEFT JOIN STUDENT S ON S.EMAIL = A.STUDENT_EMAIL";
		sql += " WHERE APPT_DATE  " + date;
		sql += " AND ADVISOR_ACCESS_ID = '" + accessId + "'";
		
		ResultSet rs = Database.fetch(sql);
		int x = 0;
		String rowClass = "";
		while(rs.next()){
			if(++x % 2 == 0) {
				rowClass = "even";
			} else {
				rowClass = "odd";
			}
			
			tbl += ("<tr class='" + rowClass + "'>");
			tbl += ("<td style='text-align: center;'>" + "<input type='checkbox' name='appointments' value='" + rs.getString("APPT_ID") + "'/>" + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("STUDENT_EMAIL")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("MAJOR_TEXT")) + "</td>");
			String currentStanding = "";
			if(rs.getInt("CURRENT_STANDING") == 1) {
				currentStanding = "Freshmen";
			} else if(rs.getInt("CURRENT_STANDING") == 2) {
				currentStanding = "Sophomore";
			} else if(rs.getInt("CURRENT_STANDING") == 3) {
				currentStanding = "Junior";
			} else if(rs.getInt("CURRENT_STANDING") == 4) {
				currentStanding = "Senior";
			} 
			
			tbl += ("<td>" + currentStanding + "</td>");
			
			tbl += ("<td>" + FormatterFactory.dateFormat(rs.getString("APPT_DATE")) + "</td>");
			tbl += ("<td>" + FormatterFactory.timeFormat(rs.getString("APPT_TIME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("REASON_TEXT")) + "</td>");
			
			String comments = "<span style='font-size: 10px;'>";
			
			/*if(rs.getInt("FIRST_APPT") == 1) {
				comments += "<span>First Appointment<br/></span>";
			}
			*/
			if(rs.getInt("CANCELLED") == 1) {
				comments += "<span style='color: red;'>Cancelled<br/></span>";
			}
			if(rs.getInt("ATTENDANCE") == 0) {
				comments += "<span style='color: red;'>No Show<br/></span>";
			}
			comments += "</span>";
			
			tbl  += ("<td>" + comments + "</td>");
			tbl += ("</tr>");
		}
		
		if(x == 0) {
			tbl += ("<tr><td colspan='9'>There are no appointments to display.</td></tr>");
		}
		tbl += ("</table>");
		tbl += ("<input type='hidden' name='date' value='" + hiddenDate + "'/>");
		tbl += ("</form>");
		return tbl;
	}
	
	public static String getAvailability(String date, String accessId) throws ClassNotFoundException, SQLException, ParseException {
		String form = "";
		String where = "";
		String title = "";
		
		boolean showTitle = false;
		if(date == null) {
			where = "DATE IS NULL";
			showTitle = false;
		} else {
			where = "DATE = '" + date + "'";
			title = "Update Your Availability for " + FormatterFactory.dateFormat(date);
			showTitle = true;
		}
		
		Hashtable<String, String> times = new Hashtable<String, String>();
		Database.connect();
		ResultSet rs = Database.fetch("SELECT * FROM ADVISOR_AVAILABILITY WHERE " + where + " AND ADVISOR_ACCESS_ID = '" + accessId + "'");
		while(rs.next()){
			times.put(rs.getString("TIME"), rs.getString("AVAILABLE"));
		}
		
		//form += ("<div style='margin-top: 10px; border-bottom: 1px solid #000; font-weight: normal; margin-bottom: 5px; position: relative;'>" + title + "<input type='submit' name='submit' value='Save' style='position: absolute; right: 0px; top: -7px; width: 100px;'/></div>");
		form += ("<table class='' style='border-collapse: collapse;'>");
		form += ("<form name='availability' style='position: relative; width: 478px;'>");
		
		if(showTitle) {
			form += ("<tr style='background-color: transparent;'>");
			form += ("<td colspan='4' style='border-bottom: 1px solid #000;'>" + title + "</td>");
			form += ("<tr>");
		}
		//if(date == null) form += ("<th colspan='3'><div style='text-align: left; height: 20px;'>Update Default Availability</div></th>");
		//form += ("<th colspan='" + colspan + "'><div style='text-align: right; height: 20px;'><input style='margin: 0px;' type='submit' name='submit' value='Save'/></div></th>");
		int max = 4;
		String ampm = "";
		String select = "";
		for(int x = 0; x < max; x++) {
			if(x <= 3) {
				ampm = "AM";
			} else {
				ampm = "PM";
			}
			
			form += ("<tr style='background-color: transparent;'>");
			
			String timestamp = "";
			String selected  = "";
			
			timestamp = timesArrayMilitary[x]  + ":00:00";
			if(times.containsKey(timestamp)) {
				if(times.get(timestamp).equals("0")) {
					selected = "selected=selected";
				}
			}
			
			select = "<select name='" + timestamp + "'>";
			select += "<option value='1'>Available</option>";
			select += "<option value='0' " + selected + ">Not Available</option>";	
			select += "</select>";
			
			form += ("<td style='width: 50px; border: none; padding-top: 5px;'>" + timesArray[x] + ":00 " + ampm + "</td><td style='padding-top: 5px; border: none; width: 150px;'><div class='styled-select'>" + select + "</div></td>");
			
			timestamp = timesArrayMilitary[x + max]  + ":00:00";
			selected  = "";
			if(times.containsKey(timestamp)) {
				if(times.get(timestamp).equals("0")) {
					selected = "selected=selected";
				}
			}
			
			select = "<select name='" + timestamp + "'>";
			select += "<option value='1'>Available</option>";
			select += "<option value='0' " + selected + ">Not Available</option>";	
			select += "</select>";
			
			form += ("<td style='width: 50px; border: none; padding-top: 5px; padding-left: 5px;'>" + timesArray[x + (max)] + ":00 PM</td><td style='padding-top: 5px; border: none; width: 150px;'><div class='styled-select'>" + select + "</div></td>");
			form += ("</tr>");
			
			form += ("<tr style='background-color: transparent;'>");
			
			timestamp = timesArrayMilitary[x]  + ":30:00";
			selected  = "";
			if(times.containsKey(timestamp)) {
				if(times.get(timestamp).equals("0")) {
					selected = "selected=selected";
				}
			}
			
			select = "<select name='" + timestamp + "'>";
			select += "<option value='1'>Available</option>";
			select += "<option value='0' " + selected + ">Not Available</option>";	
			select += "</select>";
			
			form += ("<td style='width: 50px; border: none; padding-top: 5px;'>" + timesArray[x] + ":30 " + ampm + "</td><td style='padding-top: 5px; border: none; width: 150px;'><div class='styled-select'>" + select + "</div></td>");
			
			timestamp = timesArrayMilitary[x + max]  + ":30:00";
			selected  = "";
			if(times.containsKey(timestamp)) {
				if(times.get(timestamp).equals("0")) {
					selected = "selected=selected";
				}
			}
			
			select = "<select name='" + timestamp + "'>";
			select += "<option value='1'>Available</option>";
			select += "<option value='0' " + selected + ">Not Available</option>";	
			select += "</select>";
			
			form += ("<td style='width: 50px; border: none; padding-top: 5px; padding-left: 5px;'>" + timesArray[x + (max)] + ":30 PM</td><td style='padding-top: 5px; border: none; width: 150px;'><div class='styled-select'>" + select + "</div></td>");
			form += ("</tr>");
		}
		
		form += ("<tr style='background-color: transparent;'><td colspan='4' style='text-align: right;'><input type='submit' name='submit' value='Save'/></td></tr>");

		form += ("</table>");
		
		
		if(date != null) {
			String dateArray[] = date.split("-");
			form += ("<input type='hidden' name='iYear' value='" + dateArray[0] + "'/>");
			form += ("<input type='hidden' name='iMonth' value='" + (Integer.parseInt(dateArray[1]) - 1) + "'/>");
			form += ("<input type='hidden' name='iDay' value='" + dateArray[2] + "'/>");
			form += ("<input type='hidden' name='method' value='availability'/>");
		}

		form += ("</form>");
		return form;
	}
	
	public static boolean updateAvailability_helper(Hashtable<String, String> availability, String timestamp, String date, String accessId) throws ClassNotFoundException, SQLException {
		String insertDate = "";
		String sql = "";
		if(date == null) {
			insertDate = "0000-0-00";
		} else {
			insertDate = date;
		}
		
		if(availability.containsKey(timestamp)) {
			sql  = "INSERT INTO ADVISOR_AVAILABILITY (ADVISOR_ACCESS_ID, DATE, TIME, AVAILABLE) "; 
			sql += "VALUES ('" + accessId + "', '" + insertDate + "', '" + timestamp + "', " + availability.get(timestamp) + ") "; 
			sql += "ON DUPLICATE KEY ";
			sql += "UPDATE AVAILABLE = " + availability.get(timestamp);

			return Database.execute(sql);
		
		} 
		return true;
	}
	
	public static boolean updateAvailability(String date, Hashtable<String, String> availability, String accessId ) throws ClassNotFoundException, SQLException {
		String timestamp = "";
		boolean success = true;
		for(int i = 0; i < timesArrayMilitary.length; i++) {
			timestamp = timesArrayMilitary[i] + ":00:00";
			if(!updateAvailability_helper(availability, timestamp, date, accessId)) {
				success = false;
			}
			
			timestamp = timesArrayMilitary[i] + ":30:00";
			if(!updateAvailability_helper(availability, timestamp, date, accessId)) {
				success = false;
			}
		}
		
		return success;
	}
	
	public static boolean updatePersonalInfo(String accessId, String location, String phone, String cancelAllowed, String noShowAllowed, String cancelWindow) throws ClassNotFoundException, SQLException {
		String sql = "UPDATE ADVISOR SET";
		sql += " LOCATION = '" + location.replace("'", "''") + "',";
		sql += " PHONE = '" + phone + "',";
		sql += " NO_SHOW_ALLOWED = " + noShowAllowed + ",";
		sql += " CANCEL_ALLOWED = " + cancelAllowed + ",";
		sql += " CANCEL_WINDOW = " + cancelWindow + "";
		sql += " WHERE ACCESS_ID = '" + accessId + "'";
		
		Database.connect();
		if(Database.execute(sql)) {
			return true;
		} else {
			return false;
		}
	}

	public static Hashtable<String, String> getPersonalInfo(String accessId) throws ClassNotFoundException, SQLException {
		Hashtable<String, String> info = new Hashtable<String, String>();
		Database.connect();
		String sql = "SELECT * FROM ADVISOR WHERE ACCESS_ID = '" + accessId + "'";
		ResultSet rs = Database.fetch(sql);
		if(rs.next()) {
			info.put("LOCATION", rs.getString("LOCATION"));
			info.put("PHONE", rs.getString("PHONE"));
			info.put("FIRST_NAME", rs.getString("FIRST_NAME"));
			info.put("LAST_NAME", rs.getString("LAST_NAME"));
			info.put("ADVISOR_EMAIL", rs.getString("ADVISOR_EMAIL"));
			info.put("NO_SHOW_ALLOWED", rs.getString("NO_SHOW_ALLOWED"));
			info.put("CANCEL_ALLOWED", rs.getString("CANCEL_ALLOWED"));
			info.put("CANCEL_WINDOW", rs.getString("CANCEL_WINDOW"));
		} 
		
		return info;
	}

	public static boolean markNoShow(String[] appointments) throws ClassNotFoundException, SQLException {
		String appointmentIn = "";
		for(int i = 0; i < appointments.length; i++) {
			if(!appointmentIn.isEmpty()) {
				appointmentIn += ", ";
			}
			
			appointmentIn += appointments[i];
		}
		
		String sql = "UPDATE APPOINTMENT SET ATTENDANCE = 0 WHERE APPT_ID IN (" + appointmentIn + ")";
		if(Database.execute(sql)) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean cancel(String[] appointments) throws ClassNotFoundException, SQLException, ParseException {
		/*String appointmentIn = "";
		for(int i = 0; i < appointments.length; i++) {
			if(!appointmentIn.isEmpty()) {
				appointmentIn += ", ";
			}
			
			appointmentIn += appointments[i];
		}
		
		String sql = "UPDATE APPOINTMENT SET CANCELLED = 1 WHERE APPT_ID IN (" + appointmentIn + ")";
		if(Database.execute(sql)) {
			return true;
		} else {
			return false;
		}
		*/
		
		String sql = "";
		String sql2 = "";

		for(int i = 0; i < appointments.length; i++) {
			sql = "SELECT 1 FROM EMAIL_SENT WHERE APPT_ID = " + appointments[i] + " AND TYPE = 'CANCEL'";
			ResultSet rs = Database.fetch(sql);
			if(rs.next()) {
				continue;
			} else {
				sql = "INSERT INTO EMAIL_SENT (APPT_ID, TYPE, DATE_SENT) VALUES(" + appointments[i] + ", 'CANCEL', CURDATE())"; 
				sql2 = "UPDATE APPOINTMENT SET CANCELLED = 1 WHERE APPT_ID = " + appointments[i];
				if(Database.execute(sql) && Database.execute(sql2)) {
					sql = "SELECT";
					sql += " APPT_DATE,";
					sql += " APPT_TIME,";
					sql += " REASON_TEXT,";
					sql += " ADVISOR_EMAIL,";
					sql += " AD.FIRST_NAME,";
					sql += " AD.LAST_NAME,";
					sql += " S.EMAIL,";
					sql += " LOCATION,";
					sql += " PHONE,";
					sql += " APPT_ID,";
					sql += " CANCELLED,";
					sql += " CANCEL_WINDOW";
					sql += " FROM APPOINTMENT A ";
					sql += " LEFT JOIN REASON R ON R.REASON_ID = A.REASON";
					sql += " LEFT JOIN ADVISOR AD ON AD.ACCESS_ID = A.ADVISOR_ACCESS_ID";
					sql += " LEFT JOIN STUDENT S ON S.EMAIL = A.STUDENT_EMAIL";
					sql += " WHERE APPT_ID = '" + appointments[i] + "'";
			
					rs = Database.fetch(sql);
					if(rs.next()) {
						String message = "This is a notice that your appointment " +
								"with " + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + 
								" for " + FormatterFactory.format(rs.getString("REASON_TEXT")) + " on " +
								FormatterFactory.dateFormat(rs.getString("APPT_DATE")) + " at " + 
								FormatterFactory.timeFormat(rs.getString("APPT_TIME")) + " has been cancelled.";
						String to = rs.getString("EMAIL");
						to = "ef2558@wayne.edu";
			
						if(sendEmailGmail(to, message)) {
							
						}
					} else {

					}
					
				} 
			}
		}
		return true;
	}
	
	
	public static boolean sendEmailGmail(String to, String messageStr) {
		System.out.println("Got here");
		 Properties props = new Properties();  
	        props.put("mail.smtp.host", "smtp.gmail.com");  
	        props.put("mail.smtp.socketFactory.port", "465");  
	        props.put("mail.smtp.socketFactory.class",  
	                "javax.net.ssl.SSLSocketFactory");  
	        props.put("mail.smtp.auth", "true");  
	        props.put("mail.smtp.port", "465");  
	        System.out.println("Got here 2");
	        Session session = null;
	        try {
	        	session = Session.getDefaultInstance(props,  
	    	            new javax.mail.Authenticator() {  
	                protected PasswordAuthentication getPasswordAuthentication() {  
	                    return new PasswordAuthentication("cscappointmentscheduler","cscappointmentsadmin");  
	                }  
	            });  
	        
	        } catch(Exception ex){
	        	System.out.println("Error " + ex.getMessage());
	        }
	        System.out.println("Got here 3");
	        try {  
	
	            Message message = new MimeMessage(session);  
	            message.setFrom(new InternetAddress("cscappointmentscheduler@gmail.com"));  
	            System.out.println("Got here 4");
	            message.setRecipients(Message.RecipientType.TO,  
	                    InternetAddress.parse(to));  
	            message.setSubject("CSC Appointment Scheduler");  
	            message.setText(messageStr);  
	            System.out.println("Got here 5");
	            System.out.println(messageStr);
				System.out.println(to);
				
	            Transport.send(message);  
	   
	            System.out.println("Email Sent!");  
	   
	        } catch (MessagingException e) {  
	        	System.out.println("Error");
	            throw new RuntimeException(e);  
	        } 
	      
		return true;
	}
	
	public static int sendReminders(String[] appointments) throws ClassNotFoundException, SQLException, ParseException {
		String sql = "";

		int totalSent = 0;
		for(int i = 0; i < appointments.length; i++) {
			sql = "SELECT 1 FROM EMAIL_SENT WHERE APPT_ID = " + appointments[i] + " AND TYPE = 'REMINDER'";
			ResultSet rs = Database.fetch(sql);
			if(rs.next()) {
				continue;
			} else {
				sql = "INSERT INTO EMAIL_SENT (APPT_ID, TYPE, DATE_SENT) VALUES(" + appointments[i] + ", 'REMINDER', CURDATE())"; 
				if(Database.execute(sql)) {
					sql = "SELECT";
					sql += " APPT_DATE,";
					sql += " APPT_TIME,";
					sql += " REASON_TEXT,";
					sql += " ADVISOR_EMAIL,";
					sql += " AD.FIRST_NAME,";
					sql += " AD.LAST_NAME,";
					sql += " S.EMAIL,";
					sql += " LOCATION,";
					sql += " PHONE,";
					sql += " APPT_ID,";
					sql += " CANCELLED,";
					sql += " CANCEL_WINDOW";
					sql += " FROM APPOINTMENT A ";
					sql += " LEFT JOIN REASON R ON R.REASON_ID = A.REASON";
					sql += " LEFT JOIN ADVISOR AD ON AD.ACCESS_ID = A.ADVISOR_ACCESS_ID";
					sql += " LEFT JOIN STUDENT S ON S.EMAIL = A.STUDENT_EMAIL";
					sql += " WHERE APPT_ID = '" + appointments[i] + "'";
			
					rs = Database.fetch(sql);
					if(rs.next()) {

						String message = "This is a reminder that you have an appointment " +
								"with " + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + 
								" for " + FormatterFactory.format(rs.getString("REASON_TEXT")) + " on " +
								FormatterFactory.dateFormat(rs.getString("APPT_DATE")) + " at " + 
								FormatterFactory.timeFormat(rs.getString("APPT_TIME")) + ".";
						String to = rs.getString("EMAIL");
						to = "ef2558@wayne.edu";
	
						if(sendEmailGmail(to, message)) {
							totalSent++;
						}
					} else {

					}
					
				} 
			}
		}
		
		return totalSent;
	}
}