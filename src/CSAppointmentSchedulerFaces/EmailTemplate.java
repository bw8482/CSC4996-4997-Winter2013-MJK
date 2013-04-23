/**
 * 
 */
package CSAppointmentSchedulerFaces;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author mariammhmd_10
 *
 */
public class EmailTemplate {
	public static boolean sendEmail(String to, String subject, String messageStr) {
		
		Properties props = new Properties();  
	        props.put("mail.smtp.host", "smtp.gmail.com");  
	        props.put("mail.smtp.socketFactory.port", "465");  
	        props.put("mail.smtp.socketFactory.class",  
	                "javax.net.ssl.SSLSocketFactory");  
	        props.put("mail.smtp.auth", "true");  
	        props.put("mail.smtp.port", "465");  

	        Session session = null;
	        try {
	        	session = Session.getDefaultInstance(props,  
	    	            new javax.mail.Authenticator() {  
	                protected PasswordAuthentication getPasswordAuthentication() {  
	                    return new PasswordAuthentication("cscappointmentscheduler","cscappointmentsadmin");  
	                }  
	            });  
	        
	        } catch(Exception ex){
	        	return false;
	        }
	        try {  
	
	            Message message = new MimeMessage(session);  
	            message.setFrom(new InternetAddress("cscappointmentscheduler@gmail.com"));  
	            message.setRecipients(Message.RecipientType.TO,  
	                    InternetAddress.parse(to));  
	            message.setSubject("CSC WSU " + subject);  
	            message.setText(messageStr);  

	            Transport.send(message); 
	   
	        } catch (MessagingException e) {  
	            return false; 
	        } 
	      
		return true;
	}

}
