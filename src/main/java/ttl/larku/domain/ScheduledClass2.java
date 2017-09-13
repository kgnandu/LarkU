package ttl.larku.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
//@Entity
public class ScheduledClass2 {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@XmlTransient
	//@JsonBackReference
	@ManyToMany(fetch=FetchType.EAGER)
	private List<Student2> students = new ArrayList<Student2>();
	
	private String startDate;
	private String endDate;
	
	
	@ManyToOne
	private Course course;
	
	private static int nextId = 0;
	
	public ScheduledClass2() {
	}
	
	public ScheduledClass2(Course course, String startDate,
			String endDate) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.course = course;
		
		//setId(nextId++);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	//@JsonIgnore
	public List<Student2> getStudents() {
		return students;
	}
	
	//@JsonIgnore
	public void setStudents(List<Student2> students) {
		this.students = students;
	}
	public void addStudent(Student2 student) {
		students.add(student);
	}
	
	public void removeStudent(Student2 student) {
		//students.remove(student);
		
		//Do the other side of the relationship
		//student.dropClass(this);
	}
	
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((endDate == null) ? 0 : endDate.hashCode());
		result = prime * result + id;
		result = prime * result
				+ ((startDate == null) ? 0 : startDate.hashCode());
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
		ScheduledClass2 other = (ScheduledClass2) obj;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (endDate == null) {
			if (other.endDate != null)
				return false;
		} else if (!endDate.equals(other.endDate))
			return false;
		if (id != other.id)
			return false;
		if (startDate == null) {
			if (other.startDate != null)
				return false;
		} else if (!startDate.equals(other.startDate))
			return false;
		return true;
	}

}
