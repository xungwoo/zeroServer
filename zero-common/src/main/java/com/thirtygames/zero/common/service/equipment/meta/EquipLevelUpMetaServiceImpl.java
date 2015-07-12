package com.thirtygames.zero.common.service.equipment.meta;

import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipLevelUpMetaMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelUpMeta;
import com.thirtygames.zero.common.service.meta.ApiMetaService;

@Slf4j
@Service("equipLevelUpMetaService")
public class EquipLevelUpMetaServiceImpl extends MetaServiceImpl<EquipLevelUpMetaMapper, EquipLevelUpMeta, Integer> implements EquipLevelUpMetaService {

	@Autowired
	ApiMetaService amService;
	
	@Autowired
	EquipLevelMetaService levelMetaService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public EquipLevelUpMeta getByGradeAndLevel(int grade, int level) {
		return mapper.getByGradeAndLevel(grade, level);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public boolean isLevelUpSuccess(int grade, int level, long equipLevelUpDrug)  {
		EquipLevelUpMeta levelUpMeta = this.getByGradeAndLevel(grade, level);
		if (levelUpMeta == null) {
			throw new RestException("Not.found.EquipLevelUpMeta grade:" + grade + " level:" + level);
		}
		
		float addRate = 0;
		if (equipLevelUpDrug > 0) {
			EquipLevelMeta leveMeta = levelMetaService.get(1);
			if (leveMeta == null) {
				throw new RestException("Not.found.EquipLevelMeta");
			}
			
			if (equipLevelUpDrug > leveMeta.getEquipLevelUpDrugMax()) {
				throw new RestException("Max.equipLevelUpDrug :" + leveMeta.getEquipLevelUpDrugMax());
			}				
			
			addRate = leveMeta.getEquipLevelUpDrugRate() * equipLevelUpDrug;
			log.debug("addRate::" + addRate);
		}
		
		Random r = new Random();
		log.debug("levelUpMeta.getRate()::" + levelUpMeta.getRate());
		log.debug("((levelUpMeta.getRate() + addRate) >= r.nextFloat())::" + ((levelUpMeta.getRate() + addRate) >= r.nextFloat()));
		return ((levelUpMeta.getRate() + addRate) >= r.nextFloat());
	}

}
