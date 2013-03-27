package cscscheduler;

import java.util.Hashtable;
import javax.naming.Context;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.DirContext;
import javax.naming.directory.Attributes;
import javax.naming.NamingException;

public class Ldap {

		private String accessId;
		private String password;
		
		private String error = "";
		private boolean displayError = false;
		
		public void setAccessId(String _accessId) {
			this.accessId = _accessId;
		}
		
		public String getAccessId() { 
			return accessId;
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
		
		@SuppressWarnings({ "unchecked", "rawtypes", "unused" })
		public String authorized() throws Exception {
			
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
			
			Hashtable ldap = new Hashtable();
			ldap.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
			ldap.put(Context.PROVIDER_URL, "ldap://directory.wayne.edu");
			ldap.put(Context.SECURITY_AUTHENTICATION, "ssl");
			
			String principal = "uid="+ getAccessId() + ", ou=people,dc=wayne,dc=edu";
			ldap.put(Context.SECURITY_PRINCIPAL, principal);
			ldap.put(Context.SECURITY_CREDENTIALS, getPassword());	


			try {
				DirContext ctx = new InitialDirContext(ldap);
				
				//Ask for all attributes of the object 
			    try {
			    	Attributes attrs = ctx.getAttributes("uid=ef2558, ou=People");
			    } catch (Exception ex) {
			    	System.out.println("Error: " + ex.getMessage());
			    }
			    
			    if(getAccessId().equals("aw4025")) {
			    	return "advisor";
			    }
				return "student";
			} catch (Exception e) {
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
