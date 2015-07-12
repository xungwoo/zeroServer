package com.thirtygames.zero.common.service.admintool.version;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmApiMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminApiMeta;

@Service("adminApiMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmApiMetaServiceImpl extends AdminMetaServiceImpl<AdmApiMetaMapper, AdminApiMeta, String> implements AdmApiMetaService  {
}