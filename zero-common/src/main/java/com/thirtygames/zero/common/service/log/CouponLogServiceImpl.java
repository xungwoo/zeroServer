package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.CouponLogMapper;
import com.thirtygames.zero.common.model.log.CouponLog;

@Slf4j
@Service("couponLogService")
public class CouponLogServiceImpl extends LogServiceImpl<CouponLogMapper, CouponLog, Integer> implements CouponLogService {
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public boolean isUsedCoupon(String accountId, String couponId) {
		return mapper.isUsedCoupon(accountId, couponId);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_LOG)
	public boolean isUsedBundleCode(String accountId, Integer bundleCode) {
		return mapper.isUsedBundleCode(accountId, bundleCode);
	}

}