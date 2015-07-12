package com.thirtygames.zero.common.generic;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.ResultHandler;

import com.thirtygames.zero.common.etc.exception.MapperException;

public interface GenericMapper<T, K> {
	public int save(T entity) throws MapperException;
	public int update(T entity);
	public int delete(@Param("id") K id);
	public int updateIsDel(@Param("id") K id);
	public T get(@Param("id") K id);
	public T getByAccountId(@Param("accountId") String accountId, @Param("id") K id);
	public List<T> range(@Param("from") int from, @Param("length")int length);
	public List<T> search(@Param("model") T entity );
	public List<T> search(@Param("from") int from, @Param("length")int length, @Param("model" )T entity );
	public List<T> search(@Param("from") int from, @Param("length")int length, @Param("sidx")String sidx, @Param("sord") String sord , @Param("model")T entity);
	public void excel(@Param("model") T entity, ResultHandler resultHandler);
	public T recent();
	public int size(@Param("model") T entity);
}
