package com.thirtygames.zero.common.service.equipment.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.equipment.meta.EquipImageMeta;

public interface EquipImageMetaService extends MetaService<EquipImageMeta, String> {

	public List<EquipImageMeta> getGemMetaList(String lang, int myMetaRevision);

	public List<EquipImageMeta> getEquipMetaList(String lang, int myMetaRevision);

}