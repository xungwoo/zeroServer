package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.ClientInfoMapper;
import com.thirtygames.zero.common.model.meta.ClientInfo;

@Service("clientInfoService")
public class ClientInfoServiceImpl extends MetaServiceImpl<ClientInfoMapper, ClientInfo, Integer> implements ClientInfoService {

}
