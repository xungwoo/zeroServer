package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatUpdate;

public interface EquipUpdateMapper {

	public List<EquipStatUpdate> getTargetDecoStatList(@Param("statType")int statType);
	public List<EquipStatUpdate> getUpdatedDecoStatMeta(@Param("statType")int statType);
	
	public List<EquipStatUpdate> getTargetStatList(@Param("equipmentType")int equipmentType);
	public List<EquipStatUpdate> getUpdatedStatMeta(@Param("statType")int statType);


	public int updateEquipStat(EquipmentStat es);
}
