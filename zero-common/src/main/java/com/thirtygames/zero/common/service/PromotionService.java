package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.model.meta.Reward;


public interface PromotionService {
	public String missionKeyFiltering(String missionKey);
	public List<Reward> checkMissionAndReward(String accountId, String memberNo, String missionKey, String lang) ;
}
