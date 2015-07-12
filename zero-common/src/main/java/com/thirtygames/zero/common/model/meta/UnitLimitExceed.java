package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class UnitLimitExceed implements Serializable {
	private static final long serialVersionUID = -6018059052994393673L;
	
	Integer level;
	Integer limitExceedTime;
	Integer gold;
	Integer unlockKey;
	Integer timeToCashRatio;
}
