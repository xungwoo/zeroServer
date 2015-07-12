package com.thirtygames.zero.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.LadderMatchLogValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.LadderMatch;
import com.thirtygames.zero.common.service.log.LadderMatchLogService;

@Slf4j
@Controller
@RequestMapping(value = "/ladder/match/log")
public class LadderMatchLogController extends ApiGenericController<LadderMatch, String, LadderMatchLogService, LadderMatchLogValidator> {

	@Override
	protected LadderMatch preSearch(LadderMatch match, String accountId)  {
		match.setAccountId(accountId);
		return match;
	}	
	
	@Override
	protected LadderMatch preUpdate(LadderMatch match, String accountId)  {
		throw new RestException("not.allow.request");
	}	
	
	@Override
	protected LadderMatch preAdd(LadderMatch match, String accountId)  {
		throw new RestException("not.allow.request");
	}	

}