package com.thirtygames.zero.common.service.admindata;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.Coupon;
import com.thirtygames.zero.common.model.meta.Reward;
;

public interface CouponService extends MetaService<Coupon, String> {

	public List<Reward> useCoupon(Coupon coupon) ;

}