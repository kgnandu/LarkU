package ttl.larku.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.Course;

import static ttl.larku.dao.jpa.JPAHelper.*;

public class JPACourseDAO implements BaseDAO<Course> {

	@PersistenceContext(unitName="LarkUPU_SE")
	private EntityManager entityManager;
	
	@Override
	public void update(Course updateObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.merge(updateObject);
		commitAndCloseEM();
	}

	@Override
	public void delete(Course objToDelete) {
		EntityManager entityManager = getEMAndBegin();
		Course managed = entityManager.find(Course.class, objToDelete.getId());
		entityManager.remove(managed);
		commitAndCloseEM();
	}

	@Override
	public Course create(Course newObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.persist(newObject);
		commitAndCloseEM();
		return newObject;
	}

	@Override
	public Course get(int id) {
		EntityManager entityManager = getEMAndBegin();
		Course c = entityManager.find(Course.class, id);
		commitAndCloseEM();
		return c;
	}

	@Override
	public List<Course> getAll() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Select s from Course s");
		List<Course> courses = query.getResultList();
		commitAndCloseEM();
		return courses;
	}
	
	@Override
	public void deleteStore() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Delete from Course");
		query.executeUpdate();
		commitAndCloseEM();
	}
	
	@Override
	public void createStore() {
	}
}
