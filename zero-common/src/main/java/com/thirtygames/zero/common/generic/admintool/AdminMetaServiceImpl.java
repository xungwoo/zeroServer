package com.thirtygames.zero.common.generic.admintool;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.generic.MetaService;

@Slf4j
public abstract class AdminMetaServiceImpl<M extends GenericMapper<T, K>, T, K> extends GenericServiceImpl<M, T, K> implements MetaService<T, K> {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(T entity)  {
		return mapper.save(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(T entity) {
		return mapper.update(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int delete(K id) {
		return mapper.delete(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> search(T entity) {
		return mapper.search(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> search(int from, int length, T entity) {
		return mapper.search(from, length, entity);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> search(int from, int length, String sidx, String sord, T entity) {
		return mapper.search(from, length, sidx, sord, entity);
	}
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> searchByCache(T entity, int myMetaRevision) {
		return mapper.search(entity);
	}	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int multiAdd(List<T> list)  {
		int resultCount = 0;
		for (T entity : list) {
			resultCount += this.save(entity);
		}
		return resultCount;
	}	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public T get(K id) {
		if (id == null || Strings.isNullOrEmpty(String.valueOf(id)))
			return null;
		
		return mapper.get(id);
	}	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> range(int from, int length) {
		return mapper.range(from, length);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public T getByCache(K id, int myMetaRevision) {
		if (id == null || Strings.isNullOrEmpty(String.valueOf(id)))
			return null;

		return mapper.get(id);
	}	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<T> rangeByCache(int from, int length, int myMetaRevision) {
		return mapper.range(from, length);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int size(T entity) {
		return mapper.size(entity);
	}
	
}