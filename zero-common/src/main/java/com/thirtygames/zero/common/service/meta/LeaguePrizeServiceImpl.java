package com.thirtygames.zero.common.service.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.LeaguePrizeMapper;
import com.thirtygames.zero.common.model.meta.LeaguePrize;

@Slf4j
@Service("leaguePrizeService")
public class LeaguePrizeServiceImpl extends MetaServiceImpl<LeaguePrizeMapper, LeaguePrize, Integer> implements LeaguePrizeService {
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public LeaguePrize getPrizeByRank(int league, long rank, float per) {
		return mapper.getPrizeByRank(league, rank, per);
	}


}