package com.thirtygames.zero.common.generic;

import java.util.List;

public interface LogService<T, K> extends GenericService<T, K> {
	public int save(T entity) ;
	public int update(T entity) ;

	public T get(K id);
	public T getByAccountId(String accountId, K id);

	public List<T> search(T entity);
	public List<T> search(int from, int length, T entity);
	public List<T> search(int from, int length, String sidx, String sord, T entity);
	public List<T> range(int from, int length);
}
