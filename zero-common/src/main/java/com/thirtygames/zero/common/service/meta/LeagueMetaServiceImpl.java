package com.thirtygames.zero.common.service.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.LeagueMetaMapper;
import com.thirtygames.zero.common.model.meta.LeagueMeta;

@Slf4j
@Service("leagueMetaService")
public class LeagueMetaServiceImpl extends MetaServiceImpl<LeagueMetaMapper, LeagueMeta, Integer> implements LeagueMetaService {
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Boolean isLadderAI(String countryCode) {
		return mapper.isLadderAI(countryCode);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Integer getHighLeague(int league) {
		return mapper.getHighLeague(league);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Integer getLowLeague(int league) {
		return mapper.getLowLeague(league);
	}
	
}