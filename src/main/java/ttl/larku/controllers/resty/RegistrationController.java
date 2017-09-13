package ttl.larku.controllers.resty;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

import ttl.larku.domain.ScheduledClass;
import ttl.larku.service.RegistrationService;

@Path("/v1")
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
	
	public RegistrationService getRegService() {
		return regService;
	}

	public void setRegService(RegistrationService regService) {
		this.regService = regService;
	}
}
