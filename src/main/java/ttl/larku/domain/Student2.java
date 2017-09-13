package ttl.larku.domain;

import java.util.ArrayList;
import java.util.List;

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


@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class Student2 {
	
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
	
	
	@ManyToMany(mappedBy="students", fetch=FetchType.EAGER)
	@XmlElementWrapper(name="classes")
	@XmlElement(name="class")
	//@JsonManagedReference
	private List<ScheduledClass2> classes = new ArrayList<ScheduledClass2>();
	
	private static int nextId = 0;
	
	public Student2() {
		this("Unknown");
	}
	
	public Student2(String name) {
		this.name = name;
		classes = new ArrayList<ScheduledClass2>();
	}
	
	public Student2(String name, List<ScheduledClass2> classes) {
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
	
	//@JsonIgnore
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
	

	public List<ScheduledClass2> getClasses() {
		return classes;
	}
	public void setClasses(List<ScheduledClass2> classes) {
		this.classes = classes;
	}
	
	
	public void addClass(ScheduledClass2 sClass) {
		classes.add(sClass);
	}

	public void dropClass(ScheduledClass2 sClass) {
		classes.remove(sClass);
	}

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		Student2 other = (Student2) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (phoneNumber == null) {
			if (other.phoneNumber != null)
				return false;
		} else if (!phoneNumber.equals(other.phoneNumber))
			return false;
		if (status != other.status)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Student2 [id=" + id + ", name=" + name + ", phoneNumber="
				+ phoneNumber + ", status=" + status + "]";
	}

}
