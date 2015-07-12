package com.thirtygames.zero.common.model.log;

import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class CouponLog extends GridFilter {
	
	String couponKey;
	String couponId;
	Integer couponType;
	Integer bundleCode;
	
	String accountId;
	
	Integer rewardType1;
	Long reward1;
	Integer rewardType2;
	Long reward2;
	Integer rewardType3;
	Long reward3;
	Integer rewardType4;
	Long reward4;
	Integer rewardType5;
	Long reward5;
	
	String expireYmd;
	
	Date genYmdt;
	Date useYmdt;
	
}
