package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.BoostItem;

public interface BoostItemService extends GenericService<BoostItem, String> {

	BoostItem getByBoostType(String accountId, int boostType);


}