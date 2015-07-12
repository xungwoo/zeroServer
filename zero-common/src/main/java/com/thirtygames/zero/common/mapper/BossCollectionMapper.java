package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossCollection;

public interface BossCollectionMapper extends GenericMapper<BossCollection, String> {

	List<BossCollection> getRewardInfoOfBossCollections(@Param("accountId")String accountId);

	int isCompleteCollection(@Param("accountId")String accountId, @Param("idList")List<String> idList);

	BossCollection getCollection(@Param("accountId")String accountId, @Param("collectionId")int collectionId);

	
	

}

