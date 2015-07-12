package com.thirtygames.zero.common.service.bossraid;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.BossCollection;

public interface BossCollectionService extends GenericService<BossCollection, String> {

	public List<BossCollection> getRewardInfoOfBossCollections(String accountId);

	public boolean isCompleteCollection(String accountId, List<String> idList);

	public void reward(String accountId, BossCollection collectionMeta);
	


}