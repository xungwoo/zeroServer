package com.thirtygames.zero.common.model.datasource;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class Shard implements Serializable {
	private static final long serialVersionUID = 4198430771880920709L;
	
	Long memberNo;
	String accountId;
	Integer dataSourceType;
	
}
