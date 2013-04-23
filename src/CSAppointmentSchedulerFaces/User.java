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
	private static String changed;
	
	public boolean authorized = false;
	private String error = "";
	private boolean displayError = false;

	/** Default constructor */
	public User() {
		
		User.accessId = null;
		User.password = null;
		User.firstName = null;
		User.lastName = null;
		User.email = null;

	}
	
	/** Create a new user with the following parameters and add account to the database
	 * 
	 * @param accessID = the accessID of the user
	 * @param password = the password of the user
	 * @param firstName = the user's first name
	 * @param lastName = the user's last name
	 * @param email = the user's email address
	 */
	public User(String _accessID, String _password, String _firstName, String _lastName, String _email, String _changed)
	{
		accessId= _accessID;
		password= _password;
		firstName = _firstName;
		lastName = _lastName;
		email = _email;
		changed = _changed;
		
	}

	/** Save user session **/
	public static void setUser() {
		
		System.out.print("Setting user information....");

		User user = new User(accessId, password, firstName, lastName, email, changed);
		FacesContext fc = FacesContext.getCurrentInstance();
		
		fc.getExternalContext().getSessionMap().put("user", user);	
		
		System.out.println("done." + user.toString());
	}

	/** Get current user using session */
	public static User getUser() {
		
		System.out.print("Getting user information....");
		
		try {
		FacesContext fc = FacesContext.getCurrentInstance();
		User user =(User) fc.getExternalContext().getSessionMap().get("user");
		
		
		System.out.print("done..... user" + user.toString());
		return user;
		
		
		}
		catch (NullPointerException e)
		{
			System.out.println("I can't find a user!");
			return null;
		}
		
	
	}

	/** Set accessId of user */
	public void setAccessId(String _accessId) {
		User.accessId = _accessId;
	}

	/** Get user accessId */
	public String getAccessId() { 
		return accessId;
	}
	
	/** Set user email address */
	public void setEmail(String _email) {
		User.email = _email;
	}

	/** Get user email address */
	public String getEmail() { 
		return email;
	}

	/** Set user password  
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws UnsupportedEncodingException 
	 * @throws NoSuchAlgorithmException */
	public void setPassword (String password)  {
		
		
		User.password= password;
		
		
	}

	/** Get user password */
	public String getPassword() { 
		
		return password;
	}
	
	
	/** Update user account information  and save to database
	 * @throws SQLException  = user account cannot be found
	 * @throws ClassNotFoundException = ????  **/ 
	public void update() throws ClassNotFoundException, SQLException
	{
		Database.connect();
		
		if (isAdvisor())
		{
			// Update advisor information
		}
		
		if (isStudent())
		{
			//Update student table with information
		}
		
	}
	
	public boolean getDisplayError() {
		return displayError;
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
		String header = "" +
		"<div id='wsu-header' style=''>" +
		 " <div class='c'>" +
		    "<ul class='skip'>" +
		      "<li><a href='#content'>Skip to Content</a></li>" +
		      "<li><a href='#menu'>Skip to Navigation</a></li>" +
		    "</ul>" +
		    "<h1><a href='http://wayne.edu/'><img src='//wayne.edu/global/images/wsu-wayne-state-university.gif' alt='Wayne State University' width='344' height='33' /></a></h1>" +
		    "<h2><a href='http://wayne.edu/aimhigher/'><img src='//wayne.edu/global/images/wsu-aim-higher.gif' alt='Aim Higher' width='85' height='13' /></a></h2>" +
		    "<div id='tab'>" +
		      "<ul>" +
		        "<li class='first'><a href='http://pipeline.wayne.edu/' title='WSU Pipeline' class='first'>Pipeline</a></li>" +
		        "<li><a href='http://blackboard.wayne.edu/' title='Blackboard'>Blackboard</a></li>" +
		        "<li><a href='http://stars.wayne.edu/' title='Stars'>Stars</a></li>" +
		        "<li><a href='http://ucomm.wayne.edu/~fsd/' title='WSU Directories'><span>Directories</span></a></li>" +
		        "<li><a href='http://wayne.edu/contact/' title='Contact WSU'><span>Contact WSU</span></a></li>" +
		        "<li class='last'><a href='http://wayne.edu/siteindex.php' title='WSU A-Z Site Index' class='last'><span>A-Z Index</span></a></li>" +
		      "</ul>" +
		    "</div>" +
		  "</div>" +
		"</div>";
		/*
		header += "" +
		"<div id='wsu-footer' style='position: absolute; bottom: 0px; width: 100%;'>" +
		  "<div class='c'>" +
		    "<address class='vcard' id='wsu-copyright'> <a class='url fn org' href='http://wayne.edu/'>Wayne State University</a> <span class='adr'> <span class='locality'>Detroit</span>, <span class='region'>MI</span> <span class='postal-code'>48202</span> <span class='country-name'>United States</span></span> &copy; 2013 </address>" +
		    "<p id='wsu-policy'><a href='http://wayne.edu/policies/' title='View the Privacy and University Policies' rel='license'>Privacy and University Policies</a></p>" +
		  "</div>" +
		"</div>";
		*/		
		String menu = "";
		if(!role.isEmpty()) {
			menu += "<div class='dividor' style='border-bottom: 1px solid #000; height: 0px;'></div>";
			menu += "<div id='menu'>";
			menu += "<div id='menu_inner'>";
			if(role.equals("advisor")) {
				menu += "<span>Welcome," + getFirstName() + " " + getLastName() +"</span>";
				menu += "<a href='Advisor.jsp'>Home</a>";
				menu += "<hr/>";
				menu += "<a title='Quick look at appointments for today.' href='Appointments.jsp?date=today'>View Today's Appointments</a>";
				menu += "<a title='Quick look at appointments for this week.' href='Appointments.jsp?date=week'>View This Week's Appointments</a>";
				menu += "<a title='Use the calendar to update your availability for a specific date, view appointments on a specific date and send reminders for appointments.' href='Calendar.jsp'>Calendar</a>";
				menu += "<hr/>";
				menu += "<a title='Update your default availability.' href='Availability.jsp'>Update Default Availability</a>";
				menu += "<a title ='Update your personal information (ie. phone, location, number of cancels allowed, number of no show allowed).' href='PersonalInfo.jsp'>Update Personal Information</a>";
				menu += "<a title ='Update reasons available for students to select and descriptions/feedback for each reason.' href='UpdateReasons.jsp'>Update Reasons</a>";
				menu += "<hr/>";
				menu += "<a href='#' onclick='window.open(\"../img/advisorHelp.png\", \"\", \"location=0,menubar=0\");'>Help</a>";
				menu += "<a href='../Login.jsp?logout=true'>Logout</a>";
			} else if(role.equals("student")){
				menu += "<span>Welcome, " + getFirstName() + " " + getLastName() +"</span>";
				menu += "<a href='Student.jsp'>Home</a>";
				menu += "<hr/>";
				menu += "<a title='View all your appointments.' href='Appointments.jsp'>View Your Appointments</a>";
				menu += "<a title='Schedule an appointment.' href='ScheduleAppoinment.jsp'>Schedule an Appointment</a>";	
				menu += "<hr/>";
				menu += "<a href='#' onclick='window.open(\"../img/studentHelp.png\", \"\", \"location=0,menubar=0\");'>Help</a>";
				menu += "<a href='../Login.jsp?logout=true'>Logout</a>";
			}
			
			menu += "</div>";
			menu += "</div>";
			//menu += "<div class='dividor' style='border-bottom: 1px solid #000; height: 0px;'></div>";
		}
		
		return header + menu;
		
	}

	
	public String authorized() throws NoSuchAlgorithmException, UnsupportedEncodingException, NamingException, ClassNotFoundException, SQLException {
		
		System.out.print("Processing login information...");
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
			System.out.println("Error.");
			return "Error";
			}



		// Check if user has entered an email address instead of accessID
	
		int index = accessId.indexOf("@");

		if (index>0 && accessId.substring(index+1).compareToIgnoreCase("wayne.edu")!=0)
			{
				
					 System.out.println("NonWSU");
					if (NonWSULogin())
					 {
						 
						 System.out.println("Student");
						 
					
						 	if (changed.charAt(0)=='1')
						 	{
						 		setEmail(accessId);
						 		setUser();
						 		return "Change Password";
							
						 	}
						 
						 	else 
							 {
							 	return "Student Authorized";
							 }
					 }
			}
		else 
			{
					
				accessId= accessId.substring(0, 6);
					
					if (WSULogin())
					{
						if (isAdvisor())
						{
							System.out.println("Advisor");
							return "Advisor Authorized";
						}
						
						else // Must be student
						{
							System.out.println("Student");
							return "Student Authorized";
						}
					}
					
					setError("Invalid AccessId and/or Password");
					return "Error";
			}
		
		System.out.println("Error.");
		return "Error";
					
				}

	
	/** Return true if user in is Advisor Table */
	private boolean isAdvisor() {
		
		/*  Query databaase to check if user has an  advisor account */
		try {
			Database.connect();
		} catch (ClassNotFoundException e1) {
			return false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println ("Database Error");
			return false;
		}
		
		
		String sql = "SELECT * FROM ADVISOR WHERE ACCESS_ID ='"+ accessId + "'";
		System.out.println(sql);
		try {
			ResultSet rs =Database.fetch(sql);
			
			if (!rs.next())
			{
				System.out.println("No user exists...");
				return false;
			}
			
		} catch (ClassNotFoundException e) {
			return false;
		} catch (SQLException e) {
			
			return false; 
		}
		System.out.println("you are an advisor!");
		return true;
	}

	
	/** Log user out of system */
	public void logout() {
		
		setAccessId(null);
		setError(null);
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("user");
		
	}

	/** Get user first name */
	public String getFirstName() {
		return firstName;
	}

	/** Get user last name */
	public String getLastName() {
		return lastName;
	}
	

	/** Set user first name */
	public void setFirstName(String firstName) {
		
		User.firstName= firstName;
	}
	
	/** Set user last name */
	public void setLastName (String lastName) {

	
		User.lastName = lastName;
	}
	

	
	/** Convert attributes properly */
	private  String convert(String s)
	{
		int index = s.indexOf(':');
		
		return s.substring(index+2);
			
			
	}
	
	/** Returns true if login for WSU student is successfull, false otherwise
	 * 
	 * @return true is login is sucessfull adds WSU account into the database
	 * @throws NamingException 
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private boolean WSULogin()
	{
		
		System.out.print("WSU...");
		// authenticate user through LDAP
		Hashtable<String, String> ldap = new Hashtable<String, String>();
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
			return false;
		}
		
		authorized = true;
    
		// Query LDAP for user information
 		//Create the search controls  
 							
		SearchControls searchCtls = new SearchControls();
 		String returnedAtts[]={"givenName","sn","mail"};
 		searchCtls.setReturningAttributes(returnedAtts);

 							
 		//Specify the search scope

 		searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
 					  
 		//specify the LDAP search filter
 		String searchFilter = "(&(objectClass=wayneStudent)(uid=" + accessId+ "))";
 		//Specify the Base for the search
 				
 		String searchBase = "ou=people, dc=wayne, dc=edu";
 											   
 		// Search for objects using the filter
 				
 		NamingEnumeration<?> answer = null;
		try {
			answer = ctx.search(searchBase, searchFilter, searchCtls);
		} catch (NamingException e2) {
			// TODO Auto-generated catch block
			return false;
		}
	
 							
 		System.out.print("retrieving info...");
 							
 			while (answer.hasMoreElements()) {
 								
 				SearchResult sr;
				try {
					sr = (SearchResult)answer.next();
				} catch (NamingException e) {
						return false;
				}


 			
 					// Get the attributes, throws exception if the attributes have no values
 								Attributes attrs = sr.getAttributes();
 					    
 								if (attrs != null) {
 				
 										 firstName = convert(attrs.get("givenName").toString());
 										 lastName = convert(attrs.get("sn").toString());;
 										 email = convert(attrs.get("mail").toString());
 										 /* role = convert(attrs.get("title").toString());  */
 										 
 								}
 								
 								setUser();
 								System.out.println("done!");
 						} // end retrieving information
	
 						
 								try {
									ctx.close();
								} catch (NamingException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								} 
 								
 								// Check if user has an account in database, if not create
 								 
 									 // If user is not an advisor or not already in student table add to student Table //
 									 if (!isStudent() && !isAdvisor())
 									 {
 										try {
											Student.add(accessId, email, firstName, lastName);
										} catch (ClassNotFoundException e) {
											return false;
										} catch (SQLException e) {
											// TODO Auto-generated catch block
											return false;
										}
 										System.out.println("User account added as student..");
 									 }
 	
 								
 								
 									return true;
					} // End WSU login
	
	/** Returns true if login for NON-WSU student is successful, false otherwise **/
	private boolean NonWSULogin() throws NoSuchAlgorithmException, UnsupportedEncodingException, SQLException, ClassNotFoundException
	{

		System.out.println("Your access ID... " + accessId);
		String sql = "SELECT * FROM STUDENT WHERE EMAIL= '" + accessId + "'";
				
				System.out.println("Connecting to Database");
		
				Database.connect();
				
				System.out.println("Connected");
				
				System.out.println(sql);
				
				ResultSet rs = Database.fetch(sql);
				
		System.out.println("Successful...connection");		
				
				
				while (rs.next())
				{
				
					String encryptpassword = rs.getString("PASSWORD");
					
					
					String comparepassword = Password.MD5(password);
					
					
					System.out.println("Password: " + encryptpassword);
					
					
					changed = rs.getString("CHANGEPASSWORD");
					System.out.println("Changed... "+ changed) ;
					
					
					if (comparepassword.equals(encryptpassword))
						
				
				{
									
					// Grab user information from database
									
					try {
						email = rs.getString("EMAIL");
					} catch (SQLException e1) {
						
						System.out.println("Canot retrieve email");
						return false;
					}
					try {
						firstName =rs.getString("FIRST_NAME");
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						System.out.print("Cannot retrieve user first name");
						return false;
					}
					try {
						lastName =rs.getString("LAST_NAME");
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.print("Cannot retrieve user last name");
						return false;
					}
					setUser();	
					return true;
				}
				} // End while
				setError("Invalid Access ID or password");
				return false;
				
			}
			
	/** Returns user attendance **/
	public int getAttendance(String email) throws ClassNotFoundException, SQLException
	
	{
		
		String sql = "SELECT COUNT(*) AS count  FROM APPOINTMENT WHERE STUDENT_EMAIL= '" +email + "' AND ATTENDANCE = 0";
		Database.connect();
		ResultSet rs= Database.fetch(sql);
		
		
		rs.next();
		int  attendance = rs.getInt("count");
		rs.close();
		
		return attendance;
		
	}
	
	/** Retrieves how many times student has cancelled an appointment */
	public int getCancellation(String email) throws ClassNotFoundException, SQLException 
	{
		String sql = "SELECT COUNT(*) AS count FROM APPOINTMENT WHERE STUDENT_EMAIL= '" +email + "' AND CANCELLED = 1 ";
		Database.connect();
		ResultSet rs= Database.fetch(sql);
		
		rs.next();
		int  cancelled = rs.getInt("count");
		rs.close();
		
		return cancelled;
	}

	
	/** Reset user password **/
	public boolean forgotPassword()
	{
		if (Password.reset(email))
		{
			System.out.println("Successfully changed!");
			return true;
		}
		
		System.out.println("Error resseting password!");
		return false;
	}
	
	
	public boolean changePassword(String password)
	{
		System.out.println("Changing password...");
		if (Password.change(email, password))
		{
			setPassword(password);
			System.out.println("Successfully changed.");
			return true;
		}
		System.out.println("Error changing password!");
		return false;
	}
	
	public String toString()
	{
		return firstName + " " + lastName + " " + email;
	}
	

	
	/** Return true is user is in student table */
	public boolean isStudent() {
		
	/*  Query database to check if user has an student account */
		
		try {
			Database.connect();
		} catch (ClassNotFoundException e1) {
			return false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			System.out.println ("Database Error");
			return false;
		}
		String sql = "SELECT * FROM STUDENT WHERE EMAIL ='"+ email + "'";
		try {
			
			System.out.println(sql);
			ResultSet rs= Database.fetch(sql);
			if (!rs.next())
			{
				System.out.println("No user exists...");
				return false;
			}
		} catch (ClassNotFoundException e) {
			System.out.println("Aw...snap! Something has gone wrong!");
			return false;
		} catch (SQLException e) {
			
			System.out.println("Aw...snap! Something has gone wrong!");
			return false; 
		}
		
		return true;
	}
	
}
	
