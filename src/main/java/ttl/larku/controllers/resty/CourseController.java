package ttl.larku.controllers.resty;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import ttl.larku.domain.Course;
import ttl.larku.service.RegistrationService;

@Path("/v1/courses")
public class CourseController {
	
	@Inject
	private RegistrationService regService;
	
	public CourseController() {
		int boo = 9;
	}

	@GET
	@Path(value="") 
	@Produces({"application/xml", "application/json"})
	public List<Course> getCourses() {
		List<Course> courses = regService.getCourseService().getAllCourses();
		return courses;
	}
	
	@GET
	@Path(value="{id}")
	public Course getCourse(@PathParam("id") int id) {
		Course course = regService.getCourseService().getCourse(id);
		return course;
	}
	
	@POST
	@Path(value="")
	public Course addCourse(Course course) {
		Course newCourse = regService.getCourseService().createCourse(course);
		
		return newCourse;
	}

	public RegistrationService getRegService() {
		return regService;
	}

	public void setRegService(RegistrationService regService) {
		this.regService = regService;
	}
}
