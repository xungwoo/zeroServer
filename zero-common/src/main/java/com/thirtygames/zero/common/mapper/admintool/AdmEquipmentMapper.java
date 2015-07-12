package com.thirtygames.zero.common.mapper.admintool;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.Equipment;


public interface AdmEquipmentMapper extends GenericMapper<Equipment, String> {

	List<Equipment> getList(List<String> equipmentIdList);

}
