package com.thirtygames.zero.common.mapper.datasource;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.model.datasource.Shard;

public interface DataSourceMapper {

	public int save(Shard shard);
	public Shard get(Shard shard);
	public Shard getShardByAccountId(@Param("accountId")String accountId);
	public Integer getDataSourceType(@Param("id") String id);
	public Integer recentDataSourceType();
}
