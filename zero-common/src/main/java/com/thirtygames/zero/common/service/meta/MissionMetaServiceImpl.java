package com.thirtygames.zero.common.service.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.MissionMetaMapper;
import com.thirtygames.zero.common.model.Mission;

@Service("missionMetaService")
public class MissionMetaServiceImpl extends MetaServiceImpl<MissionMetaMapper, Mission, Integer> implements MissionMetaService {

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int multiAdd(List<Mission> missionList)  {
		int resultCount = 0;
		for (Mission mission : missionList) {
			resultCount += this.save(mission);
		}
		return resultCount;
	}
	
	

}
