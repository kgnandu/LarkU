package ttl.larku.dao.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

//@DBQualifier(DBType.STUDENT)
@ApplicationScoped
public class InMemoryStudentDAO implements BaseDAO<Student> {

	private Map<Integer, Student> students = new ConcurrentHashMap<Integer, Student>();
	private static AtomicInteger nextId = new AtomicInteger(1);

	// This is a hack to initialize the InMemoryDAOs
	@Inject
	private InMemoryCourseDAO courseDAO;
	@Inject
	private InMemoryClassDAO classDAO;

	public InMemoryStudentDAO() {
		int j = 0;
	}

	@PostConstruct
	public void init() {

		// Hack hack hack. Only works because we are @ApplicationScoped
		//And will only work if you call some StudentDAO method before
		//the other DAOs
		create(new Student("Roberta", "383 892 9292", Student.Status.FULL_TIME));
		create(new Student("Madhu", "383 393 9292", Student.Status.HIBERNATING));
		create(new Student("Tim", "989 938 6748", Student.Status.FULL_TIME));
		create(new Student("Amir", "776 636 8886", Student.Status.PART_TIME));

		Course c1 = courseDAO.create(new Course("MATH-101", "Baby Math"));
		Course c2 = courseDAO.create(new Course("BKT-201", "Intermediate Basket Weaving"));
		Course c3 = courseDAO.create(new Course("PHY-302", "Intro to Quantum Mechanics"));
		Course c4 = courseDAO.create(new Course("AST-202", "Basic Cosmology"));

		classDAO.create(new ScheduledClass(c1, "10/10/2015", "12/10/2015"));
		classDAO.create(new ScheduledClass(c3, "08/10/2015", "12/10/2016"));
		classDAO.create(new ScheduledClass(c2, "06/10/2015", "9/10/2015"));
	}

	@Override
	public void update(Student updateObject) {
		if (students.containsKey(updateObject.getId())) {
			students.put(updateObject.getId(), updateObject);
		}
	}

	@Override
	public void delete(Student student) {
		students.remove(student.getId());
	}

	@Override
	public Student create(Student newObject) {
		// Create a new Id
		int newId = nextId.getAndIncrement();
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
	}

	@Override
	public void createStore() {
		students = new HashMap<Integer, Student>();
		nextId = new AtomicInteger(1);
	}

	public Map<Integer, Student> getStudents() {
		return students;
	}

	public void setStudents(Map<Integer, Student> students) {
		this.students = students;
	}
}
