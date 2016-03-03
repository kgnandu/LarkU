package ttl.larku.controllers.resty;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class RestErrorObject {
	private String code;
	private String detail;
	private ErrorType type;
	
	public RestErrorObject(String code, String detail, ErrorType type) {
		super();
		this.code = code;
		this.detail = detail;
		this.type = type;
	}

	public RestErrorObject() {
		super();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public ErrorType getType() {
		return type;
	}

	public void setType(ErrorType type) {
		this.type = type;
	}

	public enum ErrorType{
		HIS_ERROR,
		HER_ERROR,
		NOT_MINE;
	}
}
