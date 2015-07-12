package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LadderMatch extends GridFilter implements Cloneable, Serializable {
	private static final long serialVersionUID = -5190782224896585014L;

	Integer league;
	String resetDate;
	
	String roomNo;
	Long timeStamp;
	Integer gameNo;
	String accountId;
	String opponentId;
	String opDeck;
	String opNickName;
	Integer opTitle;

	Integer myLadderPoint;
	Integer opLadderPoint;

	Boolean isWin;
	Integer playTime;

	Integer winnerPoint;
	Integer looserPoint;

	Date logYmdt;
	Boolean isAI;

	public LadderMatch clone() throws CloneNotSupportedException {
		LadderMatch ladderMatch = (LadderMatch) super.clone();
		return ladderMatch;
	}
}
