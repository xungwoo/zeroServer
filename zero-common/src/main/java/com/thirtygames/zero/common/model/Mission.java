package com.thirtygames.zero.common.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.model.params.grid.GridFilter;


@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class Mission extends GridFilter {
	
	public final static boolean INIT_REWARD_DONE = false;
	
	String missionKey;
	String accountId;
	Integer missionId;            // 메인 키
	Integer missionType; 
	String iconType;                   // 이벤트 타입 (아이콘 대응)
    Long startYmdt;               // 이벤트 시작일시
    Long dueDateYmdt;               // 마지막 이벤트 기간
    String title;
    String description;
    Integer param1;
    Integer param2;
    Integer param3;
    Integer goal;                       // 목표 량
    Integer current;                    // 현재량
    Integer rewardType;          // 보상 타입 
    Long reward;                     // 보상액   
    Boolean rewardDone;                 // 리워드 완료 여부
    Boolean isDaily;
    
    String lang;
    Boolean isExpired;
	@JsonIgnore AccountResource resource = new AccountResource();

	// TODO equipTicket 등등 추가필요
	public AccountResource getResource() {
		if (this.rewardType != null) {
			resource = new AccountResource();
			if (this.accountId != null) this.resource.setAccountId(this.accountId);
			switch (this.rewardType) {
			case (1):
				this.resource.setGold(this.reward);
				break;
			case (2):
				this.resource.setCash(this.reward);
				break;
			case (3):
				this.resource.setApExtra(this.reward);
				break;
			case (4):
				this.resource.setBpExtra(this.reward);
				break;
			default:
				break;
			}
		}

		return this.resource;
	}
	
	public ShopItem clone() throws CloneNotSupportedException {
		ShopItem shopItem = (ShopItem) super.clone();
		return shopItem;
	}		
}
