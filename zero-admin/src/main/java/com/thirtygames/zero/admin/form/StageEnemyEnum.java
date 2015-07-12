package com.thirtygames.zero.admin.form;

import com.thirtygames.zero.admin.form.converter.BooleanConverter;
import com.thirtygames.zero.admin.form.converter.FloatConverter;
import com.thirtygames.zero.admin.form.converter.IntegerConverter;
import com.thirtygames.zero.admin.form.converter.TypeConverter;

public enum StageEnemyEnum {
	STAGE_KEY("stageKey", false, null),
	TURN("turn", false, new IntegerConverter()),
	UNIT_TYPE("unitType", false, new IntegerConverter()),
	LEVEL("level", false, new IntegerConverter()),
	SCALE("scale", true, new FloatConverter()),
	HP_SCALER("hpScaler", true, new FloatConverter()),
	BOSS("boss", false, new BooleanConverter()),
	X("x", false, new FloatConverter()),
	Z("z", false, new FloatConverter()),
	TRAP_RADIUS("trapRadius", false, new FloatConverter()),
	MARKER("marker", false, null),
	SIGHT("sight", true, new FloatConverter()),
	SKILL0LV("skill0Lv", false, new IntegerConverter()),
	SKILL1LV("skill1Lv", false, new IntegerConverter()),
	SKILL2LV("skill2Lv", false, new IntegerConverter()),
	SKILL3LV("skill3Lv", false, new IntegerConverter()),
	REGIMENT("regiment", false, new IntegerConverter()),
	SPAWN_EFFECT("spawnEffect", false, new IntegerConverter()),
	GROUP("group", false, new IntegerConverter()),
	SPAWN_COUNT("spawnCount", false, new IntegerConverter()),
	BEGIN("begin", false, new FloatConverter()),
	END("end", false, new FloatConverter()),
	PERIOD("period", false, new FloatConverter()),
	RELATIVE("relative", false, new FloatConverter());
	
	String name;
	
	boolean isNullable;
	
	TypeConverter<String, ?> converter;
	
	StageEnemyEnum(String name, boolean isNullable, TypeConverter<String, ?> converter) {
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
