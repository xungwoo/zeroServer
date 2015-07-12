package com.thirtygames.zero.common.service.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.FirstBuyingRewardMapper;
import com.thirtygames.zero.common.model.meta.Reward;

@Service("firstBuyingRewardService")

public class FirstBuyingRewardServiceImpl extends MetaServiceImpl<FirstBuyingRewardMapper, Reward, Integer> implements FirstBuyingRewardService {
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public List<Reward> rewardList(int itemKey) {
		return mapper.rewardList(itemKey);
	}

}
