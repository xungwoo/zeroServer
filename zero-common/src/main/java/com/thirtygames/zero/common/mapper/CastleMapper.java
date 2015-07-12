package com.thirtygames.zero.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Castle;

public interface CastleMapper extends GenericMapper<Castle, Integer> {

	int clearReward(Castle castle);

	Castle getByCastleId(@Param("accountId")String accountId, @Param("castleId")int castleId);


}

