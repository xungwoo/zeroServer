package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Achievement;
import com.thirtygames.zero.common.model.meta.Reward;

public interface AchievementService extends GenericService<Achievement, Integer> {

	/**
	 * @param achv
	 * @param metaList
	 * @return Achievement (nextStep)
	 * @throws RestException
	 */
	public Reward reward(Achievement rewardAchv, List<Achievement> metaList, boolean isReturnResource) ;

	public Integer multiAdd(List<Achievement> achvList) ;

	public List<Achievement> getAchvList(String accountId);

	public List<Reward> rewardMulti(List<Achievement> myAchvList, List<Achievement> metaList) ;

}
