package com.thirtygames.zero.common.mapper;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Account;

public interface AccountMapper extends GenericMapper<Account, String> {
	public int updateLastSyncTime(String accountId);

	public int existNickName(@Param("accountId") String accountId, @Param("nickName") String nickName);
	public void nickNameUpdate(Account account);


	public Account getAccountLoginInfo(@Param("accountId") String accountId);
	public String getToken(@Param("accountId") String accountId);
	public void updateToken(@Param("accountId")String accountId, @Param("authToken")String authToken);

	public int updateWithdraw(Account account);

	public void buyExtraInventory(Account account);

	public boolean checkMaxInventory(@Param("accountId")String accountId, @Param("extraInventoryMaxCount")int extraInventoryMaxCount);

	// Dynamic
	public void updateRemoveFacebookId(@Param("facebookId")String facebookId);

	public Integer getDiffMinuteLastLogin(String accountId);
}