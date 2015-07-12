package com.thirtygames.zero.common.service.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.UnitSkillMetaMapper;
import com.thirtygames.zero.common.model.meta.UnitSkill;

@Service("unitSkillMetaService")
public class UnitSkillMetaServiceImpl extends MetaServiceImpl<UnitSkillMetaMapper, UnitSkill, Integer> implements UnitSkillMetaService {

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int multiAdd(List<UnitSkill> unitSkillList)  {
		int resultCount = 0;
		for (UnitSkill unitSkill : unitSkillList) {
			resultCount += this.save(unitSkill);
		}
		return resultCount;
	}

}
