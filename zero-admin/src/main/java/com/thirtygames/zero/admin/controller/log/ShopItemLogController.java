package com.thirtygames.zero.admin.controller.log;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.thirtygames.zero.admin.controller.common.AdminGridController;
import com.thirtygames.zero.admin.controller.common.AdminMenu;
import com.thirtygames.zero.admin.controller.common.AdminMenu.Category;
import com.thirtygames.zero.admin.validator.ShopItemLogValidator;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.document.result.RowConverter;
import com.thirtygames.zero.common.model.columns.DisplayColumnId;
import com.thirtygames.zero.common.model.log.ShopItemLog;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.model.type.PriceType;
import com.thirtygames.zero.common.service.admintool.CommonCodeService;
import com.thirtygames.zero.common.service.admintool.log.AdmShopItemLogService;

@Slf4j
@Controller
@RequestMapping(value = "/log/shop-item")
public class ShopItemLogController extends AdminGridController<ShopItemLog, Integer, AdmShopItemLogService, ShopItemLogValidator> {
	
	@Autowired
	CommonCodeService commonCodeService;
	
	protected static final DisplayColumnId[] displayColumnIds = {
		DisplayColumnId.SHOP_ITEM_ACCOUNT_ID,
		DisplayColumnId.SHOP_ITEM_ITEM_KEY,
		DisplayColumnId.SHOP_ITEM_ITEM_ID,
		DisplayColumnId.SHOP_ITEM_ITEM_CATEGORY,
		DisplayColumnId.SHOP_ITEM_ITEM_TYPE,
		DisplayColumnId.SHOP_ITEM_ITEM_QUANTITY,
		DisplayColumnId.SHOP_ITEM_ITEM_QUANTITY_BONUS,
		DisplayColumnId.SHOP_ITEM_BUY_TYPE,
		DisplayColumnId.SHOP_ITEM_PRICE,
		DisplayColumnId.SHOP_ITEM_LOG_YMDT
	};
	
	private class ShopItemLogConverter implements RowConverter<Map<DisplayColumnId, Object>, ShopItemLog> {

		@Override
		public Map<DisplayColumnId, Object> convert(ShopItemLog shopItemLog) {
			Map<DisplayColumnId, Object> bindings = Maps.newHashMap();
			bindings.put(DisplayColumnId.SHOP_ITEM, shopItemLog);
			
			Map<DisplayColumnId, Object> row = Maps.newHashMap();
			for (DisplayColumnId id : displayColumnIds) {
				row.put(id, id.getObjectFor(bindings));
			}
			return row;
		}
		
	}
	
	public ShopItemLogController() {
		super("구매로그관리", "구매로그관리", displayColumnIds, AdminGridController.EMPTY_COLUMN_IDS);
		setRowConverter(new ShopItemLogConverter());
		pageInfo.setTitle("ShopItemLog");
		pageInfo.setPageSize(10);
	}
	
	@ModelAttribute
	public AdminMenu adminMenu() {
		adminMenu.setCategory(Category.Log);
		adminMenu.setSelectMenu("ShopItemLog");
		return adminMenu;
	}	
	
	protected ModelMap postGrid(ModelMap model) {
		super.setViewName("log/shopItemLogList");
		model.addAttribute("DataSources", DataSource.GAME_SHARDS);
		
		model.addAttribute("itemTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(ShopItem.ItemType.get$CODE_LOOKUP()));
		model.addAttribute("itemCategoryOpts", Joiner.on(";").withKeyValueSeparator(":").join(ShopItem.ItemCategory.get$CODE_LOOKUP()));
		model.addAttribute("buyTypeOpts", Joiner.on(";").withKeyValueSeparator(":").join(PriceType.get$CODE_LOOKUP()));
		
		return model;
	};	
}
