package com.thirtygames.zero.api.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.MissionValidator;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.MissionService;
import com.thirtygames.zero.common.service.meta.MissionMetaService;

@Slf4j
@Controller
@RequestMapping(value = "/mission")
public class MissionController extends ApiGenericController<Mission, Integer, MissionService, MissionValidator> {

	@Autowired
	MissionMetaService missionMetaService;

	@Autowired
	AccountResourceService arService;

	@Override
	protected Mission preAdd(Mission mission, String accountId) {
		mission.setAccountId(accountId);
		Mission oldMission = service.getByAccountId(accountId, mission.getMissionId());
		if (oldMission != null && oldMission.getRewardDone())
			throw new RestException(Errors.AlreadyRewardMission, "mission::" + mission);
		return mission;
	}

	@Override
	protected ApiJsonResult<Mission> postAdd(ApiJsonResult<Mission> result) {
		Mission params = result.getParams();
		result.setResult(service.getByAccountId(params.getAccountId(), params.getMissionId()));
		return result;
	}

	@RequestMapping(value = "/multi", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Mission> addMulti(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(required = true, value = "missions") String missions) {

		ApiJsonResult<Mission> result = new ApiJsonResult<Mission>();

		List<Mission> missionList = new ArrayList<Mission>();
		Iterable<String> itMissions = Splitter.on(',').trimResults().omitEmptyStrings().splitToList(missions);
		for (String param : itMissions) {
			String[] temp = Iterables.toArray(Splitter.on(':').trimResults().omitEmptyStrings().split(param), String.class);
			if (temp.length != 2)
				throw new RestException("Invalid.Param");
			Mission mission = new Mission();
			mission.setAccountId(myAccountId);
			mission.setMissionId(Integer.parseInt(temp[0]));
			mission.setCurrent(Integer.parseInt(temp[1]));
			missionList.add(mission);
		}
		result.setResultCount(service.multiAdd(missionList));
		return result;
	}

	@RequestMapping(value = "/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Reward> reward(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @ModelAttribute Mission mission, BindingResult bindingResult, SessionStatus status) {
		mission.setAccountId(myAccountId);
		Mission oldMission = service.getByAccountId(myAccountId, mission.getMissionId());
		ValidationUtil.isNullModel(oldMission, "Mission param::" + mission);
		if (oldMission.getRewardDone())
			throw new RestException(Errors.AlreadyRewardMission, "mission::" + mission);

		Mission meta = missionMetaService.get(mission.getMissionId());
		ValidationUtil.isNullModel(meta, "MissionMeta param::" + mission);
		if (meta.getGoal() > oldMission.getCurrent())
			throw new RestException("Not.Complete.Mission::" + oldMission);

		ApiJsonResult<Reward> result = new ApiJsonResult<Reward>();
		Reward resultReward = service.reward(oldMission, meta, false);
		result.setResult(resultReward);
		result.setResource(arService.get(myAccountId));
		return result;
	}

	@RequestMapping(value = "/reward/multi", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Mission> rewardMulti(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestParam(required = true, value = "isDaily") Boolean isDaily) {
		List<Mission> myMissionList = service.getMissionList(myAccountId);
		ValidationUtil.isNullOrEmptyList(myMissionList, "MissionList:: param::" + myAccountId);

		ApiJsonResult<Mission> result = new ApiJsonResult<Mission>();
		List<Reward> resultList = service.rewardMulti(myMissionList, isDaily);
		result.setResult(resultList);
		result.setResultCount(resultList.size());
		result.setResource(arService.get(myAccountId));
		return result;
	}

	@Override
	protected Mission preSearch(Mission entity, String accountId) {
		entity.setAccountId(accountId);
		return entity;
	}

	@Override
	protected ApiJsonResult<Mission> postSearch(ApiJsonResult<Mission> result) {
		List<Mission> missionClearList = result.getResultList();
		log.debug("result.getParams()::" + result.getParams());
		List<Mission> missionMetaList = this.getJoinedMissionList(missionClearList, result.getParams());
		result.setResult(missionMetaList);
		result.setResultList(missionMetaList);
		result.setResultCount(missionMetaList.size());
		return result;
	}

	private List<Mission> getJoinedMissionList(List<Mission> missionClearList, Mission mission) {
		List<Mission> missionMetaList = missionMetaService.search(mission);
//		Iterator<Mission> itClear = missionClearList.iterator();
//		while (itClear.hasNext()) {
//			Mission missionClear = itClear.next();
//			Iterator<Mission> itMeta = missionMetaList.iterator();
//			while (itMeta.hasNext()) {
//				Mission meta = itMeta.next();
//				if (meta.getMissionId() == missionClear.getMissionId()) {
//					//if (!missionClear.getRewardDone() && !(meta.getIsExpired() && (missionClear.getCurrent() < meta.getGoal()) ) ) {						
//						meta.setCurrent(missionClear.getCurrent());
//						meta.setRewardDone(missionClear.getRewardDone());
//					//}
//				}
//			}
//		}
		
		List<Mission> result = new LinkedList<Mission>();
		HashMap<Integer, Mission> clearLogs = new HashMap<Integer, Mission>();
		for (Mission log : missionClearList) {
			clearLogs.put(log.getMissionId(), log);
		}

		for (Mission meta: missionMetaList) {
			Integer key = meta.getMissionId();
			if (clearLogs.containsKey(key) == true) {
				Mission log = clearLogs.get(key); 
				meta.setCurrent(log.getCurrent());
				meta.setRewardDone(log.getRewardDone());
				
				if (log.getRewardDone() == true) {
					continue;
				}
				
				if (meta.getIsExpired() && (log.getCurrent() < meta.getGoal())){ 
					continue;
				}
			} else {
				if (meta.getIsExpired() == true) {
					continue;
				}
			}
			result.add(meta);
		}		
		return result;
	}

	@Override
	protected Mission preUpdate(Mission entity, String accountId) {
		throw new RestException("not.allow.request");
	}

}
