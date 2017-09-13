package ttl.json.location;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;

import ttl.larku.cdi.interceptors.Logged;

public interface LocationFetcher {

	GeoResult readLocation(String jsonString) throws JsonProcessingException, IOException;

	GeoResult readAddress(String address) throws JsonProcessingException, IOException;

}