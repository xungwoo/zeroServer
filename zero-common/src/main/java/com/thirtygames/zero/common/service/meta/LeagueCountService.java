package com.thirtygames.zero.common.service.meta;

import java.util.Map;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.LeagueCount;

public interface LeagueCountService extends MetaService<LeagueCount, Integer> {

	public LeagueCount getPrevLeagueCount(int league, String resetDate) ;

	public void increase(int league, String resetDate) ;

	public Map<String, String> getMaxResetDate();
	

}