import static org.junit.Assert.*;

import org.junit.Test;

import CSAppointmentSchedulerFaces.User;
import CSAppointmentSchedulerFaces.UserManager;


public class User_Test {

	@Test
	public void testLogout() {
		
		User user = new User();
		
		user.logout();
	}

}
