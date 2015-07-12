package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Device  implements Serializable {
	private static final long serialVersionUID = 3513869035307812708L;

	String accountId = "";	
	String deviceId = "";
	String osType = "";
	String osVersion = "";
	String carrierName	 = "";
	
}
