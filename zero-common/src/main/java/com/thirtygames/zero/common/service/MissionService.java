package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Mission;
import com.thirtygames.zero.common.model.meta.Reward;

public interface MissionService extends GenericService<Mission, Integer> {

	public Reward reward(Mission rewardMission, Mission meta, boolean isReturnResource) ;

	public Mission getMission(String accountId, Integer missionId);

	public Boolean checkMissionList(String myAccountId, List<Mission> missionList);

	public Integer multiAdd(List<Mission> missionList) ;

	public List<Mission> getMissionList(String accountId);

	public List<Reward> rewardMulti(List<Mission> oldMissionList, boolean isDaily);


}
