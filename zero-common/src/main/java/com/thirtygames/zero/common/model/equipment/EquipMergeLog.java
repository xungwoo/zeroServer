package com.thirtygames.zero.common.model.equipment;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipMergeLog extends GridFilter {

	String accountId;
	String equipmentId;
	Integer mergedEqClass;
	Integer mergedEqGrade;
	String usedEqList;
	Long usedGold;
	
	Date logYmdt;
}
