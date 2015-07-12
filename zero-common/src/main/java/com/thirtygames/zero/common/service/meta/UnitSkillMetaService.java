package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.UnitSkill;

public interface UnitSkillMetaService extends MetaService<UnitSkill, Integer> {

	public int multiAdd(List<UnitSkill> unitSkillList) ;

}
