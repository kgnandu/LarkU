package ttl.larku.controllers.resty;

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import ttl.larku.domain.Link;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;
import ttl.larku.domain.StudentLinks;
import ttl.larku.domain.StudentListHolder;
import ttl.larku.exception.LarkUException;
import ttl.larku.service.RegistrationService;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Path("/v1/students")
public class StudentRestyController {

	
	private final static String classesPath = "classes";
	private final static String studentsPath = "students";

	/*
	 * This is what actually gets the work done
	 */
	@Inject
	private RegistrationService regService;
	
	
	/**
	 * To make URIs for Links and other things
	 */
	@Context
	private UriInfo uriInfo;

	public StudentRestyController() {
		int boo = 9;
	}
	/**
	 * Return a whole Student by id as Path Parameter
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@GET
	@Path("/{id}")
	@Produces({ "application/xml", "application/json" ,"application/x-java-serialized-object"})
	public Student getStudentXJWithPathVariable(@PathParam("id") int id) throws JsonGenerationException, JsonMappingException, IOException {
		if(id == -1) {
			throw new LarkUException("Whoops, id can't be negative ");
		}
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			throw new LarkUException("Student " + id + " was not home");
		}
		
		/*
		ObjectMapper mapper = new ObjectMapper();
		//Configure mapper
		mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
		SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-dd-MM");
		mapper.setDateFormat(outputFormat);
		
		StringWriter sw = new StringWriter();
		//Convert
		mapper.writeValue(sw, student);
		//System.out.println("Hand mapped student is " + sw.toString());
		 */

		return student;
	}


	/**
	 * Return a Student, with the courses presented as
	 * Links.
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}/link")
	@Produces({ "application/xml", "application/json" ,"application/x-java-serialized-object"})
	public StudentLinks getStudentXJWithCourseLink(@PathParam("id") int id) {
		if(id == -1) {
			throw new LarkUException("Whoops, id can't be negative ");
		}
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			throw new LarkUException("Student " + id + " was not home");
		}

		List<ScheduledClass> classes = student.getClasses();
		List<Link> classLinks = new ArrayList<>();
		for(ScheduledClass sc : classes) {
		
			UriBuilder ub = uriInfo.getBaseUriBuilder().path(classesPath).path(sc.getId() + "");
			URI uri = ub.build();
			
			classLinks.add(new Link(uri, "class"));
		}
		
		StudentLinks sl = new StudentLinks(student, classLinks);

		return sl;
	}

	/**
	 * Return a Student, with the courses presented as
	 * Atom Links.
	 * @param id
	 * @return
	 */
	@GET
	@Path("/{id}/atomlink")
	@Produces({ "application/xml", "application/json" ,"application/x-java-serialized-object"})
	public StudentLinks getStudentXJWithCourseAtomLink(@PathParam("id") int id) {
		if(id == -1) {
			throw new LarkUException("Whoops, id can't be negative ");
		}
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			throw new LarkUException("Student " + id + " was not home");
		}

		List<ScheduledClass> classes = student.getClasses();
		List<Link> classLinks = new ArrayList<>();
		for(ScheduledClass sc : classes) {
		
			UriBuilder ub = uriInfo.getBaseUriBuilder().path(classesPath).path(sc.getId() + "");
			URI uri = ub.build();
			
			classLinks.add(new Link(uri));
		}
		
		StudentLinks sl = new StudentLinks(student, classLinks);

		return sl;
	}

	/*
	 * Return a Student by id as Query Parameter
	 */
	//@GET
	//@Path("/students")
	@Produces({ "application/xml", "application/json" })
	public Object getStudentXJWithRequestParam(@QueryParam("id") int id) {
		Student student = regService.getStudentService().getStudent(id);
		if (student == null) {
			return new RestErrorObject("Student not found", "Student with id " + id +
					" does not exist", RestErrorObject.ErrorType.NOT_MINE);
		}
		return student;
	}


	@GET
	@Path("")
	@Produces({ "application/xml", "application/json", "application/x-java-serialized-object"})
	public List<Student> getStudentsXmlJson() {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		return students;
	}

	@GET
	@Path("/heldstudents")
	@Produces({ "application/xml", "application/json" })
	public StudentListHolder getStudentsXmlJsonHolder() {
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		return new StudentListHolder(students);
	}

	@GET
	@Path("/links")
	@Produces({ "application/xml", "application/json"})
	public List<Link> getStudentsXmlJsonLinks() {
		List<String> matched = uriInfo.getMatchedURIs();
		//System.out.println("matched: " + matched);
		List<Student> students = regService.getStudentService()
				.getAllStudents();
		List<Link> uris = new ArrayList<Link>();
		for(Student student : students) {
			UriBuilder ub = uriInfo.getBaseUriBuilder().path(studentsPath).path(student.getId() + "");
			URI uri = ub.build();
			uris.add(new Link(uri, "student"));
		}
		return uris;
	}
	

	public RegistrationService getRegService() {
		return regService;
	}


	public void setRegService(RegistrationService regService) {
		this.regService = regService;
	}
}