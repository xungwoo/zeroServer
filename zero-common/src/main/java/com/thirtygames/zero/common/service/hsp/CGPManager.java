package com.thirtygames.zero.common.service.hsp;

import gherkin.deps.com.google.gson.JsonArray;
import gherkin.deps.com.google.gson.JsonElement;
import gherkin.deps.com.google.gson.JsonObject;
import gherkin.deps.com.google.gson.JsonParser;
import gherkin.deps.com.google.gson.JsonPrimitive;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.network.URLConnector;
import com.thirtygames.zero.common.etc.network.URLConnector.HTTPResponse;
import com.thirtygames.zero.common.model.hsp.CGPResponse;
import com.thirtygames.zero.common.model.hsp.CGPReward;
import com.thirtygames.zero.common.model.meta.Reward;

@Slf4j
@Service("CGPManager")
public class CGPManager {
	
	private String CGP_URL = "https://api-campaign-analytics.cloud.toast.com/campaign/v1/server/check-mission";
	
	@RequiredArgsConstructor
	public enum CGPRewardMsg {
		Install("*install", new String[] { "히어로즈앤나이츠를 설치해 주셔서 감사합니다.\n우편함에 보상이 있습니다.", "Thanks for installing Heroes and kingts. \nGo claim your reward in the Mailbox!"}),
		Click("*click", new String[] { "프로모션에 참여해주셔서 감사합니다.\n우편함에 보상이 있습니다.\n(1일1회)", "Your daily reward has been sent to the Mailbox!"});
		
		@Getter
		private final String missionKey;
		
		@Getter
		private final String[] message;
		
		@Getter
		private static final java.util.Map<String, CGPRewardMsg> $CODE_LOOKUP = new java.util.HashMap<String, CGPRewardMsg>();
		static {
			for (CGPRewardMsg msg : CGPRewardMsg.values()) {
				$CODE_LOOKUP.put(msg.missionKey, msg);
			}
		}
		
		public static CGPRewardMsg findByMissionKey(final String missionKey) {
			if ($CODE_LOOKUP.containsKey(missionKey)) {
				return $CODE_LOOKUP.get(missionKey);
			}
			throw new java.lang.IllegalArgumentException(java.lang.String.format("Enumeration 'CGPRewardMsg' has no value for 'code = %s'", missionKey));
		}		
	}
	
	
	private final String PARAM_HEADER = "\"header\" : ";
	private final String PARAM_TRANSACTION_ID = "\"transactionId\" : ";
	private final String PARAM_USERID = "\"userId\" : ";
	private final String PARAM_APPID = "\"appId\" : ";
	private final String PARAM_MISSION_KEY = "\"missionKey\" : ";
	private final String PARAM_MISSION_VALUE = "\"missionValue\" : ";
	private final String COMMA = ", ";
	private final String JSON_OPEN = "{";
	private final String JSON_CLOSE = "}";
	
	private final String VALUE_APPID = "\"8OSFiaBr\"";
	//private final String VALUE_MISSION_KEY = "\"*install\"";
	private final String VALUE_MISSION_VALUE = "1";
	
	private final String RES_HEADER = "header";
	private final String RES_SUCCESS = "isSuccessful";	//"true"
	private final String RES_RESULT_CODE = "resultCode";
	private final String RES_RESULT_MESSAGE = "resultMessages";
	private final String RES_SERVICE_CODE = "serviceCode";	//196611
	private final String RES_TRANSACTION_ID = "transactionId";
	private final String RES_REWARD_LIST = "rewardList";
	private final String RES_REWARD_CODE = "rewardCode";
	private final String RES_REWARD_VALUE = "rewardValue";
	
