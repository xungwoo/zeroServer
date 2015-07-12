package com.thirtygames.zero.common.service.hsp;

import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import gherkin.deps.com.google.gson.JsonPrimitive;

import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.common.base.Joiner;
import com.thirtygames.zero.common.etc.network.URLConnector;
import com.thirtygames.zero.common.etc.network.URLConnector.HTTPResponse;
import com.thirtygames.zero.common.etc.system.SystemInfo;
import com.thirtygames.zero.common.etc.system.SystemInfo.RunMode;
import com.thirtygames.zero.common.model.hsp.HSPPushResponse;

@Slf4j
@Service("HSPPushManager")
public class HSPPushManager {
	
	@Value("#{config['hsp.item_delivery_url']}")
	private String HSP_URL;
	
	// http://alpha-httpgw.hangame.com:18080/hsp/httpgw/nomad.json?api=SendPushMsg&version=1.3&memberNo=88899626759572235&gameNo=10341&headerGameNo=10341&message=TEST
	private String SEND_PUSH_API = "SendPushMsg";
	private String PARAM_GAME_NO = "&gameNo=";
	private String PARAM_HEADER_GAME_NO = "&headerGameNo=";
	private String PARAM_MEMBER_NO = "&memberNo=";
	private String PARAM_MESSAGE = "&message=";
	private String VALUE_GAME_NO = "10341";
	
	
	// http://alpha-httpgw.hangame.com:18080/hsp/httpgw/nomad.json?api=SendGroupPushMsg&version=1.3&memberNoList=88899626759572235,88899626759572236&gameNo=10341&headerGameNo=10341&pushMessage=TEST
	private String SEND_GROUP_PUSH_API = "SendGroupPushMsg";
	private String PARAM_GROUP_MEMBER_NO = "&memberNoList=";
	private String PARAM_GROUP_MESSAGE = "&pushMessage=";
	
	
	private final String RES_HEADER_KEY = "header";
	private final String RES_TRANSACTION_ID_KEY = "transactionId";
	private final String RES_STATUS_KEY = "status";
	
	public HSPPushResponse sendPush(String memberNo, String msg)  {
		
		log.debug("HSP_URL:: " + this.HSP_URL);
		StringBuffer url = new StringBuffer(this.HSP_URL);
		url.append(this.SEND_PUSH_API);
		url.append(this.PARAM_HEADER_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_MEMBER_NO);
		url.append(memberNo);
		url.append(this.PARAM_MESSAGE);
		url.append(msg);
		
		log.debug("url:: " + url.toString());

		JsonObject resObj = null;
		
		SystemInfo systemInfo = SystemInfo.getInstance(); 
		if (RunMode.LOCAL.equals(systemInfo.getRunMode()) || RunMode.DEV.equals(systemInfo.getRunMode())) {
			String resS = "{\"header\":{\"transactionId\":8203772437342285706,\"status\":0}}";
			resObj = new JsonParser().parse(resS).getAsJsonObject();
		} else {
			try {
				HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
				resObj = new JsonParser().parse(res.content).getAsJsonObject();
			} catch (Exception e) {
				log.error("HSPPush sendPush URLConnector Fail.");
			}
		}
		
		
		return makeResponse(resObj);
	}	
	
	public HSPPushResponse sendGroupPush(List<Long> memberNoList, String msg)  {
		log.debug("HSP_URL:: " + this.HSP_URL);
		StringBuffer url = new StringBuffer(this.HSP_URL);
		url.append(this.SEND_GROUP_PUSH_API);
		url.append(this.PARAM_HEADER_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_GROUP_MEMBER_NO);
		
		Iterator<Long> itMember = memberNoList.iterator();
		String members = Joiner.on(",").join(itMember);
		url.append(members);
		url.append(this.PARAM_GROUP_MESSAGE);
		url.append(msg);
		
		log.debug("url:: " + url.toString());
		
		JsonObject resObj = null;
		
		SystemInfo systemInfo = SystemInfo.getInstance(); 
		if (RunMode.LOCAL.equals(systemInfo.getRunMode()) || RunMode.DEV.equals(systemInfo.getRunMode())) {
			String resS = "{\"header\":{\"transactionId\":8203772437342285706,\"status\":0}}";
			resObj = new JsonParser().parse(resS).getAsJsonObject();
		} else {
			try {
				HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
				resObj = new JsonParser().parse(res.content).getAsJsonObject();
			} catch (Exception e) {
				log.error("HSPPush sendGroupPush URLConnector Fail.");
			}
		}
		
		return makeResponse(resObj);
	}

	public HSPPushResponse sendAllPush(String msg) {
		log.debug("HSP_URL:: " + this.HSP_URL);
		StringBuffer url = new StringBuffer(this.HSP_URL);
		url.append(this.SEND_PUSH_API);
		url.append(this.PARAM_HEADER_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_GAME_NO);
		url.append(this.VALUE_GAME_NO);
		url.append(this.PARAM_MEMBER_NO);
		url.append(this.PARAM_MESSAGE);
		url.append(msg);
		
		log.debug("url:: " + url.toString());

		JsonObject resObj = null;
		
		SystemInfo systemInfo = SystemInfo.getInstance(); 
		if (RunMode.LOCAL.equals(systemInfo.getRunMode()) || RunMode.DEV.equals(systemInfo.getRunMode())) {
			String resS = "{\"header\":{\"transactionId\":8203772437342285706,\"status\":0}}";
			resObj = new JsonParser().parse(resS).getAsJsonObject();
		} else {
			try {
				HTTPResponse res = URLConnector.httpCall(url.toString(), null, null);
				resObj = new JsonParser().parse(res.content).getAsJsonObject();
			} catch (Exception e) {
				log.error("HSPPush sendAllPush URLConnector Fail.");
			}
		}
		
		HSPPushResponse res = makeResponse(resObj);
		return res;
	}

	private HSPPushResponse makeResponse(JsonObject resObj) {
		log.debug("resObj:: " + resObj);
		
		JsonObject headerObj = resObj.getAsJsonObject(this.RES_HEADER_KEY);
		log.debug("headerObj:: " + headerObj);
		
		JsonPrimitive transactionIdObj = headerObj.getAsJsonPrimitive(this.RES_TRANSACTION_ID_KEY);
		log.debug("transactionIdObj:: " + transactionIdObj);
		
		JsonPrimitive statusObj = headerObj.getAsJsonPrimitive(this.RES_STATUS_KEY);
		log.debug("status:: " + statusObj.getAsInt());
		
		HSPPushResponse result = new HSPPushResponse(); 
		result.setTransactionId(transactionIdObj.getAsLong());
		result.setStatus(statusObj.getAsInt());
		return result;
	}	
	
}
