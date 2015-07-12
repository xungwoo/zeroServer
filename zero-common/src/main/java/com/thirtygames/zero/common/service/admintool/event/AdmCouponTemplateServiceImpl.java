package com.thirtygames.zero.common.service.admintool.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmCouponTemplateMapper;
import com.thirtygames.zero.common.model.admintool.CouponTemplate;

@Service("adminCouponTemplateService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmCouponTemplateServiceImpl extends
		AdminMetaServiceImpl<AdmCouponTemplateMapper, CouponTemplate, String> implements AdmCouponTemplateService  {
}