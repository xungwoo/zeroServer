package com.thirtygames.zero.common.service.meta;

import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.LeagueCountMapper;
import com.thirtygames.zero.common.model.meta.LeagueCount;

@Slf4j
@Service("leagueCountService")
public class LeagueCountServiceImpl extends MetaServiceImpl<LeagueCountMapper, LeagueCount, Integer> implements LeagueCountService {

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public LeagueCount getPrevLeagueCount(int league, String resetDate)  {
		return mapper.getPrevLeagueCount(league, resetDate);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public void increase(int league, String resetDate)  {
		LeagueCount param = new LeagueCount();
		param.setLeague(league);
		param.setResetDate(resetDate);
		mapper.save(param);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<String, String> getMaxResetDate() {
		return mapper.getMaxResetDate();
	}
	
}