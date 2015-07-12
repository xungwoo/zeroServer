package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Mission;

public interface MissionMapper extends GenericMapper<Mission, Integer> {

	public Mission getMission(@Param("accountId") String accountId, @Param("missionId")Integer missionId);

	public List<Mission> getMissionList(@Param("accountId") String accountId);

}
