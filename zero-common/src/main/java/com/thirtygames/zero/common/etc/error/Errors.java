package com.thirtygames.zero.common.etc.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Errors {
	
	None(0, "Unknown.error"), 
	NotFoundData(1, "Not.found.data"),
	NotEnoughCash(2, "Not.enough.cash"),
	NotEnoughGold(3, "Not.enough.gold"),
	NotEnoughAP(4, "Not.enough.ap"),
	NotEnoughBP(5, "Not.enough.bp"),
	NotEnoughUnlockKey(6, "Not.enough.unlockKey"),
	NotEnoughFP(7, "Not.enough.fp"), 
	NotEnoughEquipLevelUpDrug(8, "Not.enough.equipLevelUpDrug"), 
	NotEnoughEquipTicket(9, "Not.enough.equipTicket"),
	DuplicateRequest(10, "Duplicate.request"),
	NotPromotion(11, "Not.promotion"), 
	InvalidTimeStamp(12, "Invalid.header.timestamp"), 
	InvalidClientInfo(13, "Invalid.client.info"), 
	HSPNoItemDeliverySequences(14, "HSP.itemDelivery.no.itemDeliverySequences"), 
	InvalidDeckInfo(15, "Invalid.deckInfo"), 
	InvalidParameter(16, "Invalid.parameter"), 
	AlreadyExistUnitType(17, "Already.exist.uitType"), 
	NotFoundEquipMeta(18, "Not.found.equipment.meta"), 
	AlreadyRewardPost(19, "Already.reward.PostAdmin"),
	AlreadyRewardMission(20, "Already.reward.mission"), 
	AlreadyRewardAchievement(21, "Already.reward.achievement"), 
	AlreadyUsedCoupon(22, "Already.used.coupon"), 
	NotFoundEquipment(23, "Not.found.equipment"), 
	HSPVerifyDeliveryError(24, "HSP.VerifyDeliveryError"), 
	UrlConnector(25, "UrlConnector"), 
	AlreadyUsedCouponBundle(26, "Already.used.coupon.bundle"), 
	ExpiredCoupon(27, "Expired.coupon"), 
	NotFoundCoupon(28, "Not.found.coupon"), 
	GoldCheat(29, "Gold.cheat"), 
	FailCertification(30, "Fail.certification"), 
	AlreadyMaxInventory(31, "Max.Inventory"), 
	FailResourceUpdate(32, "Fail.resource.update"), 
	AlreadyFoundBossRaid(33, "Already.found.boss"), 
	FullMemberBossRaid(34, "Full.member.boss.raid"), 
	
	NotFinishBossRaid(35, "Not.finish.boss.raid"), 
	FinishBossRaid(36, "Finish.boss.raid"), 
	AlreadyJoinBossRaid(37, "Already.join.boss.raid"), 
	AlreadyRewardedBossRaid(38, "Already.rewarded.boss.raid"), 
	NotJoinedBossRaid(39, "Not.joined.boss.raid"), 
	AlreadyClearedBossRaid(40, "Already.cleared.boss.raid"), 
	NotCompleteBossCollection(41, "Not.finish.boss.collection"), 
	AlreadyRewardedBossCollection(42, "Already.rewarded.boss.collection"), 
	
	SendPushError(43, "HSP.push.error"), 
	LimitTimeFriendDelete(44, "Limit.time.friend.delete"), 
	FacebookFriendCannotDelete(45, "Facebook.friend.cannot.delete"), 
	CGPCheckMissionError(46, "CGP.check.mission.error"), 
	URLConnectorError(47, "URLConnector.error"), 
	LimitTimeFacebookPresent(48, "Limit.time.facebook.friend.present"); 
	
	@Getter
	private final int code;
	@Getter
	private final String message;

}