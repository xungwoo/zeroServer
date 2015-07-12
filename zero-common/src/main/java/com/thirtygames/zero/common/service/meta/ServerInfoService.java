package com.thirtygames.zero.common.service.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.ServerInfo;

public interface ServerInfoService extends MetaService<ServerInfo, Integer> {
	
	public ServerInfo getByName(String name);
}
