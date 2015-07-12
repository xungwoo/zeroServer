package com.thirtygames.zero.common.service.admintool.version;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmMetaRevisionMapper;
import com.thirtygames.zero.common.model.meta.MetaRevision;

@Service("adminMetaRevisionService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmMetaRevisionServiceImpl extends AdminMetaServiceImpl<AdmMetaRevisionMapper, MetaRevision, String> implements AdmMetaRevisionService  {
}