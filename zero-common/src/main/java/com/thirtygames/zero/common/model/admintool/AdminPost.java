package com.thirtygames.zero.common.model.admintool;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AdminPost extends GridFilter {
	
	String postKey;
	Long postAdminKey;
	Integer postType;	// 0 : Admin, 1 :User
	String accountId;
	String lang;
	String text;
	
	String fromAccountId;
	String fromNickName;
	String fromProfileUrl; 
	Integer fromLeague;

	Integer rewardCategory;
	Integer rewardType;
	Long reward;
	Boolean rewardDone;
	
	String startYmdt;
	String expireYmdt;
	String modYmdt;
	String modId;
	
	public void setRewardType(int rewardType) {
		this.rewardType = rewardType;
		this.rewardCategory = RewardType.findByCode(rewardType).getCategory().getCode();
	}
}
