package com.thirtygames.zero.common.model.datasource;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.common.etc.datasource.DataSource;

@Data
@EqualsAndHashCode(callSuper=false)
public class DataSourceInfo {
	
	String accountId;
	DataSource current;

}
