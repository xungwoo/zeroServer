package com.thirtygames.zero.common.service.equipment.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.equipment.meta.EquipMergeMeta;

public interface EquipMergeMetaService extends MetaService<EquipMergeMeta, Integer> {

	public int multiAdd(List<EquipMergeMeta> metaList) ;
	public EquipMergeMeta getEquipMergeMeta(int eqClass1, int eqClass2) ;
}
