package com.thirtygames.zero.api.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.collect.Iterables;
import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.EquipmentValidator;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta.EqClass;
import com.thirtygames.zero.common.model.params.GemGradeUpParams;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.UnitService;
import com.thirtygames.zero.common.service.equipment.EquipmentService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMergeMetaService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;

/**
 * @author x
 *
 */
/**
 * @author x
 *
 */
@Slf4j
@Controller
@RequestMapping(value = "/equipments")
public class EquipmentController extends ApiGenericController<Equipment, String, EquipmentService, EquipmentValidator> {
	
	@Autowired
	EquipMetaService emService;
	
	@Autowired
	EquipMergeMetaService emmService;	
	
	@Autowired
	UnitService unitService;
	
	@Autowired
	AccountResourceService arService;
	

	@Override
	protected Equipment preAdd(Equipment entity, String accountId) {
		throw new RestException("not.allow.request");
	}	

	@Override
	protected Equipment preUpdate(Equipment equipment, String accountId)  {
		equipment.setAccountId(accountId);
		return equipment;
	}

	@Override
	protected Equipment preSearch(Equipment entity, String accountId)  {
		entity.setAccountId(accountId);
		return entity;
	}
	
	@Override
	protected ApiJsonResult<Equipment> postSearch(ApiJsonResult<Equipment> result)  {
		List<EquipmentStat> equipStatList = service.getStatList(result.getParams().getAccountId());
		int i = 0;
		int j = 0;
		
		Iterator<Equipment> itEquip = result.getResultList().iterator();
		while(itEquip.hasNext()) {
			Equipment equip = itEquip.next();
			Iterator<EquipmentStat> itEquipStat = equipStatList.iterator();
			while(itEquipStat.hasNext()) {
				EquipmentStat eqStat = itEquipStat.next();
				if (equip.getEquipmentId().equals(eqStat.getEquipmentId())) {
					equip.getStats().add(eqStat);
					itEquipStat.remove();
				} else {
					// because sorted list.
					break;
				}
				i++;
			}
			j++;
		}
		
		log.debug("loop1 ::" + i++ + "::loop2::" + j++ + "::EquipList Size::" + result.getResultList().size() + "::equipStatList Size::" + equipStatList.size());
		return result;
	}	

	@RequestMapping(value = "/levelup", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> levelUp(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="targetId") String targetId, 
			@RequestParam(required=true, value="useIds") String useIds,
			@RequestParam(value="equipLevelUpDrug", defaultValue="0") int equipLevelUpDrug)  {
		Equipment targetEq = service.get(targetId);
		if (targetEq == null) throw new RestException(Errors.NotFoundEquipment, "equipmentId:" + targetId);
		if (targetEq.getLevel() >= EquipLevelMeta.MAX_LEVEL) throw new RestException("Already.Max.Level");
		targetEq.setAccountId(myAccountId);
		
		List<String> eqIdList = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(useIds);
		if (eqIdList == null) throw new RestException(Errors.NotFoundEquipment, "equipmentIds:" + useIds);
		if (eqIdList.contains(targetId)) throw new RestException("TargetEquipment.equal.SourceEquipment.");
		
		List<Equipment> useEqList = service.getUseEquipments(eqIdList);
		if (useEqList == null || eqIdList.size() != useEqList.size()) throw new RestException("Not.found.SourceEquipment.");
		
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResultLog(service.levelUp(targetEq, useEqList, equipLevelUpDrug));
		result.setResource(arService.get(myAccountId));
		result.setResult(service.get(targetId));
		return result;
	}
	
