package ttl.larku.dao.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

@DBQualifier(DBType.STUDENT)
@ApplicationScoped
public class InMemoryStudentDAO implements BaseDAO<Student> {

	private Map<Integer, Student> students = new HashMap<Integer, Student>();
	private static int nextId = 1;
	
	
	public InMemoryStudentDAO() {
		int j = 0;
	}

	//@PostConstruct
	public void init()
	{
		create(new Student("Roberta"));
		create(new Student("Madhu"));
		create(new Student("Tim"));
		create(new Student("Amir"));
	}

	@Override
	public void update(Student updateObject) {
		if(students.containsKey(updateObject.getId())) {
			students.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(Student student) {
		students.remove(student.getId());
	}

	@Override
	public Student create(Student newObject) {
		//Create a new Id
		int newId = nextId++;
		newObject.setId(newId);
		students.put(newId, newObject);
		
		return newObject;
	}

	@Override
	public Student get(int id) {
		return students.get(id);
	}

	@Override
	public List<Student> getAll() {
		return new ArrayList<Student>(students.values());
	}
	
	@Override
	public void deleteStore() {
		students = null;
		nextId = 1;
	}
	
	@Override
	public void createStore() {
		students = new HashMap<Integer, Student>();
	}

	public Map<Integer, Student> getStudents() {
		return students;
	}

	public void setStudents(Map<Integer, Student> students) {
		this.students = students;
	}
}
