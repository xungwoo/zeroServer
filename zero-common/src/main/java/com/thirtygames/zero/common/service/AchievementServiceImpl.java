package com.thirtygames.zero.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.AchievementMapper;
import com.thirtygames.zero.common.mapper.ResourceMapper;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.meta.Reward;

@Slf4j
@Service("achievementService")
public class AchievementServiceImpl extends GenericServiceImpl<AchievementMapper, Achievement, Integer> implements AchievementService {

	@Autowired
	ResourceMapper arMapper;
	
	@Autowired
	RewardService rewardService;

	@Override
	@Transactional
	public Reward reward(Achievement rewardAchv, List<Achievement> metaList, boolean isReturnResource)  {
		String accountId = rewardAchv.getAccountId();
		Achievement nextStep = new Achievement();
		Achievement currentStep = metaList.get(0);
		currentStep.setAccountId(accountId);
		if (currentStep.getGoal() > rewardAchv.getCurrent()) {
			throw new RestException("Not.Complete.Achivement.");
		}
		
		if (metaList.size() > 1) {
			nextStep = metaList.get(1);
			nextStep.setCurrent(rewardAchv.getCurrent());
			nextStep.setRewardDone(false);
			rewardAchv.setStep(nextStep.getStep());
		} else {
			rewardAchv.setRewardDone(true);
		}
		mapper.update(rewardAchv);
		
		Reward rewardParam = new Reward();
		rewardParam.setAccountId(accountId);
		rewardParam.setReward(currentStep.getReward());
		rewardParam.setRewardType(currentStep.getRewardType());
		rewardParam.setReasonType(Reward.ReasonType.Achievement.getCode());
		Reward resultReward = rewardService.reward(rewardParam, isReturnResource);	
		resultReward.setNextStep(nextStep);
		return resultReward;
	}

	@Override
	@Transactional
	public Integer multiAdd(List<Achievement> achvList)  {
		int resultCount = 0;
		for (Achievement achv : achvList) {
			resultCount += this.save(achv);
		}
		return resultCount;
	}

	@Override
	public List<Achievement> getAchvList(String accountId) {
		return mapper.getAchvList(accountId);
	}

	@Override
	@Transactional
	public List<Reward> rewardMulti(List<Achievement> myAchvList, List<Achievement> metaList)  {
		List<Reward> resultList = new ArrayList<Reward>();
		
		Iterator<Achievement> itMyAchievement = myAchvList.iterator();
		while(itMyAchievement.hasNext()) {
			Achievement myAchievement = itMyAchievement.next();
			//log.debug("myAchv::" + myAchievement);
			Iterator<Achievement> itAchievementMeta = metaList.iterator();
			while(itAchievementMeta.hasNext()) {
				Achievement meta = itAchievementMeta.next();
				//log.debug("myAchvMeta::" + meta);
				if (myAchievement.getAchievementId().equals(meta.getAchievementId()) && myAchievement.getStep().equals(meta.getStep())) {
					if (!myAchievement.getRewardDone() && myAchievement.getCurrent() >= meta.getGoal()) {
						resultList.add(this.reward(myAchievement, meta, false));
					}
					itAchievementMeta.remove();
					break;	// sorted list.
				}
			}
		}		
		
		return resultList;
	}
	

	@Transactional
	private Reward reward(Achievement rewardAchv, Achievement meta, boolean isReturnResource)  {
		if (meta.getIsLastStep()) {
			rewardAchv.setRewardDone(true);
		} else {
			rewardAchv.setStep(meta.getStep() + 1);
		}
		mapper.update(rewardAchv);
		
		Reward reward = new Reward();
		reward.setAccountId(rewardAchv.getAccountId());
		reward.setReward(meta.getReward());
		reward.setRewardType(meta.getRewardType());
		reward.setReasonType(Reward.ReasonType.Achievement.getCode());
		return rewardService.reward(reward, isReturnResource);
	}	

}
