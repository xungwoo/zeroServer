package com.thirtygames.zero.common.service.bossraid;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.BossCollectionMapper;
import com.thirtygames.zero.common.model.BossCollection;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.ReasonType;
import com.thirtygames.zero.common.service.RewardService;

@Slf4j
@Service("bossCollectionService")
public class BossCollectionServiceImpl extends GenericServiceImpl<BossCollectionMapper, BossCollection, String> implements BossCollectionService {
	
	
	@Autowired
	RewardService rewardService;

	@Override
	public List<BossCollection> getRewardInfoOfBossCollections(String accountId) {
		return mapper.getRewardInfoOfBossCollections(accountId);
	}

	@Override
	public boolean isCompleteCollection(String accountId, List<String> idList) {
		return (mapper.isCompleteCollection(accountId, idList) == idList.size()) ? true : false;
	}
	

	@Override
	@Transactional
	public void reward(String accountId, BossCollection meta) {
		BossCollection rewardedCollection = mapper.getCollection(accountId, meta.getCollectionId());
		if (rewardedCollection != null && rewardedCollection.getIsRewarded()) {
			throw new RestException(Errors.AlreadyRewardedBossCollection);
		}
		
		BossCollection bossColl = new BossCollection();
		bossColl.setAccountId(accountId);
		bossColl.setCollectionId(meta.getCollectionId());
		bossColl.setIsRewarded(true);
		mapper.save(bossColl);
		
		Reward collectionReward = new Reward();
		collectionReward.setAccountId(accountId);
		collectionReward.setReasonType(ReasonType.BossCollection.getCode());
		collectionReward.setRewardType(meta.getRewardType());
		collectionReward.setReward(meta.getReward());
		rewardService.reward(collectionReward, false);		
	}
	

}
