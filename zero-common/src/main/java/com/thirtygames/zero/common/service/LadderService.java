package com.thirtygames.zero.common.service;

import java.util.Map;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.model.ResetDateInfo;
import com.thirtygames.zero.common.model.meta.LeagueMeta;

public interface LadderService extends GenericService<Ladder, String> {
	
	public LadderMatch calcLadderPoint(Ladder ladder);
	public LadderMatch myLadderUpdate(Ladder ladder, LadderMatch match) ;

	public Ladder getAccountAndDeck(String accountId);
	public Map<String, Object> leagueResult(String accountId, long rank, String resetDate, int league, int totalCount) ;
	public Map<String, Object> matchResult(Ladder ladder, LeagueMeta leagueMeta);
	public Ladder getLadderNoLock(String accountId);
	public ResetDateInfo getResetDateInfo();

}