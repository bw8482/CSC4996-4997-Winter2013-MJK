package CSAppointmentSchedulerFaces;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.Address;
import javax.mail.Flags;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * @author Jackie
 * Controls the sending and receivng of all emails from the CSC Scheduler Program
 *
 */

public class EmailManager {
	
	public boolean sendEmail(Address[] emailto, String subject, String messageStr) throws MessagingException
	{
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
            	System.out.println("Error " + ex.getMessage());
            }
            Message message = new MimeMessage(session);  
            message.setFrom(new InternetAddress("cscappointmentscheduler@gmail.com")); 
            message.setRecipients(Message.RecipientType.TO, emailto); 
            message.setSubject(subject);  
            message.setText(messageStr);
            
        	
            try {  

                Transport.send(message);  
       
                System.out.println("Email Sent!");  
       
            } catch (MessagingException e) {  
            	System.out.println("Error");
                throw new RuntimeException(e);  
            } 
          
    	return true;
            
            
            
      
	        
	    
	}



	
	

}
