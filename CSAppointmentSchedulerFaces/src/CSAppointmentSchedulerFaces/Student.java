package CSAppointmentSchedulerFaces;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Student {
	
	
	public static String getAppointments() throws ClassNotFoundException, SQLException, ParseException {
		Database.connect();
		
		String tbl="";
		
		tbl += ("<table class='tbl'>");
		tbl += ("<th><div>Date</div></th>");
		tbl += ("<th><div>Time</div></th>");
		tbl += ("<th><div>Reason</div></th>");
		tbl += ("<th><div>Comments</div></th>");
		tbl += ("<th><div>Location</div></th>");
		tbl += ("<th><div>Advisor E-mail</div></th>");
		tbl += ("<th><div>Avisor Name</div></th>");
		
		
		String sql = "SELECT * FROM APPOINTMENT A JOIN REASON R ON R.REASON_ID = A.REASON LEFT JOIN ADVISOR AD ON AD.ACCESS_ID = A.ADVISOR_ACCESS_ID WHERE STUDENT_EMAIL = 'ad7893'";
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
			tbl += ("<td>" + FormatterFactory.dateFormat(rs.getString("APPT_DATE")) + "</td>");
			tbl += ("<td>" + FormatterFactory.timeFormat(rs.getString("APPT_TIME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("REASON_TEXT")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("COMMENTS")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("LOCATION")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("ADVISOR_EMAIL")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + "</td>");
			tbl += ("</tr>");
		}
		
		if(x == 0) {
			tbl += ("<tr><td colspan='7'>There are no appointments to display.</td></tr>");
		}
		tbl += ("</table>");
		return tbl;
	}

	public static boolean scheduleAppointment(String advisor, String day, String time, String reason, String major ) throws ClassNotFoundException, SQLException{
		System.out.println("hello world");
	
		Database.connect();
		
		String sql = "";
		sql  = "INSERT INTO APPOINTMENT (EMAIL, ADVISOR_ACCESS_ID, APPT_DATE, APPT_TIME) VALUES('ad7893', '" + advisor + "', '" + day + "', '" + time + "')";
		System.out.println(sql);
		if(Database.execute(sql)){
			System.out.println("Success");
			return true;
		}
		System.out.println("false");
		
		return false;
		
	}
}

