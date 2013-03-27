package CSAppointmentSchedulerFaces;

import java.util.*;

import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;

public class User {

	private String accessId;
	private String password;

	public String firstName;
	public String lastName;
	public String email;
	
	public boolean authorized = false;
	public String role = "Student";
	

	private String error = "";
	private boolean displayError = false;
	
	private static User user;

	public User() {
		
	}

	public static void setUser() {
		user = new User();
	}
	
	public static User getUser() {
		if(user == null) {
			setUser();
		}
	
		return user;
	}
	
	public void setAccessId(String _accessId) {
		this.accessId = _accessId;
	}
	
	public String getAccessId() { 
		return accessId;
	}
	
	public void setName(String _firstName, String _lastName) {
		this.firstName = _firstName;
		this.lastName = _lastName;
	}
	
	public String getName() {
		if(firstName != null && lastName != null) {
			return firstName + " " + lastName;
		} else {
			return null;
		}
		
	}
	
	public void setEmail(String _email) {
		this.email = _email;
	}
	
	public String getEmail() { 
		return email;
	}
	
	public void setPassword (String password) {
		this.password = password;
	}
	
	public String getPassword() { 
		return password;
	}
	
	public boolean getDisplayError() {
		return displayError;
	}
	
	public String getRole() { 
		return role;
	}
	
	public void setRole(String _role) {
		this.role = _role;
	}
	
	public void setError (String _error) {
		this.error = _error;
		if(_error == null){
			this.displayError = false;
		} else {
			this.displayError = true;
		}
	}
	
	public String getError() { 
		return error;
	}
	
	public String buildHeaderMenu(String role) {
		String header = "<div id ='header'>";
		header += "<div id='header_title'>";
		header += "<div id='header_wsu'>WSU</div>";
		header += "<div id='header_wsu2'>Wayne State University</div>";
		header += "<div id='header_cs'>Department of Computer Science - Appointment Scheduler</div>";
		header += "</div>";
		
		if(role.equals("advisor")){
			header += "<div id='header_user'>";
			header += "Welcome " + getName();
			header += "</div>";
		}
		
		header += "</div>";
		
		String menu = "";
		System.out.println(authorized);
		if(!role.isEmpty()) {
			menu += "<div id='menu'>";
			if(role.equals("advisor")) {
				menu += "<a href='Advisor.jsp'>Home</a>";
				menu += "<hr/>";
				menu += "<a title='Quick look at appointments for today.' href='Appointments.jsp?date=today'>View Today's Appointments</a>";
				menu += "<a title='Quick look at appointments for tomorrow.' href='Appointments.jsp?date=tomorrow'>View Tomorrow's Appointments</a>";
				menu += "<a title='Search apointments by date, student and/or reason.' href='SearchAppointments.jsp'>Search Appointments</a>";
				menu += "<hr/>";
				menu += "<a title='Use the calendar to update your availability for a specific date, view appointments on a specific date and send reminders for appointments.' href='Calendar.jsp'>Calendar</a>";
				menu += "<a title='Update your default availability.' href='Availability.jsp'>Update Availability</a>";
				menu += "<a title ='Update your personal information (ie. phone, location).' href='PersonalInfo.jsp'>Update Personal Information</a>";
				menu += "<hr/>";
				menu += "<a href='Help.jsp'>Help</a>";
				menu += "<a href='../Login.jsp'>Logout</a>";
			} else if(role.equals("student")){
				menu += "<a href='Student.jsp'>Home</a>";
				menu += "<hr/>";
				menu += "<a title='View all your appointments.' href='Appointments.jsp'>View Your Appointments</a>";
				menu += "<a title='Scheudle an appointment.' href='ScheduleAppoinment.jsp'>Schedule an Appointment</a>";
				menu += "<hr/>";
				//menu += "<a href=\"JavaScript:newPopup('http://localhost:8080/CSAppointmentSchedulerFaces/Student/overrides.html');\">Override Information and Forms</a>";   

				menu += "<a href='Help.jsp'>Help</a>";
				menu += "<a href='../Login.jsp'>Logout</a>";
			}
		
			menu += "</div>";
		}
		
		return header + menu;
		
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
	public String authorized() throws Exception {
		setUser();
		if(getAccessId().isEmpty() || getPassword().isEmpty()) {
			displayError = true;
			if(getAccessId().isEmpty() && getPassword().isEmpty()) {
				setError("Please input an Access ID and Password.");
			} else if(getAccessId().isEmpty()) {
				setError("Please input a Access ID.");
			} else if(getPassword().isEmpty()) {
				setError("Please input a Password.");
			} 
			
			return "Error";
		}
		
		//System.out.println("Got Here");
		//System.out.println("Access Id " + getAccessId());
		//System.out.println("Password " + getPassword());
		
		Hashtable ldap = new Hashtable();
		ldap.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		ldap.put(Context.PROVIDER_URL, "ldap://directory.wayne.edu");
		ldap.put(Context.SECURITY_AUTHENTICATION, "simple");
		
		String principal = "uid="+ getAccessId() + ", ou=people,dc=wayne,dc=edu";
		ldap.put(Context.SECURITY_PRINCIPAL, principal);
		ldap.put(Context.SECURITY_CREDENTIALS, getPassword());	
		
		
		
		
		
		
		
		
		
		
		
		

		try {
			DirContext ctx = new InitialDirContext(ldap);
		    authorized = true;
		    if(getAccessId().equals("ef2558")) {
		    	return "Advisor Authorized";
		    } else {
		    	
		    	// Query LDAP for user information
		    	
		    	
		    	
		    	return "Student Authorized";
		    }
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
			displayError = true;
			setError("Invalid Access ID and/or password.");
			return "Error";
		}
	}
	

	public void logout() {
		setAccessId(null);
		setPassword(null);
		setError(null);
	}
}