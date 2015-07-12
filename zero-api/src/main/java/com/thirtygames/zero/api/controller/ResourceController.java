package com.thirtygames.zero.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.ResourceValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;

@Slf4j
@Controller
@RequestMapping(value = "/resources")
public class ResourceController extends ApiGenericController<AccountResource, String, AccountResourceService, ResourceValidator> {
	
	@RequestMapping(value = "/recharge", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<AccountResource> recharge(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@ModelAttribute AccountResource resource,
			BindingResult bindingResult,
			SessionStatus status
			) throws IllegalAccessException {
		
		validator.validateRecharge(resource, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestException("Param Binding Error:" + bindingResult.getAllErrors().toString());
		}
		status.setComplete();
		
		resource.setAccountId(myAccountId);
		return new ApiJsonResult<AccountResource>(service.updateAddition(resource, true));
	}
	
	@RequestMapping(value = "/exhaust", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<AccountResource> exhaust(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@ModelAttribute AccountResource resource,
			BindingResult bindingResult,
			SessionStatus status			
			) throws IllegalAccessException {
		
		validator.validateExhaust(resource, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestException("Param Binding Error:" + bindingResult.getAllErrors().toString());
		}
		status.setComplete();
		
		resource.setAccountId(myAccountId);
		return new ApiJsonResult<AccountResource>(service.updateSubtraction(resource, true));
	}

	@Override
	protected AccountResource preAdd(AccountResource entity, String accountId)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AccountResource preUpdate(AccountResource entity, String accountId)  {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AccountResource preSearch(AccountResource entity, String accountId)  {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
