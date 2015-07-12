package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmCouponLogMapper;
import com.thirtygames.zero.common.model.log.CouponLog;

@Service("admCouponLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmCouponLogServiceImpl extends AdminLogServiceImpl<AdmCouponLogMapper, CouponLog, String> implements AdmCouponLogService {
	
}
