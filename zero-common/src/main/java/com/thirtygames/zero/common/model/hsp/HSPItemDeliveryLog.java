package com.thirtygames.zero.common.model.hsp;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HSPItemDeliveryLog {

	String accountId;
	Long itemDeliverySequence;
	String json;
	Integer code;
	
	Date logYmdt;
	
}
