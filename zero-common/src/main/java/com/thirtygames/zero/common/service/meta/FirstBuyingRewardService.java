package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.Reward;

public interface FirstBuyingRewardService extends MetaService<Reward, Integer> {

	List<Reward> rewardList(int itemKey);

}
