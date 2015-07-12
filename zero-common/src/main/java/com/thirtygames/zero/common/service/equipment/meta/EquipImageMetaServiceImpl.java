package com.thirtygames.zero.common.service.equipment.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipImageMetaMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipImageMeta;

@Slf4j
@Service("equipImageMetaService")
public class EquipImageMetaServiceImpl extends MetaServiceImpl<EquipImageMetaMapper, EquipImageMeta, String> implements EquipImageMetaService {
	
	@Override
	@Cacheable(value="meta-cache", key="{#root.targetClass, #root.method.name, #myMetaRevision, #lang}")
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipImageMeta> getGemMetaList(String lang, int myMetaRevision) {
		return mapper.getGemMetaList(lang);
	}

	@Override
	@Cacheable(value="meta-cache", key="{#root.targetClass, #root.method.name, #myMetaRevision, #lang}")
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipImageMeta> getEquipMetaList(String lang, int myMetaRevision) {
		return mapper.getEquipMetaList(lang);
	}

}
