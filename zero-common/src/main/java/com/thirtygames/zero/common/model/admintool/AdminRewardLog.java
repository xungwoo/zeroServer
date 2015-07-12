package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminRewardLog extends GridFilter {
		
		String accountId;
		Integer rewardCategory;
		Integer rewardType;
		Long reward;
		Integer reasonType;
		String reasonKey;

		String logYmdt;
	}
	