package com.thirtygames.zero.common.service.admindata;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.admindata.EventDropRateMapper;
import com.thirtygames.zero.common.model.admindata.EventDropRate;

@Service("eventDropRateService")
public class EventDropRateServiceImpl extends MetaServiceImpl<EventDropRateMapper, EventDropRate, Long> implements EventDropRateService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EventDropRate> localEventDropRateList(int localTimeZone) {
		List<EventDropRate> eventList = mapper.localEventDropRateList(localTimeZone);
		Iterator<EventDropRate> itEvent = eventList.iterator();
		while (itEvent.hasNext()) {
			EventDropRate event = itEvent.next();
			switch (event.getDayWeek()) {
			case 1:
				if (!event.getSunday()) itEvent.remove();
				break;
			case 2:
				if (!event.getMonday()) itEvent.remove();
				break;
			case 3:
				if (!event.getTuesday()) itEvent.remove();
				break;
			case 4:
				if (!event.getWednesday()) itEvent.remove();
				break;
			case 5:
				if (!event.getThursday()) itEvent.remove();
				break;
			case 6:
				if (!event.getFriday()) itEvent.remove();
				break;
			case 7:
				if (!event.getSaturday()) itEvent.remove();
				break;
			default:
				break;
			}
		}		
		return eventList;
	}

}
