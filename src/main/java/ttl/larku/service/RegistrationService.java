package ttl.larku.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

public class RegistrationService {

	private CourseService courseService;
	private StudentService studentService;
	private ClassService classService;

	//@PostConstruct
	public void init() {
		/*
		if (studentService.getAllStudents().size() == 0) {
			// init data

			studentService.createStudent(new Student("Roberta", "383 892 9292", Student.Status.FULL_TIME));
			studentService.createStudent(new Student("Madhu", "383 393 9292", Student.Status.HIBERNATING));
			studentService.createStudent(new Student("Tim", "989 938 6748", Student.Status.FULL_TIME));
			studentService.createStudent(new Student("Amir", "776 636 8886", Student.Status.PART_TIME));

			courseService.createCourse(new Course("MATH-101", "Baby Math"));
			courseService.createCourse(new Course("BKT-201",
					"Intermediate Basket Weaving"));
			courseService.createCourse(new Course("PHY-302",
					"Intro to Quantum Mechanics"));
			courseService
					.createCourse(new Course("AST-202", "Basic Cosmology"));

			addNewClassToSchedule("MATH-101", "10/10/2015", "12/10/2015");
			addNewClassToSchedule("PHY-302", "08/10/2015", "12/10/2016");
			addNewClassToSchedule("BKT-201", "06/10/2015", "9/10/2015");
		}
			*/
	}
	
	@Inject
	public RegistrationService(CourseService cs, StudentService ss,
			ClassService classService) {
		/*
		 * courseService = new CourseService(); studentService = new
		 * StudentService(); classService = new ClassService();
		 */

		courseService = cs;
		studentService = ss;
		this.classService = classService;
	}

	public ScheduledClass addNewClassToSchedule(String courseCode,
			String startDate, String endDate) {
		ScheduledClass sClass = classService.createScheduledClass(courseCode,
				startDate, endDate);
		return sClass;
	}

	public void registerStudentForClass(int studentId, String courseCode,
			String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				sc.addStudent(student);
				student.addClass(sc);
				studentService.updateStudent(student);
				break;
			}
		}
	}

	public void dropStudentFromClass(int studentId, String courseCode,
			String startDate) {
		Student student = studentService.getStudent(studentId);
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				sc.removeStudent(student);
				student.dropClass(sc);
				break;
			}
		}
	}

	public List<Student> getStudentsForClass(String courseCode, String startDate) {
		List<ScheduledClass> classes = classService
				.getScheduledClassesByCourseCode(courseCode);
		for (ScheduledClass sc : classes) {
			if (sc.getStartDate().equals(startDate)) {
				return sc.getStudents();
			}
		}
		return new ArrayList<Student>();
	}

	public List<ScheduledClass> getScheduledClasses() {
		return classService.getAllScheduledClasses();
	}

	public CourseService getCourseService() {
		return courseService;
	}

	public void setCourseService(CourseService courseService) {
		this.courseService = courseService;
	}

	public StudentService getStudentService() {
		return studentService;
	}

	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}

	public ClassService getClassService() {
		return classService;
	}

	public void setClassService(ClassService classService) {
		this.classService = classService;
	}
}
