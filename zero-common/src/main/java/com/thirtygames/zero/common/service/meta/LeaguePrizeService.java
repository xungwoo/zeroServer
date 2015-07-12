package com.thirtygames.zero.common.service.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.LeaguePrize;

public interface LeaguePrizeService extends MetaService<LeaguePrize, Integer> {

	public LeaguePrize getPrizeByRank(int league, long prevRank, float per);
	

}