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
	
	public void delete(T deleteObject);
	
	public T create(T newObject);
	
	public T get(int id);
	
	public List<T> getAll();
	
	public void deleteStore();
	
	public void createStore();
}
