package com.thirtygames.zero.common.generic.admintool;

import java.util.List;

public interface AdminLogService<T, K> {
	public int save(T entity) ;
	
	public int update(T entity);
	
	public int delete(K id);

	public T get(K id);

	public List<T> search(int from, int length, T entity);
	
	public List<T> search(int from, int length, String sidx, String sord, T entity);

	public List<T> range(int from, int length);
	
	public T recent();

	public int size(T entity);

}
