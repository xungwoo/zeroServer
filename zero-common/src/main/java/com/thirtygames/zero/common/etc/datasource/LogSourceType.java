package com.thirtygames.zero.common.etc.datasource;

import com.thirtygames.zero.common.etc.exception.RestException;

public enum LogSourceType {
	ZERO_LOG_1(1), ZERO_LOG_2(2), ;
	
	public static LogSourceType[] LOG_SHARDS = { ZERO_LOG_1, ZERO_LOG_2 };
	
	Integer shardIndexNumber;
	
	public Integer getShardIndexNumber() {
		return shardIndexNumber;
	}
	
	public void setShardIndexNumber(Integer shardIndexNumber) {
		this.shardIndexNumber = shardIndexNumber;
	}
	
	private LogSourceType() {
	}
	
	LogSourceType(Integer shardIndexNumber) {
		this.shardIndexNumber = shardIndexNumber;
	}
	
	public static LogSourceType getDataSourceFromIndexNumber(Integer index) {
		if (index == null) {
			return null;
		}
		
		for (LogSourceType logDataSourceType : LogSourceType.values()) {
			if (logDataSourceType.getShardIndexNumber() != null && index.compareTo(logDataSourceType.getShardIndexNumber()) == 0) {
				return logDataSourceType;
			}
		}
		return null;
	}

	public static LogSourceType getLogDST(String accountId)  {
		LogSourceType dst = null;
		int index = accountId.lastIndexOf("-");
		if (index > 0) {
			String dataSource = accountId.substring(index + 1);
			try {
				int shardNo = Integer.parseInt(dataSource);
				dst = LogSourceType.getDataSourceFromIndexNumber(shardNo);
			} catch(NumberFormatException e) {
				throw new RestException("Invalid.format.AccountId::" + accountId);
			}
		}
		return dst;
	}
}