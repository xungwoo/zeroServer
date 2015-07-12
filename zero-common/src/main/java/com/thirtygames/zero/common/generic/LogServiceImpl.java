package com.thirtygames.zero.common.generic;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;

@Slf4j
public abstract class LogServiceImpl<M extends GenericMapper<T, K>, T, K> extends GenericServiceImpl<M, T, K> implements GenericService<T, K> { 

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)	
	public int save(T entity)  {
		return mapper.save(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public int update(T entity)  {
		return mapper.update(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public T get(K id) {
		if (id == null || Strings.isNullOrEmpty(String.valueOf(id)))
			return null;

		return  mapper.get(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public T getByAccountId(String accountId, K id) {
		return mapper.getByAccountId(accountId, id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public List<T> search(T entity) {
		return mapper.search(entity);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public List<T> search(int from, int length, T entity) {
		return mapper.search(from, length, entity);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public List<T> search(int from, int length, String sidx, String sord, T entity) {
		return mapper.search(from, length, sidx, sord, entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)	
	public List<T> range(int from, int length) {
		return mapper.range(from, length);
	}

}
