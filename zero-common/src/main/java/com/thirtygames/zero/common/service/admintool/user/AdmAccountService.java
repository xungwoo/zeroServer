package com.thirtygames.zero.common.service.admintool.user;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.admintool.UserAccount;

public interface AdmAccountService extends GenericService<UserAccount, String> {

	public void resetAuthTokenValidYmdt(String accountId);
}