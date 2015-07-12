package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.Mission;

public interface MissionMetaService extends MetaService<Mission, Integer> {

	public int multiAdd(List<Mission> missionList) ;

}
