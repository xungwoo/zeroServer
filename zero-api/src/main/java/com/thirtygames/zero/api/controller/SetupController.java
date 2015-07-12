package com.thirtygames.zero.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.SetupValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Setup;
import com.thirtygames.zero.common.service.SetupService;

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
@RequestMapping(value = "/setup")
public class SetupController extends ApiGenericController<Setup, String, SetupService, SetupValidator> {
	@Override
	protected Setup preAdd(Setup entity, String accountId)  {
		entity.setAccountId(accountId);
		return entity;
	}

	@Override
	protected Setup preUpdate(Setup entity, String accountId)  {
		entity.setAccountId(accountId);
		return entity;
	}

	@Override
	protected Setup preSearch(Setup entity, String accountId)  {
		// TODO Auto-generated method stub
		return null;
	}
	

}