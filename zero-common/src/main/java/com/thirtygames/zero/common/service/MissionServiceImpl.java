package com.thirtygames.zero.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.MissionMapper;
import com.thirtygames.zero.common.mapper.ResourceMapper;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.meta.MissionMetaService;

@Slf4j
@Service("missionService")
public class MissionServiceImpl extends GenericServiceImpl<MissionMapper, Mission, Integer> implements MissionService {

	@Autowired
	ResourceMapper arMapper;
	
	@Autowired
	RewardService rewardService;
	
	@Autowired
	MissionMetaService missionMetaService;

	@Override
	@Transactional
	public Reward reward(Mission rewardMission, Mission meta, boolean isReturn)  {
		rewardMission.setRewardDone(true);
		mapper.update(rewardMission);

		Reward reward = new Reward();
		reward.setAccountId(rewardMission.getAccountId());
		reward.setRewardType(meta.getRewardType());
		reward.setReward(meta.getReward());
		reward.setReasonType(Reward.ReasonType.Mission.getCode());
		return rewardService.reward(reward, isReturn); 
	}

	@Override
	public Mission getMission(String accountId, Integer missionId) {
		return mapper.getMission(accountId, missionId);
	}

	@Override
	public Boolean checkMissionList(String accountId, List<Mission> missionList) {
		boolean isRewarded = false;
		Mission param = new Mission();
		param.setAccountId(accountId);
		List<Mission> oldMissionList = mapper.search(param);
		for(Mission oldMission : oldMissionList) {
			for (Mission mission : missionList) {
				if (mission.getMissionId().equals(oldMission.getMissionId()) && oldMission.getRewardDone()) {
					isRewarded = true;
					log.debug("Check Mission List::" + isRewarded);
					break;
				}
			}
		}
		return isRewarded;
	}

	@Override
	@Transactional
	public Integer multiAdd(List<Mission> missionList)  {
		int resultCount = 0;
		for (Mission mission : missionList) {
			resultCount += this.save(mission);
		}
		return resultCount;
	}

	@Override
	public List<Mission> getMissionList(String accountId) {
		return mapper.getMissionList(accountId);
	}

	@Override
	@Transactional
	public List<Reward> rewardMulti(List<Mission> myMissionList, boolean isDaily)  {
		Mission mission = new Mission();
		mission.setIsDaily(isDaily);
		List<Mission> metaList = missionMetaService.search(mission);
		if (metaList == null || metaList.size() <= 0) throw new RestException("Not.found.MissionMeta");
		
		List<Reward> resultList = new ArrayList<Reward>();
		
		Iterator<Mission> itMyMission = myMissionList.iterator();
		while(itMyMission.hasNext()) {
			Mission myMission = itMyMission.next();
			
			Iterator<Mission> itMissionMeta = metaList.iterator();
			while(itMissionMeta.hasNext()) {
				Mission meta = itMissionMeta.next();
				if (myMission.getMissionId().equals(meta.getMissionId())) {
					if (!myMission.getRewardDone() && meta.getGoal() <= myMission.getCurrent()) {
						resultList.add(this.reward(myMission, meta, false));
					}
					itMissionMeta.remove();
					break;	// sorted list.
				}
			}
		}		
		
		return resultList;
	}
}
