package com.thirtygames.zero.admin.controller.common;


import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.admin.ftl.MenuItem;

@Data
@EqualsAndHashCode(callSuper=false)
public class AdminMenu {
	private static AdminMenu instance = new AdminMenu();
	
	private AdminMenu() {
		setLeftMenu();
	}

	public static AdminMenu getInstance() {
		return instance;
	}
	
	private MenuItem leftMenu = new MenuItem();
	private Category category;	
	private String selectMenu = "";

	private void setLeftMenu() {
		
		MenuItem version = new MenuItem(Category.Version);
		version.addSubItem(new MenuItem("Cleint Version 관리", "ClientInfo", "/zero-admin/version/client-info/grid/1"));
		version.addSubItem(new MenuItem("Server URL 관리", "ServerUrl", "/zero-admin/version/server-url/grid/1"));
		version.addSubItem(new MenuItem("Meta Revision 관리", "MetaRevision", "/zero-admin/version/meta-revision/grid/1"));
		version.addSubItem(new MenuItem("Trevi 관리", "ApiMeta", "/zero-admin/version/api-meta/grid/1"));
		leftMenu.addSubItem(version);
		
		MenuItem balanceEquip = new MenuItem(Category.BalanceEquip);
		balanceEquip.addSubItem(new MenuItem("장비분류", "EquipmentType", "/zero-admin/balance/equipment/type/grid"));
		balanceEquip.addSubItem(new MenuItem("장식속성분류", "EquipDecoStat", "/zero-admin/balance/equipment/decostat/grid"));
		balanceEquip.addSubItem(new MenuItem("장식속성 생성확률", "EquipChoiceDecoStat", "/zero-admin/balance/equipment/decostat-rate/1"));
		balanceEquip.addSubItem(new MenuItem("장비 메타", "EquipmentMeta", "/zero-admin/balance/equipment/grid"));
		balanceEquip.addSubItem(new MenuItem("장비생성TEST", "EquipmentVirtual", "/zero-admin/balance/equipment/virtual"));
		balanceEquip.addSubItem(new MenuItem("장비Level", "EquipLevelMeta", "/zero-admin/balance/equipment/level/grid"));
		leftMenu.addSubItem(balanceEquip);
		
		MenuItem balanceEtc = new MenuItem(Category.BalanceEtc);
		balanceEtc.addSubItem(new MenuItem("스테이지 메타", "Stage", "/zero-admin/balance/stage/grid"));
		balanceEtc.addSubItem(new MenuItem("스테이지적군 메타", "StageEnemy", "/zero-admin/balance/stageEnemy/grid"));		
		balanceEtc.addSubItem(new MenuItem("미션 메타", "MissionMeta", "/zero-admin/balance/mission/grid"));		
		balanceEtc.addSubItem(new MenuItem("미션텍스트정보", "MissionName", "/zero-admin/balance/mission-name/grid"));		
		balanceEtc.addSubItem(new MenuItem("업적 메타", "AchievementMeta", "/zero-admin/balance/achievement/grid"));		
		balanceEtc.addSubItem(new MenuItem("유닛 메타", "UnitMeta", "/zero-admin/balance/unit/grid"));		
		leftMenu.addSubItem(balanceEtc);
		
		MenuItem bossRaid = new MenuItem(Category.Boss);
		bossRaid.addSubItem(new MenuItem("보스레이드 메타", "BossRaidMeta", "/zero-admin/boss/raid/grid/1"));
		bossRaid.addSubItem(new MenuItem("이벤트레이드 메타", "EventRaidMeta", "/zero-admin/boss/event/grid/1"));
		bossRaid.addSubItem(new MenuItem("보스컬렉션 메타", "CollectionMeta", "/zero-admin/boss/collection/grid/1"));
		leftMenu.addSubItem(bossRaid);
		
		MenuItem castle = new MenuItem(Category.Castle);
		castle.addSubItem(new MenuItem("무한성 메타", "CastleMeta", "/zero-admin/castle/grid/1"));
		leftMenu.addSubItem(castle);
		
		MenuItem stats = new MenuItem(Category.Stats);
		stats.addSubItem(new MenuItem("스테이지 통계", "StageStats", "/zero-admin/stats/stage/grid/1"));
		leftMenu.addSubItem(stats);
		
		MenuItem shop = new MenuItem(Category.Shop);
		shop.addSubItem(new MenuItem("상점 아이템 관리", "ShopItem", "/zero-admin/shop-item/grid/1"));
		leftMenu.addSubItem(shop);		
		
		MenuItem event = new MenuItem(Category.Event);
		event.addSubItem(new MenuItem("공지사항 배너", "NoticeBanner", "/zero-admin/event/notice-banner/grid/1"));
		//event.addSubItem(new MenuItem("쿠폰 발행", "Coupon", "/zero-admin/event/coupon/template/grid/1"));
		event.addSubItem(new MenuItem("기간 드랍율부스팅", "BoostringEvent", "/zero-admin/event/drop-rate/grid/1"));
		event.addSubItem(new MenuItem("쿠폰 관리", "Coupon", "/zero-admin/event/coupon/grid/1"));
		event.addSubItem(new MenuItem("복귀환영보상 관리", "WellcomePresent", "/zero-admin/event/wellcome-present/grid/1"));
		leftMenu.addSubItem(event);		

		MenuItem user = new MenuItem(Category.User);
		user.addSubItem(new MenuItem("유저 관리", "Account", "/zero-admin/user/account/grid/1"));
		user.addSubItem(new MenuItem("재화 관리", "Resource", "/zero-admin/user/resource/grid/1"));
		user.addSubItem(new MenuItem("덱 관리", "Deck", "/zero-admin/user/deck/grid/1"));
		user.addSubItem(new MenuItem("유닛 관리", "Unit", "/zero-admin/user/unit/grid/1"));
		user.addSubItem(new MenuItem("장비 관리", "Equipment", "/zero-admin/user/equipment/grid/1"));
		user.addSubItem(new MenuItem("모험 관리", "StageClear", "/zero-admin/user/stage-clear/grid/1"));
		user.addSubItem(new MenuItem("친구 관리", "Friend", "/zero-admin/user/friend/grid"));
		user.addSubItem(new MenuItem("업적 관리", "Achievement", "/zero-admin/user/achievement/grid/1"));
		user.addSubItem(new MenuItem("미션 관리", "Mission", "/zero-admin/user/mission/grid/1"));
		user.addSubItem(new MenuItem("래더 관리", "Ladder", "/zero-admin/ladder/account/grid/1"));
		leftMenu.addSubItem(user);
		

		MenuItem log = new MenuItem(Category.Log);
		log.addSubItem(new MenuItem("재화 로그", "ResourceLog", "/zero-admin/log/resource/grid/1"));
		log.addSubItem(new MenuItem("보상 로그", "RewardLog", "/zero-admin/log/reward/grid/1"));
		log.addSubItem(new MenuItem("모험 로그", "StageLog", "/zero-admin/log/stage/grid/1"));
		log.addSubItem(new MenuItem("유닛 로그", "UnitLog", "/zero-admin/log/unit/grid/1"));
		log.addSubItem(new MenuItem("장비강화 로그", "EquipLevelUpLog", "/zero-admin/log/equip/level-up/grid/1"));
		log.addSubItem(new MenuItem("장비합성 로그", "EquipMergeLog", "/zero-admin/log/equip/merge/grid/1"));
		log.addSubItem(new MenuItem("젬 로그", "GemLog", "/zero-admin/log/gem/grid/1"));
		log.addSubItem(new MenuItem("구매 로그", "ShopItemLog", "/zero-admin/log/shop-item/grid/1"));
		log.addSubItem(new MenuItem("쿠폰 로그", "CouponLog", "/zero-admin/log/coupon/grid/1"));
		log.addSubItem(new MenuItem("어드민 수정내역", "AdminLog", "/zero-admin/log/admin/grid/1"));
		leftMenu.addSubItem(log);		
		
		MenuItem ladder = new MenuItem(Category.Ladder);
		ladder.addSubItem(new MenuItem("리그정보", "LeagueInfo", "/zero-admin/league/count/grid/1"));
		ladder.addSubItem(new MenuItem("리그국가별AI설정", "LadderCountry", "/zero-admin/league/country/grid/1"));
		ladder.addSubItem(new MenuItem("리그메타 관리", "LeagueMeta", "/zero-admin/league/meta/grid"));
		leftMenu.addSubItem(ladder);
		
		MenuItem post = new MenuItem(Category.Post);
		post.addSubItem(new MenuItem("유저별 우편함", "PostUser", "/zero-admin/post/user/grid/1"));
		post.addSubItem(new MenuItem("관리자 우편함", "PostAdmin", "/zero-admin/post/admin/grid/1"));
		leftMenu.addSubItem(post);
		
		MenuItem qaTool = new MenuItem(Category.QATool);
		qaTool.addSubItem(new MenuItem("모험클리어", "StageClearTool", "/zero-admin/qatool/stage-clear-tool"));
		leftMenu.addSubItem(qaTool);

	}	
	
	public enum Category {
		Home, User, BalanceEquip, BalanceEtc, Log, OutLink, Stage, Ladder, Post, QATool, Version, Event, Shop, Stats, Boss, Castle;
	}
	
	public void setSelectMenu(String menuName) {
		this.selectMenu = menuName;
	}	
}
