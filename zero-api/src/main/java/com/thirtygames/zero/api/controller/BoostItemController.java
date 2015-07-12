package com.thirtygames.zero.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.BoostItemValidator;
import com.thirtygames.zero.common.model.BoostItem;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.ApiMeta;
import com.thirtygames.zero.common.service.BoostItemService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;


@Slf4j
@Controller
@RequestMapping(value = "/boost-items")
public class BoostItemController extends ApiGenericController<BoostItem, String, BoostItemService, BoostItemValidator> {
	
	@Autowired
	ApiMetaService apiMetaService;	
	
	@RequestMapping(value = "/item", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Object> boostItem(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestHeader("myMetaRevision") int myMetaRevision)  {
		ApiJsonResult<Object> result = new ApiJsonResult<Object>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		BoostItem param = new BoostItem();
		param.setAccountId(myAccountId);
		List<BoostItem> itemList = service.search(param);
		if (itemList != null && itemList.size() > 0) {
			resultMap.put("boostItemList", itemList);
		}

		ApiMeta itemBoostMeta = apiMetaService.getByCache(BoostItem.ITEM_DROP_BOOST, myMetaRevision);
		resultMap.put("itemBoostRate", itemBoostMeta.getFloatValue());
		resultMap.put("itemBoostTime", itemBoostMeta.getLongValue().intValue());
		ApiMeta goldBoostMeta = apiMetaService.getByCache(BoostItem.GOLD_DROP_BOOST, myMetaRevision);
		resultMap.put("goldBoostRate", goldBoostMeta.getFloatValue());
		resultMap.put("goldBoostTime", goldBoostMeta.getLongValue().intValue());
		result.setResult(resultMap);
		return result;
	}	
	
	
	
	@Override
	protected BoostItem preAdd(BoostItem entity, String accountId) {
		return null;
	}

	@Override
	protected BoostItem preUpdate(BoostItem entity, String accountId) {
		return null;
	}

	@Override
	protected BoostItem preSearch(BoostItem entity, String accountId) {
		return null;
	}
	
	
}