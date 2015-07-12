package com.thirtygames.zero.common.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.LoginRewardMapper;
import com.thirtygames.zero.common.model.LoginReward;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.meta.LoginRewardMetaService;

@Slf4j
@Service("loginRewardService")
public class LoginRewardServiceImpl extends GenericServiceImpl<LoginRewardMapper, LoginReward, String> implements LoginRewardService {
	
	public static final int ONE_DAY_TIMESTAMP = 86400;
	
	@Autowired
	RewardService rewardService;
	
	@Autowired
	LoginRewardMetaService loginRewardMetaService;

	@Override
	public boolean checkRewardTime(String accountId) {
		return mapper.checkRewardTime(accountId) == 1 ? true : false;
	}
	
	@Override
	@Transactional
	public Map<String, Object> loginReward(String accountId) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		LoginReward loginRewardMeta = loginRewardMetaService.get(0);
		LoginReward myLoginReward = mapper.get(accountId);
		log.debug("myLoginReward::" + myLoginReward);
		
		int rewardCount = 1;
		if (myLoginReward != null) {
			int dayGap = myLoginReward.getDayGap();
			int day = myLoginReward.getDayOfWeek();
			if (day > 1) {
				day = day - 1;
			} else {
				day = 7;
			}
			if (dayGap > 0 && dayGap < day) {
				rewardCount = myLoginReward.getRewardCount() + 1;
			}
		}
		
		loginRewardMeta.setRewardCount(rewardCount);
		
		Reward reward = new Reward();
		reward.setAccountId(accountId);
		reward.setReward(loginRewardMeta.getTodayReward());
		reward.setRewardType(loginRewardMeta.getTodayRewardType());
		reward.setReasonType(Reward.ReasonType.LoginReward.getCode());
		resultMap.put("reward", rewardService.reward(reward, false));
		
		loginRewardMeta.setAccountId(accountId);
		resultMap.put("resultCount", mapper.save(loginRewardMeta));
		resultMap.put("result", mapper.get(accountId));
		return resultMap;
	}

}

