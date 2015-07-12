package com.thirtygames.zero.common.model.meta;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class StageEnemy extends GridFilter {
	private static final long serialVersionUID = -6648828434131169683L;

	Integer enemyId;
	String stageKey;
	Integer turn;
	Integer unitType;
	Integer level;
	Float scale;
	Float hpScaler;
	Boolean boss;
	Float x;
	Float z;
	Float trapRadius;
	String marker;
	String enemyName;
	Boolean summoned;
	Float sight;
	Integer skill0Lv;
	Integer skill1Lv;
	Integer skill2Lv;
	Integer skill3Lv;
	Integer skill4Lv;
	Integer regiment;
	Integer spawnEffect;
	
	Integer corpsNo;
	Integer corpsSize;
	Float corpsBegin;
	Float corpsEnd;
	Float corpsInterval;
	Float corpsRelative;

}
