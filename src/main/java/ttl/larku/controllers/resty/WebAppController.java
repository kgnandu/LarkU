package ttl.larku.controllers.resty;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.RequestDispatcher;
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

/**
 * A controller to make a Web APPLICATION using JAX-RS.
 * This works by producing "text/html", which redirects
 * to the ttl.larku.converters.JspForwardingConverter,
 * which in turn forwards to a jsp.  WARNING - the
 * converter uses a CXF specific technique to do
 * the forwarding - may need to change if you use some
 * other library.
 * 
 * With Jersey and plain old Tomcat, the doForward() technique
 * seems to work, though Jersey does have a View class that should 
 * be further explored.
 * 
 * @author whynot
 *
 */
@Path("/v1/webapp")
public class WebAppController {

	@Inject
	private RegistrationService regService;

	//@Context private MessageContext context;
	
	//We need this to fool CXF into calling our jspForwardingConverter
	//If we send back a String, then it doesn't seem to get called 
	//in tomee 7.0.1
	private final static SillyDummy sillyDummy = new SillyDummy();

	@GET
	@Path("/getStudents")
	@Produces("text/html")
	public List<Student> getStudents(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/showStudents.jsp");
		//jspForwardCXF(request,  response, "/WEB-INF/jsp/showStudents.jsp");
		//doForward(request, response);
		return students;
	}

	@GET
	@Path("/getStudent")
	@Produces("text/html")
	public Student getStudent(@QueryParam("id") int id,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		Student student = regService.getStudentService().getStudent(id);
		request.setAttribute("student", student);
		request.setAttribute("url", "/WEB-INF/jsp/showStudent.jsp");
		//doForward(request,response);
		return student;
	}

	@GET
	@Path("/admin/addStudent")
	@Produces("text/html")
	public SillyDummy addStudentForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		request.setAttribute("url", "/WEB-INF/jsp/addStudent.jsp");
		//doForward(request,response);
		//return "";
		return sillyDummy;
	}

	@POST
	@Path("/admin/addStudent")
	@Produces("text/html")
	public Student addStudent(MultivaluedMap<String, String> formParams,
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
		//doForward(request,response);
		return student;
	}

	@GET
	@Path("/getAllClasses")
	@Produces("text/html")
	public List<ScheduledClass> getClasses(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<ScheduledClass> classes = regService.getClassService()
				.getAllScheduledClasses();
		request.setAttribute("classes", classes);
		request.setAttribute("url", "/WEB-INF/jsp/showClasses.jsp");
		//doForward(request,response);
		return classes;
	}

	@GET
	@Path("/admin/addClass")
	@Produces("text/html")
	public SillyDummy addClassForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Course> courses = regService.getCourseService().getAllCourses();
		request.setAttribute("courses", courses);
		request.setAttribute("url", "/WEB-INF/jsp/addClass.jsp");
		//doForward(request,response);
		//return "";
		return sillyDummy;
	}

	@POST
	@Path("/admin/addClass")
	@Produces("text/html")
	public SillyDummy addClass(@FormParam("courseCode") String courseCode,
			@FormParam("startDate") String startDate,
			@FormParam("endDate") String endDate,
			@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode,
				startDate, endDate);
		request.setAttribute("url", "/index.jsp");
		//doForward(request, response);
		//return "";
		return sillyDummy;
	}

	@GET
	@Path("/admin/registerStudentForClass")
	@Produces("text/html")
	public SillyDummy registerStudentForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<ScheduledClass> classes = regService.getClassService()
				.getAllScheduledClasses();
		request.setAttribute("classes", classes);
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);

		request.setAttribute("url", "/WEB-INF/jsp/addRegistration.jsp");
		//doForward(request, response);
		//return "";
		return sillyDummy;
	}

	@POST
	@Path("/admin/registerStudentForClass")
	@Produces("text/html")
	public SillyDummy registerStudent(@FormParam("studentId") int studentId,
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
		//doForward(request, response);
		//return "";
		return sillyDummy;
	}

	@GET
	@Path("/masterDetailRest")
	@Produces("text/html")
	public SillyDummy masterDetailForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/studentMasterDetailJQuery.jsp");
		//This will be handled by the JspForwardingConverter - is *very* cxf jaxrs specific
		//doForward(request, response);
		//return "";
		return sillyDummy;
	}

	@GET
	@Path("/admin/addStudentRest")
	@Produces("text/html")
	public SillyDummy addRestfullyForm(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws ServletException,
			IOException {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		request.setAttribute("students", students);
		request.setAttribute("url", "/WEB-INF/jsp/showStudentsJQueryAjax.jsp");
		//doForward(request, response);
		//return "";
		return sillyDummy;
	}

	public void doForward(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		String url = (String)request.getAttribute("url");
		RequestDispatcher rd = request.getRequestDispatcher(url);
		rd.forward(request, response);
	}
}

class SillyDummy {}