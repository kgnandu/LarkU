package ttl.larku.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnore;


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Student implements Serializable {
	
	public enum Status { 
		FULL_TIME,
		PART_TIME,
		HIBERNATING
	};
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	
	private String name;
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private Status status = Status.FULL_TIME;
	
	
	//@JsonManagedReference
	@ManyToMany(fetch=FetchType.EAGER)
	@XmlElementWrapper(name="classes")
	@XmlElement(name="class")
	private List<ScheduledClass> classes;
	
	private static int nextId = 0;
	
	public Student() {
		this("Unknown");
	}
	
	public Student(String name) {
		this(name, new ArrayList<ScheduledClass>());
		this.name = name;
		classes = new ArrayList<ScheduledClass>();
	}
	
	public Student(String name, String phoneNumber, Status status) {
		this(name, new ArrayList<ScheduledClass>());
		this.phoneNumber = phoneNumber;
		this.status = status;
	}

	public Student(String name, List<ScheduledClass> classes) {
		super();
		//this.id = nextId++;
		this.name = name;
		this.classes = classes;
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
	
	/*
	public boolean isFullTime() {
		return fullTime;
	}

	public void setFullTime(boolean fullTime) {
		this.fullTime = fullTime;
	}
	*/
	
	@JsonIgnore
	public Status [] getStatusList() {
		return Status.values();
	}
	
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}


	public List<ScheduledClass> getClasses() {
		return classes;
	}
	public void setClasses(List<ScheduledClass> classes) {
		this.classes = classes;
	}
	
	
	public void addClass(ScheduledClass sClass) {
		classes.add(sClass);
	}

	public void dropClass(ScheduledClass sClass) {
		classes.remove(sClass);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", status=" + status + ", classes=" + classes
				+ "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classes == null) ? 0 : classes.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Student other = (Student) obj;
		if (classes == null) {
			if (other.classes != null)
				return false;
		} else if (!classes.equals(other.classes))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
