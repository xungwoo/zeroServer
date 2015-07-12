package com.thirtygames.zero.common.service;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.LadderMapper;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.model.ResetDateInfo;
import com.thirtygames.zero.common.model.meta.ApiMeta;
import com.thirtygames.zero.common.model.meta.LeagueMeta;
import com.thirtygames.zero.common.model.meta.LeaguePrize;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.ReasonType;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.log.LadderMatchLogService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;
import com.thirtygames.zero.common.service.meta.LeagueCountService;
import com.thirtygames.zero.common.service.meta.LeagueMetaService;
import com.thirtygames.zero.common.service.meta.LeaguePrizeService;

@Slf4j
@Service("ladderService")
public class LadderServiceImpl extends GenericServiceImpl<LadderMapper, Ladder, String> implements LadderService {
	
	@Autowired
	DataSourceService dsManager;

	@Autowired
	LadderOpponentService ladderOpponentService;
	
	@Autowired
	LadderMatchLogService matchLogService;

	@Autowired
	LeaguePrizeService prizeMetaService;

	@Autowired
	LeagueMetaService leagueMetaService;

	@Autowired
	LeagueCountService countMetaService;

	@Autowired
	RewardService rewardService;
	
	@Autowired
	DeckInfoService deckInfoService;
	
	@Autowired
	ApiMetaService apiMetaService;

	@Override
	public LadderMatch myLadderUpdate(Ladder ladder, LadderMatch match) {
		log.debug("#### myLadderUpdate ladder::" + ladder);
		ladder.setLadder(ladder.getMyLadder() + (ladder.getIsWin() ? match.getWinnerPoint() : match.getLooserPoint()));
		ladder.setLastGameTimeStamp(ladder.getTimeStamp());
		ladder.setLastGameNo(ladder.getGameNo());
		mapper.update(ladder);

		match.setLeague(ladder.getLeague());
		match.setResetDate(ladder.getResetDate());
		match.setIsWin(ladder.getIsWin());
		match.setAccountId(ladder.getAccountId());
		match.setOpponentId(ladder.getOpponentId());
		match.setOpDeck(ladder.getOpDeck());
		match.setOpNickName(ladder.getOpNickName());
		match.setOpTitle(ladder.getOpTitle());
		match.setMyLadderPoint(ladder.getMyLadder());
		match.setOpLadderPoint(ladder.getOpLadder());
		match.setTimeStamp(ladder.getTimeStamp());
		match.setGameNo(ladder.getGameNo());
		match.setRoomNo(ladder.getRoomNo());
		match.setPlayTime(ladder.getPlayTime());
		match.setIsAI(ladder.getIsAI());
		matchLogService.save(match);
		log.debug("#### myLadderUpdate match::" + match);
		return match;
	}
	
	public LadderMatch opLadderUpdate(Ladder ladder, LadderMatch match) {
		// 내 accountId 는 상대방의 opponentId 
		String opOpponentId = ladder.getAccountId();
		String opAccountId = ladder.getOpponentId();
		boolean opIsWin = !ladder.getIsWin();
		int opMyLadder = ladder.getOpLadder();
		int opOpLadder = ladder.getMyLadder();

		ladder.setAccountId(opAccountId);
		ladder.setOpponentId(opOpponentId);
		ladder.setIsWin(opIsWin);
		ladder.setMyLadder(opMyLadder);
		ladder.setOpLadder(opOpLadder);
		ladder.setLadder(opMyLadder + (opIsWin ? match.getWinnerPoint() : match.getLooserPoint()));
		ladder.setLastGameTimeStamp(ladder.getTimeStamp());
		ladder.setLastGameNo(ladder.getGameNo());
		mapper.update(ladder);

		match.setLeague(ladder.getLeague());
		match.setResetDate(ladder.getResetDate());
		match.setAccountId(opAccountId);
		match.setOpponentId(opOpponentId);
		match.setOpDeck(ladder.getOpDeck());
		match.setOpTitle(ladder.getOpTitle());
		match.setOpNickName(ladder.getOpNickName());
		match.setIsWin(opIsWin);
		match.setMyLadderPoint(opMyLadder);
		match.setOpLadderPoint(opOpLadder);
		match.setTimeStamp(ladder.getTimeStamp());
		match.setGameNo(ladder.getGameNo());
		match.setRoomNo(ladder.getRoomNo());
		match.setPlayTime(ladder.getPlayTime());
		match.setIsAI(ladder.getIsAI());
		matchLogService.save(match);
		return match;
	}	

	@Override
	public Ladder getAccountAndDeck(String accountId) {
		return mapper.getAccountAndDeck(accountId);
	}

	/**
	 * @param pointList
	 * @param match
	 * @return
	 * 
	 */
	public LadderMatch calcLadderPoint(Ladder ladder) {
		int point1 = (ladder.getIsWin()) ? ladder.getMyLadder() : ladder.getOpLadder();
		int point2 = (ladder.getIsWin()) ? ladder.getOpLadder() : ladder.getMyLadder();
		int winnerPoint = 0;
		int looserPoint = 3;
		
		int basePoint = 600;
		float pointRatio = 0.05f;
		int diffPoint = 0;
		
		diffPoint = (int)(((point2 - point1) + basePoint) * pointRatio);
		if (point1 <= point2) {
			winnerPoint = Math.min(diffPoint, (int)(basePoint * 0.2));
		} else {
			winnerPoint = Math.max(diffPoint, 5);
		}

		LadderMatch match = new LadderMatch();
		match.setWinnerPoint(winnerPoint);
		match.setLooserPoint(looserPoint);
		log.debug("Winner::" + winnerPoint);
		log.debug("Looser::" + looserPoint);
		return match;
	}

