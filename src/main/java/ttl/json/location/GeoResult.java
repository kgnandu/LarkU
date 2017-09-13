package ttl.json.location;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GeoResult {

	private double lat;
	private double lon;
	private String status;
	
	
	public GeoResult() { }

	public GeoResult(double lat, double lon, String status) {
		super();
		this.lat = lat;
		this.lon = lon;
		this.status = status;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "GeoResult [lat=" + lat + ", lon=" + lon + ", status=" + status + "]";
	}
	
	
}
