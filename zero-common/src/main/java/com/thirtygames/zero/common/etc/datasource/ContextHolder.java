package com.thirtygames.zero.common.etc.datasource;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.exception.RestException;



public class ContextHolder {
	private static final ThreadLocal<String> account = new ThreadLocal<String>();
	private static final ThreadLocal<DataSource> accountDS = new ThreadLocal<DataSource>();
	private static final ThreadLocal<DataSource> dynamicDS = new ThreadLocal<DataSource>();
	private static final ThreadLocal<DataSource> logDS = new ThreadLocal<DataSource>();

	
	public static void setDataSource(DataSource dataSourceType) {
		if (dataSourceType == null) {
			throw new RestException("DataSourceType.is.Null");
		}		
		accountDS.set(dataSourceType);
	}
	
	public static DataSource getDataSource(){
		return accountDS.get();
	}
	
	public static void setDynamicDS(DataSource dst)  {
		if (dst == null) {
			throw new RestException("Dynamic.DataSourceType.is.Null");
		}		
		dynamicDS.set(dst);
	}
	
	public static DataSource getDynamicDS(){
		return dynamicDS.get();
	}
	
	public static DataSource getLogDS() {
		return logDS.get();
	}	
	
	public static void setAccount(String accountId)  {
		if (Strings.isNullOrEmpty(accountId)) {
			throw new RestException("accountId.is.Null");
		}		
		account.set(accountId);
		accountDS.set(DataSource.getAccountDS(accountId));
		logDS.set(DataSource.getLogDS(accountId));
	}
	
	public static String getAccount(){
		return account.get();
	}
	
	public static void clearDS(){
		accountDS.remove();
	}
	
	public static void clear(){
		account.remove();
		accountDS.remove();
		logDS.remove();
	}
	

	
}