package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;

public interface AdmEquipmentStatService extends GenericService<EquipmentStat, String> {
	List<EquipmentStat> getEquipmentStatList(String sidx, String sord,
			EquipmentStat equipmentStat);
}