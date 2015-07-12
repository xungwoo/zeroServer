package com.thirtygames.zero.common.service.admintool.version;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmServerUrlMapper;
import com.thirtygames.zero.common.model.meta.ServerInfo;

@Service("adminServerUrlService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmServerUrlServiceImpl extends AdminMetaServiceImpl<AdmServerUrlMapper, ServerInfo, String> implements AdmServerUrlService  {
}