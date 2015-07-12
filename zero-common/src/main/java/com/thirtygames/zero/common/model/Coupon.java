package com.thirtygames.zero.common.model;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.annotation.JsonInclude;

@SuppressWarnings("serial")
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Coupon implements Serializable {
	
	String couponId;
	Integer couponType;
	
	Boolean allowDuplicate;
	Integer bundleCode;
	
	String accountId;
	
	Integer rewardType1;
	Long reward1;
	Integer rewardCategory2;
	Integer rewardType2;
	Long reward2;
	Integer rewardCategory3;
	Integer rewardType3;
	Long reward3;
	Integer rewardCategory4;
	Integer rewardType4;
	Long reward4;
	Integer rewardCategory5;
	Integer rewardType5;
	Long reward5;
	Boolean rewardDone;
	
	String expireYmd;
	Date genYmdt;
	Date useYmdt;
	
	Boolean isExpired;
	
	
	@RequiredArgsConstructor
	public enum CouponType {
		Admin(0), User(1);
		
		@Getter
		private final int code;
		
		@Getter
		private static final java.util.Map<java.lang.Integer, CouponType> $CODE_LOOKUP = new java.util.HashMap<java.lang.Integer, CouponType>();
		static {
			for (CouponType status : CouponType.values()) {
				$CODE_LOOKUP.put(status.code, status);
			}
		}
		
		public static CouponType findByCode(final int code) {
			if ($CODE_LOOKUP.containsKey(code)) {
				return $CODE_LOOKUP.get(code);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'CouponType' has no value for 'code = %s'", code));
		}
		
	}
}
