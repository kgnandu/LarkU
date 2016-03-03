package ttl.larku.controllers.resty;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import ttl.larku.domain.Student;
import ttl.larku.exception.LarkUException;
import ttl.larku.service.RegistrationService;
import ttl.larku.service.StudentService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerMockTest {

	@Mock
	private RegistrationService regService;
	
	@Mock
	private StudentService studentService;
	
	@Mock
	private UriInfo uriInfo;
	
	@InjectMocks
	private StudentRestyController studentController;

	private Student getStudent;
	private Student newStudent;
	private int goodId = 1;
	private int negId = -1;
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

		Mockito.when(regService.getStudentService()).thenReturn(studentService);

		Mockito.when(studentService.getStudent(goodId)).thenReturn(getStudent);
		Mockito.when(studentService.getStudent(badId)).thenReturn(null);
		Mockito.when(studentService.createStudent("dummy")).thenReturn(newStudent);
		Mockito.when(studentService.createStudent(newStudent)).thenReturn(newStudent);
		Mockito.doNothing().when(studentService).updateStudent(getStudent);
		Mockito.when(studentService.getAllStudents()).thenReturn(students);
	}
	

	@Test
	public void getStudentGood() throws Exception{
		Student result = studentController.getStudentXJWithPathVariable(goodId);
		assertEquals("Joe", result.getName());
		Mockito.verify(studentService).getStudent(goodId);
	}

	@Test(expected=LarkUException.class)
	public void testGetStudentWithBadId() throws JsonGenerationException, JsonMappingException, IOException {
		Student result = studentController.getStudentXJWithPathVariable(badId);
		
		assertEquals("Student Should be Null", null, result );
		Mockito.verify(studentService).getStudent(Mockito.any(int.class));
	}

	@Test(expected=LarkUException.class)
	public void testGetStudentWithNegId() throws JsonGenerationException, JsonMappingException, IOException {
		Student result = studentController.getStudentXJWithPathVariable(negId);
		
		assertEquals("Student Should be Null", null, result );
		Mockito.verify(regService, Mockito.never()).getStudentService().getStudent(Mockito.any(int.class));
	}


	@Test
	public void testGetAll() {
		List<Student> students = studentController.getStudentsXmlJson();
		
		assertEquals(2, students.size());
		Mockito.verify(studentService).getAllStudents();
	}
}
