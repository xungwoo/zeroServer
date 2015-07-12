package com.thirtygames.zero.common.model.meta;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class GemMerge implements Serializable {
	private static final long serialVersionUID = -6018059052994393673L;
	
	Integer grade;
	Integer gold;
	Integer empty1Cash;
	Integer empty2Cash;
}
