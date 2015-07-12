package com.thirtygames.zero.api.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

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

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.StageValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.StageClear;
import com.thirtygames.zero.common.model.StageParam;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.log.CheatLog;
import com.thirtygames.zero.common.model.meta.ApiMeta;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.FriendService;
import com.thirtygames.zero.common.service.StageService;
import com.thirtygames.zero.common.service.log.CheatLogService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/stages")
public class StageController extends ApiGenericController<Stage, String, StageService, StageValidator> {
	
	@Autowired
	FriendService frService;
	
	@Autowired
	ApiMetaService apiMetaService;
	
	@Autowired
	AccountResourceService arService;
	
	@Autowired
	CheatLogService cheatLogService;	
	
	
	@RequestMapping(value = "/result", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Object> result(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@ModelAttribute StageParam stageParam,
			BindingResult bindingResult, 
			SessionStatus status,
			HttpServletRequest request)  {

		//validator.validate(entity, bindingResult);
		if (bindingResult.hasErrors()) {
			throw new RestException("Param Binding Error:" + bindingResult.getAllErrors().toString());
		}
		
		if((stageParam.getGainGold() != null && stageParam.getGainGold() > 20000) || (stageParam.getDropUnlockKey() != null && stageParam.getDropUnlockKey() > 20 )) {
			CheatLog cheatLog = new CheatLog();
			cheatLog.setAccountId(myAccountId);
			String requestInfo = " token:" + request.getHeader("myAccessToken");
			requestInfo += " ts:" + request.getHeader("myTimeStamp");
			requestInfo += request.getLocalAddr();
			cheatLog.setAdditionalInfo("StageResult:Gold:" + stageParam.getGainGold() + ", UnlockKey:" + stageParam.getDropUnlockKey() + requestInfo);
			cheatLogService.save(cheatLog);
			//throw new RestException(Errors.GoldCheat, "params:" + stageParam);
		}		

		status.setComplete();
		
		ApiJsonResult<Object> result = new ApiJsonResult<Object>();
		stageParam.setAccountId(myAccountId);
		Map<String, Object> resultMap = service.stageResult(stageParam);
		result.setResult(resultMap);
		result.setResource(arService.get(myAccountId));
		return result;
	}

	
	@RequestMapping(value = "/clear", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<StageClear> clearStepList(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform)  {
		
		List<StageClear> clearStepList = service.getClearStepList(myAccountId);
		
		return new ApiJsonResult<StageClear>(clearStepList);
	}
	
	
	@RequestMapping(value = "/revival", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<AccountResource> revival(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform)  {
		
		ApiJsonResult<AccountResource> result = new ApiJsonResult<AccountResource>();
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ApiMeta meta = apiMetaService.get(ApiMeta.STAGE_REVIVAL_CASH);
		if (meta == null || meta.getLongValue() == null) {
			throw new RestException("Not.found.ApiMeta.STAGE_REVIVAL_CASH");
		}
		ar.setCash(meta.getLongValue());
		result.setResource(arService.updateSubtraction(ar, true));
		return result;
	}	
	
	@Override
	protected Stage preSearch(Stage stage, String accountId)  {
		return stage;
	}	

	@Override
	protected Stage preAdd(Stage entity, String accountId)  {
		throw new RestException("not.allow.request");
	}

	@Override
	protected Stage preUpdate(Stage entity, String accountId)  {
		throw new RestException("not.allow.request");
	}
	
}