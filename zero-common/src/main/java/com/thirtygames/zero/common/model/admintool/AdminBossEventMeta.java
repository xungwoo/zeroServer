package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminBossEventMeta  extends GridFilter {

	
	String metaKey; 
	String bossRaidMetaId;

	Boolean isUse;
	
	String bossImage; 
	String scene;
	String texture0;
	String texture1;
	String texture2;

	Integer bossUnitType;
	Integer bossUnitLevel;
	Integer bossMaxHp;
	
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
	Integer founderEquipmentType; 
	Integer gradeRatio;
	
	String exposeStartYmdt; 
	String exposeEndYmdt;
	
	String recommendationUnit;
	
	// name
	String nameKey;
	String titleKo;
	String titleEn;
	String bossNameKo;
	String bossNameEn;
	String pushKo;
	String pushEn;
	
	String modYmdt;
	String modId;
	
}
