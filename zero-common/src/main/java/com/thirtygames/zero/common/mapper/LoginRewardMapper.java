package com.thirtygames.zero.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.LoginReward;

public interface LoginRewardMapper extends GenericMapper<LoginReward, String> {

	public int checkRewardTime(@Param("accountId") String accountId);
	
}
