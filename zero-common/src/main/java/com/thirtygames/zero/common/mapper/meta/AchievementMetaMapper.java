package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Achievement;

public interface AchievementMetaMapper extends GenericMapper<Achievement, Integer> {
	
	public List<Achievement> getCurrentAndNextStep(@Param("achievementId") int achievementId, @Param("step") int step, @Param("lang")String lang);

}
