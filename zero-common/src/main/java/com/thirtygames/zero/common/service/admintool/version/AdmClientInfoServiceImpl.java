package com.thirtygames.zero.common.service.admintool.version;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmClientInfoMapper;
import com.thirtygames.zero.common.model.meta.ClientInfo;

@Service("adminClientInfoService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmClientInfoServiceImpl extends
		AdminMetaServiceImpl<AdmClientInfoMapper, ClientInfo, String> implements AdmClientInfoService  {
}