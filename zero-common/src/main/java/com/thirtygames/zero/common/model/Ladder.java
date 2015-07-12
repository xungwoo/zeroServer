package com.thirtygames.zero.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Ladder extends GridFilter {
	
	public static String INIT_RESET_DATE = "00000000";
	public static final int INIT_LEAGUE = 1;
	public static final int INIT_LADDER = 1000;
	public static final int INIT_WIN = 0;
	public static final int INIT_LOSE = 0;
	public static final boolean INIT_PREV_WIN = false;
	public static final int INIT_WINNING_STREAK = 0;
	public static final int INIT_WINNING_STREAK_MAX = 0;
	
	
	String accountId;
	
	Integer leagueKey;
	Integer league;
	String resetDate;
	Boolean isRewarded;
	Integer ladder;
	Integer win;
	Integer lose;
	Boolean isPrevWin;
	Integer winningStreakCnt;
	Integer winningStreakMax;
	Long lastGameTimeStamp;
	Integer lastGameNo;
	
	// params
	String opponentId;
	String myDeck;
	String myNickName;
	Integer myTitle;
	String opDeck;
	String opNickName;
	Integer opTitle;
	Integer myLadder;
	Integer opLadder;
	String roomNo;
	Long timeStamp;
	Integer gameNo;
	Boolean isWin;
	Integer playTime;
	Boolean isAI;
	
}
