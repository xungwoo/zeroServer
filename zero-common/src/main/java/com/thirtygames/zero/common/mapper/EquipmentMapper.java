package com.thirtygames.zero.common.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;

public interface EquipmentMapper extends GenericMapper<Equipment, String> {

	public void saveStat(EquipmentStat stat);
	public void updateStat(EquipmentStat stat);

	public List<EquipmentStat> getStatList(String accountId);
	
	@MapKey("equipmentId")
	public Map<String, Equipment> getEquipList(String accountId);
	
	public void deleteStat(String equipmentId);

	public List<Equipment> getUseEquipments(List<String> eqIdList);
	public void findEquipByGemId(String equipmentId);
	public Equipment findInstalledEquip(Equipment gemInstallledEquipParam);
	
	public Integer gemInstall(@Param("equipmentId")String equipmentId, @Param("gemId")String gemId, @Param("socketNo")int socketNo);
	public Integer gemClear(@Param("equipmentId")String equipmentId, @Param("socketNo")int socketNo);
	public void clearSocketByGemId(@Param("gemId") String gemId);
	public Integer checkInstalledGem(@Param("gemId") String gemId);
	public Equipment getInstalledGem(@Param("equipmentId")String equipmentId);
}
