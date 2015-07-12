package com.thirtygames.zero.common.model.columns;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.StageClear;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.admintool.AdminLog;
import com.thirtygames.zero.common.model.admintool.DeckGrid;
import com.thirtygames.zero.common.model.equipment.EquipLog;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.log.StageLog;
import com.thirtygames.zero.common.model.log.UnitLog;
import com.thirtygames.zero.common.model.meta.LeagueCount;
import com.thirtygames.zero.common.model.meta.ShopItem;

public enum DisplayColumnId {
	// 계정
	ACCOUNT(Account.class),
	ACCOUNT_ACCOUT_ID(ACCOUNT, "accountId", String.class),
	ACCOUNT_MEMBER_NO(ACCOUNT, "memberNo", String.class),
	ACCOUNT_NICK_NAME(ACCOUNT, "nickName", String.class),
	ACCOUNT_CHANNEL_ID(ACCOUNT, "channelId", String.class),
	ACCOUNT_CHANNEL_TYPE(ACCOUNT, "channelType", String.class),
	ACCOUNT_PROFILE_URL(ACCOUNT, "profileUrl", String.class),
	ACCOUNT_TITLE(ACCOUNT, "title", String.class),
	ACCOUNT_MAX_CLEAR_STAGE(ACCOUNT, "maxClearStage", String.class),
	ACCOUNT_MAX_CLEAR_MODE(ACCOUNT, "maxClearMode", String.class),
	ACCOUNT_TUTORIAL(ACCOUNT, "tutorial", String.class),
	ACCOUNT_AUTH_TOKEN(ACCOUNT, "authToken", String.class),
	ACCOUNT_AUTH_TOKEN_VALID_YMDT(ACCOUNT, "authTokenValidYmdt", Integer.class),
	ACCOUNT_LANGUAGE(ACCOUNT, "language", String.class),
	ACCOUNT_COUNTRY(ACCOUNT, "country", String.class),
	ACCOUNT_LOCAL_TIME_ZONE(ACCOUNT, "localTimeZone", String.class),
	ACCOUNT_IS_BLOCK(ACCOUNT, "isBlock", Boolean.class),
	ACCOUNT_SEND_LOG(ACCOUNT, "sendLog", Boolean.class),
	ACCOUNT_LAST_SYNC_YMDT(ACCOUNT, "lastSyncYmdt", Date.class),
	ACCOUNT_MOD_YMDT(ACCOUNT, "modYmdt", Date.class),

	RESOURCE(AccountResource.class),
	RESOURCE_ACCOUT_ID(RESOURCE, "accountId", String.class),
	RESOURCE_MEMBER_NO(RESOURCE, "memberNo", String.class),
	RESOURCE_NICK_NAME(RESOURCE, "nickName", String.class),
	RESOURCE_GOLD(RESOURCE, "gold", Long.class),
	RESOURCE_CASH(RESOURCE, "cash", Long.class),
	RESOURCE_AP_LAST_YMDT(RESOURCE, "apLastYmdt", String.class),
	RESOURCE_AP_LAST_VALUE(RESOURCE, "apLastValue", String.class),
	RESOURCE_AP_MAX(RESOURCE, "apMax", Long.class),
	RESOURCE_BP_LAST_YMDT(RESOURCE, "bpLastYmdt", String.class),
	RESOURCE_BP_LAST_VALUE(RESOURCE, "bpLastValue", String.class),
	RESOURCE_BP_MAX(RESOURCE, "bpMax", Long.class),
	RESOURCE_FP(RESOURCE, "fp", Long.class),
	RESOURCE_UNLOCK_KEY(RESOURCE, "unlockKey", Long.class),
	RESOURCE_EQUIP_LEVELUP_DRUG(RESOURCE, "equipLevelUpDrug", Long.class),
	RESOURCE_EQUIP_TICKET(RESOURCE, "equipTicket", Long.class),
	RESOURCE_GEN_YMDT(RESOURCE, "genYmdt", Date.class),
	
