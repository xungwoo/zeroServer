package com.thirtygames.zero.common.service.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.LeagueMeta;

public interface LeagueMetaService extends MetaService<LeagueMeta, Integer> {

	public Boolean isLadderAI(String countryCode);

	public Integer getHighLeague(int league);

	public Integer getLowLeague(int league);
	

}