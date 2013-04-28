package CSAppointmentSchedulerFaces;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest; 
import java.security.NoSuchAlgorithmException; 
import java.sql.SQLException;
import java.util.Random;
import javax.faces.application.FacesMessage;     
import javax.faces.component.UIComponent;     
import javax.faces.component.UIInput;     
import javax.faces.context.FacesContext;     
import javax.faces.validator.ValidatorException;     
     
	/** Class to manage user passwords 
	 * @author Jacqueline D. Brown, aw4025
	 *
	 */
public class Password{
	
private static String convertToHex(byte[] data) { 
		        StringBuffer buf = new StringBuffer();
		        for (int i = 0; i < data.length; i++) { 
		            int halfbyte = (data[i] >>> 4) & 0x0F;
		            int two_halfs = 0;
		            do { 
		                if ((0 <= halfbyte) && (halfbyte <= 9)) 
		                    buf.append((char) ('0' + halfbyte));
		                else 
		                    buf.append((char) ('a' + (halfbyte - 10)));
		                halfbyte = data[i] & 0x0F;
		            } while(two_halfs++ < 1);
		        } 
		        return buf.toString();
		    } 
		 
		    public static String MD5(String text) 
		    throws NoSuchAlgorithmException, UnsupportedEncodingException  { 
		        MessageDigest md;
		        md = MessageDigest.getInstance("MD5");
		        byte[] md5hash = new byte[32];
		        md.update(text.getBytes("iso-8859-1"), 0, text.length());
		        md5hash = md.digest();
		        return convertToHex(md5hash);
		    } 
		    
		    private static String generate()
		    {
		    	String alphabet="abcdefghijklmnopqrstuvwxyz1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZ";
				 char[] text = new char[8]; // PAssword must be at least 8 characters.
				 
				 for (int i = 0; i < text.length; i++)
				    {
					 Random rand = new Random();
					 text[i]=alphabet.charAt(rand.nextInt(alphabet.length()));
				    }
				    return new String(text);    
		    }
		    
		    public static boolean reset(String email)
		    
		    {
		    	
					User user = null;
					user = user.getUser();
		    		int index=email.indexOf("@");
		    		if (email.substring(index+1, email.length())=="wayne.edu")
		    		{
		    			System.out.println("WSU Students cannot change password.");
		    			return false;
		    		}
		    		
		    		System.out.print("Index:"+ index);
		    		if (user.isStudent())
		    			{
		    				String password = generate();

		    		
		    				String message = "Dear " + email + ",\n\n" +
		    				"You indicated your forgot your password to the CSC Scheduler, and requested that it be changed.\n\n" +
		    				
		    				"Your username is: "+ email + "\n" +
		    				"your new password is: " + password + "\n" +
		    				"\n\n" +
		    				"You can use your new password to log into the CSC Appointment Scheduler.\n" +
		    				"Please note that your password is case-sensitive, so make sure you type it exaclty as it appears above.\n" +
		    				"After you log back into the CSC Appointment Scheduler, you will be required to change your password to something you can remember.\n" +
		    				"\n" +
		    				"Please follow this link to return to the login screen: \n\n" +
		    				"http://localhost:8080/CSC4996-4997-Winter2013-MJK/faces/Login.jsp\n" ;
		    		
		    				String subject = "Requested Account Change";
		    		
		    				String encryptedPassword = null;
		    			
		    			
		    				// Send email with new password 
		    				EmailTemplate.sendEmail(email, subject, message);
		    			
		    			try {
		    				encryptedPassword =  Password.MD5(password);
		    			} catch (NoSuchAlgorithmException e1) {
		    				System.out.println("Password Error...");
		    			} catch (UnsupportedEncodingException e1) {
		    				// TODO Auto-generated catch block
		    				e1.printStackTrace();
		    			}
		    			
		    			// Require user to change password flag
		    			String sql = "UPDATE STUDENT SET CHANGEPASSWORD = true WHERE EMAIL ='" + email + "'";
		    			String sql2 = "UPDATE STUDENT SET PASSWORD='"+ encryptedPassword+ "' WHERE EMAIL = '" + email + "'";
		    			try {
		    				Database.connect();
		    			} catch (ClassNotFoundException e) {
		    				return  false;
		    			} catch (SQLException e) {
		    				return  false;
		    			}
		    			try {
		    				Database.execute(sql);
		    				Database.execute(sql2);
		    			} catch (ClassNotFoundException e) {
		    				return false;
		    			} catch (SQLException e) {
		    				return false;
		    			}
		    			
		    				System.out.println("Successfully changed password!");
		    				return true;
		    			}
		    			else 
		    			{
		    				return false;
		    			}
		 

		    	
}
		    public static boolean change(String email, String password)
	    	{
		    	try {
					Database.connect();
				} catch (ClassNotFoundException e) {
					return false;
				} catch (SQLException e) {
				return false;
				}
				
					String encryptedPassword;
					try {
						encryptedPassword = Password.MD5(password);
					} catch (NoSuchAlgorithmException e) {
						// TODO Auto-generated catch block
						return false;
					} catch (UnsupportedEncodingException e) {
						// TODO Auto-generated catch block
						return false;
					}
					
					// Update password
	    			String sql = "UPDATE STUDENT SET CHANGEPASSWORD = false WHERE EMAIL ='" + email + "'";
	    			String sql2 = "UPDATE STUDENT SET PASSWORD='"+ encryptedPassword+ "' WHERE EMAIL = '" + email + "'";
					
	    			try {
						Database.execute(sql);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						return false;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						return false;
					}
					try {
						Database.execute(sql2);
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						return false;
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						return false;
					}
					return true;
				
	    		}
}

		        
	
		
		
	
