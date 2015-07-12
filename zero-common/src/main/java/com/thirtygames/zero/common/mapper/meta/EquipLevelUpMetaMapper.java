package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelUpMeta;

public interface EquipLevelUpMetaMapper extends GenericMapper<EquipLevelUpMeta, Integer> {

	public EquipLevelUpMeta getByGradeAndLevel(@Param("grade")int grade, @Param("level")int level);


}
