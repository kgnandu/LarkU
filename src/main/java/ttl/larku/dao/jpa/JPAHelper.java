package ttl.larku.dao.jpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Injection of persistence context is not working, so we use this util class
 * till we figure it out
 * 
 * @author whynot
 *
 */
public class JPAHelper {
	private static EntityManagerFactory entityManagerFactory;
	private static ThreadLocal<EntityManager> localHolder;

	static {
		entityManagerFactory = Persistence.createEntityManagerFactory("LarkUPU_SE");
		localHolder = new ThreadLocal<EntityManager>();
	}

	public static EntityManager getEntityManager() {
		EntityManager em = localHolder.get();
		if (em == null) {
			em = entityManagerFactory.createEntityManager();
			localHolder.set(em);
		}
		return em;
	}

	public static EntityManager getEMAndBegin() {
		EntityManager em = getEntityManager();
		beginTransaction();
		return em;
	}

	public static void commitAndCloseEM() {
		try {
			commit();
		} catch (Exception e) {
			e.printStackTrace();
			rollback();
		}
		closeEntityManager();
	}

	public static void closeEntityManager() {
		EntityManager em = localHolder.get();
		if (em != null) {
			em.close();
			localHolder.set(null);
		}
	}

	public static void closeEntityManagerFactory() {
		entityManagerFactory.close();
	}

	public static void beginTransaction() {
		getEntityManager().getTransaction().begin();
	}

	public static void rollback() {
		getEntityManager().getTransaction().rollback();
	}

	public static void commit() {
		getEntityManager().getTransaction().commit();
	}
}
