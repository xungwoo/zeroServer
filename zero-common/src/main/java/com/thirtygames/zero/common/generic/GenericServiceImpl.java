package com.thirtygames.zero.common.generic;

import java.util.List;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.ResultHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.util.GenericUtil;

@Slf4j
public abstract class GenericServiceImpl<M extends GenericMapper<T, K>, T, K> implements GenericService<T, K> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ApplicationContext applicationContext;
	private Class<M> mapperClass;
	private Class<T> modelClass;

	protected M mapper;


	@SuppressWarnings("unchecked")
	public GenericServiceImpl() {
		this.mapperClass = (Class<M>) GenericUtil.getClassOfGenericTypeIn(getClass(), 0);
		this.modelClass = (Class<T>) GenericUtil.getClassOfGenericTypeIn(getClass(), 1);
	}

	@PostConstruct
	public void setUpMapper() {
		this.mapper = this.applicationContext.getBean(mapperClass);
	}

	@Override
	public int save(T entity)  {
		return mapper.save(entity);
	}

	@Override
	@Transactional	
	public int bulkSave(List<T> entities)  {
		int success = 0;
		for (T entity : entities) {
			success += mapper.save(entity);
		}
		return success;
	}
	
	@Override
	public int update(T entity)  {
		return mapper.update(entity);
	}

	@Override
	public int delete(K id) {
		return mapper.delete(id);
	}
	
	@Override
	@Transactional		
	public int multiDelete(List<K> ids) {
		int deleteRowCnt = 0;
		for (K id : ids) {
			mapper.delete(id);
			deleteRowCnt++;
		}

		return deleteRowCnt;
	}
	
	@Override
	public int updateIsDel(K id) {
		return mapper.updateIsDel(id);
	}

	@Override
	public T get(K id) {
		if (id == null || Strings.isNullOrEmpty(String.valueOf(id)))
			return null;

		return  mapper.get(id);
	}
	
	@Override
	public T getByAccountId(String accountId, K id) {
		return mapper.getByAccountId(accountId, id);
	}
	
	@Override
	public List<T> search(T entity) {
		return mapper.search(entity);
	}
	
	@Override
	public List<T> search(int from, int length, T entity) {
		return mapper.search(from, length, entity);
	}
	
	@Override
	public List<T> search(int from, int length, String sidx, String sord, T entity) {
		return mapper.search(from, length, sidx, sord, entity);
	}

	@Override
	public List<T> range(int from, int length) {
		return mapper.range(from, length);
	}

	@Override
	public T recent() {
		return mapper.recent();
	}

	@Override
	public int size(T entity) {
		return mapper.size(entity);
	}
	
	@Override
	public void excel(T entity, ResultHandler resultHandler) {
		throw new UnsupportedOperationException();
	}

}
