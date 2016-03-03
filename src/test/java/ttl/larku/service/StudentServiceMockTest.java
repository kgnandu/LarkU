package ttl.larku.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Student;

@RunWith(MockitoJUnitRunner.class)
public class StudentServiceMockTest {

	@Mock
	private InMemoryStudentDAO dao;

	@InjectMocks
	private StudentService studentService;
	
	private Student getStudent;
	private Student newStudent;
	private int goodId = 1;
	private int badId = 1000;
	
	private List<Student> students;

	@Before
	public void setup() {
		getStudent = new Student("Joe");
		getStudent.setId(goodId);
		newStudent = new Student("Sammy");
		newStudent.setId(goodId+1);
		
		students = new ArrayList<>();
		students.add(getStudent);
		students.add(newStudent);

		Mockito.when(dao.get(goodId)).thenReturn(getStudent);
		Mockito.when(dao.get(badId)).thenReturn(null);
		Mockito.when(dao.create(Mockito.any())).thenReturn(newStudent);
		Mockito.doNothing().when(dao).update(getStudent);
		Mockito.when(dao.getAll()).thenReturn(students);
	}
	

	@Test
	public void getStudentGood() throws Exception{
		Student result = studentService.getStudent(goodId);
		assertEquals("Joe", result.getName());
		Mockito.verify(dao).get(Mockito.any(int.class));
	}

	@Test
	public void testGetStudentWithBadId() {
		Student student = studentService.getStudent(badId);
		
		assertEquals("Student Should Not be Null", null, student );
		Mockito.verify(dao).get(Mockito.any(int.class));
	}

	@Test
	public void testCreateStudent() {
		Student student = studentService.createStudent("Sammy");
		
		assertNotEquals(0, student.getId());
		Mockito.verify(dao).create(Mockito.any(Student.class));
	}

	@Test
	public void testCreateStudentWithStudent() {
		Student student = studentService.createStudent(newStudent);
		
		assertNotEquals(0, student.getId());
		Mockito.verify(dao).create(Mockito.any(Student.class));
	}

	@Test
	public void deleteGoodStudent() {
		studentService.deleteStudent(goodId);
		
		Mockito.verify(dao).get(goodId);
		Mockito.verify(dao).delete(getStudent);
	}

	@Test
	public void deleteStudentWithBadId() {
		studentService.deleteStudent(badId);
		
		Mockito.verify(dao).get(badId);
	}

	@Test
	public void testUpdateStudent() {
		Student student = studentService.getStudent(goodId);
		studentService.updateStudent(student);
		
		Mockito.verify(dao).update(student);
	}

	@Test
	public void testGetAll() {
		List<Student> students = studentService.getAllStudents();
		
		assertEquals(2, students.size());
		Mockito.verify(dao).getAll();
	}
}
