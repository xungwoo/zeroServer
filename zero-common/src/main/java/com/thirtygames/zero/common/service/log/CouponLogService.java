package com.thirtygames.zero.common.service.log;

import com.thirtygames.zero.common.generic.LogService;
import com.thirtygames.zero.common.model.log.CouponLog;

public interface CouponLogService extends LogService<CouponLog, Integer> {

	public boolean isUsedCoupon(String accountId, String couponId);

	public boolean isUsedBundleCode(String accountId, Integer bundleCode);

}