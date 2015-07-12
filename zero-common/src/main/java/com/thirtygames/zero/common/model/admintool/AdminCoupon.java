package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminCoupon extends GridFilter {
	
	String couponKey;
	String couponId;
	Integer couponType;
	
	Boolean allowDuplicate;
	Integer bundleCode;
	
	String accountId;
	
	Integer rewardCategory1;
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
	String genYmdt;
	String useYmdt;
	
}
