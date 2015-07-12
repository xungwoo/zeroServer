package com.thirtygames.zero.common.model.meta;

import java.util.ArrayList;
import java.util.Arrays;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Stage extends GridFilter {
	private static final long serialVersionUID = 2850797522028512410L;
	
	Long metaKey;
	String stageKey;
	Integer chapter;
	Integer stage;
	Integer clearMode;
	Integer playMode;
	Integer costAp;
	Integer costBp;

	Integer limitTime;
	Float goldScaleForEnemyKill;
	String scene;
	String bossSpriteName;
	Float unlockKeyDropRate;
	Integer itemDropFairAdder;
	Float itemDropRate;
	Float itemGrade;
	Float itemGradeRange;
	
	//Boolean isHidden;
	//Integer reveal;

	Float gradeMode1;
	Float gradeMode2;
	Float gradeMode3;
	Float gradeMode4;
	Float gradeMode1Range;
	Float gradeMode2Range;
	Float gradeMode3Range;
	Float gradeMode4Range;
	
	Float bossItemDropRate;
	Integer bossItem;
	Integer bossItemCategory;
	Integer bossItemGrade;
	Integer bossCameraType;
	Integer bossCameraTime;
	
	
	Float hiddenOpenRate;
	String hiddenStageKey;
	
	Boolean chapterBossStage;
	Integer enemyLvAdder;
	Integer enemySkillAdder;
	Float enemyHpScaler;
	
    Long eventClosingDate;
    String bossRaidMetaId;
    Integer bossUnitLevelMin;
    Integer bossUnitLevelMax;
    
	
	String modId;
	
	public enum PlayMode {
		KillAll, // 0
		BossAttack, // 1
		TimeAttack, // 2
		TimeDefence, // 3
		Endless, // 4
		Bonus, // 5
		PvP // 6
	}
	
	ArrayList<PlayMode> playModeList = new ArrayList<PlayMode>(Arrays.asList(PlayMode.values()));
	
	public enum RevealCondition {
		AlwaysOpened, // 0
		GetAllStarInChapter, // 1
	}
	
	ArrayList<RevealCondition> revealList = new ArrayList<RevealCondition>(Arrays.asList(RevealCondition.values()));

}
