package com.thirtygames.zero.common.service.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.AchievementMetaMapper;
import com.thirtygames.zero.common.model.Achievement;

@Service("achivementMetaService")
public class AchievementMetaServiceImpl extends MetaServiceImpl<AchievementMetaMapper, Achievement, Integer> implements AchievementMetaService {

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int multiAdd(List<Achievement> achvList)  {
		int resultCount = 0;
		for (Achievement achv : achvList) {
			resultCount += this.save(achv);
		}
		return resultCount;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Achievement> getCurrentAndNextStep(int achievementId, int step, String lang) {
		return mapper.getCurrentAndNextStep(achievementId, step, lang);
	}

}
