package cscscheduler;

import java.util.Iterator;
import java.util.List;

import javax.transaction.Transaction;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.jmx.HibernateService;

public class StudentManager {
	
	private static SessionFactory factory;
	public StudentManager() 
	{
		factory = HibernateUtil.getSessionFactory();
	}

	/* METHOD TO CREATE an appointment in the database */
	
	public String addStudent(String accessID, String email, String firstName, String lastName)
	{
		Session session = factory.openSession();
		org.hibernate.Transaction tx=null;
		
		try {
			tx= session.beginTransaction();
			Students student = new Students(accessID, email, firstName, lastName);
			accessID = (String) session.save(student);
			tx.commit();
		}
		catch (HibernateException e) {
			
			if (tx!=null)
			{
				tx.rollback();
				e.printStackTrace();
			}
		}
		finally 
		{
			session.close();
		}
		
			return accessID;
		}

public void listStudents() {
	
	Session session = factory.openSession();
	org.hibernate.Transaction tx=null;
	try {
		
		tx=session.beginTransaction();
		List students = session.createQuery("FROM Students").list();
		String format ="|%1$ - 5s|%2$ - 15s | %3$ - 15s| \n";
		System.out.format(format,"Access ID", "First name", "Last name");
		for (Iterator iterator = students.iterator(); iterator.hasNext(); )
		{
			Students student = (Students) iterator.next();
			System.out.format(format, student.getAccessId(), student.getFirstName(), student.getLastName());
		}
		
		tx.commit();			
	} catch (HibernateException e) {
		if (tx!=null)
			tx.rollback();
			e.printStackTrace();
	} finally {
		session.close();
	}
	}
	
}
		
