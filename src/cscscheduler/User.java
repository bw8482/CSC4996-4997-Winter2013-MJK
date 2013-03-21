package cscscheduler;


	import java.util.Hashtable;
	import javax.naming.Context;
	import javax.naming.directory.InitialDirContext;

	public class User {

				private String accessID;
				private String password;

		
	public String getaccessID() {
			return accessID;
		}

		public void setAccessID(String accessID) {
		this.accessID = accessID;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}


		public String login() throws Exception
		{
				//Hardcode advisor
				
				if (accessID == "fg8527"  && password == "advisor")
					{
						return "advisor";
					}
				
			
					// Try to authenticate via LDAP)

				Hashtable ldap= new Hashtable();
				ldap.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
				ldap.put(Context.PROVIDER_URL, "ldap://directory.wayne.edu");
				ldap.put(Context.SECURITY_AUTHENTICATION, "simple");


				String principal = "uid="+ accessID + ", ou=people,dc=wayne,dc=edu";
				ldap.put(Context.SECURITY_PRINCIPAL, principal);
				ldap.put(Context.SECURITY_CREDENTIALS, password);	


				try
				{
					InitialDirContext ctx = new InitialDirContext(ldap);
					return "student";
				}

				catch (Exception e)
				{
					return "failed";
				}
		}
	}

