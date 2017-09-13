package ttl.larku.cdi.producer;

import javax.enterprise.inject.Produces;

import ttl.json.location.LocationFetcher;
import ttl.json.location.LocationFetcherImpl;
import ttl.json.location.OtherLocationFetcherImpl;
import ttl.larku.cdi.qualifier.GoogleLoc;

/**
 * Produces DAO's.  To change the DAO associated with DBType.STUDENT,
 *	just replace it with another @Produces method that returns something else.
 * Neither the Injection Point nor the Injected class need to be changed.
 * DI at it's best!! 
 * @author whynot
 *
 */
public class GeoProducer {
	
	@Produces
	public LocationFetcher getOtherLocationFetcher(OtherLocationFetcherImpl olf) {
		return olf;
	}

	@Produces
	@GoogleLoc
	public LocationFetcher getLocationFetcher(LocationFetcherImpl lf) {
		return lf;
	}
	

}
