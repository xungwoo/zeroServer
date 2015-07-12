package com.thirtygames.zero.common.model.log;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CheatLog extends GridFilter implements Serializable {

	Long logKey;
	String accountId;
	String additionalInfo;
	Date logYmdt;
}
