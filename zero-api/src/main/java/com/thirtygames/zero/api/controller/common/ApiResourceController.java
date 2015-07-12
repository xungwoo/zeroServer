package com.thirtygames.zero.api.controller.common;

import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.thirtygames.zero.common.etc.validator.BaseValidator;
import com.thirtygames.zero.common.generic.ResourceService;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.common.ApiJsonResult;

@Slf4j
public abstract class ApiResourceController<T, K, S extends ResourceService<T, K>, V extends BaseValidator> extends ApiGenericController<T, K, S, V> {
	abstract protected T preUpdateByResource(T entity, String accountId) ;
	protected ApiJsonResult<T> postUpdateByResource(ApiJsonResult<T> result)  {
		return result;
	};

	@RequestMapping(value = "/update-resource", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<T> updateByResource(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(value = "useGold", defaultValue="0") long useGold,
			@RequestParam(value = "useCash", defaultValue="0") long useCash,			
			@ModelAttribute T entity, 
			BindingResult bindingResult, 
			SessionStatus status)  {
		log.debug("################### GenericController update by resource!!" + entity.toString());

		validator.validate(entity, bindingResult);
		validator.processErrors(bindingResult);
		
		status.setComplete();

		ApiJsonResult<T> result = new ApiJsonResult<T>();
		result.setParams(entity);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setGold(useGold);
		ar.setCash(useCash);	
		result.setResource(service.updateByResource(preUpdateByResource(entity, myAccountId), ar));
		return postUpdateByResource(result);
	}		
}
