package com.thirtygames.zero.common.service.datasource;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.mapper.datasource.DataSourceMapper;
import com.thirtygames.zero.common.mapper.index.FriendMapper;
import com.thirtygames.zero.common.model.datasource.Shard;

@Slf4j
@Service("dataSourceService")
public class DataSourceService {

	@Autowired
	private DataSourceMapper dataSourceMapper;

	@Autowired
	private FriendMapper friendMapper;

	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public void switchDataSource(String accountId)  {
		ContextHolder.setDynamicDS(DataSource.getAccountDS(accountId));
	}
	
	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public void switchDataSource(DataSource dst)  {
		ContextHolder.setDynamicDS(dst);
	}
	
	@Deprecated
	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public DataSource getDataSourceType(String accountId)  {
		Integer dataSourceTypeNo = dataSourceMapper.getDataSourceType(accountId);
		if (dataSourceTypeNo == null) {
			// 실제로는 500으로 날아감 ㅡㅡ;; runtimeException
			throw new RestException(HttpServletResponse.SC_UNAUTHORIZED, "Not.found.dataSourceTypeNo:::" + accountId);
		}

		DataSource dst = DataSource.ZERO_GAME_1;
		if (dataSourceTypeNo == 2) {
			dst = DataSource.ZERO_GAME_2;
		}

		return dst;
	}

	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public Shard getShard(Shard shardParam)  {
		Shard shard = dataSourceMapper.get(shardParam);
		if (shard != null) {
			ContextHolder.setDataSource(DataSource.getAccountDS(shard.getAccountId()));
		}

		return shard;
	}	

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public int saveShard(Shard shard)  {
		return dataSourceMapper.save(shard);
	}

	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public Shard getShardByAccountId(String accountId)  {
		Shard shard = dataSourceMapper.getShardByAccountId(accountId);
		return shard;
	}
	


}
