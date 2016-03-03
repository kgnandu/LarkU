package ttl.dummy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/first")
public class FirstService {

	@GET
	@Path("student") 
	public String getStudent() {
		return "/first/student";
	}
}
