package com.thirtygames.zero.api.controller.meta;

import java.util.ArrayList;
import java.util.Iterator;
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
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.PeriodItem;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.service.PeriodItemService;
import com.thirtygames.zero.common.service.meta.ShopItemMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/meta/shop-item")
public class ShopItemMetaController extends ApiMetaController<ShopItem, Integer, ShopItemMetaService, ShopItemMetaValidator> {

	@Autowired
	ShopItemMetaService service;

	@Autowired
	PeriodItemService periodItemService;

	@RequestMapping(value = "/langs/{lang}", method = { RequestMethod.GET })
	public @ResponseBody
	List<ShopItem> listByLang(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestHeader("myMetaRevision") int myMetaRevision, @PathVariable("lang") String lang) {

		ShopItem metaParams = new ShopItem();
		metaParams.setLang(lang);
		List<ShopItem> shopItemMetaList = service.search(metaParams);
		log.debug("shopItemMetaList::" + shopItemMetaList);
		
		PeriodItem itemParams = new PeriodItem();
		itemParams.setAccountId(myAccountId);
		List<PeriodItem> periodItemList = periodItemService.search(itemParams);

		List<ShopItem> newCopyMetaList = new ArrayList<ShopItem>();
		if (periodItemList.isEmpty()) {
			newCopyMetaList = shopItemMetaList;
			
		} else {
			Iterator<ShopItem> itShopItemMeta = shopItemMetaList.iterator();
			while(itShopItemMeta.hasNext()){
				try {
					ShopItem copyShopItemMeta = itShopItemMeta.next().clone();
					newCopyMetaList.add(copyShopItemMeta);
					Iterator<PeriodItem> itPeriodItem = periodItemList.iterator();
					while (itPeriodItem.hasNext()) {
						PeriodItem periodItem = itPeriodItem.next();
						if (copyShopItemMeta.getItemKey().equals(periodItem.getMetaItemKey())) {
							copyShopItemMeta.setRepeatStartYmdt(periodItem.getStartYmdt());
							copyShopItemMeta.setRepeatEndYmdt(periodItem.getEndYmdt());
							itPeriodItem.remove();
							break;
						}
					}					
				} catch (CloneNotSupportedException e) {
					throw new RestException(Errors.None, e.getCause().toString());
				}
			}
		}

		return newCopyMetaList;
	}
}