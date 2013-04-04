package CSAppointmentSchedulerFaces;

import java.sql.*;

public class Database {
	private static final String driver = "com.mysql.jdbc.Driver";

	private static final String connectionString = "jdbc:mysql://mjkuser.db.8687431.hostedresource.com:3306/mjkuser";
	private static final String username = "mjkuser";
	private static final String password = "MJKuser@1";

	private static Statement stmt;
	private static Connection dbConnection = null;
	
	public static Connection connect() 
		throws ClassNotFoundException, SQLException {
		
		try {
			Class.forName(driver).newInstance();
		  	dbConnection = DriverManager.getConnection(connectionString,username,password);
		} catch (Exception e) {
			System.out.println("Error unable to connect to database for CSAppointmentScheduler.");
			System.out.println(e.toString());
			return null;
		}
		return dbConnection;
	}
	
	public static boolean execute(String sql) 
			throws ClassNotFoundException, SQLException {
		if(sql.isEmpty()){
			return false;
		}
		if(dbConnection == null){
			if(Database.connect() == null){
				return false;	
			}
		}
		
		stmt = dbConnection.createStatement();
		int result = stmt.executeUpdate(sql);

		if(result == 0) { //no rows affected meaning nothing got updated or inserted..
			return false;
		} 
		
		return true;
	}
	
	public static ResultSet fetch(String sql) 
			throws ClassNotFoundException, SQLException {
		if(sql.isEmpty()){
			return null;
		}
		if(dbConnection == null){
			if(Database.connect() == null){
				return null;	
			}
		}
		return dbConnection.createStatement().executeQuery(sql);
	}
	
	public static void close() 
			throws ClassNotFoundException, SQLException {
		if(dbConnection != null) {
			dbConnection.close();
		}
		
	}
}
