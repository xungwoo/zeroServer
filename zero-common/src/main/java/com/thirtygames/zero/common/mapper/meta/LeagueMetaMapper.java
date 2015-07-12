package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.LeagueMeta;

public interface LeagueMetaMapper extends GenericMapper<LeagueMeta, Integer> {

	public Boolean isLadderAI(@Param("code") String countryCode);

	public Integer getHighLeague(@Param("league")int league);

	public Integer getLowLeague(@Param("league")int league);

}