	// 덱
	DECK_GRID(DeckGrid.class),
	DECK_DECK_KEY(DECK_GRID, "deckKey", String.class),
	DECK_ACCOUNT_ID(DECK_GRID, "accountId", String.class),
	DECK_TEAM_ID(DECK_GRID, "teamId", Integer.class),
	DECK_POSITION_ID(DECK_GRID, "positionId", Integer.class),
	DECK_UNIT_ID(DECK_GRID, "unitId", String.class),
	DECK_UNIT_TYPE(DECK_GRID, "unitType", Integer.class),
	DECK_LEVEL(DECK_GRID, "level", Integer.class),
	DECK_LIMIT_EXCEED_END_YMDT(DECK_GRID, "limitExceedEndYmdt", String.class),
	DECK_SKILL_0LV(DECK_GRID, "skill0Lv", Integer.class),
	DECK_SKILL_1LV(DECK_GRID, "skill1Lv", Integer.class),
	DECK_SKILL_2LV(DECK_GRID, "skill2Lv", Integer.class),
	DECK_SKILL_3LV(DECK_GRID, "skill3Lv", Integer.class),
	
	// 유닛
	UNIT(Unit.class),
	UNIT_UNIT_KEY(UNIT, "unitKey", String.class),
	UNIT_UNIT_ID(UNIT, "unitId", String.class),
	UNIT_ACCOUNT_ID(UNIT, "accountId", String.class),
	UNIT_UNIT_TYPE(UNIT, "unitType", Integer.class),
	UNIT_LEVEL(UNIT, "level", Integer.class),
	UNIT_LIMIT_EXCEED_END_YMDT(UNIT, "limitExceedEndYmdt", String.class),
	UNIT_SKILL_0LV(UNIT, "skill0Lv", Integer.class),
	UNIT_SKILL_1LV(UNIT, "skill1Lv", Integer.class),
	UNIT_SKILL_2LV(UNIT, "skill2Lv", Integer.class),
	UNIT_SKILL_3LV(UNIT, "skill3Lv", Integer.class),
	
	// 장비
	EQUIPMENT(Equipment.class),
	EQUIPMENT_EQUIPMENT_ID(EQUIPMENT, "equipmentId", String.class),
	EQUIPMENT_ACCOUNT_ID(EQUIPMENT, "accountId", String.class),
	EQUIPMENT_UNIT_ID(EQUIPMENT, "unitId", String.class),
	EQUIPMENT_EQUIPMENT_POSITION(EQUIPMENT, "equipPosition", Integer.class),
	EQUIPMENT_EQUIPMENT_TYPE(EQUIPMENT, "equipmentType", Integer.class),
	EQUIPMENT_CATEGORY(EQUIPMENT, "category", Integer.class),
	EQUIPMENT_SUBCATEGORY(EQUIPMENT, "subCategory", Integer.class),
	EQUIPMENT_GRADE(EQUIPMENT, "grade", Integer.class),
	EQUIPMENT_DECORATION1(EQUIPMENT, "decoration1", Integer.class),
	EQUIPMENT_DECORATION2(EQUIPMENT, "decoration2", Integer.class),
	EQUIPMENT_LEVEL(EQUIPMENT, "level", Integer.class),
	EQUIPMENT_EXP(EQUIPMENT, "exp", Integer.class),
	EQUIPMENT_TOTAL_EXP(EQUIPMENT, "totalExp", Float.class),
	EQUIPMENT_OPEN_SOCKETS(EQUIPMENT, "openSockets", Integer.class),
	EQUIPMENT_MAX_SOCKETS(EQUIPMENT, "maxSockets", Integer.class),
	EQUIPMENT_SOCKET1(EQUIPMENT, "socket1", String.class),
	EQUIPMENT_SOCKET2(EQUIPMENT, "socket2", String.class),
	EQUIPMENT_SOCKET3(EQUIPMENT, "socket3", String.class),
	EQUIPMENT_GEN_YMDT(EQUIPMENT, "genYmdt", Integer.class),
	
