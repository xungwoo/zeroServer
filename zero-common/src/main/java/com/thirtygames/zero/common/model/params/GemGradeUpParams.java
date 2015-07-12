package com.thirtygames.zero.common.model.params;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.equipment.Equipment;


@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class GemGradeUpParams implements Serializable {
	private static final long serialVersionUID = 5573816432367051574L;
	
	String accountId;
	Integer subCategory;
	Integer grade;
	String gem1Id;
	String gem2Id;
	String gem3Id;
	Long useGold;
	Long useCash;
	
	Equipment gem1;
	Equipment gradeUpGem;
	Integer gradeUpEquipType;

}
