package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.base.CharMatcher;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.equipment.Equipment;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Reward implements Serializable {
	
	public static final long WITH_FRIEND_MY_POINT = 10;
	public static final long WITH_FRIEND_PARTNER_POINT = 20;
	public static final long WITH_FACEBOOK_FRIEND_POINT = 50;
	public static final long UNIT_ALTERNATIVE_REWARD_UNLOCKKEY = 3;	// Unit 이미 발급된 경우 대체수단으로 발급되는 unlockKey 수량 
	
	String accountId;
	Integer rewardCategory;
	Integer rewardType;
	Long reward;
	Integer reasonType;
	
	// reward Result
	AccountResource resource;	// AccountResource, Equipment, Unit
	Equipment equipment;
	Unit unit;
	Achievement nextStep;
	
	// param
	Integer itemKey;
	
	
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
		this.rewardCategory = RewardType.findByCode(rewardType).getCategory().getCode();
	}

	@RequiredArgsConstructor
	public enum ReasonType {
		Achievement(0), 
		Mission(1), 
		ItemDelivery(2), 
		LoginReward(3), 
		Post(4), 
		Coupon(5), 
		FirstBuying(6), 
		Ladder(7), 
		League(8), 
		BossRaid(9),
		BossCollection(10),
		Castle(11), 
		CGPPromotion(12);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, ReasonType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, ReasonType>();
		static {
			for (ReasonType status : ReasonType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static ReasonType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'ReasonType' has no value for 'code = %s'", code));
		}
	}
	
	@RequiredArgsConstructor
	public enum RewardType {
		Gold(1, RewardCategory.Resource), 
		Cash(2, RewardCategory.Resource), 
		AP(3, RewardCategory.Resource), 
		BP(4, RewardCategory.Resource), 
		FP(5, RewardCategory.Resource), 
		UnlockKey(6, RewardCategory.Resource), 
		Title(7, RewardCategory.Resource), 
		EquipTicket(8, RewardCategory.Resource), 
		Equipment(9, RewardCategory.Equipment), 
		Unit(10, RewardCategory.Unit),
		EquipLevelUpDrug(11, RewardCategory.Resource);
		
		@Getter
		private final int code;
		@Getter
		private final RewardCategory category;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, RewardType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, RewardType>();
		static {
			for (RewardType status : RewardType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static RewardType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'RewardType' has no value for 'code = %s'", code));
		}
		
		public static String getCodeStr() {
			String resultStr = "";
			for(RewardType status : RewardType.values()) {
				resultStr += status.getCode() + ":" + status.toString() + ";";
			}	
			
			return CharMatcher.anyOf(";").trimFrom(resultStr);
		}
	}
	
	@RequiredArgsConstructor
	public enum RewardCategory {
		Resource(1), Equipment(2), Unit(3);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, RewardCategory> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, RewardCategory>();
		static {
			for (RewardCategory status : RewardCategory.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static RewardCategory findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'RewardCategory' has no value for 'code = %s'", code));
		}
		
		
		public static String getCodeStr() {
			String resultStr = "";
			for(RewardCategory status : RewardCategory.values()) {
				resultStr += status.getCode() + ":" + status.toString() + ";";
			}	
			
			return CharMatcher.anyOf(";").trimFrom(resultStr);
		}		
	}

}
