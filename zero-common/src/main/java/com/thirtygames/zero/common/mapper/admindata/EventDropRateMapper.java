package com.thirtygames.zero.common.mapper.admindata;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admindata.EventDropRate;

public interface EventDropRateMapper extends GenericMapper<EventDropRate, Long> {

	List<EventDropRate> localEventDropRateList(int localTimeZone);
	
}
