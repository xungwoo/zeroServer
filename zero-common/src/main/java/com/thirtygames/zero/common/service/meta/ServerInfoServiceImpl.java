package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.ServerInfoMapper;
import com.thirtygames.zero.common.model.meta.ServerInfo;

@Service("serverInfoService")
	
public class ServerInfoServiceImpl extends MetaServiceImpl<ServerInfoMapper, ServerInfo, Integer> implements ServerInfoService {
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public ServerInfo getByName(String name) {
		return mapper.getByName(name);
	}

}
