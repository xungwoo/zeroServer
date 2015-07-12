package com.thirtygames.zero.common.generic.admintool;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.apache.ibatis.session.ResultHandler;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.generic.GenericServiceImpl;

@Slf4j
@Transactional(propagation = Propagation.REQUIRES_NEW)
public abstract class AdminServiceImpl<M extends GenericMapper<T, K>, T, K> extends GenericServiceImpl<M, T, K> implements GenericService<T, K> {
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int save(T entity)  {
		return mapper.save(entity);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int update(T entity) {
		return mapper.update(entity);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int delete(K id) {
		return mapper.delete(id);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int multiDelete(List<K> ids) {
		int deleteRowCnt = 0;
		for (K id : ids) {
			mapper.delete(id);
			deleteRowCnt++;
		}

		return deleteRowCnt;
	}	

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public T get(K id) {
		if (id == null || Strings.isNullOrEmpty(String.valueOf(id)))
			return null;
		
		T entity = null;		
		for (DataSource dataSourceType : DataSource.GAME_SHARDS) {
			ContextHolder.setDataSource(dataSourceType);
			entity = mapper.get(id);
			
			if (entity != null) {
				break;
			}
		}		

		return entity;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<T> search(int from, int length, T entity) {
		List<T> result = Lists.newArrayList();
		
		/*for (DataSourceType dataSourceType : DataSourceType.ZERO_SHARDS) {
			ContextHolder.setDataSourceType(dataSourceType);
			result.addAll(injectShardIndexNumber(dataSourceType, mapper.search(from, length, entity)));
		}*/
		result = mapper.search(from, length, entity);
		
		return result;
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<T> search(int from, int length, String sidx, String sord, T entity) {
		List<T> result = Lists.newArrayList();
		
		// 전체 샤드 대상으로 검색은 아래 로직으로 수행
		/*for (DataSourceType dataSourceType : DataSourceType.ZERO_SHARDS) {
			ContextHolder.setDataSourceType(dataSourceType);
			result.addAll(injectShardIndexNumber(dataSourceType, mapper.search(from, length, sidx, sord, entity)));
		}*/
		result = mapper.search(from, length, sidx, sord, entity);
		
		return result;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<T> range(int from, int length) {
		List<T> result = Lists.newArrayList();
		
		/*for (DataSourceType dataSourceType : DataSourceType.ZERO_SHARDS) {
			ContextHolder.setDataSourceType(dataSourceType);
			result.addAll(injectShardIndexNumber(dataSourceType, mapper.range(from, length)));
		}*/
		result = mapper.range(from, length);
		
		return result;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public T recent() {
		return mapper.recent();
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int size(T entity) {
		/*int result = 0;
		
		for (DataSourceType dataSourceType : DataSourceType.ZERO_SHARDS) {
			ContextHolder.setDataSourceType(dataSourceType);
			result += mapper.size(entity);
		}*/
		
		return mapper.size(entity);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public void excel(T entity, ResultHandler resultHandler) {
		mapper.excel(entity, resultHandler);
	}
}
