package ttl.json.location;

import java.io.IOException;

import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;

import ttl.larku.cdi.interceptors.Perf;

public class LocationFetcherImpl implements LocationFetcher {

	public static void main(String[] args) throws IOException {
		//byte [] bytes = Files.readAllBytes(Paths.get("geoResult.json"));
		//String jsonString = new String(bytes);
		
		//LocationFetcher lf = new LocationFetcher();
		//GeoResult result = lf.readLocation(jsonString);
		
		//System.out.println("result is " + result);
		
		LocationFetcher lf = new LocationFetcherImpl();
		GeoResult result = lf.readAddress("22 Main Street, St. Louis, mo");
		
		System.out.println("result 2 is " + result);
	}
	
	@Override
	public GeoResult readLocation(String jsonString) throws JsonProcessingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		JsonNode root = mapper.readTree(jsonString);
		
		String status = root.path("status").asText();

		ArrayNode studentsNode = (ArrayNode) root.path("results");
		
		JsonNode result = studentsNode.get(0);
		
		double lat = result.path("geometry").path("location").path("lat").asDouble();
		double lon = result.path("geometry").path("location").path("lng").asDouble();
		
		
		GeoResult gr = new GeoResult(lat, lon, status);
		return gr;
		
	}
	
	private static final String baseAddress = "https://maps.googleapis.com/maps/api/geocode/json";


	@Override
	@Perf
	public GeoResult readAddress(String address) throws JsonProcessingException, IOException {
		WebClient target = WebClient.create(baseAddress);
		target.query("address", address);

		Response response = target.get();
		if(response.getStatus() >= 400) {
			throw new RuntimeException("Reponse Status was " + response.getStatus());
		}
		
		String jsonString = response.readEntity(String.class);
		
		GeoResult result = readLocation(jsonString);
		
		return result;
	}
} 
