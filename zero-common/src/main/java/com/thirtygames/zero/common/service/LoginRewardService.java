package com.thirtygames.zero.common.service;

import java.util.Map;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.LoginReward;

public interface LoginRewardService extends GenericService<LoginReward, String> {

	public boolean checkRewardTime(String accountId);

	public Map<String, Object> loginReward(String myAccountId);

}
