package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.Reward;

public interface FirstBuyingRewardMapper extends GenericMapper<Reward, Integer> {

	List<Reward> rewardList(@Param("itemKey") int itemKey);
	
}
