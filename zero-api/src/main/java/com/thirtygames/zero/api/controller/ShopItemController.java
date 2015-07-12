package com.thirtygames.zero.api.controller;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.api.controller.common.ApiMetaController;
import com.thirtygames.zero.api.validator.meta.ShopItemMetaValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.model.meta.ShopItem.ItemType;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.ShopItemService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.meta.ShopItemMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/shop-item")
public class ShopItemController extends ApiMetaController<ShopItem, Integer, ShopItemService, ShopItemMetaValidator> {
	
	@Autowired
	ShopItemMetaService metaService;
	
	@Autowired
	EquipMetaService eqMetaService;
	
	@Autowired
	AccountResourceService arService;
	
	
	@RequestMapping(value = "/buy/{itemKey}", method = { RequestMethod.PUT })
	public @ResponseBody ApiJsonResult<Equipment> buy(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@PathVariable("itemKey") int itemKey) {
		
		ApiJsonResult<Equipment> result = new ApiJsonResult<Equipment>();
		
		ShopItem itemMeta = metaService.get(itemKey);
		if (itemMeta == null) {
			throw new RestException("Not.found.ShopItemMeta itemKey:" + itemKey);
		}
		itemMeta.setAccountId(myAccountId);
		
		ItemType type = ItemType.findByCode(itemMeta.getItemType());
		switch(type) {
		case Cash:
			break;
		case AP: case BP: case UnlockKey: case Gold: case EquipLevelUpDrug: 
			result.setResource(service.buyResource(itemMeta));
			break;
		case Equipment:
			List<Equipment> eqList = eqMetaService.generateGachaEquips(myAccountId, itemMeta);
			service.buyEquips(itemMeta, eqList);
			result.setResultList(eqList);
			result.setResult(eqList);
			result.setResultCount(eqList.size());
			result.setResource(arService.get(myAccountId));
			break;
		case Gem:			
			List<Equipment> gemList = eqMetaService.generateGachaGems(myAccountId, itemMeta);
			service.buyGems(itemMeta, gemList);
			result.setResultList(gemList);
			result.setResult(gemList);
			result.setResultCount(gemList.size());
			result.setResource(arService.get(myAccountId));
			break;
		case ItemBoost:
			service.buyItemBoost(itemMeta);
			result.setResource(arService.get(myAccountId));
			break;
		case GoldBoost:
			service.buyGoldBoost(itemMeta);
			result.setResource(arService.get(myAccountId));
			break;
		default:
			break;
		}
		
		return result; 
	}


}