package com.thirtygames.zero.api.controller;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.api.validator.AccountValidator;
import com.thirtygames.zero.common.etc.datasource.ContextHolder;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.TokenUtil;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.datasource.Shard;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

/**
 *
 * API List
 * 1. /add
 * 2. /delete
 * 3. /login
 *
 * @author xungwoo
 */
@Slf4j
@Controller
@RequestMapping(value = "/accounts")
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	@Autowired
	private AccountValidator validator;
	
	@Autowired
	private DataSourceService dsManager;
	
	@RequestMapping(value="/new", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Account> create(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,  
			@ModelAttribute Account account, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		Long memberNo = account.getMemberNo();
		try {
			account.setAuthToken( TokenUtil.generateAccessToken() );
		} catch (RestException e) {
			throw new RestException("cannot.generate.accesstoken");
		}		
		
		
		Shard shardParam = new Shard();
		shardParam.setMemberNo(memberNo);
		
		Shard oldShard = dsManager.getShard(shardParam);
		log.debug("oldShard::" + oldShard);
		if (oldShard == null) {
			account.generateAccountId();
			account.setMemberNo(memberNo);
			if (account.getChannelType() == null) {
				account.setChannelType(Account.DEFAULT_CHANNEL_TYPE);
			}
			
			ContextHolder.setAccount(account.getAccountId());
			log.debug("saveShard::" + ContextHolder.getDataSource());
			
			service.save(account);
		} else {
			String oldAccountId = oldShard.getAccountId();
			account.setAccountId(oldAccountId);
			ContextHolder.setAccount(oldAccountId);
		}
		
		Account accountResult = service.get(account.getAccountId());
		ValidationUtil.isNullModel(accountResult, "accountId:" + account.getAccountId());
		result.setResult(accountResult);
		result.setResource(accountResult.getAccountResource());
		return result;
	}
			

	
	@RequestMapping(value = "/login", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Account> login(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform)  {
		
		ContextHolder.setAccount(myAccountId);
		Account accountLoginInfo = service.getAccountLoginInfo(myAccountId);
		if (accountLoginInfo == null) {
			throw new RestException(HttpServletResponse.SC_UNAUTHORIZED, "Not.found.Account!");
		}
		
		log.debug("accountLoginInfo.getLanguage()::" + accountLoginInfo.getLanguage());
		service.getMinuteDiffLastLogin(myAccountId, accountLoginInfo.getLanguage());
		
		try {
			String authToken  = TokenUtil.generateAccessToken();
			service.updateToken(myAccountId, authToken);
		} catch (RestException e) {
			throw new RestException("Fail.generate.accesstoken");
		}		
		
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		Account loginAccount = service.get(accountLoginInfo.getAccountId());
		result.setResult(loginAccount);
		result.setResource(loginAccount.getAccountResource());
		return result;
	}

	
	@RequestMapping(method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Account> update(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@ModelAttribute Account account, 
			BindingResult bindingResult, 
			SessionStatus status)  {

		validator.validate(account, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();
		
		if (account.getNickName() != null) {
			this.checkNickNameDuplicated(myAccountId, account.getNickName());
		}
		
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		account.setAccountId(myAccountId);
		result.setResultCount(service.update(account));
		result.setResult(service.get(myAccountId));
		return result;
	}	
	
	
	@RequestMapping(value="/withdraw", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Account> nickName(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="memberNo") long memberNo)  {
		
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		
		Account account = new Account();
		account.setAccountId(myAccountId);
		account.setMemberNo(memberNo);
		account.setWithdraw(true);
		result.setResult(service.withdrawUpdate(account));
		return result;
	}
	
	@RequestMapping(value="/nickname", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Account> nickName(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required=true, value="nickName") String nickName)  {
		
		this.checkNickNameDuplicated(myAccountId, nickName);
		
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		
		Account account = new Account();
		account.setAccountId(myAccountId);
		account.setNickName(nickName);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setCash(Account.NICKNAME_CHANGE_CASH);
		
		result.setResource(service.nickNameUpdate(account, ar));
		return result;
	}
	
	@RequestMapping(value="/extra-inventory", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Account> extraInventory(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp 
			)  {
		
		if (service.checkMaxInventory(myAccountId, Account.EXTRA_INVENTORY_MAX_COUNT)) {
			throw new RestException(Errors.AlreadyMaxInventory);
		};
		
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		
		Account account = service.get(myAccountId);
		long cache = account.calcNextInventoryBuyCache(account.getExtraInventory());
		
		Account accountParams = new Account();
		accountParams.setAccountId(myAccountId);
		accountParams.setExtraInventory(Account.EXTRA_INVENTORY_ADD_COUNT);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setCash(cache);
		
		account.setExtraInventory(account.getExtraInventory() + Account.EXTRA_INVENTORY_ADD_COUNT);
		result.setResult(account);
		result.setResource(service.buyExtraInventory(accountParams, ar));
		return result;
	}


	// all Shard check.
	private void checkNickNameDuplicated(String accountId, String nickName)  {
		boolean isExist = false;
		for(DataSource dst : DataSource.GAME_SHARDS) {
			dsManager.switchDataSource(dst);
			isExist = isExist | service.existNickName(accountId, nickName); 
		}
		
		if (isExist) {
			throw new RestException(HttpServletResponse.SC_NOT_ACCEPTABLE, "NickName.is.duplicated." + nickName);
		}
	}	
	
}
