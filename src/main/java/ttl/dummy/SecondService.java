package ttl.dummy;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/second")
public class SecondService {

	@GET
	@Path("student") 
	public String getStudent() {
		return "/second/student";
	}
}
