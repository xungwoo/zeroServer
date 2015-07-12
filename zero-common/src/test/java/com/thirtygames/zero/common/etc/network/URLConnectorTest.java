package com.thirtygames.zero.common.etc.network;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;

import java.util.HashMap;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

import com.thirtygames.zero.common.etc.network.URLConnector.HTTPResponse;

@Slf4j
public class URLConnectorTest {


	@Ignore
	@Test
	public void httpCallTest() {
		Map<String, String> headersParam = new HashMap<String, String>();
		headersParam.put("myAccountId", "c29169ba-3841-4f49-ae66-988c2aa39fa2-002");
		headersParam.put("myAccessToken", "[Fantastic 30Games!]");
		headersParam.put("myClientVersion", "1.0.0.KG");
		headersParam.put("myClientPlatform", "android");
		HTTPResponse r = URLConnector.httpCall("http://dev.30games.co.kr:8080/zero-api/friends", headersParam, null);
		log.debug("r:::" + r.content);
		
	    JsonObject newObj = new JsonParser().parse(r.content).getAsJsonObject();
	    log.debug("newObj::" + newObj);
	    
	    JsonObject e = newObj.getAsJsonObject("params");
	    log.debug("e::" + e);
	    JsonArray result = newObj.getAsJsonArray("result");
	    log.debug("result::" + result);


	    
	    
//	    RESULT :
//	    {
//	                header: {
//	                                    transactionId: -2985124624929566000,
//	                                    status: 0
//	                },
//	                deliveryResponse: {
//	                deliveryTxId: 1234,
//	                memberNo: 4105000000137716,
//	                gameNo: 9001,
//	                requester: "ItemBLOC:mvxalpha07.smtgame.nhnsystem.com",
//	                eventNo: 0,
//	                deliveryStatus: "SENT",
//	                code: 0,
//	                currentTime: 1383638257995,
//	                itemDeliverySequences: [340],
//	                itemIds: ["McoinItem"],
//	                itemQuantities: [1],
//	                itemGiveReasonCodes: ["PAYMENT"],
//	                itemGiveReasonKeys: ["43803"],
//	                deliveryReceipt: "319749a0d9ae391522cb1bf3f4e6e70a",
//	                compactJsonResult: "{"tid":1234,"ids":["McoinItem"],"sqs":[340],"qts":[1],"rct":"319749a0d9ae391522cb1bf3f4e6e70a"}"
//	                }
//	    }	    
	    
	    
	    
	    //HSPItemDeliveryManager();
	    

//	    Request :
//	    deliveryHeader 
//	    deliveryTxId long M 배송transactionId : 아이템배송요청(RequestItemDelivery)시 사용한 deliveryTxId 를 사용해야 함.
//	    memberNo long M : HSP 유저 고유 번호 ( sno )
//	    gameNo int M : HSP Game No
//	    requester String O :요청하는 서버IP
//	    itemDeliverySequences long[] M : 아이템요청(RequestItemDelivery)시 전달 받은itemDeliverySequences
//	    deliveryReceipt String M : 아이템요청(RequestItemDelivery)시 전달 받은 deliveryReceipt
	    
//	    Result :
//	    {
//	                header: {
//	                                    transactionId: -5037579323076326000,
//	                                    status: 0
//	                },
//	                deliveryResponse: {
//	                deliveryTxId: 1234,
//	                memberNo: 4105000000137716,
//	                gameNo: 9001,
//	                requester: "ItemBLOC:mvxalpha07.smtgame.nhnsystem.com",
//	                eventNo: 0,
//	                deliveryStatus: "VERIFY",
//	                code: 0,
//	                currentTime: 1383640689410,
//	                itemDeliverySequences: [340],
//	                itemIds: ["McoinItem"],
//	                itemQuantities: [1],
//	                itemGiveReasonCodes: ["PAYMENT"],
//	                itemGiveReasonKeys: ["43803"],
//	                compactJsonResult: "{"tid":1234,"ids":["McoinItem"],"sqs":[340],"qts":[1],"rct":""}"
//	                }
//	    }
	    
	    //String verifyDeliveryReceiptUrl = HSPURL + api + AMPERSAND + DELIVERY_HEADER_KEY + MEMBER_NO;es=340&deliveryReceipt=319749a0d9ae391522cb1bf3f4e6e70a";
	    		 
		
	}


}
