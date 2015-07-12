package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.LadderMapper;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.log.LadderMatchLogService;

@Slf4j
@Service("ladderOpponentService")
public class LadderOpponentServiceImpl extends GenericServiceImpl<LadderMapper, Ladder, String> implements LadderOpponentService {

	@Autowired
	LadderService ladderService;
	
	@Autowired
	LadderMatchLogService matchLogService;

	@Autowired
	DataSourceService dsManager;

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public Ladder getLadder(String accountId) {
		return mapper.get(accountId);
	};

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public LadderMatch opLadderUpdate(Ladder ladder, LadderMatch match) {
		// 내 accountId 는 상대방의 opponentId 
		String opOpponentId = ladder.getAccountId();
		String opAccountId = ladder.getOpponentId();
		boolean opIsWin = !ladder.getIsWin();
		int opMyLadderPoint = ladder.getOpLadder();
		int opOpLadderPoint = ladder.getMyLadder();

		ladder.setAccountId(opAccountId);
		ladder.setOpponentId(opOpponentId);
		ladder.setIsWin(opIsWin);
		ladder.setMyLadder(opMyLadderPoint);
		ladder.setOpLadder(opOpLadderPoint);
		ladder.setLadder(opMyLadderPoint + (opIsWin ? match.getWinnerPoint() : match.getLooserPoint()));
		ladder.setLastGameTimeStamp(ladder.getTimeStamp());
		ladder.setLastGameNo(ladder.getGameNo());
		//mapper.update(ladder);

		match.setLeague(ladder.getLeague());
		match.setResetDate(ladder.getResetDate());
		match.setAccountId(opAccountId);
		match.setOpponentId(opOpponentId);
		match.setOpDeck(ladder.getOpDeck());
		match.setOpTitle(ladder.getOpTitle());
		match.setOpNickName(ladder.getOpNickName());
		match.setIsWin(opIsWin);
		match.setMyLadderPoint(opMyLadderPoint);
		match.setOpLadderPoint(opOpLadderPoint);
		match.setTimeStamp(ladder.getTimeStamp());
		match.setGameNo(ladder.getGameNo());
		match.setRoomNo(ladder.getRoomNo());
		match.setPlayTime(ladder.getPlayTime());
		match.setIsAI(ladder.getIsAI());
		//matchLogService.save(match);
		return match;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public Ladder getLadderNoLock(String opponentId) {
		return mapper.getLadderNoLock(opponentId); 
	}
	
	


}
