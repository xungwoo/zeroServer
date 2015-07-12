package com.thirtygames.zero.common.mapper.meta;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.LeagueCount;

public interface LeagueCountMapper extends GenericMapper<LeagueCount, Integer> {

	public LeagueCount getPrevLeagueCount(@Param("league")int league, @Param("resetDate")String resetDate);

	public Map<String, String> getMaxResetDate();


}

