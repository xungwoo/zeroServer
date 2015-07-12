package com.thirtygames.zero.common.model.admintool;

import java.io.Serializable;

import com.thirtygames.zero.common.model.params.grid.GridFilter;

import lombok.Data;
import lombok.EqualsAndHashCode;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
public class AdminUnitMeta extends GridFilter{

	Long metaKey;

    Integer unitType;
    Integer  attackType;
    Integer  presentionType;
	Integer unlockKey;

    Integer levelMax;
    Float attackLv1;
    Float attackGrowth;

    Float spellLv1;
    Float spellGrowth;

    Float hpLv1;
    Float hpGrowth;
    Float hpRegen;

    Float mpLv1;
    Float mpGrowth;
    Float mpRegen;

    Float defensePhyLv1;
    Float defensePhyGrowth;

    Float defenseSpellLv1;
    Float defenseSpellGrowth;

    Integer   goldLv1;
    Float goldGrowth;

    // Move speed. Meters per second
    Float moveSpeed;

    Float attackCoolTime;
    Float attackRange;

    // Critical attack rate. between 0 and 1.0
    Float criticalRate;
    Float criticalDamage;

    Float scale;
    Float power;
    Float mass;

    Integer evo1LvJump;
    Integer evo2LvJump;
    Integer evo3LvJump;
    Integer evo4LvJump;
    Integer evo5LvJump;
    Integer evo6LvJump;
    Integer evo7LvJump;
    Integer evo8LvJump;
    Integer evo9LvJump;


    Integer skill0Type;
    Integer skill1Type;
    Integer skill2Type;
    Integer skill3Type;
    Integer skill4Type;
    Integer weapon1;
    Integer weapon2;
    Integer weapon3;
    Integer weapon4;
    Integer weapon5;
    
	String modId;
}
