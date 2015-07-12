package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UnitSkill implements Serializable {
	private static final long serialVersionUID = 2678104265248915276L;
	
	Integer skillType;
	Integer level;
	Integer mpCost;
	Integer upgradeCost;
	Integer lifeTime;
	Integer skillCoolTime;
	Float spellScaler;
	Float attackScaler;
	Float param1;
	Float param2;
	Float param3;
	Integer levelMax;

}
