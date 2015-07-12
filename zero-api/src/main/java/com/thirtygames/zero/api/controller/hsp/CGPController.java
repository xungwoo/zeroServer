package com.thirtygames.zero.api.controller.hsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.PromotionService;
import com.thirtygames.zero.common.service.hsp.CGPManager.CGPRewardMsg;

/**
 *
 * API List
 * 1. /add
 * 2. /delete
 * 3. /login
 *
 * @author xungwoo
 */
@Slf4j
@Controller
@RequestMapping(value = "/cgp")
public class CGPController {
	
	
	@Autowired
	PromotionService promotionService;
	
	@Autowired
	AccountResourceService arService;
	
	@RequestMapping(value = "/check-mission", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Map<String, Object>> checkMission (
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam("memberNo") String memberNo,
			@RequestParam("missionKey") String missionKey, 
			@RequestParam("lang") String lang)  {
		
		ApiJsonResult<Map<String, Object>> result = new ApiJsonResult<Map<String, Object>>();
		Map<String, Object> resultMap = new HashMap<String, Object>();
		missionKey = promotionService.missionKeyFiltering(missionKey);
		
		List<Reward> rewardList = promotionService.checkMissionAndReward(myAccountId, memberNo, missionKey, lang);
		boolean isReward = (rewardList.size() > 0) ? true : false;
		resultMap.put("isExist", isReward);
		if (isReward) {
			resultMap.put("msg", CGPRewardMsg.findByMissionKey(missionKey).getMessage()[LanguageCode.findByCode(lang).getIndex()]);
		}
		result.setResult(resultMap);
		return result;
		
	}
			

	
	
	
}