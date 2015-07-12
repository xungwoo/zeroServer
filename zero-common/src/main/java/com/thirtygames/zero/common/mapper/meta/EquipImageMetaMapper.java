package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipImageMeta;

public interface EquipImageMetaMapper extends GenericMapper<EquipImageMeta, String> {

	public List<EquipImageMeta> getGemMetaList(@Param("lang") String lang);

	public List<EquipImageMeta> getEquipMetaList(@Param("lang") String lang);
	
}
