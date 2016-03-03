package ttl.larku.controllers.resty;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;

import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.RegistrationService;

@Path("/webapp")
public class WebAppController {

	@Inject
	private RegistrationService regService;

	//@Context private MessageContext context;

	@GET
	@Path("/getStudents")
	@Produces("text/html")
	public Object getStudents(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/showStudents.jsp");
		//jspForwardCXF(request,  response, "/WEB-INF/jsp/showStudents.jsp");
		return students;
	}

	@GET
	@Path("/getStudent")
	@Produces("text/html")
	public Object getStudents(@QueryParam("id") int id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		Student student = regService.getStudentService().getStudent(id);
		request.setAttribute("student", student);
		request.setAttribute("url", "/WEB-INF/jsp/showStudent.jsp");
		return student;
	}

	@GET
	@Path("/admin/addStudent")
	@Produces("text/html")
	public Object addStudentForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		request.setAttribute("url", "/WEB-INF/jsp/addStudent.jsp");
		return "";
	}

	@POST
	@Path("/admin/addStudent")
	@Produces("text/html")
	public Object addStudent(MultivaluedMap<String, String> formParams,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		String name = formParams.get("name").get(0);
		String phoneNumber = formParams.get("phoneNumber").get(0);
		String strStatus = formParams.get("status").get(0);
		Student.Status status = strStatus == null ? 
				Student.Status.FULL_TIME : Student.Status.valueOf(strStatus);
		Student student = new Student(name, phoneNumber, status);

		//The returned student will have an Id
		student = regService.getStudentService().createStudent(student);

		request.setAttribute("student", student);
		request.setAttribute("url", "/WEB-INF/jsp/showStudent.jsp");
		return student;
	}

	@GET
	@Path("/getAllClasses")
	@Produces("text/html")
	public Object getClasses(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<ScheduledClass> classes = regService.getClassService()
				.getAllScheduledClasses();
		request.setAttribute("classes", classes);
		request.setAttribute("url", "/WEB-INF/jsp/showClasses.jsp");
		return classes;
	}

	@GET
	@Path("/admin/addClass")
	@Produces("text/html")
	public Object addClassForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Course> courses = regService.getCourseService().getAllCourses();
		request.setAttribute("courses", courses);
		request.setAttribute("url", "/WEB-INF/jsp/addClass.jsp");
		return "";
	}

	@POST
	@Path("/admin/addClass")
	@Produces("text/html")
	public Object addClass(@FormParam("courseCode") String courseCode,
			@FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode,
				startDate, endDate);
		request.setAttribute("url", "/index.jsp");
		return "";
	}

	@GET
	@Path("/admin/registerStudentForClass")
	@Produces("text/html")
	public Object registerStudentForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<ScheduledClass> classes = regService.getClassService()
				.getAllScheduledClasses();
		request.setAttribute("classes", classes);
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);

		request.setAttribute("url", "/WEB-INF/jsp/addRegistration.jsp");
		return "";
	}

	@POST
	@Path("/admin/registerStudentForClass")
	@Produces("text/html")
	public Object registerStudent(@FormParam("studentId") int studentId,
			@FormParam("classId") int classId,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		ScheduledClass sClass = regService.getClassService().getScheduledClass(
				classId);

		regService.registerStudentForClass(studentId, sClass.getCourse()
				.getCode(), sClass.getStartDate());

		Student student = regService.getStudentService().getStudent(studentId);

		request.setAttribute("student", student);
		request.setAttribute("url", "/WEB-INF/jsp/showStudent.jsp");
		return "";
	}

	@GET
	@Path("/masterDetailRest")
	@Produces("text/html")
	public Object masterDetailForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/studentMasterDetailJQuery.jsp");
		//This will be handled by the JspForwardingConverter - is *very* cxf jaxrs specific
		return "";
	}

	@GET
	@Path("/admin/addStudentRest")
	@Produces("text/html")
	public Object addRestfullyForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/showStudentsJQueryAjax.jsp");
		return "";
	}

}