	// 장비스탯
	EQUIPMENT_STAT(EquipmentStat.class),
	EQUIPMENT_STAT_STAT_KEY(EQUIPMENT_STAT, "statKey", String.class),
	EQUIPMENT_STAT_EQUIPMENT_ID(EQUIPMENT_STAT, "equipmentId", String.class),
	EQUIPMENT_STAT_ACCOUNT_ID(EQUIPMENT_STAT, "accountId", String.class),
	EQUIPMENT_STAT_TYPE(EQUIPMENT_STAT, "type", Integer.class),
	EQUIPMENT_STAT_DECO_CODE(EQUIPMENT_STAT, "decoCode", Integer.class),
	EQUIPMENT_STAT_MIN(EQUIPMENT_STAT, "min", Float.class),
	EQUIPMENT_STAT_MAX(EQUIPMENT_STAT, "max", Float.class),
	EQUIPMENT_STAT_STAT_RATE(EQUIPMENT_STAT, "statRate", Float.class),
	EQUIPMENT_STAT_DURATION(EQUIPMENT_STAT, "duration", Float.class),
	EQUIPMENT_STAT_MIN_RANDOM(EQUIPMENT_STAT, "minRandom", Float.class),
	EQUIPMENT_STAT_MAX_RANDOM(EQUIPMENT_STAT, "maxRandom", Float.class),
	EQUIPMENT_STAT_RATE_RANDOM(EQUIPMENT_STAT, "rateRandom", Float.class),
	EQUIPMENT_STAT_DUR_RANDOM(EQUIPMENT_STAT, "durRandom", Float.class),
	EQUIPMENT_STAT_BASE_STAT(EQUIPMENT_STAT, "baseStat", Integer.class),
	EQUIPMENT_STAT_SET_STAT(EQUIPMENT_STAT, "setStat", Integer.class),
	EQUIPMENT_STAT_ONLY_FOR(EQUIPMENT_STAT, "onlyFor", Integer.class),
	
	FRIEND(Friend.class),
	FRIEND_FRIEND_ACCOUNT_KEY(FRIEND, "friendAccountKey", String.class),
	FRIEND_FRIEND_ID(FRIEND, "friendId", String.class),
	FRIEND_LAST_HELP_YMDT(FRIEND, "lastHelpYmdt", String.class),
	FRIEND_DECK(FRIEND, "deck", String.class),
	FRIEND_MAX_CLEAR_STAGE(FRIEND, "maxClearStage", String.class),
	FRIEND_LEAGUE(FRIEND, "league", String.class),
	FRIEND_LADDER(FRIEND, "ladder", String.class),
	FRIEND_WIN(FRIEND, "win", String.class),
	FRIEND_LOSE(FRIEND, "lose", String.class),
	FRIEND_WINNING_STREAK_COUNT(FRIEND, "winningStreakCnt", String.class),
	FRIEND_WINNING_STREAK_MAX(FRIEND, "winningStreakMax", String.class),
	FRIEND_TITLE(FRIEND, "title", String.class),
	FRIEND_NICKNAME(FRIEND, "nickName", String.class),
	FRIEND_PROFILE_URL(FRIEND, "profileUrl", String.class),

	GEMLOG(EquipLog.class), 
	GEMLOG_KEY(GEMLOG, "logKey", Long.class),
	GEMLOG_EQUIPMENT_ID(GEMLOG, "equipmentId", String.class),
	GEMLOG_EQUIPMENT_TYPE(GEMLOG, "equipmentType", Integer.class),
	GEMLOG_ACCOUNT_ID(GEMLOG, "accountId", String.class),
	GEMLOG_SUB_CATEGORY(GEMLOG, "subCategory", Integer.class),
	GEMLOG_GRADE(GEMLOG, "grade", Integer.class),
	GEMLOG_STATUS(GEMLOG, "status", Integer.class),
	GEMLOG_INSTALLED_EQUIP_ID(GEMLOG, "installedEquipId", String.class),
	GEMLOG_INSTALLED_SOCKET_NO(GEMLOG, "installedSocketNo", Integer.class),
	GEMLOG_USED_EQ_LIST(GEMLOG, "usedEqList", String.class),
	GEMLOG_LOGYMDT(GEMLOG, "logYmdt", Date.class),
	
