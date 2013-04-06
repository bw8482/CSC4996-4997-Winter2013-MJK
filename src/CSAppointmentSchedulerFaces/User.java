package CSAppointmentSchedulerFaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

public class User {

	private static String accessId;
	private static String password;
	private static String firstName;
	private static String lastName;
	public static String email;
	
	private boolean WSU;

	public boolean authorized = false;
	public String role = "Student";


	private String error = "";
	private boolean displayError = false;

	private static User user;

	public User() {

	}
	
	public User(String accessID, String password, String firstName, String lastName, String email)
	{
		this.accessId = accessID;
		this.password= password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	public static void setUser() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		User user = new User(accessId, password, firstName, lastName, email);
		fc.getExternalContext().getSessionMap().put("user", user);	
		
	}

	public static User getUser() {
		
		System.out.println("Getting user information....");
		
		try {
		FacesContext fc = FacesContext.getCurrentInstance();
		User user =(User) fc.getExternalContext().getSessionMap().get("user");
		System.out.println(user.toString());
		return user;
		}
		catch (NullPointerException e)
		{
		return null;
		}
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

	public String getPassword()  { 
		String temp = null;
		return temp;	
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
		//header += "<div id='header_title'>";
		//header += "<div id='header_wsu'>WSU</div>";
		//header += "<div id='header_wsu2'>Wayne State University</div>";
		//header += "<div id='header_cs'>Department of Computer Science - Appointment Scheduler</div>";
		header += "<div id='header_logo'><img src='http://localhost:8080/CSC4996-4997-Winter2013-MJK/faces/img/logo.png'></div>";
		header += "<div id='header_title'>Computer Science Department Appointment Scheduler</div>";
		header += "</div>";

		if(role.equals("advisor")){
			header += "<div id='header_user'>";
			header += "Welcome " + getName();
			header += "</div>";
		}
		if(role.equals("student")){
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
				menu += "<a title='Quick look at appointments for this week.' href='Appointments.jsp?date=week'>View This Week's Appointments</a>";
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
				menu += "<a href=title = 'Override Information.jsp'('overrides.html');\">Override Information and Forms</a>";   

				menu += "<a href='Help.jsp'>Help</a>";
				menu += "<a href='../Login.jsp'>Logout</a>";
			}

			menu += "</div>";
		}

		return header + menu;

	}

	
	public String authorized() throws NoSuchAlgorithmException, UnsupportedEncodingException, NamingException, ClassNotFoundException, SQLException {
		
				// Check for valid access ID
		if(accessId.isEmpty() || password.isEmpty()) {
			displayError = true;
			if(accessId.isEmpty() && password.isEmpty()) {
				setError("Please input an Access ID and Password.");
			} else if(accessId.isEmpty()) {
				setError("Please input a Access ID.");
			} else if(password.isEmpty()) {
				setError("Please input a Password.");
			} 

			return "Error";
			}
		
		// Check if user has entered an email address instead of accessID
		else if (accessId.contains("@"))
				{
					System.out.println("This is a email address!");
						int index = accessId.indexOf("@");
						
						// Check for not @wayne.edu
						if (accessId.substring(index+1).compareToIgnoreCase("wayne.edu")!=0)
							
							{
								System.out.println("Your access ID... " + accessId);
								String sql = "SELECT * FROM STUDENT WHERE EMAIL= '" + accessId + "'";
								Database.connect();
								ResultSet rs = Database.fetch(sql);
								
								while (rs.next())
								{
								
								String  encryptpassword = rs.getString("PASSWORD");
								
								System.out.println("Password: " + encryptpassword);
								String 	comparepassword = MD5.MD5(password);
								if (comparepassword.equals(encryptpassword))
								
								{
													
									// Grab user information from database
													
									try {
										email = rs.getString("EMAIL");
									} catch (SQLException e1) {
										
										return "Create Account";
									}
									try {
										firstName =rs.getString("FIRST_NAME");
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
									try {
										lastName =rs.getString("LAST_NAME");
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									
									
									setUser();
									System.out.println("Student Authorized!");
									return "Student Authorized";
								}
								} // End while
								
							}
								
				}
		else // WSU-affiliated login using LDAP
				{
								
										
							System.out.println("you are a WSU student...");
							System.out.println("Your access ID is..."+ accessId);
							// authenticate user through LDAP
							Hashtable ldap = new Hashtable();
							ldap.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
							ldap.put(Context.PROVIDER_URL, "ldap://directory.wayne.edu");
							ldap.put(Context.SECURITY_AUTHENTICATION, "simple");

							String principal = "uid="+ getAccessId() + ", ou=people,dc=wayne,dc=edu";
							ldap.put(Context.SECURITY_PRINCIPAL, principal);
							ldap.put(Context.SECURITY_CREDENTIALS, password);
							
							DirContext ctx = null;
							try {
								ctx = new InitialDirContext(ldap);
							} catch (NamingException e3) {
								// TODO Auto-generated catch block
								e3.printStackTrace();
							}
							authorized = true;
					    
							// Query LDAP for user information
					 		//Create the search controls  
					 							
							SearchControls searchCtls = new SearchControls();
					 		String returnedAtts[]={"givenName","sn","mail"};
					 		searchCtls.setReturningAttributes(returnedAtts);
					 		System.out.println("Successfully set up search Controls");
					 							
					 		//Specify the search scope

					 		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
					 					  
					 		//specify the LDAP search filter
					 		String searchFilter = "(&(objectClass=wayneStudent)(uid=" + accessId+ "))";
					 		//Specify the Base for the search
					 				
					 		String searchBase = "ou=people, dc=wayne, dc=edu";
					 											   
					 		// Search for objects using the filter
					 				
					 		NamingEnumeration answer = null;
							try {
								answer = ctx.search(searchBase, searchFilter, searchCtls);
							} catch (NamingException e2) {
								// TODO Auto-generated catch block
								e2.printStackTrace();
							}
					 							
					 		System.out.println("Retrieving user information...");
					 							
					 			while (answer.hasMoreElements()) {
					 								
					 				SearchResult sr = (SearchResult)answer.next();

					 					System.out.println(">>>" + sr.getName());
					 			
					 					// Get the attributes, throws exception if the attributes have no values
					 								Attributes attrs = sr.getAttributes();
					 					    
					 								if (attrs != null) {
					 				
					 										 firstName = convert(attrs.get("givenName").toString());
					 										 lastName = convert(attrs.get("sn").toString());;
					 										 email = convert(attrs.get("mail").toString());
					 										 /* role = convert(attrs.get("title").toString());  */
					 										 System.out.println("User attributes retrieved.");
					 										 System.out.println(toString());
					 								}
					 						} // end retrieving information
						
					 						
					 								try {
														ctx.close();
													} catch (NamingException e1) {
														// TODO Auto-generated catch block
														e1.printStackTrace();
													} 
					 								
					 								// Check if user has an account in database, if not create
					 								
					 								
					 								try
					 								{
					 									 setUser(); 
					 									addAccount();
					 	
					 								}
					 								catch(Exception e)
					 								{
					 									System.out.println("Account exists..");
					 								}
					 							
					 								
					 	
					
					 								if(accessId.equals("ef2558"))
					 								{
					 									System.out.println("Advisor Authorized!!");	
					 									return "Advisor Authorized";
					 								} 
					 								else {
					 									
					 									System.out.println("Student Authroized!!");	
					 									return "Student Authorized";
					 								}
										} // End WSU login
		return "Error";
		
				}
							
	public void logout() {
		setAccessId(null);
		setPassword(null);
		setError(null);
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
	
	public String toString()
	{
		return "Name: " + firstName + " " + lastName + "\n" +
				"Email : " + email + "\n" +
						"Access ID : " + accessId  +"\n" +
				"Password: " + getPassword() + "\n" +
						"Role: " +role;
					
	}

	public void setFirstName(String firstName) {
		
		this.firstName= firstName;
		
	}
	
	public void setLastName (String lastName) {
	
		this.lastName = lastName;
	}
	
	public void addAccount() throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
		
			if (!role.equals("Advisor"))
			{
				if (!WSU)
				{
				String password = MD5.MD5(this.getPassword());
				}
				else
				{
					password = null;
				}
			
			String sql = "INSERT INTO STUDENT" +
					" (ACCESS_ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)" +
					"VALUES ('"+ accessId + "','" + email + "','" +
					firstName + "','" + lastName + "','" + password + "')";
		
			System.out.println(sql);
				if (sql!=null)
				{
					Database.connect();
					Database.execute(sql);
				}
			} // End student 
		else
					{
				String sql = "INSERT INTO ADVISOR" +
						" (ACCESS_ID, EMAIL, FIRST_NAME, LAST_NAME, PHONE LOCATION)" +
						"VALUES ('"+ accessId + "','" + email + "','" +
						firstName + "','" + lastName + "','" + password + "')";
			
				System.out.println(sql);
			}
	}
	public String convert(String s)
	{
		int index = s.indexOf(':');
		
		return s.substring(index+2);
			
			
	}
}
	
