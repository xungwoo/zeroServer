package com.thirtygames.zero.admin.controller.shop;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ShopItemValidator;
import com.thirtygames.zero.common.model.admintool.AdminShopItem;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.model.type.PriceType;
import com.thirtygames.zero.common.service.admintool.AdmShopItemService;

@Slf4j
@Controller
@RequestMapping(value = "/shop-item")
public class ShopItemController extends AdminGridController<AdminShopItem, String, AdmShopItemService, ShopItemValidator> {
	
	public ShopItemController() {
		pageInfo.setTitle("ShopItem");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Shop);
		adminMenu.setSelectMenu("ShopItem");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("shop/itemList");
		
		model.addAttribute("RewardTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(Reward.RewardType.get$CODE_LOOKUP()));
		model.addAttribute("itemTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(ShopItem.ItemType.get$CODE_LOOKUP()));
		model.addAttribute("itemCategoryOpts", Joiner.on(";").withKeyValueSeparator(":").join(ShopItem.ItemCategory.get$CODE_LOOKUP()));
		model.addAttribute("buyTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(PriceType.get$CODE_LOOKUP()));
		return model;
	};	
}