	private final String VALUE_REWARD_CODE = "promotion_ruby";
	
	
	
	
	public CGPResponse checkMission(String memberNo, String missionKey)  {
	
		StringBuffer json = new StringBuffer();
		json.append(this.JSON_OPEN);
		json.append(this.PARAM_HEADER);
		json.append(this.JSON_OPEN);
		json.append(this.PARAM_TRANSACTION_ID);
		json.append(memberNo);
		
		json.append(this.JSON_CLOSE);
		json.append(this.COMMA);
		json.append(this.PARAM_USERID);
		json.append("\"" + memberNo + "\"");
		
		json.append(this.COMMA);
		json.append(this.PARAM_APPID);
		json.append(this.VALUE_APPID);
		json.append(this.COMMA);
		json.append(this.PARAM_MISSION_KEY);
		json.append("\"" + missionKey + "\"");
		json.append(this.COMMA);
		json.append(this.PARAM_MISSION_VALUE);
		json.append(this.VALUE_MISSION_VALUE);
		json.append(this.JSON_CLOSE);
		
		
		StringBuffer url = new StringBuffer(this.CGP_URL);
		log.debug("url:: " + url.toString());

		JsonObject resObj;
		
//		SystemInfo systemInfo = SystemInfo.getInstance(); 
//		RunMode.LOCAL.equals(systemInfo.getRunMode()) || RunMode.DEV.equals(systemInfo.getRunMode())
		
//		Random r = new Random();
//		if (r.nextFloat() > 0.5) {
//			String resS = "{ \"header\": { \"isSuccessful\": \"true\", \"resultCode\": 0, \"resultMessages\": [ \"Work Success\" ], \"serviceCode\": 196611, \"transactionId\": 92348729384729 }, \"rewardList\" : [ { \"campaignId\" : 7, \"promoDateBegin\" : \"2014-10-10 00:00:00\", \"promoDateEnd\" : \"2014-10-11 00:00:00\", \"rewardDateBegin\" : \"2014-10-10 00:00:00\", \"rewardDateEnd\" : \"2014-10-12 00:00:00\", \"rewardCode\" : \"promotion_ruby\", \"rewardValue\" : 500 } ] }";
//			resObj = new JsonParser().parse(resS).getAsJsonObject();
//		} else {
			try {
				HTTPResponse res = URLConnector.httpCallPostJson(url.toString(), json.toString());
				resObj = new JsonParser().parse(res.content).getAsJsonObject();
				log.debug("resObj::" + resObj);
			} catch (Exception e) {
				throw new RestException(Errors.URLConnectorError, "url::" + url);
			}
//		}
		
		return makeResponse(resObj);
	}	
	
	private CGPResponse makeResponse(JsonObject resObj) {
		log.debug("resObj:: " + resObj);
		JsonObject headerObj = resObj.getAsJsonObject(this.RES_HEADER);
		log.debug("headerObj:: " + headerObj);

		JsonPrimitive transactionIdObj = headerObj.getAsJsonPrimitive(this.RES_TRANSACTION_ID);
		log.debug("transactionIdObj:: " + transactionIdObj);
		
		JsonPrimitive resultCodeObj = headerObj.getAsJsonPrimitive(this.RES_RESULT_CODE);
		log.debug("resultCode:: " + resultCodeObj.getAsInt());
		
		JsonArray rewardListArray = resObj.getAsJsonArray(this.RES_REWARD_LIST);
		log.debug("rewardList:: " + rewardListArray);
		
		List<CGPReward> rewardList = Lists.newArrayList();
		for(JsonElement e : rewardListArray) {
			JsonObject obj = e.getAsJsonObject();
			CGPReward reward = new CGPReward();
			JsonPrimitive rewardCodeJson = obj.getAsJsonPrimitive(this.RES_REWARD_CODE);
			String rewardCode = rewardCodeJson.getAsString();
			reward.setRewardCode(rewardCode);
			
			if (this.VALUE_REWARD_CODE.equals(rewardCode)) {
				reward.setRewardType(Reward.RewardType.Cash.getCode());
			}
			
			JsonPrimitive rewardValue = obj.getAsJsonPrimitive(this.RES_REWARD_VALUE);
			reward.setRewardValue(rewardValue.getAsLong());
			
			rewardList.add(reward);
		}

		if (resultCodeObj.getAsInt() != 0) {
			throw new RestException(Errors.CGPCheckMissionError,  "resultCode:" + resultCodeObj.getAsInt());
		}
		
		CGPResponse result = new CGPResponse(); 
		result.setResultCode(resultCodeObj.getAsInt());
		result.setRewardList(rewardList);
		log.debug("result::" + result);
		return result;
	}	
	
}
