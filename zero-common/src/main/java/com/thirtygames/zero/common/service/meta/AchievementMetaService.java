package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.Achievement;

public interface AchievementMetaService extends MetaService<Achievement, Integer> {

	public int multiAdd(List<Achievement> achvList) ;

	public List<Achievement> getCurrentAndNextStep(int achievemenId, int step, String lang);

}
