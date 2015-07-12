package com.thirtygames.zero.api.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.AchievementValidator;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.AchievementService;
import com.thirtygames.zero.common.service.meta.AchievementMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/achievement")
public class AchievementController extends ApiGenericController<Achievement, Integer, AchievementService, AchievementValidator> {

	@Autowired
	AchievementMetaService achvMetaService;

	@Autowired
	AccountResourceService arService;

	@Override
	protected Achievement preAdd(Achievement achv, String accountId)  {
		achv.setAccountId(accountId);
		Achievement oldAchv = service.getByAccountId(accountId, achv.getAchievementId());
		if (oldAchv != null && oldAchv.getRewardDone())  throw new RestException(Errors.AlreadyRewardAchievement, "achievement::" + achv);
		return achv;
	}
	
	@Override
	protected ApiJsonResult<Achievement> postAdd(ApiJsonResult<Achievement> result)  {
		Achievement params = result.getParams();
		result.setResult(service.getByAccountId(params.getAccountId(), params.getAchievementId()));
		return result;
	}

	@Override
	protected Achievement preSearch(Achievement entity, String accountId)  {
		entity.setAccountId(accountId);
		return entity;
	}

	@Override
	protected ApiJsonResult<Achievement> postSearch(ApiJsonResult<Achievement> result)  {
		String lang = result.getParams().getLang();
		List<Achievement> achvClearList = result.getResultList();
		List<Achievement> achvMetaList = getJoinedList(achvClearList, lang);
		
		result.setResult(achvMetaList);
		result.setResultList(achvMetaList);
		result.setResultCount(achvMetaList.size());
		return result;
	}	

	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Achievement> multiAdd(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required=true, value="achievements") String achievements,
			@RequestParam(required=true, value="lang") String lang)  {
		ApiJsonResult<Achievement> result = new ApiJsonResult<Achievement>();
		
		List<Achievement> achvList = new ArrayList<Achievement>();
		Iterable<String> itAchv = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(achievements);
		for (String param : itAchv) {
			String[] temp = Iterables.toArray(Splitter.on(':').trimResults().omitEmptyStrings().split(param), String.class);
			if (temp.length != 2) throw new RestException(Errors.InvalidParameter, "Use seperator ':'");
			Achievement achv = new Achievement();
			achv.setAccountId(myAccountId);
			achv.setAchievementId(Integer.parseInt(temp[0]));
			int current = Integer.parseInt(temp[1]);
			if (current < 0) {
				throw new RestException(Errors.InvalidParameter, " current < 0");
			}
			achv.setCurrent(current);
			achvList.add(achv);
		}
		
		result.setResultCount(service.multiAdd(achvList));
		List<Achievement> resultList = service.getAchvList(myAccountId);
		result.setResult(this.getJoinedList(resultList, lang));
		return result;
	}	
	

	@RequestMapping(value = "/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Achievement> reward(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@RequestParam(required=true, value="achievementId") int achievementId,
			@RequestParam(required=true, value="lang") String lang)  {
		Achievement oldAchv = service.getByAccountId(myAccountId, achievementId);
		if (oldAchv == null) throw new RestException(Errors.NotFoundData, "Achievement::" + achievementId);
		if (oldAchv.getRewardDone()) throw new RestException(Errors.AlreadyRewardAchievement, "achievementId::" + achievementId);
		
		List<Achievement> metaList = achvMetaService.getCurrentAndNextStep(achievementId, oldAchv.getStep(), lang);
		if (metaList.isEmpty()) throw new RestException(Errors.NotFoundData, "AchivementMeta::" + achievementId);
		
		ApiJsonResult<Achievement> result = new ApiJsonResult<Achievement>();
		result.setResult(service.reward(oldAchv, metaList, false));	// 모든 업적 수행완료 데이터없음
		result.setResource(arService.get(myAccountId));
		return result;
	}
	
	@RequestMapping(value = "/reward/multi", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Achievement> rewardMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform )  {
		List<Achievement> myAchvList = service.getAchvList(myAccountId);
		if (myAchvList.isEmpty()) throw new RestException(Errors.NotFoundData, "Achievement");
		
		List<Achievement> metaList = achvMetaService.range(0, 0);
		if (metaList.isEmpty()) throw new RestException(Errors.NotFoundData, "AchivementMeta");
		
		ApiJsonResult<Achievement> result = new ApiJsonResult<Achievement>();
		result.setResult(service.rewardMulti(myAchvList, metaList));	
		result.setResource(arService.get(myAccountId));
		return result;
	}

	private List<Achievement> getJoinedList(List<Achievement> achvClearList, String lang) {
		Achievement achvParam = new Achievement();
		achvParam.setLang(lang);
		
		List<Achievement> achvMetaList = achvMetaService.search(achvParam);
		Iterator<Achievement> itClear = achvClearList.iterator();
		while (itClear.hasNext()) {
			Achievement clear = itClear.next();
			Iterator<Achievement> itMeta1 = achvMetaList.iterator();
			while (itMeta1.hasNext()) {
				Achievement meta = itMeta1.next();
				if (meta.getAchievementId().equals(clear.getAchievementId())) {
					if (meta.getStep().equals(clear.getStep())) {
						meta.setCurrent(clear.getCurrent());
						meta.setRewardDone(clear.getRewardDone());
						itClear.remove();
					} else {
						itMeta1.remove();
					}
				}
			}
		}
		
		int lastId = -1;
		Iterator<Achievement> itResult = achvMetaList.iterator();
		while (itResult.hasNext()) {
			Achievement result = itResult.next();
			if (lastId == result.getAchievementId()) {
				itResult.remove();
			} else {
				lastId = result.getAchievementId();
			}
		}
		return achvMetaList;
	}

	@Override
	protected Achievement preUpdate(Achievement entity, String accountId)  {
		throw new RestException("not.allow.request");
	}

}
