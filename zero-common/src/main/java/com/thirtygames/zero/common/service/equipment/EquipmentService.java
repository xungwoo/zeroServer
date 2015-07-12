package com.thirtygames.zero.common.service.equipment;

import java.util.List;
import java.util.Map;

import com.thirtygames.zero.common.generic.ResourceService;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.equipment.EquipLevelUpLog;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.params.GemGradeUpParams;

public interface EquipmentService extends ResourceService<Equipment, String> {
	
	public EquipLevelUpLog levelUp(Equipment targetEq, List<Equipment> useEqList, long equipLevelUpDrug) ;
	public AccountResource sell(EquipmentMeta meta, Equipment eq) ;

	public Integer gemInstall(String equipmentId, String gemId, int socketNo) ;
	public Integer gemClear(String equipmentId, String gemId, int socketNo) ;
	public Equipment gemGradeUp(GemGradeUpParams params) ;

	public void multiAdd(List<Equipment> equipmentList) ;
	public void addMergedEquipByResource(Equipment equip, Equipment eq1, Equipment eq2, AccountResource ar) ;

	public List<EquipmentStat> getStatList(String equipmentId);
	public Map<String, Equipment> getEquipList(String accountId);
	public List<Equipment> getUseEquipments(List<String> eqIdList);

	public Integer equipSwitch(Equipment eq, String removeId);
	public Integer sellMulti(String ids, String accountId);
	public Equipment getInstalledGem(String equpmentId);

}