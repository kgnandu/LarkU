package ttl.larku.controllers.resty;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.service.RegistrationService;

@Path("")
public class RegistrationController {
	
	@Inject
	private RegistrationService regService;
	
	public RegistrationController() {
		int boo = 0;
	}
	@GET
	@Path(value="/classes")
	@Produces({"application/xml", "application/json"})
	public List<ScheduledClass> getAllClasses() {
		List<ScheduledClass> classes = regService.getClassService().getAllScheduledClasses();
		return classes;
	}

	@GET
	@Path(value="/classes/{id}")
	@Produces({"application/xml", "application/json"})
	public ScheduledClass getClassById(@PathParam("id") int id) {
		ScheduledClass cls = regService.getClassService().getScheduledClass(id);
		return cls;
	}
	
	
	@POST
	@Path(value="/admin/class")
	@Produces({"application/xml", "application/json"})
	public ScheduledClass addClass(@QueryParam("courseCode") String courseCode,
			@QueryParam("endDate") String startDate, @QueryParam("endDate") String endDate) {
		
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode, startDate, endDate);
				
		return sc;
	}

	@POST
	@Path(value="/admin/class/{courseCode}/endDate/{endDate}/startDate/{startDate}")
	public ScheduledClass addClassPathParams(@PathParam("courseCode") String courseCode,
			@PathParam("endDate") String startDate, @PathParam("endDate") String endDate) {
		
		ScheduledClass sc = regService.addNewClassToSchedule(courseCode, startDate, endDate);
				
		return sc;
	}
	
	@POST
	@Path(value="/admin/registerStudentForClass")
	public Student registerStudent(@QueryParam("studentId") int studentId, 
			@QueryParam("classId") int classId) {
		
		ScheduledClass sClass = regService.getClassService().getScheduledClass(classId);
		regService.registerStudentForClass(studentId, sClass.getCourse().getCode(), sClass.getStartDate());
		
		Student student = regService.getStudentService().getStudent(studentId);
		
		return student;
	}

	@POST
	@Path(value="/admin/class/{classId}/student/{studentId}")
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
