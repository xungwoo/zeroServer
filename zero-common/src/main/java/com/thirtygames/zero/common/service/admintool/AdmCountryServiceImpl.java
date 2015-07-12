package com.thirtygames.zero.common.service.admintool;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmCountryMapper;
import com.thirtygames.zero.common.model.admintool.AdminCountry;

@Service("adminCountryService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmCountryServiceImpl extends AdminMetaServiceImpl<AdmCountryMapper, AdminCountry, String> implements AdmCountryService  {
	
}