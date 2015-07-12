package com.thirtygames.zero.common.mapper.meta;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipMergeMeta;

public interface EquipMergeMetaMapper extends GenericMapper<EquipMergeMeta, Integer> {

	public EquipMergeMeta getEquipMergeMeta(@Param("eqClass1")int eqClass1, @Param("eqClass2")int eqClass2);

}
