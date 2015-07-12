package com.thirtygames.zero.common.mapper;


import com.mysql.jdbc.MysqlDataTruncation;
import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.AccountResource;

public interface ResourceMapper extends GenericMapper<AccountResource, String> {
	
	public int updateAddition(AccountResource ar) throws MysqlDataTruncation;
	public int updateSubtraction(AccountResource ar) throws MysqlDataTruncation;
	
}