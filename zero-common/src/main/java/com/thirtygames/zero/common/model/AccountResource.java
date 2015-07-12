package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.meta.ShopItem.ItemType;
import com.thirtygames.zero.common.model.type.PriceType;

@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class AccountResource implements Serializable {
	private static final long serialVersionUID = 2850797522028512410L;
	
	public static final long INIT_LEVEL = 1;
	public static final long INIT_EXP = 0;
	public static final long INIT_AP_LAST_VALUE = 10;
	public static final long INIT_AP_MAX = 10;
	public static final long INIT_AP_EXTRA = 0;
	public static final long INIT_BP_LAST_VALUE = 10;
	public static final long INIT_BP_MAX = 10;
	public static final long INIT_BP_EXTRA = 0;
	public static final long INIT_GOLD = 5000;
	public static final long INIT_CASH = 10;
	public static final long INIT_UNLOCK_KEY = 40;
	public static final long INIT_FP = 0;
	public static final long INIT_LEVELUP_DRUG = 3;
	public static final long INIT_EQUIPMENT_TICKET = 0;

	
	public final static int LIMIT_TITLE_BUNDLE = 60;
	
	// TODO DB Save...
	public static Long apRechargeTime = (long) (60 * 5);
	public static Long bpRechargeTime = (long) (60 * 5);

	String accountId;
	Long gold;
	Long cash;
	Long unlockKey;
	
	Long equipLevelUpDrug;
	Long equipTicket;
	
	//Long apLastValue;
	Long apMax;
	Long apLastYmdt;
	Long apExtra;
	
	//Long bpLastValue;
	Long bpMax;
	Long bpLastYmdt;
	Long bpExtra;
	
	Long fp;
	
	Long titleBundle1;
	Long titleBundle2;
	Long titleBundle3;	

	@JsonIgnore Long currentTimeStamp;
	
	// param : 소모point
	@JsonIgnore Long ap;
	@JsonIgnore Long bp;
	
	
	// subtraction only
	@JsonIgnore Long price;
	public void setPrice(int resourceTypeCode, long price) {
		PriceType rType = PriceType.findByCode(resourceTypeCode);
		switch (rType) {
		case Billing:
			break;
		case Cash:
			this.cash = price;
			break;
		case Gold:
			this.gold = price;
			break;
		case FP:
			this.fp = price;
			break;
		case UnlockKey:
			this.unlockKey = price;
			break;
		case EquipTicket:
			this.equipTicket = price;
			break;
		}
	}
	

	@JsonIgnore Long resource;	
	public void setResource(int itemTypeCode, long quantity) {
		ItemType itemType = ItemType.findByCode(itemTypeCode);
		switch (itemType) {
		case AP:
			this.apExtra = quantity;
			break;
		case BP:
			this.bpExtra = quantity;
			break;
		case Gold:
			this.gold = quantity;
			break;
		case Cash:
			this.cash = quantity;
			break;
		case UnlockKey:
			this.unlockKey = quantity;
			break;
		case EquipLevelUpDrug:
			this.equipLevelUpDrug = quantity;
			break;
		default:
			break;
		}		
	}

	@JsonIgnore Long title;
	public void setTitle(long title) {
		int i = (int) (title / AccountResource.LIMIT_TITLE_BUNDLE);
		title = (title % AccountResource.LIMIT_TITLE_BUNDLE) - 1;
		long pow = (long) Math.pow(2, title);
		
		switch(i + 1) {
		case 1:
			this.titleBundle1 = pow;
			break;
		case 2:
			this.titleBundle2 = pow;
			break;
		case 3:
			this.titleBundle3 = pow;
			break;
		default:
			break;
		}
	}

	public boolean checkTitle(int title) {
		if (title > (AccountResource.LIMIT_TITLE_BUNDLE * 3)) {
			return false;
		}
		
		boolean hasTitle = false;
		int i = (int) (title / AccountResource.LIMIT_TITLE_BUNDLE);
		title = (title % AccountResource.LIMIT_TITLE_BUNDLE) - 1;
		long pow = (long) Math.pow(2, title);
		log.debug("pow::" + pow);

		
		switch(i + 1) {
		case 1:
			hasTitle = (this.titleBundle1 == (this.titleBundle1 | pow)) ;
			break;
		case 2:
			hasTitle = (this.titleBundle2 == (this.titleBundle2 | pow)) ;
			break;
		case 3:
			hasTitle = (this.titleBundle3 == (this.titleBundle3 | pow)) ;
			break;
		default:
			break;
		}
		
		return hasTitle;
	}		


}
