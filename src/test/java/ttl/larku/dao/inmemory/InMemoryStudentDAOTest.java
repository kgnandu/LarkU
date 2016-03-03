package ttl.larku.dao.inmemory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;


@RunWith(MockitoJUnitRunner.class)
public class InMemoryStudentDAOTest {
	
	@Spy
	InMemoryStudentDAO dao; 
	
	private Student getStudent;
	private Student newStudent;
	private Student noStudent;
	private int goodId = 1;
	private int badId = 1000;
	
	@Before
	public void setup() {
		getStudent = new Student("Joe");
		newStudent = new Student("Sammy");
		
		noStudent = new Student("No One");
		noStudent.setId(badId);
		
		dao.create(getStudent);
		dao.create(newStudent);
	}
	
	@After
	public void tearDown() {
		dao.deleteStore();
		dao.createStore();
	}

	@Test
	public void getStudentGood() throws Exception{
		Student result = dao.get(goodId);
		assertEquals("Joe", result.getName());
	}

	@Test
	public void testGetStudentWithBadId() {
		Student student = dao.get(badId);
		
		assertEquals("Student Should Be Null", null, student);
	}

	@Test
	public void testCreateStudent() {
		Student student = dao.create(newStudent);
		
		assertTrue(student != null && student.getId() > 0);
	}

	@Test
	public void deleteGoodStudent() {
		dao.delete(getStudent);
	}

	@Test
	public void testUpdateStudent() {
		dao.update(getStudent);
	}
	
	@Test 
	public void testUpdateBadStudent() {
		dao.update(noStudent);
	}

	@Test
	public void testGetAll() {
		List<Student> students = dao.getAll();
		
		assertEquals(2, students.size());
	}

}
