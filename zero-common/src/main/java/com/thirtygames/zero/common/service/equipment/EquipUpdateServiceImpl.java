package com.thirtygames.zero.common.service.equipment;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.mapper.EquipUpdateMapper;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatUpdate;

@Slf4j
@Service("equipUpdateService")
public class EquipUpdateServiceImpl implements EquipUpdateService {
	
	
	@Autowired
	EquipUpdateMapper mapper;
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipStatUpdate> getUpdatedDecoStatMeta(int statType) {
		return mapper.getUpdatedDecoStatMeta(statType);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipStatUpdate> getUpdatedStatMeta(int statType) {
		return mapper.getUpdatedStatMeta(statType);
	}
	
	@Override
	public List<EquipStatUpdate> getTargetDecoStatList(int statType) {
		return mapper.getTargetDecoStatList(statType);
	}
	
	@Override
	public List<EquipStatUpdate> getTargetStatList(int equipmentType) {
		return mapper.getTargetStatList(equipmentType);
	}

	@Override
	public int updateEquipStat(EquipmentStat es) {
		return mapper.updateEquipStat(es);
	}


}
