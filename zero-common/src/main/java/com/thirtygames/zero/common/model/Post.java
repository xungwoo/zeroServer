package com.thirtygames.zero.common.model;

import java.util.EnumSet;
import java.util.Set;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.model.params.grid.GridFilter;
import com.thirtygames.zero.common.service.meta.ServerInfoService;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Post extends GridFilter {
	
	@Autowired
	ServerInfoService serverInfoService;
	
	public static final int MAX_COUNT_HELP_POST = 50;
	public static final int MAX_VIEW_POST = 50;

	Long postKey;
	Long postAdminKey;
	Integer postType; // 0 : Admin, 1 :User
	String accountId;
	String lang;
	String text;

	String fromAccountId;
	String fromNickName;
	String fromProfileUrl;
	Integer fromLeague;

	Integer rewardCategory;
	Integer rewardType;
	Long reward;
	Boolean rewardDone;
	Boolean availableReply;

	Long startYmdt;
	Long expireYmdt;

	String startYmd;
	String expireYmd;

	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
		this.rewardCategory = RewardType.findByCode(rewardType).getCategory().getCode();
	}

	public void makeSystemUser(String cdnUrl, LanguageCode languageCode) {
		switch (languageCode) {
		case Korean:
			this.setFromAccountId(SystemUser.ko.getAccountId());
			this.setFromNickName(SystemUser.ko.getNickName());
			this.setFromProfileUrl(cdnUrl + SystemUser.ko.getProfileUrl());
			this.setFromLeague(SystemUser.ko.getLeague());
			break;
		case English:
			this.setFromAccountId(SystemUser.en.getAccountId());
			this.setFromNickName(SystemUser.en.getNickName());
			this.setFromProfileUrl(cdnUrl + SystemUser.en.getProfileUrl());
			this.setFromLeague(SystemUser.en.getLeague());
			break;
		default:
			this.setFromAccountId(SystemUser.ko.getAccountId());
			this.setFromNickName(SystemUser.ko.getNickName());
			this.setFromProfileUrl(cdnUrl + SystemUser.ko.getProfileUrl());
			this.setFromLeague(SystemUser.ko.getLeague());
			break;
		}
	}

	@RequiredArgsConstructor
	public enum SystemUser {
		ko("system", "히어로즈연합", "/notice/heroes_agent.png", 43), en("system", "Heroes", "/notice/heroes_agent.png", 43);
		@Getter
		private final String accountId;
		@Getter
		private final String nickName;
		@Getter
		private final String profileUrl;
		@Getter
		private final int league;
	}

	@RequiredArgsConstructor
	public enum TextType {
		FriendPointFrom, FriendPointTo, FirstBuyuing, OpenPackage, PeriodItem, UnitRestGold, UnitRestUnlockKey, FacebookFriendPoint, WellcomePresent, PromotionReward;
	}

	@RequiredArgsConstructor
	public enum PostText {
		FPFromKorean(TextType.FriendPointFrom, LanguageCode.Korean, "친구와 모험을 함께하고 우정포인트를 획득했습니다."), 
		FPToKorean(TextType.FriendPointTo, LanguageCode.Korean, "친구의 모험에 참여하고 우정포인트를 획득했습니다."), 
		FacebookFPKorean(TextType.FacebookFriendPoint, LanguageCode.Korean, "페이스북 친구로부터 우정포인트가 도착했습니다."), 
		FirstBuyingKorean(TextType.FirstBuyuing, LanguageCode.Korean, "첫구매에 감사드립니다! 정성가득한 선물을 드립니다."), 
		PeriodItemKorean(TextType.PeriodItem, LanguageCode.Korean, "오늘의 30일정기권 상품이 지급되었습니다."), 
		OpenPackageKorean(TextType.OpenPackage, LanguageCode.Korean, "패키지 추가보상이 도착했습니다."), 
		WellcomePresentKorean(TextType.WellcomePresent, LanguageCode.Korean, "오랜만이에요! 히어로님을 위한 보상입니다!"),
		UnitRestGoldKorean(TextType.UnitRestGold, LanguageCode.Korean, "유닛 초기화에 대한 골드가 지급 되었습니다."),
		PromotionRewardKorean(TextType.PromotionReward, LanguageCode.Korean, "프로모션 보상이 지급되었습니다."), 
		UnitRestUnlockKeyKorean(TextType.UnitRestUnlockKey, LanguageCode.Korean, "유닛 초기화에 대한 열쇠가 지급 되었습니다."),
		
		FPFromEnglish(TextType.FriendPointFrom, LanguageCode.English, "Friendship points earned by inviting friend to join your adventure."),
		FPToEnglish(TextType.FriendPointTo, LanguageCode.English, "Friendship points earned by participating in your friends adventure."), 
		FacebookFPEnglish(TextType.FacebookFriendPoint, LanguageCode.English, "Friendship points from your facebook friend has arrived."),
		FirstBuyingEnglish(TextType.FirstBuyuing, LanguageCode.English, "Thank you for your first purchase! Please accept our wonderful gift."), 
		PeriodItemEnglish(TextType.PeriodItem, LanguageCode.English, "Today's 30-day season ticket has been given."), 
		OpenPackageEnglish(TextType.OpenPackage, LanguageCode.English, "Package's additional reward has arrived."),
		WellcomePresentEnglish(TextType.WellcomePresent, LanguageCode.English, "It's been a while Hero! Here's a reward for you!"),
		UnitRestGoldEnglish(TextType.UnitRestGold, LanguageCode.English, "Gold from unit reset has been given."),
		PromotionRewardEnglish(TextType.PromotionReward, LanguageCode.English, "Promotion reward has arrived."),
		UnitRestUnlockKeyEnglish(TextType.UnitRestUnlockKey, LanguageCode.English, "Key from unit reset has been given."), 
;
		
		@Getter
		private final TextType type;
		@Getter
		private final LanguageCode languageCode;
		@Getter
		private final String text;

		public static Set<PostText> koreans = EnumSet.range(FPFromKorean, UnitRestUnlockKeyKorean);
		public static Set<PostText> englishes = EnumSet.range(FPFromEnglish, UnitRestUnlockKeyEnglish);

		private static final java.util.Map<TextType, PostText> $CODE_LOOKUP_KOREAN = new java.util.HashMap<TextType, PostText>();
		static {
			for (PostText status : PostText.koreans) {
				$CODE_LOOKUP_KOREAN.put(status.type, status);
			}
		}

		private static final java.util.Map<TextType, PostText> $CODE_LOOKUP_ENGLISH = new java.util.HashMap<TextType, PostText>();
		static {
			for (PostText status : PostText.englishes) {
				$CODE_LOOKUP_ENGLISH.put(status.type, status);
			}
		}

		public static PostText findByCode(final TextType textType, LanguageCode languageCode) {
			switch (languageCode) {
			case Korean:
				if ($CODE_LOOKUP_KOREAN.containsKey(textType)) {
					return $CODE_LOOKUP_KOREAN.get(textType);
				}
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'PostText' has no value for 'code = %s'", textType));
			case English:
				if ($CODE_LOOKUP_ENGLISH.containsKey(textType)) {
					return $CODE_LOOKUP_ENGLISH.get(textType);
				}
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'PostText' has no value for 'code = %s'", textType));
			default:
				throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'PostText' has no value for 'code = %s'", textType));
			}
		}
	}

	@RequiredArgsConstructor
	public enum PostType {
		Admin(0), User(1);

		@Getter
		private final int code;

		private static final java.util.Map<java.lang.Integer, PostType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, PostType>();
		static {
			for (PostType status : PostType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}

		public static PostType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'PostType' has no value for 'code = %s'", code));
		}
	}

}
