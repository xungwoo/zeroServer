package com.thirtygames.zero.common.mapper.meta;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.LoginReward;

public interface LoginRewardMetaMapper extends GenericMapper<LoginReward, Integer> {

	LoginReward getLoginReward();
	
}
