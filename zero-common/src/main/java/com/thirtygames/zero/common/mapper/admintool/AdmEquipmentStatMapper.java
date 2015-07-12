package com.thirtygames.zero.common.mapper.admintool;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;


public interface AdmEquipmentStatMapper extends GenericMapper<EquipmentStat, String> {
	List<EquipmentStat> selectEquipmentStatList(@Param("sidx")String sidx, @Param("sord")String sord,
			@Param("model")EquipmentStat equipmentStat);

}
