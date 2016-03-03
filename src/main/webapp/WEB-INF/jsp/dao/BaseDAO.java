package ttl.larku.dao;

import java.util.List;

/**
 * 
 * @author anil
 *
 * @param <T>
 */
public interface BaseDAO<T> {

	public void update(T updateObject);
	
	public void delete(int id);
	
	public int create(T newObject);
	
	public T get(int id);
	
	public List<T> getAll();
	
	public void deleteStore();
	
	public void createStore();
}
