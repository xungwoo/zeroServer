package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Achievement;

public interface AchievementMapper extends GenericMapper<Achievement, Integer> {

	public List<Achievement> getAchvList(@Param("accountId") String accountId);

}
