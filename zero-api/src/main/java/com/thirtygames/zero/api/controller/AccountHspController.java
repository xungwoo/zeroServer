package com.thirtygames.zero.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountHspService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Controller
@RequestMapping(value = "/accounts/hsp")
public class AccountHspController {
	
	@Autowired
	AccountHspService service;
	
	@Autowired
	DataSourceService dsManager;


	@RequestMapping(value = "/{memberNo}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Account> get(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("memberNo") long memberNo)  {
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		
		Account account = new Account();
		for(DataSource dst : DataSource.GAME_SHARDS) {
			dsManager.switchDataSource(dst);
			account = service.get(memberNo);
			if (account != null) break;
		}
		
		if (account == null) {
			throw new RestException(Errors.NotFoundData, "Account:" + myAccountId);
		}
		
		result.setResult(account);
		return result;
	}	
}