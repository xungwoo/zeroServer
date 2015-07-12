package com.thirtygames.zero.common.service.hsp;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import gherkin.deps.com.google.gson.JsonPrimitive;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.network.URLConnector;
import com.thirtygames.zero.common.etc.network.URLConnector.HTTPResponse;
import com.thirtygames.zero.common.etc.system.SystemInfo;
import com.thirtygames.zero.common.etc.system.SystemInfo.RunMode;
import com.thirtygames.zero.common.model.hsp.HSPItemDelivery;

@Slf4j
@Service("hspItemDeliveryManager")
public class HSPItemDeliveryManager {
	
	@Value("#{config['hsp.item_delivery_url']}")
	private String HSP_URL;

	// Request Header & Header Keys
	private final String DELIVERY_HEADER_KEY = "deliveryHeader";
	private final String DELIVERY_RECEIPT_KEY = "deliveryReceipt";
	private final String ITEM_DELIVERY_SEQUENCES_KEY = "itemDeliverySequences";	    
	private final String MEMBER_NO_HEADER = "memberNo-";
	private final String GAME_NO_HEADER = "gameNo-";
	private final String DELIVERY_TX_ID_HEADER = "deliveryTxId-";
	
	// ResultJson keys
	private final String DELIVERY_RESPONSE_KEY = "deliveryResponse";
	private final String HEADER_KEY = "header";

	// seperator
	private final String AMPERSAND = "&";
	private final String EQUAL = "=";
	private final String COMMA = ",";
	
	public HSPItemDelivery verifyDeliveryReceipt(HSPItemDelivery itemDelivery)  {
		HSPItemDelivery result = new HSPItemDelivery();
		
		log.debug("HSP_URL:: " + HSP_URL);
		log.debug("itemDelivery:: " + itemDelivery);
		String api = "VerifyDeliveryReceipt";
		StringBuffer url = new StringBuffer(this.HSP_URL);
		url.append(api);
		url.append(makeDeliveryHeader(itemDelivery));
		log.debug("url:: " + url.toString());

		JsonObject resObj;
		
		SystemInfo systemInfo = SystemInfo.getInstance(); 
		if (RunMode.LOCAL.equals(systemInfo.getRunMode()) || RunMode.DEV.equals(systemInfo.getRunMode())) {
			//String resS = "{\"header\":{\"transactionId\":-776743856769273733,\"status\":0},\"deliveryResponse\":{\"deliveryTxId\":-1062320417895069382,\"memberNo\":88899626759671544,\"gameNo\":10341,\"requester\":\"ItemBLOC:test-mvxalpha07.ncl.nhnsystem.com\",\"eventNo\":0,\"deliveryStatus\":\"VERIFY\",\"code\":0,\"currentTime\":1412772258753,\"itemDeliverySequences\":[20370,20371,20372,20375,20376,20377,20378,20379,20385,20536,20537,20539,20540,20541,20581,20596,20622,20633,20634,20652,20751],\"itemIds\":[\"ad_300\",\"ad_350\",\"ad_600\",\"ad_1300\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\",\"ad_120\"],\"itemQuantities\":[1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1],\"itemGiveReasonCodes\":[\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\",\"PAYMENT\"],\"itemGiveReasonKeys\":[\"2000030990\",\"2000030991\",\"2000030994\",\"2000031027\",\"2000031028\",\"2000031029\",\"2000031030\",\"2000031031\",\"2000031039\",\"2000031382\",\"2000031383\",\"2000031387\",\"2000031388\",\"2000031390\",\"2000031472\",\"2000031500\",\"2000031546\",\"2000031572\",\"2000031573\",\"2000031594\",\"2000031751\"]}}";
			String resS = "{\"header\":{\"transactionId\":-776743856769273733,\"status\":0},\"deliveryResponse\":{\"deliveryTxId\":-1062320417895069382,\"memberNo\":88899626759671544,\"gameNo\":10341,\"requester\":\"ItemBLOC:test-mvxalpha07.ncl.nhnsystem.com\",\"eventNo\":0,\"deliveryStatus\":\"VERIFY\",\"code\":0,\"currentTime\":1412772258753,\"itemDeliverySequences\":[20370,20371],\"itemIds\":[\"ad_600\",\"ad_1300\"],\"itemQuantities\":[1,1],\"itemGiveReasonCodes\":[\"PAYMENT\",\"PAYMENT\"],\"itemGiveReasonKeys\":[\"2000030990\",\"2000030991\"]}}";
			resObj = new JsonParser().parse(resS).getAsJsonObject();
		} else {
			try {
				HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
				resObj = new JsonParser().parse(res.content).getAsJsonObject();
			} catch (Exception e) {
				throw new RestException(Errors.HSPVerifyDeliveryError, "url::" + url);
			}
		}
		
		log.debug("resObj:: " + resObj);
		JsonObject headerObj = resObj.getAsJsonObject(this.HEADER_KEY);
		log.debug("headerObj:: " + headerObj);
		JsonObject deliveryResponseObj = resObj.getAsJsonObject(this.DELIVERY_RESPONSE_KEY);
		log.debug("deliveryResponseObj:: " + deliveryResponseObj);
		
		JsonPrimitive code = deliveryResponseObj.getAsJsonPrimitive("code");
		result.setCode(code.getAsInt());
		log.debug("code:: " + code);
		if (code.getAsInt() != 0) {
			throw new RestException("HSP.itemDelivery.code :" + code);
		}
		
		List<Long> seqList = new ArrayList<Long>();
		JsonArray sequences = deliveryResponseObj.getAsJsonArray(this.ITEM_DELIVERY_SEQUENCES_KEY);
		for(JsonElement seq :  sequences) {
			seqList.add(seq.getAsLong());
			log.debug("seq.getAsInt()::" + seq.getAsInt());
		}
		
		List<String> idList = new ArrayList<String>();
		JsonArray ids = deliveryResponseObj.getAsJsonArray("itemIds");
		for(JsonElement id :  ids) {
			idList.add(id.getAsString());
			log.debug("id.getAsString()::" + id.getAsString());
		}
		
		result.setItemDeliverySequences(seqList);
		result.setItemIds(idList);
		result.setJson(deliveryResponseObj.toString());
		return result;
	}
	
	
	// Not use. only Test.
	public JsonObject finishItemDelivery(HSPItemDelivery itemDelivery) {
		String api = "FinishItemDelivery";
		StringBuffer url = new StringBuffer(this.HSP_URL);
		url.append(api);
		url.append(this.makeDeliveryHeader(itemDelivery));
		HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
		
		JsonObject resObj = new JsonParser().parse(res.content).getAsJsonObject();
		return resObj;
	}
	
	
	// Not use. only Test.
	public JsonObject requestItemDelivery(HSPItemDelivery itemDelivery) {
	    String api = "RequestItemDelivery";
	    StringBuffer url = new StringBuffer(this.HSP_URL);
	    url.append(api);
	    url.append(makeDeliveryHeader(itemDelivery));
	    HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
	    
	    JsonObject resObj = new JsonParser().parse(res.content).getAsJsonObject();
	    return resObj;
	}	


