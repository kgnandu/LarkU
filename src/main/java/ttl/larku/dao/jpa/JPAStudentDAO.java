package ttl.larku.dao.jpa;

import static ttl.larku.dao.jpa.JPAHelper.commitAndCloseEM;
import static ttl.larku.dao.jpa.JPAHelper.getEMAndBegin;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Student;

public class JPAStudentDAO implements BaseDAO<Student> {

	@PersistenceUnit(unitName="LarkUPU_SE")
	private EntityManagerFactory factory;

	@PersistenceContext(unitName="LarkUPU_NON_JTA")
	private EntityManager entityManager;
	
	
	@Override
	public void update(Student updateObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.merge(updateObject);
		commitAndCloseEM();
	}

	@Override
	public void delete(Student objToDelete) {
		EntityManager entityManager = getEMAndBegin();
		Student managed = entityManager.find(Student.class, objToDelete.getId());
		entityManager.remove(managed);
		commitAndCloseEM();
	}

	@Override
	public Student create(Student newObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.persist(newObject);
		commitAndCloseEM();
		return newObject;
	}

	@Override
	public Student get(int id) {
		EntityManager entityManager = getEMAndBegin();
		Student s = entityManager.find(Student.class, id);
		commitAndCloseEM();
		return s;
	}

	@Override
	public List<Student> getAll() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Select s from Student s");
		List<Student> students = query.getResultList();
		commitAndCloseEM();
		return students;
	}
	
	@Override
	public void deleteStore() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Delete from Student");
		query.executeUpdate();
		commitAndCloseEM();
	}
	
	@Override
	public void createStore() {
	}
}
