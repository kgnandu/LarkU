package ttl.larku.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonProperty;

@XmlRootElement(name="Results")
public class OperationResult {
	
	@XmlElement(name="result")
	@JsonProperty("Results")
	public List<Integer> list = new ArrayList<>();
	
	public String error;

	@Override
	public String toString() {
		return "OperationResult [list=" + list + ", error=" + error + "]";
	}

}
