package com.thirtygames.zero.api.controller;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.LoginRewardValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.LoginReward;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.LoginRewardService;

@Slf4j
@Controller
@RequestMapping(value = "/login-reward")
public class LoginRewardController extends ApiGenericController<LoginReward, String, LoginRewardService, LoginRewardValidator> {
	
	@Autowired
	AccountResourceService arService;
	
	@RequestMapping(value = "/confirm", method = { RequestMethod.POST })
	public @ResponseBody
	Map<String, Object> confirm(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform)  {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		if (service.checkRewardTime(myAccountId)) {
			resultMap.put("result", service.get(myAccountId));
		} else {
			resultMap = service.loginReward(myAccountId);
		}
		
		resultMap.put("resource", arService.get(myAccountId));
		return resultMap;
	}
	
	@Override
	protected LoginReward preSearch(LoginReward entity, String accountId)  {
		return entity;
	}
	
	@Override
	protected LoginReward preAdd(LoginReward entity, String accountId)  {
		throw new RestException("not.allow.request");
	}

	@Override
	protected LoginReward preUpdate(LoginReward entity, String accountId)  {
		throw new RestException("not.allow.request");
	}


}