	@RequestMapping(value = "/merge", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> merge(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(value="useGold", defaultValue="0") long useGold,
			@RequestParam(value="useCash", defaultValue="0") long useCash,			
			@RequestParam(required=true, value="useId1") String eqId1,
			@RequestParam(required=true, value="useId2") String eqId2)  {
		Equipment eq1 = service.get(eqId1);
		if (eq1 == null) throw new RestException(Errors.NotFoundEquipment, "equipmentId:" + eqId1);
		if (eq1.getLevel() < EquipLevelMeta.MAX_LEVEL) throw new RestException("Not.enough.Level");
		
		Equipment eq2 = service.get(eqId2);
		if (eq2 == null) throw new RestException(Errors.NotFoundEquipment, "equipmentId:" + eqId2);
		if (eq2.getLevel() < EquipLevelMeta.MAX_LEVEL) throw new RestException("Not.enough.Level");
		if (eq1.getGrade() != eq2.getGrade()) throw new RestException("Not.equal.Grade.");
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(myAccountId);
		ar.setGold(useGold);
		ar.setCash(useCash);
		
		int mergedGrade = eq1.getGrade() + 1;
		int mergedClass = emService.getMergedEquipmentClass(eq1.getEqClass(), eq2.getEqClass());
		boolean isDecoRateWeight = false;
		if (mergedClass == EqClass.Magic.getCode()) {
			mergedClass = EqClass.Normal.getCode();
			isDecoRateWeight = true;
		}
		Integer mergedEquipmentType = emService.getMergedEquipmentType(mergedGrade, mergedClass, eq1.getCategory());
		if (mergedEquipmentType == null) {
			if ((mergedClass - 1) == EqClass.Magic.getCode()) {
				mergedClass = EqClass.Normal.getCode();
				isDecoRateWeight = true;
			}			
			mergedEquipmentType = emService.getMergedEquipmentType(mergedGrade, mergedClass - 1, eq1.getCategory());
		}
		
		if (mergedEquipmentType == null) {
			throw new RestException(Errors.NotFoundEquipMeta, "Merged EquipmentType:" + eqId1);
		}
		
		Equipment equip = new Equipment();
		equip.setAccountId(myAccountId);
		equip.setEquipmentType(mergedEquipmentType); 
		equip.setGrade(mergedGrade);
		equip = emService.generateEquip(equip, isDecoRateWeight);		
		
		service.addMergedEquipByResource(equip, eq1, eq2, ar);
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResult(equip);
		result.setResource(arService.get(myAccountId));
		return result;
	}
	
	@RequestMapping(value = "/sell/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody
	ApiJsonResult<Equipment> sell(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("id") String id)  {
		
		// validator.validate(entity, bindingResult);

		Equipment equip = service.get(id);
		if (equip == null)
			throw new RestException(Errors.NotFoundEquipment, "equipmentId:" + id);

		EquipmentMeta meta = emService.getEquipmentMeta(equip);		
		if (meta == null)
			throw new RestException(Errors.NotFoundEquipMeta, "equipmentId:" + id);	
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResource(service.sell(meta, equip));
		return result;
	}
	
	
	@RequestMapping(value = "/sell/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Equipment> sellMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="equipmentIds") String ids)  {
		// validator.validate(entity, bindingResult);
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResult(service.sellMulti(ids, myAccountId));
		result.setResource(arService.get(myAccountId));
		return result;
	}	
	
	@RequestMapping(value = "/gem/install", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> gemInstall (
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="equipmentId") String equipmentId,
			@RequestParam(required=true, value="gemId") String gemId,
			@RequestParam(required=true, value="socketNo") int socketNo
			)  {

		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResultCount(service.gemInstall(equipmentId, gemId, socketNo));
		result.setResult(service.get(equipmentId));
		return result;
	}
	
	@RequestMapping(value = "/gem/clear", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> gemClear (
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="equipmentId") String equipmentId,
			@RequestParam(required=true, value="gemId") String gemId,
			@RequestParam(required=true, value="socketNo") int socketNo
			)  {
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResultCount(service.gemClear(equipmentId, gemId, socketNo));
		result.setResult(service.get(equipmentId));
		return result;
	}
	
	@RequestMapping(value = "/gem/gradeup", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> gemInstall (
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@ModelAttribute GemGradeUpParams params
			)  {
		
		// validator.validate(params, bindingResult);
		params.setAccountId(myAccountId);
		
		Equipment gem1 = service.get(params.getGem1Id());
		if (gem1 == null) throw new RestException("Not.found.Gem1");
		if (gem1.getGrade() >= Equipment.GEM_MAX_GRADE) throw new RestException("Already.Max.Grade");
		params.setGem1(gem1);
		
		Integer gradeUpEquipType = emService.getGemGradeUpEquipType(gem1.getSubCategory(), gem1.getGrade());
		if (gradeUpEquipType == null) {
			throw new RestException("Not.found.high.grade.Gem.Meta");
		}
		params.setGradeUpEquipType(gradeUpEquipType);
		
		Equipment gradeUpGem = new Equipment();
		gradeUpGem.setAccountId(myAccountId);
		gradeUpGem.setEquipmentType(gradeUpEquipType); 
		gradeUpGem.setGrade(gem1.getGrade() + 1);
		gradeUpGem = emService.generateEquip(gradeUpGem, false);
		params.setGradeUpGem(gradeUpGem);
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResult(service.gemGradeUp(params));
		result.setResource(arService.get(myAccountId));
		return result;
	}
	
	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Equipment> addMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = true, value = "equipParams") String equipParams)  {
		
		List<Equipment> genEquipmentList = new ArrayList<Equipment>();
		Iterable<String> equips = Splitter.on(',').trimResults().omitEmptyStrings().split(equipParams);
		if (!equips.iterator().hasNext()) throw new RestException("Invalid.Param");
		
		for (String param : equips) {
			Equipment equip = new Equipment();
			String[] temp = Iterables.toArray(Splitter.on(':').trimResults().omitEmptyStrings().split(param), String.class);
			if (temp.length != 2) throw new RestException("Invalid.Param"); 
			equip.setAccountId(myAccountId);
			equip.setEquipmentType(Integer.parseInt(temp[0]));
			equip.setGrade(Integer.parseInt(temp[1]));
			equip = emService.generateEquip(equip, false);
			genEquipmentList.add(equip);
		}		
		
		service.multiAdd(genEquipmentList);

		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResultCount(genEquipmentList.size());
		result.setResult(genEquipmentList);
		return result;
	}

	@RequestMapping(value = "/switch", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Equipment> equipSwitch(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "equipId") String equipId,
			@RequestParam(required = true, value = "equipPosition") Integer equipPosition,
			@RequestParam(required = false, value = "removeId") String removeId,
			@RequestParam(required = true, value = "unitId") String unitId
			)  {
		
		if ((Strings.isNullOrEmpty(equipId) || Strings.isNullOrEmpty(unitId)) && Strings.isNullOrEmpty(removeId)) {
			throw new RestException("Not.invalid.param");
		}
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		Equipment eq = new Equipment();
		eq.setAccountId(myAccountId);
		eq.setUnitId(unitId);
		eq.setEquipPosition(equipPosition);
		
		if (Strings.isNullOrEmpty(removeId)) {
			List<Equipment> eqList = service.search(0, 0, eq);
			if (eqList != null && eqList.size() > 0) {
				throw new RestException("Already.used.position");
			}

			eq.setEquipmentId(equipId);
			result.setResultCount(service.update(eq));
		} else {
			eq.setEquipmentId(equipId);
			result.setResultCount(service.equipSwitch(eq, removeId));
		}
		
		result.setParams(eq);	
		return result;
	}	
	
	@RequestMapping(value = "/virtual", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Equipment> virtual(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = true, value = "equipParams") String equipParams)  {
		
		List<Equipment> genEquipmentList = new ArrayList<Equipment>();
		Iterable<String> equips = Splitter.on(',').trimResults().omitEmptyStrings().split(equipParams);
		if (!equips.iterator().hasNext()) throw new RestException("Invalid.Param");
		
		for (String param : equips) {
			Equipment equip = new Equipment();
			String[] temp = Iterables.toArray(Splitter.on(':').trimResults().omitEmptyStrings().split(param), String.class);
			if (temp.length != 2) throw new RestException("Invalid.Param"); 
			equip.setAccountId(myAccountId);
			equip.setEquipmentType(Integer.parseInt(temp[0]));
			equip.setGrade(Integer.parseInt(temp[1]));
			equip = emService.generateEquip(equip, false);
			genEquipmentList.add(equip);
		}		
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		result.setResultCount(genEquipmentList.size());
		result.setResult(genEquipmentList);
		return result;
	}
}
