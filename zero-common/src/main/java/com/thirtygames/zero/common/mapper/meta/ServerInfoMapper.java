package com.thirtygames.zero.common.mapper.meta;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.ServerInfo;

public interface ServerInfoMapper extends GenericMapper<ServerInfo, Integer> {
	
	public ServerInfo getByName(String name);

}
