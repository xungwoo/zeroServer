package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.Joiner;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Friend extends GridFilter implements Serializable {
	private static final long serialVersionUID = -4757271606261309254L;

	public static final int MINUTE_DELETE_LIMIT = 10080;	// 24 * 60 * 7
	public static final int MINUTE_FACEBOOK_PRESENT_TIME = 1440;	// 24 * 60
	
	@JsonIgnore Integer friendRelationKey;
	String accountId;
	String friendId;	
	Long memberNo;
	Long friendMemberNo;
	Long lastHelpYmdt;
	Long lastPresentYmdt;
	Boolean isFacebookFriend;
	Boolean isRestoreDeleted;
	
	
	List<Deck> deckUnitList;
	String deck;
	String maxClearStage;
	Integer maxClearMode;
	Integer castlePoint;
	Integer castleLastClearFloor;
	
	String resetDate;
	Integer league;
	Integer ladder;
	Integer win;
	Integer lose;
	Integer winningStreakCnt;
	Integer winningStreakMax;

	Integer title;
	
	String nickName;
	String profileUrl;
	String country;
	String facebookId;
	
	Long addedYmdt;
	Integer limitDeleteMinute; 
	
	@JsonIgnore Boolean isDel;
	
	
	public void setDeckListToStr(List<String> deckList) {
		Iterator<String> itDeck = deckList.iterator();
		this.deck = Joiner.on(",").join(itDeck);
	}

	
	
}
