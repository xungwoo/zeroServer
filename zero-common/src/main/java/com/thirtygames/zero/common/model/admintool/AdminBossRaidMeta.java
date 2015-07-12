package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminBossRaidMeta  extends GridFilter {

	
	String metaKey;
	String bossRaidMetaId;
	
	Integer playTime;

	String bossImage; 
	String scene;
	String texture0;
	String texture1;
	String texture2;

	Integer bossUnitType;
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
	
	Long goldRewardFirst; 
	Float goldRewardScaler;
	Integer founderEquipCategory; 
	Integer gradeRatio;
	
	Float exposeRate;
	Long exposeStartYmdt; 
	Long exposeEndYmdt;
	
	String recommendationUnit;
	
	// name
	String nameKey;
	String titleKo;
	String titleEn;
	String bossNameKo;
	String bossNameEn;
	
	String modYmdt;
	String modId;
	
}
