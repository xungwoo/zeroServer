package com.thirtygames.zero.common.model.hsp;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class HSPPushResponse {

	Long transactionId;
	Integer status;

}
