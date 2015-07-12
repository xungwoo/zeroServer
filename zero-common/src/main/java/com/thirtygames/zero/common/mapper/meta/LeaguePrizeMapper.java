package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.LeaguePrize;

public interface LeaguePrizeMapper extends GenericMapper<LeaguePrize, Integer> {

	public LeaguePrize getPrizeByRank(@Param("league")int league, @Param("rank")long rank, @Param("per")float per);


}

