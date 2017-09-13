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
import ttl.larku.domain.Course;

//@DBQualifier(DBType.COURSE)
@ApplicationScoped
public class InMemoryCourseDAO implements BaseDAO<Course> {

	private Map<Integer, Course> courses = new HashMap<Integer, Course>();
	private static int nextId = 0;
	
	public InMemoryCourseDAO() {
		int boo = 10;
	}
	
	//@PostConstruct
	public void init()
	{
		create(new Course("MATH-101", "Baby Math"));
		create(new Course("BKT-201", "Intermediate Basket Weaving"));
		create(new Course("PHY-302", "Intro to Quantum Mechanics"));
		create(new Course("AST-202", "Basic Cosmology"));
	}
	@Override
	public void update(Course updateObject) {
		if(courses.containsKey(updateObject.getId())) {
			courses.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(Course course) {
		courses.remove(course.getId());
	}

	@Override
	public Course create(Course newObject) {
		//Create a new Id
		int newId = nextId++;
		newObject.setId(newId);
		courses.put(newId, newObject);
		
		return newObject;
	}

	@Override
	public Course get(int id) {
		return courses.get(id);
	}

	@Override
	public List<Course> getAll() {
		return new ArrayList<Course>(courses.values());
	}
	
	@Override
	public void deleteStore() {
		courses = null;
	}
	
	@Override
	public void createStore() {
		courses = new HashMap<Integer, Course>();
	}
	
	public Map<Integer, Course> getCourses() {
		return courses;
	}

	public void setCourses(Map<Integer, Course> courses) {
		this.courses = courses;
	}
}
