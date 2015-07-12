package com.thirtygames.zero.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.EquipmentMapper;
import com.thirtygames.zero.common.mapper.ShopItemMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.BoostItem;
import com.thirtygames.zero.common.model.BoostItem.BoostType;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.log.ShopItemLog;
import com.thirtygames.zero.common.model.meta.ApiMeta;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.service.equipment.EquipmentService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.log.ShopItemLogService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;

@Service("shopItemService")
public class ShopItemServiceImpl extends MetaServiceImpl<ShopItemMapper, ShopItem, Integer> implements ShopItemService {

	@Autowired
	EquipmentMapper equipmentMapper;

	@Autowired
	AccountResourceService arService;

	@Autowired
	EquipMetaService eqMetaService;

	@Autowired
	EquipmentService eqService;
	
	@Autowired
	ApiMetaService apiMetaService;	
	
	@Autowired
	BoostItemService boostItemService;
	
	@Autowired
	ShopItemLogService shopItemLogService;
	
	@Autowired
	RewardService rewardService;

	@Override
	@Transactional
	public AccountResource buyResource(ShopItem itemMeta) {
		String accountId = itemMeta.getAccountId();

		AccountResource priceR = new AccountResource();
		priceR.setAccountId(accountId);
		priceR.setPrice(itemMeta.getBuyType(), itemMeta.getPrice());
		arService.updateSubtraction(priceR, false);

		AccountResource productR = new AccountResource();
		productR.setAccountId(accountId);
		productR.setResource(itemMeta.getItemType(), itemMeta.getItemQuantity());
		AccountResource resultR = arService.updateAddition(productR, true);
		
		this.saveLog(itemMeta);
		return resultR;
	}
	
	@Override
	@Transactional
	public void buyEquips(ShopItem itemMeta, List<Equipment> eqList) {
		for (Equipment eq : eqList) {
			eqService.save(eq);
		}
		
		AccountResource priceR = new AccountResource();
		priceR.setAccountId(itemMeta.getAccountId());
		priceR.setPrice(itemMeta.getBuyType(), itemMeta.getPrice());
		arService.updateSubtraction(priceR, false);
		this.saveLog(itemMeta);
	}

	@Override
	@Transactional
	public void buyGems(ShopItem itemMeta, List<Equipment> gemList) {
		for (Equipment gem : gemList) {
			eqService.save(gem);
		}

		AccountResource priceR = new AccountResource();
		priceR.setAccountId(itemMeta.getAccountId());
		priceR.setPrice(itemMeta.getBuyType(), itemMeta.getPrice());
		arService.updateSubtraction(priceR, false);
		this.saveLog(itemMeta);
	}

	@Override
	@Transactional
	public void buyItemBoost(ShopItem itemMeta) {
		ApiMeta meta = apiMetaService.getByCache(BoostItem.ITEM_DROP_BOOST, 1);
		BoostItem boostItem = new BoostItem();
		boostItem.setAccountId(itemMeta.getAccountId());
		boostItem.setBoostType(BoostType.Item.getCode());
		boostItem.setDurationTime(meta.getLongValue().intValue());
		
		BoostItem currentItem = boostItemService.getByBoostType(itemMeta.getAccountId(), BoostType.Item.getCode());
		if (currentItem == null) {
			boostItemService.save(boostItem);
		} else {
			boostItem.setDuplicationCount(currentItem.getDuplicationCount() + 1);
			boostItemService.update(boostItem);
		}
		
		AccountResource priceR = new AccountResource();
		priceR.setAccountId(itemMeta.getAccountId());
		priceR.setPrice(itemMeta.getBuyType(), itemMeta.getPrice());
		arService.updateSubtraction(priceR, false);		
		this.saveLog(itemMeta);
	}

	@Override
	@Transactional
	public void buyGoldBoost(ShopItem itemMeta) {
		ApiMeta meta = apiMetaService.getByCache(BoostItem.GOLD_DROP_BOOST, 1);
		BoostItem boostItem = new BoostItem();
		boostItem.setAccountId(itemMeta.getAccountId());
		boostItem.setBoostType(BoostType.Gold.getCode());
		boostItem.setDurationTime(meta.getLongValue().intValue());
		
		BoostItem currentItem = boostItemService.getByBoostType(itemMeta.getAccountId(), BoostType.Gold.getCode());
		if (currentItem == null) {
			boostItemService.save(boostItem);
		} else {
			boostItem.setDuplicationCount(currentItem.getDuplicationCount() + 1);
			boostItemService.update(boostItem);
		}
		
		AccountResource priceR = new AccountResource();
		priceR.setAccountId(itemMeta.getAccountId());
		priceR.setPrice(itemMeta.getBuyType(), itemMeta.getPrice());
		arService.updateSubtraction(priceR, false);		
		this.saveLog(itemMeta);
	}

	private void saveLog(ShopItem itemMeta) {
		ShopItemLog log = new ShopItemLog();
		log.setItemKey(itemMeta.getItemKey());
		log.setAccountId(itemMeta.getAccountId());
		log.setBuyType(itemMeta.getBuyType());
		log.setItemType(itemMeta.getItemType());
		log.setItemCategory(itemMeta.getItemCategory());
		log.setItemId(itemMeta.getItemId());
		log.setItemQuantity(itemMeta.getItemQuantity());
		log.setItemQuantityBonus(itemMeta.getItemQuantityBonus());
		log.setPrice(itemMeta.getPrice());
		log.setProductId(itemMeta.getProductId());
		shopItemLogService.save(log);
	}	
}
