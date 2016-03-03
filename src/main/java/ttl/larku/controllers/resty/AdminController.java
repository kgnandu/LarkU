package ttl.larku.controllers.resty;

import java.net.URI;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.RegistrationService;

@Path("/admin")
public class AdminController {
	
	
	private final static String classesPath = "classes";
	private final static String studentsPath = "students";
	/**
	 * To make URIs for Links and other things
	 */
	@Context
	private UriInfo uriInfo;
	
	@Inject
	private RegistrationService regService;

	@DELETE
	@Path("/students/{id}")
	public Response deleteStudent(@PathParam("id") int id) {
		regService.getStudentService().deleteStudent(id);
		
		return Response.noContent().build();
	}
	
	@PUT
	@Path("/students/{id}")
	public Response updateStudent(Student student) {
		regService.getStudentService().updateStudent(student);
		
		return Response.ok().build();

	}
	/**
	 * Create a Student and return the newly created 
	 * Student (with new Id).  
	 * 
	 * @param student
	 * @return
	 */
	//@POST
	//@Path("/students")
	@Consumes({ "application/xml", "application/json" })
	@Produces({ "application/xml", "application/json" })
	public Student addStudent(Student student) {
		Student student2 = regService.getStudentService()
				.createStudent(student);
		return student2;
	}

	/**
	 * Create a Student and return the newly created 
	 * Student (with new Id).  
	 * 
	 * Add a Location header to the response using UriInfo
	 * @param student
	 * @return
	 */
	@POST
	@Path("/students")
	@Consumes({ "application/xml", "application/json", "application/x-java-serialized-object" })
	@Produces({ "application/xml", "application/json" , "application/x-java-serialized-object"})
	public Response addStudentWithLocation(Student student) {
		Student student2 = regService.getStudentService()
				.createStudent(student);
		
        UriBuilder ub = uriInfo.getRequestUriBuilder().path(student2.getId() + "");
        URI uri = ub.build();
		
		return Response.created(uri).entity(student2).build();
	}


	@POST
	@Path(value="/class")
	@Produces({"application/xml", "application/json"})
	public ScheduledClass addClass(@QueryParam("courseCode") String courseCode,
			@QueryParam("endDate") String startDate, @QueryParam("endDate") String endDate) {
		
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode, startDate, endDate);
				
		return sc;
	}

	@POST
	@Path(value="/class/{courseCode}/endDate/{endDate}/startDate/{startDate}")
	public ScheduledClass addClassPathParams(@PathParam("courseCode") String courseCode,
			@PathParam("endDate") String startDate, @PathParam("endDate") String endDate) {
		
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode, startDate, endDate);
				
		return sc;
	}
	
	@POST
	@Path(value="/registerStudentForClass")
	public Student registerStudent(@QueryParam("studentId") int studentId, 
			@QueryParam("classId") int classId) {
		
		ScheduledClass sClass = regService.getClassService().getScheduledClass(classId);
		regService.registerStudentForClass(studentId, sClass.getCourse().getCode(), sClass.getStartDate());
		
		Student student = regService.getStudentService().getStudent(studentId);
		
		return student;
	}

	@POST
	@Path(value="/class/{classId}/student/{studentId}")
	public Student registerStudentPathParam(@PathParam("studentId") int studentId, 
			@PathParam("classId") int classId) {
		
		ScheduledClass sClass = regService.getClassService().getScheduledClass(classId);
		regService.registerStudentForClass(studentId, sClass.getCourse().getCode(), sClass.getStartDate());
		
		Student student = regService.getStudentService().getStudent(studentId);
		
		return student;
	}

	
	public RegistrationService getRegService() {
		return regService;
	}

	public void setRegService(RegistrationService regService) {
		this.regService = regService;
	}

}
