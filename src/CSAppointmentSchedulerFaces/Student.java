package CSAppointmentSchedulerFaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class Student extends User {
	
	/** Create default student account */
	public Student()
	{
		super.setAccessId(null);
		super.setFirstName(null);
		super.setLastName(null);
		super.setEmail(null);
	}
	
	/** Create student account */
	public Student(String accessId, String password, String firstName, String lastName, String email)
	{
		super.setAccessId(accessId);
		super.setFirstName(firstName);
		super.setLastName(lastName);
		super.setEmail(email);
	}
		
	public static String getAppointments(String email) throws ClassNotFoundException, SQLException, ParseException {
		Database.connect();
		
		String tbl="";
		
		tbl += ("<table class='tbl'>");
		tbl += ("<th style='width: 120px;'><div>Date</div></th>");
		tbl += ("<th style='width: 60px;'><div>Time</div></th>");
		tbl += ("<th style='width: 100px;'><div>Reason</div></th>");
		tbl += ("<th style='width: 130px;'><div>Advisor E-mail</div></th>");
		tbl += ("<th style='width: 120px;'><div>Avisor Name</div></th>");
		tbl += ("<th style='width: 100px;'><div>Advisor Phone</div></th>");
		tbl += ("<th style='width: 120px;'><div>Location</div></th>");
		tbl += ("<th style='width: 100px;'><div>&nbsp;</div></th>");
		
		
		String sql = "SELECT";
				sql += " APPT_DATE,";
				sql += " APPT_TIME,";
				sql += " REASON_TEXT,";
				sql += " ADVISOR_EMAIL,";
				sql += " FIRST_NAME,";
				sql += " LAST_NAME,";
				sql += " LOCATION,";
				sql += " PHONE,";
				sql += " APPT_ID,";
				sql += " CANCELLED,";
				sql += " CANCEL_WINDOW,";
				sql += "CASE WHEN STR_TO_DATE(CONCAT(DATE_FORMAT(APPT_DATE, '%m/%d/%Y')   , ' ', APPT_TIME), '%m/%d/%Y %H:%i:%s') >";
						sql += "DATE_ADD(SYSDATE(), INTERVAL CANCEL_WINDOW HOUR)";
								sql += "THEN 'TRUE' ELSE 'FALSE' END AS CANCEL_ALLOWED";
			sql += " FROM APPOINTMENT A ";
			sql += " LEFT JOIN REASON R ON R.REASON_ID = A.REASON";
			sql += " LEFT JOIN ADVISOR AD ON AD.ACCESS_ID = A.ADVISOR_ACCESS_ID";
			sql += " WHERE STUDENT_EMAIL = '" + email + "'";
	
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
			tbl += ("<td>" + FormatterFactory.format(rs.getString("ADVISOR_EMAIL")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("FIRST_NAME")) + " " + FormatterFactory.format(rs.getString("LAST_NAME")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("PHONE")) + "</td>");
			tbl += ("<td>" + FormatterFactory.format(rs.getString("LOCATION")) + "</td>");
			
			if(rs.getInt("CANCELLED") == 1) {
				tbl += ("<td style='text-align: center; color: red; font-size: 10px;'>Cancelled</td>");
			} else {
				tbl += ("<td style='text-align: center;'>");
				tbl += ("<form>");
				tbl += ("<input type='hidden' name='appointment' value='" + rs.getString("APPT_ID") + "'>");
				
				String disabled = "";
				if(rs.getString("CANCEL_ALLOWED").equals("FALSE")){
					disabled = "disabled='disabled'";
				}
				tbl += ("<input type='submit' name='submit' " + disabled + " value='Cancel'/>");
				tbl += ("</form>");
				tbl += ("</td>");
			}
			tbl += ("</tr>");
		}
		
		if(x == 0) {
			tbl += ("<tr><td colspan='7'>There are no appointments to display.</td></tr>");
		}
		tbl += ("</table>");
		return tbl;
	}

	public static boolean scheduleAppointment(String email, String advisor, String date, String time, String reason, String standing, String major, String firstTime) throws ClassNotFoundException, SQLException, ParseException{
		Database.connect();
		
		String sql = "";
		sql  = "INSERT INTO APPOINTMENT (STUDENT_EMAIL, ADVISOR_ACCESS_ID, APPT_DATE, APPT_TIME, REASON, CURRENT_STANDING, MAJOR)";
		sql += "VALUES('" + email +"', '" + advisor + "', '" + FormatterFactory.dateFormat2(date) + "', '" + time + "', " + reason + ", " + standing + ", " + major + ")";
		
		System.out.println(sql);
		if(Database.execute(sql)){
			return true;
		}
		return false;
	}
	
	public static boolean cancel(String apptId) throws ClassNotFoundException, SQLException{
		Database.connect();
		
		String sql = "";
		sql  = "UPDATE APPOINTMENT SET CANCELLED = 1, CANCELLED_BY_STUDENT = 1 WHERE APPT_ID = " + apptId;
		System.out.println(sql);
		if(Database.execute(sql)){
			return true;
		}
	
		return false;
		
	}
	
	public static boolean register(String email, String firstName, String lastName, String password) throws ClassNotFoundException, SQLException {
		Database.connect();
		
		 try {
			String encryptedPassword = Password.MD5(password);
		
			String sql = "";
			sql  = "INSERT INTO STUDENT (EMAIL, FIRST_NAME, LAST_NAME, PASSWORD, CHANGEPASSWORD)";
			sql += "VALUES('" + email.replace("'", "''") + "', '" + firstName.replace("'", "''")  + "', '" + lastName.replace("'", "''")  + "', '" + encryptedPassword  + "',"+ false + ")";

			if(Database.execute(sql)){
				return true;
			}
			
		 } catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return false;
	}

	/** Add student account 
	 * @return 
	 * @throws SQLException 
	 * @throws ClassNotFoundException **/
	public static boolean add(String accessId, String email, String firstName,
			String lastName) throws ClassNotFoundException, SQLException {
			
		Database.connect();
		
		String sql = "";
		sql  = "INSERT INTO STUDENT (ACCESS_ID, EMAIL, FIRST_NAME, LAST_NAME)";
		sql += "VALUES('" + accessId.replace("'", "''") + "', '" + email.replace("'","'") + "','"+ firstName.replace("'", "''")  + "', '" + lastName.replace("'", "''")  + "')";
		if(Database.execute(sql)){	
			return true;
		}
		return false;
	}
		
}
	




