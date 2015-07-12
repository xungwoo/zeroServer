package com.thirtygames.zero.api.controller.hsp;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.hsp.HSPItemDelivery;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.ItemDeliveryService;

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
@RequestMapping(value = "/hsp/item-delivery")
public class HSPItemDeliveryController {
	
	@Autowired
	private ItemDeliveryService service;
	
	@Autowired
	private AccountResourceService arService;
	
	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Account> itemDelivery(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@ModelAttribute HSPItemDelivery hspItemDelivery, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		ApiJsonResult<Account> result = new ApiJsonResult<Account>();
		
		hspItemDelivery.setAccountId(myAccountId);
		result.setResult(service.itemDelivery(hspItemDelivery));
		result.setResource(arService.get(myAccountId));
		return result;
		
	}
			

	
	
	
}