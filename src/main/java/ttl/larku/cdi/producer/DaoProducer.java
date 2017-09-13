package ttl.larku.cdi.producer;

import javax.enterprise.inject.Produces;

import ttl.larku.cdi.qualifier.DBQualifier;
import ttl.larku.cdi.qualifier.DBType;
import ttl.larku.dao.BaseDAO;
import ttl.larku.dao.inmemory.InMemoryClassDAO;
import ttl.larku.dao.inmemory.InMemoryCourseDAO;
import ttl.larku.dao.inmemory.InMemoryStudentDAO;
import ttl.larku.domain.Course;
import ttl.larku.domain.ScheduledClass;
import ttl.larku.domain.Student;

/**
 * Produces DAO's.  To change the DAO associated with DBType.STUDENT,
 *	just replace it with another @Produces method that returns something else.
 * Neither the Injection Point nor the Injected class need to be changed.
 * DI at it's best!! 
 * @author whynot
 *
 */
public class DaoProducer {
	
	/*
	 * Produce In Memory DAOs
	 */
	@Produces
	@DBQualifier(DBType.STUDENT)
	public BaseDAO<Student> getStudentDao(InMemoryStudentDAO dao) {
		return dao;
	}

	@Produces
	@DBQualifier(DBType.COURSE)
	public BaseDAO<Course> getCourseDAO(InMemoryCourseDAO dao) {
		return dao;
	}

	@Produces
	@DBQualifier(DBType.CLASS)
	public BaseDAO<ScheduledClass> getClassDao(InMemoryClassDAO dao) {
		return dao;
	}
	
	/*
	 * Produce JPA DAOs
	 *
	@Produces
	@DBQualifier(DBType.STUDENT)
	public BaseDAO<Student> getStudentDao(JPAStudentDAO dao) {
		return dao;
	}
	
	@Produces
	@DBQualifier(DBType.COURSE)
	public BaseDAO<Course> getCourseDAO(JPACourseDAO dao) {
		return dao;
	}

	@Produces
	@DBQualifier(DBType.CLASS)
	public BaseDAO<ScheduledClass> getClassDao(JPAClassDAO dao) {
		return dao;
	}
	*/
}
