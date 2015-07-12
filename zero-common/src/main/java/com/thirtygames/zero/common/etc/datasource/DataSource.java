package com.thirtygames.zero.common.etc.datasource;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.thirtygames.zero.common.etc.exception.RestException;

@Slf4j
@RequiredArgsConstructor
public enum DataSource {
	ZERO_INDEX(0), ZERO_GAME_1(1), ZERO_GAME_2(2), ZERO_LOG_1(10001), ZERO_LOG_2(10002), ZERO_META(20000);
	
	public static DataSource[] GAME_SHARDS = { ZERO_GAME_1, ZERO_GAME_2 };
	public static DataSource[] LOG_SHARDS = { ZERO_LOG_1, ZERO_LOG_2 };

	@Getter
	private final int code;
	
	private static final int LOG_CODE_BASE = 10000;
	//private static final int META_CODE_BASE = 20000;

	private static final java.util.Map<java.lang.Integer, DataSource> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, DataSource>();
	static {
		for (DataSource status : DataSource.values()) {
			$CODE_LOOKUP.put(status.code, status);
		}
	}
	
	public static DataSource findByCode(final int code) {
		if ($CODE_LOOKUP.containsKey(code)) {
			return $CODE_LOOKUP.get(code);
		}
		throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'DataSource' has no value for 'code = %s'", code));
	}
	
	public static DataSource getAccountDS(String accountId)  {
		return DataSource.findByCode(getShardNo(accountId));
	}	
	
	public static DataSource getDynamicLogDS(DataSource dynamicDS)  {
		log.debug("getDynamicLogDS::" + dynamicDS.getCode() + LOG_CODE_BASE);
		return DataSource.findByCode(dynamicDS.getCode() + LOG_CODE_BASE);
	}
	
	public static DataSource getLogDS(String accountId)  {
		log.debug("getLogDS::" + DataSource.findByCode(getShardNo(accountId) + LOG_CODE_BASE));
		return DataSource.findByCode(getShardNo(accountId) + LOG_CODE_BASE);
	}


	public static int getShardNo(String accountId)  {
		int index = accountId.lastIndexOf("-");
		if (index < 0) {
			throw new RestException("Invalid.format.AccountId::" + accountId);
		}
		
		String dataSource = accountId.substring(index + 1);
		try {
			return Integer.parseInt(dataSource);
		} catch(NumberFormatException e) {
			throw new RestException("Invalid.format.AccountId::" + accountId);
		}
	}	
}

