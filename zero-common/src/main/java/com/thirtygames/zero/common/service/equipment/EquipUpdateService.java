package com.thirtygames.zero.common.service.equipment;

import java.util.List;

import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatUpdate;

public interface EquipUpdateService {
	
	public List<EquipStatUpdate> getTargetDecoStatList(int statType);
	public List<EquipStatUpdate> getTargetStatList(int equipmentType);

	public List<EquipStatUpdate> getUpdatedDecoStatMeta(int statType);
	public List<EquipStatUpdate> getUpdatedStatMeta(int statType);

	public int updateEquipStat(EquipmentStat es);

}