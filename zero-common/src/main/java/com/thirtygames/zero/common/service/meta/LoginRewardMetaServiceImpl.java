package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.LoginRewardMetaMapper;
import com.thirtygames.zero.common.model.LoginReward;

@Service("loginRewardMetaService")
public class LoginRewardMetaServiceImpl extends MetaServiceImpl<LoginRewardMetaMapper, LoginReward, Integer> implements LoginRewardMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public LoginReward getLoginReward() {
		return mapper.getLoginReward();
	}

}
