package com.thirtygames.zero.common.service.equipment.meta;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;

public interface EquipLevelMetaService extends MetaService<EquipLevelMeta, Integer> {

	public EquipLevelMeta getWithStatGrowth(int equipmentType);

}