	private String makeDeliveryHeader(HSPItemDelivery itemDelivery) {
		log.debug("makeDeliveryHeader statrt::");
		StringBuffer url = new StringBuffer();
		url.append(this.AMPERSAND);
	    url.append(this.DELIVERY_HEADER_KEY);
	    url.append(this.EQUAL);
	    if (itemDelivery.getMemberNo() != null) {
		    url.append(this.MEMBER_NO_HEADER);
		    url.append(itemDelivery.getMemberNo());
		    url.append(this.COMMA);
	    }
	    if (itemDelivery.getGameNo() != null) {
		    url.append(this.GAME_NO_HEADER);
		    url.append(itemDelivery.getGameNo());
		    url.append(this.COMMA);
	    }
	    if (itemDelivery.getDeliveryTxId() != null) {
		    url.append(this.DELIVERY_TX_ID_HEADER);
		    url.append(itemDelivery.getDeliveryTxId());
	    }
	    
	    if (itemDelivery.getItemDeliverySequences() != null) {
	    	url.append(this.AMPERSAND);
	    	url.append(this.ITEM_DELIVERY_SEQUENCES_KEY);
	    	url.append(this.EQUAL);
	    	url.append(itemDelivery.strGetItemDeliverySequences());
	    }
	    if (itemDelivery.getDeliveryReceipt() != null) {
	    	url.append(this.AMPERSAND);
	    	url.append(this.DELIVERY_RECEIPT_KEY);
	    	url.append(this.EQUAL);
	    	url.append(itemDelivery.getDeliveryReceipt());
	    }
	    log.debug("makeDeliveryHeader url.toString()::" + url.toString());
	    return url.toString();
	}	
	
}
