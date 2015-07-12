package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.ResetDateInfo;
import com.thirtygames.zero.common.model.meta.LeaguePrize;
import com.thirtygames.zero.common.model.meta.PrizeMatch;

public interface LadderMapper extends GenericMapper<Ladder, String> {

	public List<LeaguePrize> getLeaguePrize();

	public List<PrizeMatch> getMatchPrize();

	public Ladder getAccountAndDeck(@Param("myId") String myId);

	public Ladder getLadderNoLock(String accountId);

	public ResetDateInfo getResetDateInfo(@Param("resetTime")String resetTime);

}

