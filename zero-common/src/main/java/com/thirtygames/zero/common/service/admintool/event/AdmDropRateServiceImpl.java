package com.thirtygames.zero.common.service.admintool.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmDropRateMapper;
import com.thirtygames.zero.common.model.admintool.AdminEventDropRate;

@Service("adminDropRateService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmDropRateServiceImpl extends
		AdminMetaServiceImpl<AdmDropRateMapper, AdminEventDropRate, String> implements AdmDropRateService  {
}