package com.thirtygames.zero.common.service.admintool.event;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmCouponMapper;
import com.thirtygames.zero.common.model.admintool.AdminCoupon;

@Service("adminCouponService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmCouponServiceImpl extends
		AdminMetaServiceImpl<AdmCouponMapper, AdminCoupon, String> implements AdmCouponService  {
}