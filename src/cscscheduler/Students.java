package cscscheduler;

// Generated Mar 20, 2013 4:04:02 PM by Hibernate Tools 3.4.0.CR1

/**
 * Students generated by hbm2java
 */
public class Students implements java.io.Serializable {

	private String email;
	private String accessId;
	private String firstName;
	private String lastName;

	public Students() {
	}

	public Students(String email) {
		this.email = email;
	}

	public Students(String email, String accessId, String firstName,
			String lastName) {
		this.email = email;
		this.accessId = accessId;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccessId() {
		return this.accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getFirstName() {
		return this.firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return this.lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}