package com.thirtygames.zero.common.generic.admintool;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;

public interface AdminMetaService<T, K> extends GenericService<T, K> {

	public int multiAdd(List<T> list) ;
	
	public T getByCache(K id, int myMetaRevision);
	public List<T> rangeByCache(int from, int length, int myMetaRevision);
	public List<T> searchByCache(T entity, int myMetaRevision);
	
}
