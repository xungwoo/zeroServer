package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;

public interface EquipLevelMetaMapper extends GenericMapper<EquipLevelMeta, Integer> {

	public EquipLevelMeta getWithStatGrowth(@Param("equipmentType") int equipmentType);

}
