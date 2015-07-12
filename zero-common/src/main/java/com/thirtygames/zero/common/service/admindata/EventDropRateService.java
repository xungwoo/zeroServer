package com.thirtygames.zero.common.service.admindata;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.admindata.EventDropRate;

public interface EventDropRateService extends MetaService<EventDropRate, Long> {

	List<EventDropRate> localEventDropRateList(int localTimeZone);

}