	STAGECLEAR(StageClear.class),
	STAGECLEAR_ACCOUNT_ID(STAGECLEAR, "accountId", String.class),
	STAGECLEAR_KEY(STAGECLEAR, "stageKey", String.class),
	STAGECLEAR_CLEAR_MODE(STAGECLEAR, "clearMode", Integer.class),
	STAGECLEAR_CLEAR_STEP(STAGECLEAR, "clearStep", Integer.class),
	STAGECLEAR_CLEAR_PROGRESS(STAGECLEAR, "clearProgress", Integer.class),
	STAGECLEAR_EXPOSED_SCENES(STAGECLEAR, "exposedScenes", Integer.class),
	STAGECLEAR_MOD_YMDT(STAGECLEAR, "", Long.class),
	
	ACHIEVEMENT(Achievement.class),
	ACHIEVEMENT_ACCOUNT_ID(ACHIEVEMENT, "accountId", String.class),
	ACHIEVEMENT_ACHIEVEMENT_ID(ACHIEVEMENT, "achievementId", Long.class),
	ACHIEVEMENT_STEP(ACHIEVEMENT, "step", Integer.class),
	ACHIEVEMENT_CURRENT(ACHIEVEMENT, "current", String.class),
	ACHIEVEMENT_REWARD_DONE(ACHIEVEMENT, "rewardDone", Boolean.class),
	
	MISSION(Mission.class),
	MISSION_ACCOUNT_ID(MISSION, "accountId", String.class),
	MISSION_MISSION_ID(MISSION, "missionId", Integer.class),
	MISSION_CURRENT(MISSION, "current", Integer.class),
	MISSION_REWARD_DONE(MISSION, "rewardDone", Boolean.class),
	
	STAGE_LOG(StageLog.class),
	STAGE_LOG_ACCOUNT_ID(STAGE_LOG, "accountId", String.class),
	STAGE_LOG_STAGE_KEY(STAGE_LOG, "stageKey", String.class),
	STAGE_LOG_DECK(STAGE_LOG, "deck", String.class),
	STAGE_LOG_CLEAR_MODE(STAGE_LOG, "clearMode", String.class),
	STAGE_LOG_PLAY_TIME(STAGE_LOG, "playTime", Integer.class),
	STAGE_LOG_GAIN_GOLD(STAGE_LOG, "gainGold", Integer.class),
	STAGE_LOG_IS_WIN(STAGE_LOG, "isWin", Boolean.class),
	STAGE_LOG_LOG_YMDT(STAGE_LOG, "logYmdt", String.class),
	
	UNIT_LOG(UnitLog.class),
	UNIT_LOG_UNIT_ID(UNIT_LOG, "unitId", String.class),
	UNIT_LOG_UNIT_TYPE(UNIT_LOG, "unitType", String.class),
	UNIT_LOG_ACCOUNT_ID(UNIT_LOG, "accountId", String.class),
	UNIT_LOG_STATUS(UNIT_LOG, "status", Integer.class),
	UNIT_LOG_RESULT_LEVEL(UNIT_LOG, "resultLevel", Integer.class),
	UNIT_LOG_LOG_YMDT(UNIT_LOG, "logYmdt", String.class),
	
	SHOP_ITEM(ShopItem.class),
	SHOP_ITEM_ACCOUNT_ID(SHOP_ITEM, "accountId", String.class),
	SHOP_ITEM_ITEM_KEY(SHOP_ITEM, "itemKey", String.class),
	SHOP_ITEM_ITEM_ID(SHOP_ITEM, "itemId", String.class),
	SHOP_ITEM_ITEM_CATEGORY(SHOP_ITEM, "itemCategory", String.class),
	SHOP_ITEM_ITEM_TYPE(SHOP_ITEM, "itemType", String.class),
	SHOP_ITEM_ITEM_QUANTITY(SHOP_ITEM, "itemQuantity", String.class),
	SHOP_ITEM_ITEM_QUANTITY_BONUS(SHOP_ITEM, "itemQuantityBonus", String.class),
	SHOP_ITEM_BUY_TYPE(SHOP_ITEM, "buyType", String.class),
	SHOP_ITEM_PRICE(SHOP_ITEM, "price", String.class),
	SHOP_ITEM_LOG_YMDT(SHOP_ITEM, "logYmdt", String.class),

