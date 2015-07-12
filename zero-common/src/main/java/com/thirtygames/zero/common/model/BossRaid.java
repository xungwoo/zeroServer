package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class BossRaid implements Serializable {

	public static final int MAX_COUNT_OF_PARTY = 5;
	public static final int MAX_DIFFICULTY = 7;
	public static final double DIFFICULTY_UNIT_RANGE = 15;
	
	String raidKey;
	String bossRaidMetaId;
	String bossRaidId;
	String titleForEvent;

	Boolean isEvent;
	Boolean isMyBoss;
	Boolean isRewarded;
	Boolean isPlay;
	Boolean isClear;
	
	Integer bossRaidStatus;
	Integer bossRaidTimeStatus;
	
	Integer difficulty; 
	Integer playTime;
	
	String bossName; 
	String bossImage; 

	String scene;
	String texture0;
	String texture1;
	String texture2;

	Integer bossUnitType;
	Integer bossUnitLevel;
	Integer bossHpFirst;
	Float bossHpScaler;
	
	Float bossScale;
	Float bossDefensePhy;
	Float defensePhyScaler;
	Float bossDefenseSpell;
	Float defenseSpellScaler;
	Integer bossSkillLv0;
	Integer bossSkillLv1;
	Integer bossSkillLv2;
	
	Integer minionUnitType;
	Integer relativeMinionLevel;
	Float minionHpScale;
	Float minionScale;
	Integer minionSkillLv0;
	Integer minionSkillLv1;
	Integer minionSkillLv2;
	
	Integer minionSpawnNum;
	Float minionSpawnStart;
	Float minionSpawnInterval;
	
	Integer fee;
	
	Long goldReward;
	Integer equipmentReward;
	
	Long goldRewardFirst; 
	Float goldRewardScaler;
	Integer founderEquipCategory; 
	Integer gradeRatio;
	
	// Event BOSS
	Integer founderEquipmentType; 
	
	Float exposeRate;
	Long exposeStartYmdt; 
	Long exposeEndYmdt;
	
	String recommendationUnit;
	
	Long startYmdt; 
	Long endYmdt;
	Boolean isOverEndTime;
	
	// user
	String accountId;
	String foundAccountId;
	Integer bossMaxHp;
	Double bossNowHp;
	Integer totalDamage;
	Integer damageSum;
	Double damagePercent;
	
	Long memberNo;
	
	Integer countPartyFriend;
	
	String lang;
	
	
	@RequiredArgsConstructor
	public enum BossRaidTimeStatus {
		NoOver(0), OverAndWait(1), TimeOver(2);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, BossRaidTimeStatus> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, BossRaidTimeStatus>();
		static {
			for (BossRaidTimeStatus status : BossRaidTimeStatus.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static BossRaidTimeStatus findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'BossRaidTimeStatus' has no value for 'code = %s'", code));
		}
	}	
	
	
	@RequiredArgsConstructor
	public enum BossRaidStatus {
		Find(0), Start(1), RewardCheck(2), RaidFinish(3);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, BossRaidStatus> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, BossRaidStatus>();
		static {
			for (BossRaidStatus status : BossRaidStatus.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static BossRaidStatus findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'BossRaidStatus' has no value for 'code = %s'", code));
		}
	}

	
}
