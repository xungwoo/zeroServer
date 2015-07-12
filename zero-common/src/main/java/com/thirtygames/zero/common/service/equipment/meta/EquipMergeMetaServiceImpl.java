package com.thirtygames.zero.common.service.equipment.meta;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipMergeMetaMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipMergeMeta;

@Service("equipComposeMetaService")
public class EquipMergeMetaServiceImpl extends MetaServiceImpl<EquipMergeMetaMapper, EquipMergeMeta, Integer> implements EquipMergeMetaService {

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int multiAdd(List<EquipMergeMeta> metaList)  {
		int resultCount = 0;
		for (EquipMergeMeta meta : metaList) {
			resultCount += this.save(meta);
		}
		return resultCount;
	}
	

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public EquipMergeMeta getEquipMergeMeta(int eqClass1, int eqClass2)  {
		return mapper.getEquipMergeMeta(eqClass1, eqClass2);
	}	

}