	LEAGUECOUNT(LeagueCount.class),
	LEAGUECOUNT_LEAGUE(LEAGUECOUNT, "league", Integer.class),
	LEAGUECOUNT_COUNT(LEAGUECOUNT, "ladder", Integer.class),
	LEAGUECOUNT_RESET_DATE(LEAGUECOUNT, "resetDate", String.class),	

	LADDER(Ladder.class),
	LADDER_ACCOUNT_ID(LADDER, "accountId", String.class),
	LADDER_LEAGUE(LADDER, "league", Integer.class),
	LADDER_LADDER(LADDER, "ladder", Integer.class),
	LADDER_WIN(LADDER, "win", Integer.class),
	LADDER_LOSE(LADDER, "lose", Integer.class),
	LADDER_IS_PREV_WIN(LADDER, "isPrevWin", Boolean.class),
	LADDER_WINNING_STREAK_CNT(LADDER, "winningStreakCnt", Integer.class),
	LADDER_WINNING_STREAK_MAX(LADDER, "winningStreakMax", Integer.class),
	LADDER_LAST_GAME_TIMESTAMP(LADDER, "lastGameTimeStamp", String.class),
	LADDER_LAST_GAME_NO(LADDER, "lastGameNo", Integer.class),
	LADDER_RESET_DATE(LADDER, "resetDate", String.class), 
	
	ADMIN_LOG(AdminLog.class),
	ADMIN_LOG_LOG_KEY(ADMIN_LOG, "logKey", Long.class),
	ADMIN_LOG_MEMO(ADMIN_LOG, "memo", String.class),
	ADMIN_LOG_TYPE(ADMIN_LOG, "type", Integer.class),
	ADMIN_LOG_DATA_KEY(ADMIN_LOG, "dataKey", String.class),
	ADMIN_LOG_MOD_ID(ADMIN_LOG, "modId", String.class),
	ADMIN_LOG_MOD_YMDT(ADMIN_LOG, "modYmdt", Date.class);
	

	DisplayColumnId rootParent = null;

	DisplayColumnId parent = null;

	SpelExpressionParser parser;

	Class<?> bindingClass;

	Class<?> targetClass;

	Expression expression;

	Method readMethod;

	List<DisplayColumnId> children = new ArrayList<DisplayColumnId>();

	private DisplayColumnId(Class<?> bindingClass) {

		initParent(bindingClass);

	}

	private DisplayColumnId(DisplayColumnId parent, String expression) {

		this(parent, expression, null);

	}

	private DisplayColumnId(DisplayColumnId parent, String expression,
			Class<?> targetClass) {

		initChild(parent, expression, targetClass);

	}

	private void initParent(Class<?> bindingClass) {

		this.rootParent = this;

		this.bindingClass = bindingClass;

		this.targetClass = bindingClass;

		this.parser = new SpelExpressionParser();

	}

	private void initChild(DisplayColumnId parent, String expressionString,
			Class<?> targetClass) {

		if (parent == null) {

			throw new RuntimeException("parent should be specified.");

		}

		this.rootParent = parent.rootParent;

		this.parent = parent;

		this.parent.addChild(this);

		this.bindingClass = parent.bindingClass;

		this.parser = parent.parser;

		if (parent.expression != null) {

			expressionString = parent.expression.getExpressionString() + "."
					+ expressionString;

		}

		this.expression = parser.parseExpression(expressionString);

		this.targetClass = targetClass;

	}

	private void addChild(DisplayColumnId displayColumnId) {

		this.children.add(displayColumnId);

	}

	public List<DisplayColumnId> getChildren() {

		return children;

	}

	public Object getObjectFor(Map<DisplayColumnId, Object> bindings) {

		Object rootObject = bindings.get(this.rootParent);

		if (expression == null || rootObject == null) {

			return null;

		}

		try {

			return expression.getValue(rootObject);

		} catch (Exception e) {

			return null;

		}

	}

	public Class<?> getTargetClass() {

		return targetClass;

	}

	public boolean isTargetInstanceOf(String name) {

		if (targetClass == null) {

			return false;

		}

		try {

			Class<?> superClass = Class.forName(name);

			return superClass.isAssignableFrom(targetClass);

		} catch (ClassNotFoundException e) {

			return false;

		}

	}

}
