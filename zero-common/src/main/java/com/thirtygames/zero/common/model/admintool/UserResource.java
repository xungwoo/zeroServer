package com.thirtygames.zero.common.model.admintool;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@SuppressWarnings("serial")
@Slf4j
@Data
@EqualsAndHashCode(callSuper=false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class UserResource extends GridFilter implements Serializable {
	
	String accountId;
	String memberNo;
	String nickName;
	
	Long level;
	Long exp;
	Long gold;
	Long cash;
	Long unlockKey;
	Long titleBundle1;
	Long titleBundle2;
	Long titleBundle3;	

	Long equipLevelUpDrug;
	Long equipTicket;
	
	Long apLastValue;
	Long apMax;
	Long apLastYmdt;
	Long apExtra;
	
	Long bpLastValue;
	Long bpMax;
	Long bpLastYmdt;
	Long bpExtra;
	
	Long fp;
	
	String modYmdt;
	Date apLastDate;
	Date bpLastDate;
	
	//params
	@JsonIgnore Long ap;
	@JsonIgnore Long bp;
	@JsonIgnore Long calcAp;
	@JsonIgnore Long calcBp;
	@JsonIgnore Long currentTimeStamp;
	
	String memo;
	
	

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
