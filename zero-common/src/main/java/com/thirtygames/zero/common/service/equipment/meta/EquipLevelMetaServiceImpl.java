package com.thirtygames.zero.common.service.equipment.meta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipLevelMetaMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;

@Service("equipLevelMetaService")
public class EquipLevelMetaServiceImpl extends MetaServiceImpl<EquipLevelMetaMapper, EquipLevelMeta, Integer> implements EquipLevelMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public EquipLevelMeta getWithStatGrowth(int equipmentType) {
		return mapper.getWithStatGrowth(equipmentType);
	}

}
