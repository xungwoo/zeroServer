package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Account extends GridFilter implements Serializable {
	private static final long serialVersionUID = 4198430771880920709L;
	
	private static final int SHARD_MAX_DIGIT_NUMBER = 3;	//자리수
	public static final long NICKNAME_CHANGE_CASH = 200;
	
	public static final int EXTRA_INVENTORY_MAX_COUNT = 40;
	public static final int EXTRA_INVENTORY_ADD_COUNT = 4;
	public static final long EXTRA_INVENTORY_BUY_CACHE = 20;

	public static final Integer DEFAULT_CHANNEL_TYPE = 0;

	
	Long memberNo; //HSP
	String accountId;
	String nickName;
	String channelId;
	String facebookId;
	Integer channelType;
	Boolean channelCertification;
	
	String password; // channeler 인증시 불필요
	Integer profileType;
	String profileUrl;

	String authToken;
	String language;
	String country;
	Integer localTimeZone;
	
	Boolean isBlock;
	Boolean sendLog;
	Boolean withdraw;
	
	Integer title;
	String maxClearStage;
	Integer maxClearMode;
	Integer castlePoint;
	Integer castleLastClearFloor;
	Long tutorial;
	Integer extraInventory;
	
	// Sync Info
	Integer lastSyncYmdt;
	Integer currentYmdt;
	
	// League Info
	Integer league;
	Integer ladder;
	Integer win;
	Integer lose;
	Integer winningStreakCnt;	
	Integer winningStreakMax;
	
	String resetDate;
	String apLastDate;
	String bpLastDate;
	
	Long genTimeStamp;
	
	
	//?
	boolean isNew = false;
	
	//TODO : mybatis resultMap.
	//AccountResource accountResource;
	
	// Resource Info
	@JsonIgnore Long apLastYmdt;
	@JsonIgnore Long apLastValue;
	@JsonIgnore Long apMax;
	@JsonIgnore Long apExtra;
	@JsonIgnore Long bpLastYmdt;
	@JsonIgnore Long bpLastValue;
	@JsonIgnore Long bpMax;
	@JsonIgnore Long bpExtra;
	@JsonIgnore Long fp;
	@JsonIgnore Long gold;
	@JsonIgnore Long cash;
	@JsonIgnore Long unlockKey;	
	@JsonIgnore Long titleBundle1;
	@JsonIgnore Long titleBundle2;
	@JsonIgnore Long titleBundle3;	
	@JsonIgnore Long equipLevelUpDrug;
	@JsonIgnore Long equipTicket;

	
	@JsonIgnore 
	public AccountResource getAccountResource() {
		AccountResource ar = new AccountResource();
		ar.setAccountId(this.getAccountId());
		ar.setGold(this.getGold());
		ar.setCash(this.getCash());
		ar.setApMax(this.getApMax());
		ar.setApLastYmdt(this.apLastYmdt);
		ar.setApExtra(this.apExtra);
		ar.setBpMax(this.getBpMax());
		ar.setBpLastYmdt(this.bpLastYmdt);
		ar.setBpExtra(this.bpExtra);
		ar.setUnlockKey(this.getUnlockKey());
		ar.setTitleBundle1(this.titleBundle1);
		ar.setTitleBundle2(this.titleBundle2);
		ar.setTitleBundle3(this.titleBundle3);
		ar.setFp(this.fp);
		ar.setEquipLevelUpDrug(this.equipLevelUpDrug);
		ar.setEquipTicket(this.equipTicket);
		return ar;
	}
	
	@JsonIgnore DataSource dataSourceType;
	public String generateAccountId() {
		String newAccountId = UUID.randomUUID().toString();
		
		Random r = new Random();
		int shardNo = r.nextInt(DataSource.GAME_SHARDS.length) + 1;
		String result = "-00" + Integer.toString(shardNo);
		
		newAccountId += result;
		this.accountId = newAccountId;
		this.dataSourceType = DataSource.findByCode(shardNo);
		return newAccountId;
	}
	
	public int calcNextInventoryBuyCache(int extraInventory) {
		return (int) (Account.EXTRA_INVENTORY_BUY_CACHE * ((extraInventory / Account.EXTRA_INVENTORY_ADD_COUNT) + 1));
	}
	
	public String getLanguage() {
		return LanguageCode.findByCode(this.language).getCode(); 
	}
}
