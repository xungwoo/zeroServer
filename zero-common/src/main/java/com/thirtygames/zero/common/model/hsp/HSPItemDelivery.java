package com.thirtygames.zero.common.model.hsp;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.google.common.base.Splitter;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;

@Data
@EqualsAndHashCode(callSuper=false)
public class HSPItemDelivery {

	String accountId;
	Long transactionId;
	Integer status;

	// deliveryResponse
	Long deliveryTxId; //??
	Integer code;
	Long memberNo;
	Integer gameNo;
	
	Integer eventNo;
	String deliveryStatus;
	String message;
	List<Long> itemDeliverySequences;
	List<String> itemIds;
	List<Integer> itemQuantities;
	List<String> itemGiveReasonCodes;
	List<String> itemGiveReasonKeys;
	String deliveryReceipt;
	String requester;
	Long requestTime;
	Long currentTime;
	
	String json;
	String lang;
	
	public void strSetItemDeliverySequences(String itemDeliverySequences) {
		List<String> tempList = Splitter.on(",").omitEmptyStrings().splitToList(itemDeliverySequences);
		this.itemDeliverySequences = new ArrayList<Long>();
		for(String temp : tempList) {
			this.itemDeliverySequences.add(Long.parseLong(temp));
		}
	}
	
	public String strGetItemDeliverySequences() {
		if (this.itemDeliverySequences == null || this.itemDeliverySequences.size() <= 0) {
			throw new RestException(Errors.HSPNoItemDeliverySequences);
		}
		
		String result = "";
		for(long itemDeliverySequence : this.itemDeliverySequences) {
			result += Long.toString(itemDeliverySequence) + ",";
		}
		
		return result.substring(0, result.length() - 1);
	}
	
	
	
}
