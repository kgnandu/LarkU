package ttl.larku.controllers.resty;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ttl.larku.domain.Student;
import ttl.larku.service.StudentService;


@Path("studentservice")
public class SomeController { 

	@Inject
	private StudentService studentService;
	
	@GET
	@Path("/students/{id}")
	@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
	public Student getStudent(@PathParam("id") int id) {
		Student student = studentService.getStudent(id);
		return student;
	}
}
