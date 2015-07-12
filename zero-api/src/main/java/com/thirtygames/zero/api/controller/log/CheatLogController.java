package com.thirtygames.zero.api.controller.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.CheatLogValidator;
import com.thirtygames.zero.common.model.log.CheatLog;
import com.thirtygames.zero.common.service.log.CheatLogService;


@Slf4j
@Controller
@RequestMapping(value = "/log/cheat")
public class CheatLogController extends ApiGenericController<CheatLog, Long, CheatLogService, CheatLogValidator> {
	@Override
	protected CheatLog preAdd(CheatLog log, String accountId) {
		log.setAccountId(accountId);
		return log;
	}

	@Override
	protected CheatLog preUpdate(CheatLog entity, String accountId) {
		return null;
	}

	@Override
	protected CheatLog preSearch(CheatLog entity, String accountId) {
		return null;
	}
	
	
}