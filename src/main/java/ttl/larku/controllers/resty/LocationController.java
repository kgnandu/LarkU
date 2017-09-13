package ttl.larku.controllers.resty;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import ttl.json.location.GeoResult;
import ttl.json.location.LocationFetcher;
import ttl.larku.cdi.interceptors.Logged;
import ttl.larku.cdi.qualifier.GoogleLoc;

@Path("/location")
public class LocationController {

	@Inject @GoogleLoc
	private LocationFetcher locationFetcher; // = new LocationFetcherImpl();
	
	
	@GET
	public Response getLocationFromAddress(@QueryParam("address") String address) {
		try {
			GeoResult gr = locationFetcher.readAddress(address);
			
			return Response.status(Status.OK).entity(gr).build();

		} catch (IOException e) {
			GeoResult gr = new GeoResult();
			gr.setStatus("Error: " + e.getMessage());
			return Response.status(Status.BAD_REQUEST).entity(gr).build();
		}

	}
}
