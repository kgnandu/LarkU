package ttl.larku.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import ttl.larku.dao.BaseDAO;
import ttl.larku.domain.ScheduledClass;

import static ttl.larku.dao.jpa.JPAHelper.*;

public class JPAClassDAO implements BaseDAO<ScheduledClass> {

	@PersistenceContext(unitName="LarkUPU_SE")
	private EntityManager entityManager;
	private static int nextId = 0;
	
	@Override
	public void update(ScheduledClass updateObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.merge(updateObject);
		commitAndCloseEM();
	}

	@Override
	public void delete(ScheduledClass objToDelete) {
		EntityManager entityManager = getEMAndBegin();
		ScheduledClass managed = entityManager.find(ScheduledClass.class, objToDelete.getId());
		entityManager.remove(managed);
		commitAndCloseEM();
	}

	@Override
	public ScheduledClass create(ScheduledClass newObject) {
		EntityManager entityManager = getEMAndBegin();
		entityManager.persist(newObject);
		commitAndCloseEM();
		return newObject;
	}

	@Override
	public ScheduledClass get(int id) {
		EntityManager entityManager = getEMAndBegin();
		ScheduledClass sc = entityManager.find(ScheduledClass.class, id);
		commitAndCloseEM();
		return sc;
	}

	@Override
	public List<ScheduledClass> getAll() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Select s from ScheduledClass s");
		List<ScheduledClass> classes = query.getResultList();
		commitAndCloseEM();
		return classes;
	}
	
	@Override
	public void deleteStore() {
		EntityManager entityManager = getEMAndBegin();
		Query query = entityManager.createQuery("Delete from ScheduledClass");
		query.executeUpdate();
		commitAndCloseEM();
	}
	
	@Override
	public void createStore() {
	}
}
