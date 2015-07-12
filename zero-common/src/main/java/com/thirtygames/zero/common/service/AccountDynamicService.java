package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Account;

public interface AccountDynamicService extends GenericService<Account, String> {

	void updateRemoveFacebookId(String facebookId);
	
}