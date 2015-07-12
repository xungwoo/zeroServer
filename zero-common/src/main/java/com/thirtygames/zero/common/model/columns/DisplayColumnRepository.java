package com.thirtygames.zero.common.model.columns;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.document.CellType;
import com.thirtygames.zero.common.model.columns.DisplayColumn.Align;

@Repository
public class DisplayColumnRepository {

	/* Default 값들 */

	private static final int DEFAULT_GRID_YMDT_WIDTH = 150;

	private static final Align DEFAULT_GRID_YMDT_ALIGN = Align.CENTER;

	/**
	 * 
	 * 노출 영역.
	 */

	public static enum DisplayArea {

		ADMIN

	}

	/** The types map. */

	Map<DisplayArea, Map<DisplayColumnId, DisplayColumn>> typesMap;

	/**
	 * 
	 * Inits the repository.
	 */

	@PostConstruct
	public void initRepository() {

		typesMap = new LinkedHashMap<DisplayArea, Map<DisplayColumnId, DisplayColumn>>();

		typesMap.put(DisplayArea.ADMIN, createAdminDisplayColumns());

	}

	/**
	 * 
	 * 어드민 영역을 위한 Display.
	 * 
	 * 
	 * 
	 * @return the map
	 */

	private Map<DisplayColumnId, DisplayColumn> createAdminDisplayColumns() {

		Map<DisplayColumnId, DisplayColumn> columns = new LinkedHashMap<DisplayColumnId, DisplayColumn>();

		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_ACCOUT_ID, "사용자ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_MEMBER_NO, "HSP MemberNo").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_NICK_NAME, "닉네임").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_CHANNEL_ID, "연동플랫폼ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_CHANNEL_TYPE, "연동플랫폼Type").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_PROFILE_URL, "프로필URL").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_TITLE, "칭호").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_MAX_CLEAR_STAGE, "최고클리어 스테이지").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_MAX_CLEAR_MODE, "최고클리어 모드").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_TUTORIAL, "튜토리얼").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_AUTH_TOKEN, "인증Token").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_AUTH_TOKEN_VALID_YMDT, "인증Token유효일시").setGridData(110, Align.CENTER).setExcelData(CellType.DATE, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_LANGUAGE, "언어").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_COUNTRY, "국가").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_LOCAL_TIME_ZONE, "LocalTimeZone").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_IS_BLOCK, "제재여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_SEND_LOG, "로깅여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_LAST_SYNC_YMDT, "마지막Sync일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACCOUNT_MOD_YMDT, "수정일시").setGridData(110, Align.CENTER).setExcelData(CellType.DATE, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_ACCOUT_ID, "사용자ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_MEMBER_NO, "HSP MemberNo").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_NICK_NAME, "닉네임").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_GOLD, "골드").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_CASH, "캐쉬").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_AP_LAST_YMDT, "최종AP저장일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_AP_LAST_VALUE, "최종AP값").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_AP_MAX, "최대AP").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_BP_LAST_YMDT, "최종BP저장일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_BP_LAST_VALUE, "최종BP값").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_BP_MAX, "최대BP").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_FP, "우정포인트").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_UNLOCK_KEY, "열쇠").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_EQUIP_LEVELUP_DRUG, "장비강화제").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_EQUIP_TICKET, "장비뽑기권").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.RESOURCE_GEN_YMDT, "생성일시").setGridData(110, Align.CENTER).setExcelData(CellType.DATE, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_DECK_KEY, "덱키").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_ACCOUNT_ID, "덱사용자ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_TEAM_ID, "팀ID").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_POSITION_ID, "덱포지션ID").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_UNIT_ID, "덱유닛ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_UNIT_TYPE, "유닛타입").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_LEVEL, "유닛레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_LIMIT_EXCEED_END_YMDT, "한계돌파종료일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_SKILL_0LV, "스킬0레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_SKILL_1LV, "스킬1레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_SKILL_2LV, "스킬2레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.DECK_SKILL_3LV, "스킬3레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_UNIT_KEY, "유닛키").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_UNIT_ID, "유닛ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_ACCOUNT_ID, "유닛사용자ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_UNIT_TYPE, "유닛타입").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LEVEL, "유닛레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LIMIT_EXCEED_END_YMDT, "한계돌파종료일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_SKILL_0LV, "스킬0레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_SKILL_1LV, "스킬1레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_SKILL_2LV, "스킬2레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_SKILL_3LV, "스킬3레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());

		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_EQUIPMENT_ID, "장비ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_ACCOUNT_ID, "아이디").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_UNIT_ID, "유닛아이디").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_EQUIPMENT_POSITION, "장착위치").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_EQUIPMENT_TYPE, "장비Code").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_CATEGORY, "대분류").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_SUBCATEGORY, "상세분류").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_GRADE, "등급").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_DECORATION1, "장식속성1").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_DECORATION2, "장식속성2").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_LEVEL, "레벨").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_EXP, "경험치").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_TOTAL_EXP, "누적경험치").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_OPEN_SOCKETS, "소켓개수").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_MAX_SOCKETS, "최대소켓수").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_SOCKET1, "소켓1").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_SOCKET2, "소켓2").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_SOCKET3, "소켓3").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_GEN_YMDT, "생성일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());

		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_STAT_KEY, "장비스탯키").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_EQUIPMENT_ID, "장비ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_ACCOUNT_ID, "아이디").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_TYPE, "타입").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_DECO_CODE, "장식코드").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_MIN, "최소").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_MAX, "최대").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_STAT_RATE, "스탯률").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_DURATION, "지속").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_MIN_RANDOM, "최소랜덤").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_MAX_RANDOM, "최대랜덤").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_RATE_RANDOM, "스탯률랜덤").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_DUR_RANDOM, "지속랜덤").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_BASE_STAT, "기본스탯").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_SET_STAT, "세트스탯").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.EQUIPMENT_STAT_ONLY_FOR, "고유").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_FRIEND_ID, "아이디").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_DECK, "덱").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_MAX_CLEAR_STAGE, "최대클리어스테이지").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_LEAGUE, "리그").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_LADDER, "래더").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_WIN, "승").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_LOSE, "패").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_WINNING_STREAK_COUNT, "연속승수").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.FRIEND_WINNING_STREAK_MAX, "최대연속승수").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());

		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_KEY, "로그일련번호").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_EQUIPMENT_ID, "장비ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_EQUIPMENT_TYPE, "장비Type").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_SUB_CATEGORY, "상세분류").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_GRADE, "등급").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_STATUS, "로그구분").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_INSTALLED_EQUIP_ID, "장착된장비ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_INSTALLED_SOCKET_NO, "장착된소켓번호").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_USED_EQ_LIST, "소모된장비목록").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.GEMLOG_LOGYMDT, "기록일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_KEY, "스테이지Key").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_CLEAR_MODE, "Clear Mode").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_CLEAR_STEP, "Clear Step").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_CLEAR_PROGRESS, "Clear Progress").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_EXPOSED_SCENES, "스토리노출").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGECLEAR_MOD_YMDT, "수정일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACHIEVEMENT_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACHIEVEMENT_ACHIEVEMENT_ID, "업적ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACHIEVEMENT_STEP, "단계").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACHIEVEMENT_CURRENT, "현재").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.ACHIEVEMENT_REWARD_DONE, "완료여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.MISSION_ACCOUNT_ID, "유닛ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.MISSION_MISSION_ID, "미션아이디").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.MISSION_CURRENT, "현재").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.MISSION_REWARD_DONE, "보상여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_STAGE_KEY, "StageKey").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_DECK, "덱구성").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_CLEAR_MODE, "클리어모드").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_PLAY_TIME, "플레이타임").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_GAIN_GOLD, "획득골드").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_IS_WIN, "승리여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.STAGE_LOG_LOG_YMDT, "기록일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_UNIT_ID, "유닛ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_UNIT_TYPE, "유닛타입").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_STATUS, "상태").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_RESULT_LEVEL, "결과레벨").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.UNIT_LOG_LOG_YMDT, "저장일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_KEY, "itemKey").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_ID, "itemId").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_CATEGORY, "itemCategory").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_TYPE, "itemType").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_QUANTITY, "수량").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_ITEM_QUANTITY_BONUS, "보너스수량").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_BUY_TYPE, "구매분류").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_PRICE, "가격").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.SHOP_ITEM_LOG_YMDT, "기록일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_ACCOUNT_ID, "유저ID").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_LEAGUE, "리그").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_LADDER, "래더점수").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_WIN, "승리").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_LOSE, "패배").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_IS_PREV_WIN, "직전게임 승리여부").setGridData(110, Align.CENTER).setExcelData(CellType.BOOLEAN, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_WINNING_STREAK_CNT, "연속승수").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_WINNING_STREAK_MAX, "최대 연속승수").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_LAST_GAME_TIMESTAMP, "최종 게임일시").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_LAST_GAME_NO, "마지막 게임번호").setGridData(110, Align.CENTER).setExcelData(CellType.NUMBER, 15).getDisplayColumn());
		addToColumns(columns, DisplayColumnBuilder.create(DisplayColumnId.LADDER_RESET_DATE, "리그종료일").setGridData(110, Align.CENTER).setExcelData(CellType.TEXT, 15).getDisplayColumn());
		
		return columns;

	}

	Map<DisplayColumnId[], List<DisplayColumn>> cacheMap = new LinkedHashMap<DisplayColumnId[], List<DisplayColumn>>();

	/**
	 * 
	 * Find column.
	 * 
	 * 
	 * 
	 * @param displayType
	 *            the display type
	 * 
	 * @param columnId
	 *            the column id
	 * 
	 * @return the display column
	 */

	public List<DisplayColumn> findColumns(DisplayArea displayType,
			DisplayColumnId... columnIds) {

		List<DisplayColumn> results = Lists
				.newArrayListWithCapacity(columnIds.length);

		Map<DisplayColumnId, DisplayColumn> columns = typesMap.get(displayType);

		if (columns != null) {

			for (DisplayColumnId id : columnIds) {

				results.add(columns.get(id));

			}

		}

		return results;

	}

	public List<DisplayColumn> findColumns(DisplayArea displayType,
			List<DisplayColumnId> columnIds) {

		List<DisplayColumn> results = Lists.newArrayListWithCapacity(columnIds
				.size());

		Map<DisplayColumnId, DisplayColumn> columns = typesMap.get(displayType);

		if (columns != null) {

			for (DisplayColumnId id : columnIds) {

				results.add(columns.get(id));

			}

		}

		return results;

	}
	
	/**
	 * 컬럼정보 저장 map 에 생성된 컬럼을 추가하는 함수 key 는 컬럼의 Is
	 * 
	 * @param map
	 * @param displayColumn
	 */
	private void addToColumns(Map<DisplayColumnId, DisplayColumn> map, DisplayColumn displayColumn) {
		if (displayColumn.getId() != null) {
			map.put(displayColumn.getId(), displayColumn);
		}
	}

}
