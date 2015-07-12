package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminWellcomePresent extends GridFilter {
	
	Integer metaKey;
	Integer minuteFromLastLogin;
	Integer rewardType;
	Long reward;
	String msgKo;
	String msgEn;
	
	String modYmdt;
	String modId;
}
