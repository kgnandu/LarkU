package ttl.larku.domain;

import java.net.URI;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

//import com.fasterxml.jackson.annotation.JsonProperty;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Link
{
	@XmlAttribute
	//@JsonProperty("href")
	private URI href;
	@XmlAttribute
	private String rel;
	@XmlAttribute
	private String type;
	
	public Link(URI href, String rel, String type) {
		this.href = href;
		this.rel = rel;
		this.type = type;
	}

	public Link(URI href, String rel) {
		this(href, rel, null);
	}

	public Link() {}
	
	public Link(URI href) {
		this.href = href;
	}
	
	public void setHref(URI href) {
		this.href = href;
	}

	public String getRel() {
		return rel;
	}

	public void setRel(String rel) {
		this.rel = rel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}