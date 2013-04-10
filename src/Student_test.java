import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;

import CSAppointmentSchedulerFaces.Student;


public class Student_test {

	@Test
	public void test() {
		
		Student student = new Student();
		try {
			System.out.println(student.getAttendance("ad7893@wayne.edu"));
			System.out.println(student.getCancellation("ad7893@wayne.edu"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
