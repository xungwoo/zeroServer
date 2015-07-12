package com.thirtygames.zero.api.controller.meta;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.service.meta.LoginRewardMetaService;
import com.thirtygames.zero.common.service.meta.WellcomePresentService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/login-reward")
public class LoginRewardMetaController {


	@Autowired
	WellcomePresentService wellcomePresentService;
	
	@Autowired
	LoginRewardMetaService loginRewardMetaService;
	
	
	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	Map<String, Object> meta(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, 
			@RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestHeader("myMetaRevision") int myMetaRevision)
			throws RestException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		resultMap.put("wellcomePresent", wellcomePresentService.rangeByCache(0, 0, myMetaRevision));
		resultMap.put("loginReward", loginRewardMetaService.getLoginReward());
		
		return resultMap;
	}
	
	
	
	
}
