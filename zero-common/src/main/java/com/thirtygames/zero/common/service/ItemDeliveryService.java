package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.hsp.HSPItemDelivery;
import com.thirtygames.zero.common.model.hsp.HSPItemDeliveryLog;

public interface ItemDeliveryService extends GenericService<HSPItemDeliveryLog, Long> {

	public boolean itemDelivery(HSPItemDelivery hspItemDelivery) ;
}
