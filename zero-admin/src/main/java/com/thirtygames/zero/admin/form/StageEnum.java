package com.thirtygames.zero.admin.form;

import com.thirtygames.zero.admin.form.converter.FloatConverter;
import com.thirtygames.zero.admin.form.converter.IntegerConverter;
import com.thirtygames.zero.admin.form.converter.TypeConverter;

public enum StageEnum {
	STAGE_KEY("stageKey", false, null),
	CHAPTER("chapter", false, new IntegerConverter()),
	STAGE("stage", false, new IntegerConverter()),
	PLAYMODE("playmode", false, new IntegerConverter()),
	COST_AP("costAp", true, new IntegerConverter()),
	COST_BP("costBp", true, new IntegerConverter()),
	LIMIT_TIME("limitTime", false, new IntegerConverter()),
	GOLD_SCALE_FOR_ENEMY_KILL("goldScaleForEnemyKill", false, new FloatConverter()),
	SCENE("scene", false, null),
	BOSS_SPRITE_NAME("bossSpriteName", true, null),
	ITEM_DROP_RATE("itemDropRate", false, new FloatConverter()),
	BOSS_ITEM_DROP_RATE("bossItemDropRate", false, new FloatConverter()),
	BOSS_ITEM("bossItem", false, new IntegerConverter()),
	BOSS_ITEM_CATEGORY("bossItemCategory", false, new IntegerConverter()),
	BOSS_ITEM_GRADE("bossItemGrade", false, new IntegerConverter());
	
	String name;
	
	boolean isNullable;
	
	TypeConverter<String, ?> converter;
	
	StageEnum(String name, boolean isNullable, TypeConverter<String, ?> converter) {
		this.name = name;
		this.isNullable = isNullable;
		this.converter = converter;
	}

	public String getName() {
		return name;
	}


	public boolean isNullable() {
		return isNullable;
	}

	public TypeConverter<String, ?> getConverter() {
		return converter;
	}
	
}
