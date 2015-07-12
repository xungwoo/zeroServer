package com.thirtygames.zero.api.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Coupon;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.admindata.CouponService;
import com.thirtygames.zero.common.service.log.CouponLogService;


@Slf4j
@Controller
@RequestMapping(value = "/coupon")
public class CouponController {
	
	@Autowired
	CouponService service;
	
	@Autowired
	CouponLogService couponLogService;
	
	@Autowired
	AccountResourceService arService;
	
	
	@RequestMapping(value = "/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Coupon> confirm(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required = true, value = "couponId") String couponId)  {

		ApiJsonResult<Coupon> result = new ApiJsonResult<Coupon>();
		
		Coupon coupon = service.get(couponId);
		if (coupon == null) {
			throw new RestException(Errors.NotFoundCoupon, "couponId:" + couponId);
		}
		
		if (coupon.getIsExpired()) {
			throw new RestException(Errors.ExpiredCoupon, "couponId:" + couponId);
		}			
		
		if (coupon.getCouponType() == Coupon.CouponType.Admin.getCode()) {
			if(couponLogService.isUsedCoupon(myAccountId, couponId)) {
				throw new RestException(Errors.AlreadyUsedCoupon, "coupon" + couponId);
			}
		} else {
			if (coupon.getRewardDone()) {
				throw new RestException(Errors.AlreadyUsedCoupon, "couponId:" + couponId);
			}
			if (!coupon.getAllowDuplicate()) {
				if (couponLogService.isUsedBundleCode(myAccountId, coupon.getBundleCode())) {
					throw new RestException(Errors.AlreadyUsedCouponBundle, "coupon:" + coupon);
				}
			}			
		}
		
		coupon.setAccountId(myAccountId);
		result.setResult(service.useCoupon(coupon));
		result.setResource(arService.get(myAccountId));
		return result;
	}	
	
}