package com.thirtygames.zero.common.service.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.LoginReward;

public interface LoginRewardMetaService extends MetaService<LoginReward, Integer> {

	LoginReward getLoginReward();

}
