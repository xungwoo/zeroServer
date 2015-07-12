package com.thirtygames.zero.common.service.equipment.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelUpMeta;

public interface EquipLevelUpMetaService extends MetaService<EquipLevelUpMeta, Integer> {

	public EquipLevelUpMeta getByGradeAndLevel(int grade, int level);

	public boolean isLevelUpSuccess(int grade, int level, long equipLevelUpDrug) ;


}
