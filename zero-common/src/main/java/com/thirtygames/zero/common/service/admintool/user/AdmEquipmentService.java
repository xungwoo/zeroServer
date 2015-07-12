package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.equipment.Equipment;

public interface AdmEquipmentService extends GenericService<Equipment, String> {

	List<Equipment> getList(List<String> equipmentIdList);
}