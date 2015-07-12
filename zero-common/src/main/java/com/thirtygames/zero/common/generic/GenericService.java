package com.thirtygames.zero.common.generic;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;

public interface GenericService<T, K> {
	public int save(T entity) ;
	public int bulkSave(List<T> entites) ;
	
	public int update(T entity) ;
	
	public int delete(K id);
	public int updateIsDel(K id);
	public int multiDelete(List<K> ids);

	public T get(K id);
	public T getByAccountId(String accountId, K id);

	public List<T> search(T entity);
	public List<T> search(int from, int length, T entity);
	public List<T> search(int from, int length, String sidx, String sord, T entity);
	public List<T> range(int from, int length);
	public void excel(T entity, ResultHandler resultHandler);
	
	public T recent();
	public int size(T entity);

}
