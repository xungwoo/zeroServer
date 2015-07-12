package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.AccountResource;

public interface AccountService extends GenericService<Account, String> {
	
	public int updateLastSyncTime(String myAccountId);
	public boolean existNickName(String accountId, String nickName);
	public AccountResource nickNameUpdate(Account account, AccountResource ar);
	public String getToken(String accountId);
	
	public Account getAccountLoginInfo(String accountId);
	public void updateToken(String accountId, String token);
	public int withdrawUpdate(Account account);
	public Account getAccountDynamic(String accountId);
	public AccountResource buyExtraInventory(Account account, AccountResource ar);
	public boolean checkMaxInventory(String accountId, int extraInventoryMaxCount);
	
	public void getMinuteDiffLastLogin(String accountId, String lang);
}