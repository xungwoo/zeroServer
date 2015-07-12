package com.thirtygames.zero.common.mapper.admintool;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.UserAccount;


public interface AdmAccountMapper extends GenericMapper<UserAccount, String> {

	void resetAuthTokenValidYmdt(@Param("accountId") String accountId);
}