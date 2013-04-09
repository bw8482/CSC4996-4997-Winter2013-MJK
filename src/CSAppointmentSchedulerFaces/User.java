package CSAppointmentSchedulerFaces;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.faces.context.FacesContext;
import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

/** Controls & Stores access to user  information 
 * 
 * @author Jacqueline D. Brown, aw4025@wayne.edu
 *
 */

public class User {

	private static String accessId;
	private static String password;
	private static String firstName;
	private static String lastName;
	private static String email;
	private static String role = "Student";
	
	public boolean authorized = false;
	private String error = "";
	private boolean displayError = false;

	/** Default constructor */
	public User() {

	}
	
	/** Create a new user with the following parameters
	 * 
	 * @param accessID = the accessID of the user
	 * @param password = the password of the user
	 * @param firstName = the user's first name
	 * @param lastName = the user's last name
	 * @param email = the user's email address
	 */
	public User(String accessID, String password, String firstName, String lastName, String email)
	{
		this.accessId = accessID;
		this.password= password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
	}

	/** Save user session
	 * 
	 */
	public static void setUser() {
		
		FacesContext fc = FacesContext.getCurrentInstance();
		User user = new User(accessId, password, firstName, lastName, email);
		fc.getExternalContext().getSessionMap().put("user", user);	
		
	}

	/** Get current user using session */
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

	/** Set accessId of user */
	public void setAccessId(String _accessId) {
		this.accessId = _accessId;
	}

	/** Get user accessId */
	public String getAccessId() { 
		return accessId;
	}

	/** Returns user first & Last name */
	public String getName() {
		if(firstName != null && lastName != null) {
			return firstName + " " + lastName;
		} else {
			return null;
		}

	}
	
	/** Set user email address */
	public void setEmail(String _email) {
		this.email = _email;
	}

	/** Get user email address */
	public String getEmail() { 
		return email;
	}

	/** Set user password  */
	public void setPassword (String password)  {
		this.password= password;
	}

	/** Get user password */
	public String getPassword() { 
		
		return password;
	}
	
	public boolean getDisplayError() {
		return displayError;
	}

	/** Get user role */
	public String getRole() { 
		return role;
	}

	/** Set user role */
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
		
		int index = 0;
		
		// Check if user has entered an email address instead of accessID
			if (accessId.contains("@"))
				{
					System.out.println("This is a email address!");
						
					index = accessId.indexOf("@");
						// Check for not @wayne.edu
						
						if (accessId.substring(index+1).compareToIgnoreCase("wayne.edu")!=0)
						{
							 if (NonWSULogin())
							 {
								 return "Student Authorized";
							 }
						}
				}
							
			else
				{
					index = accessId.indexOf("@");
					if (index!=0 && index!=-1)
					{
					accessId = accessId.substring(0,index-1);
					}
				
					if (WSULogin())
					{
						return "Student Authorized";
					}	
				}
			return "Error";
	}
					
		
	
	
	/** Log user out of system 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException */
	public void logout() throws NoSuchAlgorithmException, UnsupportedEncodingException {
		setAccessId(null);
		setPassword(null);
		setError(null);
	}

	/** Get user first name */
	public String getFirstName() {
		return firstName;
	}

	/** Get user last name */
	public String getLastName() {
		return lastName;
	}
	
	/** Print user information as string */
	public String toString()
	{
		try {
			return "Name: " + firstName + " " + lastName + "\n" +
					"Email : " + email + "\n" +
							"Access ID : " + accessId  +"\n" +
					"Password: " + MD5.MD5(password) + "\n" +
							"Role: " +role;
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
					
	}

	/** Set user first name */
	public void setFirstName(String firstName) {
		
		this.firstName= firstName;
		
	}
	
	/** Set user last name */
	public void setLastName (String lastName) {
	
		this.lastName = lastName;
	}
	
	/** Add user account to database 
	 * @param type */
	private void addAccount(String type) throws ClassNotFoundException, SQLException, NoSuchAlgorithmException, UnsupportedEncodingException
	{
			String encryptpassword;
		
			if (type.equals("WSU"))
			{
				encryptpassword = "";
			}
			else
			{
				encryptpassword = MD5.MD5(password);
			}
			
			if (!role.equals("Advisor"))
			{
				
			
				String sql = "INSERT INTO STUDENT" +
					" (ACCESS_ID, EMAIL, FIRST_NAME, LAST_NAME, PASSWORD)" +
					"VALUES ('"+ accessId + "','" + email + "','" +
					firstName + "','" + lastName + "','" + encryptpassword + "')";
		
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
	
	/** Convert attributes properly */
	private  String convert(String s)
	{
		int index = s.indexOf(':');
		
		return s.substring(index+2);
			
			
	}
	
	/** Returns true if login for WSU student is succesfull, false otherwise
	 * 
	 * @return true is login is sucessfull adds WSU account into the database
	 * @throws NamingException 
	 * 
	 */
	private boolean WSULogin() throws NamingException
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
	
		ctx = new InitialDirContext(ldap);
		
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
		answer = ctx.search(searchBase, searchFilter, searchCtls);
	
 							
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
 									addAccount("WSU");
 	
 								}
 								catch(Exception e)
 								{
 									System.out.println("Account exists..");
 								}
 							
 								
 	

 								if(accessId.equals("ef2558"))
 								{
 									role = "Advisor";
 									return true;
 								} 
 								else {
 									
 									role = "Student";	
 									return true;
 								}
					} // End WSU login
	
	private boolean NonWSULogin() throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, ClassNotFoundException
	{

		System.out.println("Your access ID... " + accessId);
		String sql = "SELECT * FROM STUDENT WHERE EMAIL= '" + accessId + "'";
				Database.connect();
				ResultSet rs = Database.fetch(sql);
				
				while (rs.next())
				{
				
				String  encryptpassword = rs.getString("PASSWORD");
				String comparepassword = MD5.MD5(password);
				
				System.out.println("Password: " + encryptpassword);
				if (comparepassword.equals(encryptpassword))
				
				{
									
					// Grab user information from database
									
					try {
						email = rs.getString("EMAIL");
					} catch (SQLException e1) {
						
						return false;
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
					return true;
				}
				} // End while
				setError("Invalid Access ID or password");
				return false;
				
			}
				
	
}
	
