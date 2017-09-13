package ttl.larku.odomain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class StudentLinks implements Serializable {
	
	private int id;
	private String name;
	private String phoneNumber;
	
	private Student.Status status = Student.Status.FULL_TIME;
	
	
	@XmlElementWrapper(name="classLinks")
	@XmlElement(name="link")
	private List<Link> classLinks;
	
	private static int nextId = 0;
	
	public StudentLinks() {
	}
	
	public StudentLinks(Student s, List<Link> links) {
		this.id = s.getId();
		this.name = s.getName();
		this.phoneNumber = s.getPhoneNumber();
		this.status = s.getStatus();
		
		this.classLinks = links;
	}
	
	public StudentLinks(String name, List<Link> classes) {
		super();
		this.name = name;
		this.classLinks = classes;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Student.Status getStatus() {
		return status;
	}

	public void setStatus(Student.Status status) {
		this.status = status;
	}


	public List<Link> getClasses() {
		return classLinks;
	}
	public void setClasses(List<Link> classes) {
		this.classLinks = classes;
	}

	@Override
	public String toString() {
		return "StudentLinks [id=" + id + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", status=" + status + ", classLinks="
				+ classLinks + "]";
	}
	
}