	@Override
	@Transactional
	public Map<String, Object> leagueResult(String accountId, long rank, String resetDate, int league, int totalCount) {
		if (rank <= 0 || totalCount <= 0) {
			throw new RestException(Errors.NotPromotion);
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Integer changeLeague = null;

		float per = (float)rank / (float)totalCount;
		resultMap.put("percent", per);
		per = per * 100;
		if (per > 100)
			per = 100;
		log.debug("per::" + per);
		
		LeaguePrize prizeMeta = prizeMetaService.getPrizeByRank(league, rank, per);
		if (prizeMeta == null) {
			throw new RestException("Not.found.prize.meta.by.league :" + league);
		}
		resultMap.put("prize", prizeMeta);
		resultMap.put("rank", rank);
		resultMap.put("totalCount", totalCount);

		Reward reward = new Reward();
		reward.setAccountId(accountId);
		reward.setRewardType(prizeMeta.getRewardType());
		reward.setReward(prizeMeta.getReward());
		reward.setReasonType(Reward.ReasonType.League.getCode());
		rewardService.reward(reward, false);
		resultMap.put("reward", reward);

		LeagueMeta leagueMeta = leagueMetaService.get(league);
		log.debug("leagueMeta::" + leagueMeta);

		if (leagueMeta.getLeagueUp() > per) {
			changeLeague = leagueMetaService.getHighLeague(league);
		} else if (leagueMeta.getLeagueDown() > (100 - per)) {
			changeLeague = leagueMetaService.getLowLeague(league);
		}

		// 가장 하위 리그이거나, 가장 상위 리그인 경우
		if (changeLeague == null) {
			changeLeague = league;
		}

		Ladder ladder = new Ladder();
		ladder.setAccountId(accountId);
		ladder.setResetDate(Ladder.INIT_RESET_DATE);
		ladder.setLeague(changeLeague);
		ladder.setLadder(Ladder.INIT_LADDER);
		ladder.setWin(Ladder.INIT_WIN);
		ladder.setLose(Ladder.INIT_LOSE);
		ladder.setIsPrevWin(Ladder.INIT_PREV_WIN);
		ladder.setWinningStreakCnt(Ladder.INIT_WINNING_STREAK);
		ladder.setWinningStreakMax(Ladder.INIT_WINNING_STREAK_MAX);
		this.save(ladder);

		resultMap.put("league", changeLeague);
		return resultMap;
	}

	@Override
	@Transactional
	public Map<String, Object> matchResult(Ladder ladder, LeagueMeta leagueMeta) {
		Map<String, Object> ladderMap = new HashMap<String, Object>();
		boolean isWin = ladder.getIsWin();
		
		String myAccountId = ladder.getAccountId();
		ladder.setAccountId(myAccountId);
		String opponentId = ladder.getOpponentId();

		log.debug("##0. Param ladder::" + ladder);
		Ladder myCurrentLadder = this.get(myAccountId);
		ValidationUtil.isNullModel(myCurrentLadder, myAccountId + "'s ladder");

		dsManager.switchDataSource(opponentId);
		Ladder opCurrentLadder = ladderOpponentService.getLadder(opponentId);
		ValidationUtil.isNullModel(opCurrentLadder, opponentId + "'s ladder");
		
		ladder.setMyLadder(ladder.getMyLadder());
		ladder.setOpLadder(ladder.getOpLadder());

		LadderMatch myMatchLog = new LadderMatch();
		myMatchLog = this.calcLadderPoint(ladder);
		myMatchLog = this.myLadderUpdate(ladder, myMatchLog);
		
		ladderMap.put("winnerPoint", myMatchLog.getWinnerPoint());
		ladderMap.put("looserPoint", myMatchLog.getLooserPoint());
		
		log.debug("##2. myLadder update:: " + "myladder::" + ladder + "::myMatchLog::" + myMatchLog);
		
		Reward reward = new Reward();
		reward.setAccountId(myAccountId);
		reward.setRewardType(leagueMeta.getRewardType());
		long rewardValue;
		if (isWin) {
			rewardValue = leagueMeta.getRewardWinner();
		} else {
			rewardValue = leagueMeta.getRewardLooser();
		}
		if (ladder.getGameNo() > 1) {
			rewardValue = (long) (rewardValue * leagueMeta.getGoldBonus4Regame());
		}
		reward.setReward(rewardValue);
		reward.setReasonType(ReasonType.Ladder.getCode());
		rewardService.reward(reward, false);
		ladderMap.put("reward", reward);		

		return ladderMap;
	}

	@Override
	public Ladder getLadderNoLock(String accountId) {
		return mapper.getLadderNoLock(accountId);
	}

	@Override
	public ResetDateInfo getResetDateInfo() {
		ApiMeta meta = apiMetaService.get(ApiMeta.RESET_TIME);
		ValidationUtil.isNullModel(meta, "ApiMeta.RESET_TIME");
		String resetTime = meta.getValue();
		int resetDayOfWeek = meta.getLongValue().intValue();
		
		ResetDateInfo resetDate = mapper.getResetDateInfo(resetTime);
		return resetDate.makeResetDateInfo(resetTime, resetDayOfWeek);
	}
	
	


}
