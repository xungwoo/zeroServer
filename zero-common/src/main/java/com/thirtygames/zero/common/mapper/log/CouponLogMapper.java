package com.thirtygames.zero.common.mapper.log;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.log.CouponLog;

public interface CouponLogMapper extends GenericMapper<CouponLog, Integer> {

	public boolean isUsedCoupon(@Param("accountId")String accountId, @Param("couponId")String couponId);

	public boolean isUsedBundleCode(@Param("accountId")String accountId, @Param("bundleCode")Integer bundleCode);


}
