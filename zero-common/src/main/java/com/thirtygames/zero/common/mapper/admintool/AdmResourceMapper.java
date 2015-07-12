package com.thirtygames.zero.common.mapper.admintool;


import com.mysql.jdbc.MysqlDataTruncation;
import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.UserResource;


public interface AdmResourceMapper extends GenericMapper<UserResource, String> {

	public int updateAddition(UserResource ar) throws MysqlDataTruncation;
}