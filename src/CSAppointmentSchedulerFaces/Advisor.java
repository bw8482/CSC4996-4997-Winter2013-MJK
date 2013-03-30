package CSAppointmentSchedulerFaces;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.*;
import java.util.Hashtable;
//import java.util.Date;

public class Advisor {
	
	public static int timesArrayMilitary[] = {8, 9, 10, 11, 12, 13, 14, 15};
	public static int timesArray[] = {8, 9, 10, 11, 12, 1, 2, 3};
	
	
	public static String getAppointments(String date) throws ClassNotFoundException, SQLException, ParseException {
		Database.connect();
		
		String dateNiceFormat;
		String tbl = "";
		if(date.equals("today") || date == null) {
			date = "= DATE_ADD(CURDATE(), INTERVAL 0 DAY)";
			dateNiceFormat = "today";
		} else if(date.equals("tomorrow")){
			date = "= DATE_ADD(CURDATE(), INTERVAL 1 DAY)";
			dateNiceFormat = "tomorrow";
		} else if(date.equals("week")){
			date = "IS NOT NULL";
			dateNiceFormat = "this week";
		} else {
			dateNiceFormat = "on " + FormatterFactory.dateFormat(date);
			date = "= '" + date + "'";
		}
		
		tbl += ("<table class='tbl'>");
		tbl += ("<th><div>Access ID</div></th>");
		tbl += ("<th><div>Email</div></th>");
		tbl += ("<th><div>Name</div></th>");
		tbl += ("<th><div>Date</div></th>");
		tbl += ("<th><div>Time</div></th>");
		tbl += ("<th><div>Reason</div></th>");
		tbl += ("<th><div style='width: 150px;'>Additional Comments</div></th>");
		
		String sql = "SELECT * FROM APPOINTMENT A JOIN REASON R ON R.REASON_ID = A.REASON LEFT JOIN STUDENT S ON S.EMAIL = A.STUDENT_EMAIL WHERE APPT_DATE  " + date;
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
			tbl += ("<td>" + FormatterFactory.format(rs.getString("ACCESS_ID")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("STUDENT_EMAIL")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.dateFormat(rs.getString("APPT_DATE")) + "</td>");
			tbl += ("<td>" + FormatterFactory.timeFormat(rs.getString("APPT_TIME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("REASON_TEXT")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("COMMENTS")) + "</td>");
			tbl += ("</tr>");
		}
		
		if(x == 0) {
			tbl += ("<tr><td colspan='7'>There are no appointments " + dateNiceFormat + ".</td></tr>");
		}
		tbl += ("</table>");
		return tbl;
	}
	
	public static String getAvailability(String date) throws ClassNotFoundException, SQLException, ParseException {
		String form = "";
		String where = "";
		String colspan = "4";
		String title = "";
		
		if(date == null) {
			where = "DATE IS NULL";
			title = "Update Your Default Availability";
		} else {
			where = "DATE = '" + date + "'";
			colspan = "4";
			title = "Update Your Availability for " + FormatterFactory.dateFormat(date);;
		}
		
		Hashtable<String, String> times = new Hashtable<String, String>();
		Database.connect();
		ResultSet rs = Database.fetch("SELECT * FROM ADVISOR_AVAILABILITY WHERE " + where + " AND ADVISOR_ACCESS_ID = 'ef2558'");
		while(rs.next()){
			times.put(rs.getString("TIME"), rs.getString("AVAILABLE"));
		}
		
		//System.out.println("SELECT * FROM ADVISOR_AVAILABILITY WHERE " + where + " AND ADVISOR_ACCESS_ID = 'ef2558'");
		form += ("<form name='availability' style='position: relative; width: 500px;'>");
		//form += ("<div style='width: 470px; height: 15px; position: relative;'>Update Default Availability <input type='submit' value='Save' style='position: absolute; right: 0px; top: 0px;'/></div>");
		form += ("<div style='height: 20px; border-bottom: 1px solid #000; font-weight: bold; margin-bottom: 5px;'>" + title + "<input type='submit' name='submit' value='Save' style='position: absolute; right: 0px; top: -1px; width: 100px;'/></div>");
		form += ("<table class='tblNoHighlight' style='borer-collapse: collapse; border: 1px solid #aaa; width: 100%;'>");
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
			
			form += ("<tr class='even'>");
			
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
			
			form += ("<td style='width: 70px; border: none;'>" + timesArray[x] + ":00 " + ampm + "</td><td style='border: none; width: 150px;'>" + select + "</td>");
			
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
			
			form += ("<td style='width: 70px; border: none;''>" + timesArray[x + (max)] + ":00 PM</td><td style='border: none; width: 150px;'>" + select + "</td>");
			form += ("</tr>");
			
			form += ("<tr class='odd'>");
			
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
			
			form += ("<td style='width: 70px; border: none;''>" + timesArray[x] + ":30 " + ampm + "</td><td style='border: none; width: 150px;'>" + select + "</td>");
			
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
			
			form += ("<td style='width: 70px; border: none;''>" + timesArray[x + (max)] + ":30 PM</td><td style='border: none; width: 150px;'>" + select + "</td>");
			form += ("</tr>");
		}
		
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
	
	public static boolean updateAvailability_helper(Hashtable<String, String> availability, String timestamp, String date) throws ClassNotFoundException, SQLException {
		String insertDate = "";
		String sql = "";
		if(date == null) {
			insertDate = "0000-0-00";
		} else {
			insertDate = date;
		}
		
		if(availability.containsKey(timestamp)) {
			sql  = "INSERT INTO ADVISOR_AVAILABILITY (ADVISOR_ACCESS_ID, DATE, TIME, AVAILABLE) "; 
			sql += "VALUES ('ef2558', '" + insertDate + "', '" + timestamp + "', " + availability.get(timestamp) + ") "; 
			sql += "ON DUPLICATE KEY ";
			sql += "UPDATE AVAILABLE = " + availability.get(timestamp);

			return Database.execute(sql);
		
		} 
		return true;
	}
	
	public static boolean updateAvailability(String date, Hashtable<String, String> availability ) throws ClassNotFoundException, SQLException {
		String timestamp = "";
		boolean success = true;
		for(int i = 0; i < timesArrayMilitary.length; i++) {
			timestamp = timesArrayMilitary[i] + ":00:00";
			if(!updateAvailability_helper(availability, timestamp, date)) {
				success = false;
			}
			
			timestamp = timesArrayMilitary[i] + ":30:00";
			if(!updateAvailability_helper(availability, timestamp, date)) {
				success = false;
			}
		}
		
		return success;
	}
}