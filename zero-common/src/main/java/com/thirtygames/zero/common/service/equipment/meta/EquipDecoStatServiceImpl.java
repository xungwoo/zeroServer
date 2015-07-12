package com.thirtygames.zero.common.service.equipment.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipDecoStatMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;

@Slf4j
@Service("equipDecoStatService")
public class EquipDecoStatServiceImpl extends MetaServiceImpl<EquipDecoStatMapper, EquipStatMeta, String> implements EquipDecoStatService {

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(EquipStatMeta meta) {

		String grade = Integer.toString(meta.getGrade());
		String statType = Strings.padStart(Integer.toString(meta.getStatType()), 2, '0');
		meta.setDecoCode(Integer.parseInt(grade + statType));
		return mapper.save(meta);
	}

}
