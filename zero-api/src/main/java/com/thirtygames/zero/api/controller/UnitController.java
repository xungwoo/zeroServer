package com.thirtygames.zero.api.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.common.base.Splitter;
import com.thirtygames.zero.api.controller.common.ApiResourceController;
import com.thirtygames.zero.api.validator.UnitValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.UnitLimitExceed;
import com.thirtygames.zero.common.service.UnitService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.meta.UnitLimitExceedMetaService;


@Slf4j
@Controller
@RequestMapping(value = "/units")
public class UnitController extends ApiResourceController<Unit, String, UnitService, UnitValidator> {
	
	@Autowired
	UnitLimitExceedMetaService unitlimitExceedMetaService;
	
	@Autowired
	DataSourceService dsManager;	
	
	@RequestMapping(value = "/unlock", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Unit> unlock(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = true, value = "unitType") int unitType,
			@RequestParam(value = "useGold", defaultValue="0") long useGold,
			@RequestParam(value = "useCash", defaultValue="0") long useCash,	
			@RequestParam(value = "unlockKey", defaultValue="0") long unlockKey)  {

		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		
		Unit unit = service.generateUnit(unitType, myAccountId);
		result.setParams(unit);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setGold(useGold);
		ar.setCash(useCash);
		ar.setUnlockKey(unlockKey);
		result.setResource(service.unlockUnit(unit, ar));
		return result;
	}
	
	
	// unit list 
	@Override
	protected Unit preSearch(Unit unit, String accountId) {
		unit.setAccountId(accountId);
		return unit;
	}
	

	// updateByResource
	@Override
	protected Unit preUpdateByResource(Unit unit, String accountId) {
		unit.setAccountId(accountId);
		return unit;
	}

	@RequestMapping(value = "/limit-exceed/start", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Unit> limitExceedStart(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@ModelAttribute Unit unit, 
			@RequestParam(required = true, value = "useGold") long useGold,
			@RequestParam(required = true, value = "useUnlockKey") long useUnlockKey,			
			@RequestParam(value = "level", required=true) int level,			
			BindingResult bindingResult, 
			SessionStatus status)  {
		
		validator.validateLimitExceed(unit, bindingResult);	
		validator.processErrors(bindingResult);
		
		UnitLimitExceed meta = unitlimitExceedMetaService.get(unit.getLevel());
		
		unit.setLimitExceedTime(meta.getLimitExceedTime());
		unit.setAccountId(myAccountId);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setGold(useGold);
		ar.setUnlockKey(useUnlockKey);
		validator.validateResource(ar, bindingResult);
		validator.processErrors(bindingResult);
		status.setComplete();

		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		result.setParams(unit);
		result.setResource(service.limitExceedStart(unit, ar));
		result.setResult(service.get(unit.getUnitId()));
		return result;
	}
	
	@RequestMapping(value = "/limit-exceed/end", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Unit> limitExceedStart(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "unitId") String unitId,
			@RequestParam(required = true, value = "useCash") long useCash)  {
		
		Unit unit = service.get(unitId);
		if (unit == null) throw new RestException("Not.found.Unit");
		
		UnitLimitExceed meta = unitlimitExceedMetaService.get(unit.getLevel());
		int needCash = unit.getRemainderTime() / meta.getTimeToCashRatio();
		if (unit.getRemainderTime() % meta.getTimeToCashRatio() > 0) {
			needCash += 1; 
		}
		
		if (useCash < needCash) {
			throw new RestException("Need.Cash.value.is " + needCash);
		}
		
		unit.setLimitExceedTime(1);
		unit.setAccountId(myAccountId);
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setCash(useCash);
		
		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		result.setParams(unit);
		result.setResource(service.limitExceedEnd(unit, ar));
		result.setResult(service.get(unitId));
		return result;
	}

	@RequestMapping(value = "/limit-exceed/confirm", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Unit> limitExceedConfirm(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "unitId") String unitId)  {
		
		Boolean isComplete = service.checkLimitExceedComplete(unitId);
		if (!isComplete) throw new RestException("Not.complete.Unit.limitExceed.");

		Unit unit = new Unit();
		unit.setUnitId(unitId);
		unit.setLimitExceedTime(0);
		unit.setAccountId(myAccountId);
		
		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		result.setParams(unit);
		result.setResultCount(service.update(unit));
		result.setResult(service.get(unitId));
		return result;
	}
	
	@RequestMapping(value = "/{id}/equipments", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Unit> unitEquipments(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("id") String unitId)  {
		
		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		Unit unit = service.getUnitEquipments(unitId);
		result.setResult(unit);
		result.setResultCount((unit==null) ? 0 : 1);
		return result;
	}
	
	@RequestMapping(value = "/equipments/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Unit> multiUnitEquipments(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="unitIds") String unitIds,
			@RequestParam(value="accountId", defaultValue="") String accountId)  {
		
		List<String> unitIdList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(unitIds);
		if (unitIdList == null || unitIdList.size() <= 0) throw new RestException("Invalid.Param");
		
		if (accountId.isEmpty()) {
			accountId = myAccountId;
		}
		
		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		
		dsManager.switchDataSource(accountId);
		List<Unit> unitList = service.getMultiUnitEquipments(unitIdList);
		result.setResult(unitList);
		result.setResultCount(unitList.size());
		return result;
	}
	
	@RequestMapping(value = "/equipments/gems/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Unit> multiUnitEquipmentsWithGems(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="unitIds") String unitIds,
			@RequestParam(value="accountId", defaultValue="") String accountId)  {
		
		List<String> unitIdList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(unitIds);
		if (unitIdList == null || unitIdList.size() <= 0) throw new RestException("Invalid.Param");
		
		if (accountId.isEmpty()) {
			accountId = myAccountId;
		}
		
		ApiJsonResult<Unit> result = new ApiJsonResult<Unit>();
		
		dsManager.switchDataSource(myAccountId);
		List<Unit> unitList = service.getMultiUnitEquipmentsWithGems(unitIdList);
		result.setResult(unitList);
		result.setResultCount(unitList.size());
		return result;
	}
	

	@Override
	protected Unit preAdd(Unit entity, String accountdId)  {
		throw new RestException("not.allow.request");
	}

	@Override
	protected Unit preUpdate(Unit entity, String accountdId)  {
		throw new RestException("not.allow.request");
	}
	
}