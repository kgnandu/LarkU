package ttl.larku.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;
import ttl.larku.exception.LarkUException;

public class StudentServiceTest {

	private StudentService studentService;

	@Before
	public void setup() {
		studentService = new StudentService();
		BaseDAO<Student> dao = new InMemoryStudentDAO();
		studentService.setStudentDAO(dao);
		studentService.createStudent("Joe");
		studentService.createStudent("Mary");
	}
	
	@Test
	public void testGetStudent() {
		int idToTest = 1;
		Student student = studentService.getStudent(idToTest);
		
		assertEquals("Joe", student.getName());
	}
	
	public void testGetStudentWithBadId() {
		int idToTest = 14;
		Student student = studentService.getStudent(idToTest);
		
		assertTrue("Student Should Not be Null", student == null);
	}
}
