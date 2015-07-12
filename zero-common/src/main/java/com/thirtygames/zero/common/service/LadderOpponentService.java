package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;

public interface LadderOpponentService extends GenericService<Ladder, String> {
	
	// Different Shard's opponent
	public Ladder getLadder(String opponentId);
	public LadderMatch opLadderUpdate(Ladder ladder, LadderMatch match);
	public Ladder getLadderNoLock(String opponentId);


}