package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class LoginReward implements Serializable {
	private static final long serialVersionUID = 807763444510893037L;

	public final static boolean INIT_REWARD_DONE = false;
	
	String accountId;
	Integer rewardType1;           
    Integer rewardType2;           
    Integer rewardType3;           
    Integer rewardType4;           
    Integer rewardType5;           
    Integer rewardType6;           
    Integer rewardType7;           
    Long reward1;                        
    Long reward2;                        
    Long reward3;                        
    Long reward4;                        
    Long reward5;                        
    Long reward6;                        
    Long reward7;                        
    Integer rewardCount;                 // 리워드 카운트
    Long lastRewardYmdt;             // 마지막 보상을 받은 시간
    Boolean doingEvent;
    
    @JsonIgnore Boolean doneTodayReward;
    
    // Params
    Integer dayOfWeek;
    Integer dayGap;
    
    
    @JsonIgnore Long todayReward;
    @JsonIgnore Integer todayRewardType;
    
    public void setRewardCount(int rewardCount) {
    	this.rewardCount = rewardCount;
    	switch(rewardCount) {
    	case 1:
    		this.todayRewardType = rewardType1;
    		this.todayReward = reward1;
    		break;
    	case 2:
    		this.todayRewardType = rewardType2;
    		this.todayReward = reward2;
    		break;
    	case 3:
    		this.todayRewardType = rewardType3;
    		this.todayReward = reward3;
    		break;
    	case 4:
    		this.todayRewardType = rewardType4;
    		this.todayReward = reward4;
    		break;
    	case 5:
    		this.todayRewardType = rewardType5;
    		this.todayReward = reward5;
    		break;
    	case 6:
    		this.todayRewardType = rewardType6;
    		this.todayReward = reward6;
    		break;
    	case 7:
    		this.todayRewardType = rewardType7;
    		this.todayReward = reward7;
    		break;
    	default:
    		this.todayRewardType = rewardType1;
    		this.todayReward = reward1;    		
    		break;
    	}
    }
    
    
}
