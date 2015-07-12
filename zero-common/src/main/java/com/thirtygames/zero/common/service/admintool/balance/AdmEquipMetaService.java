package com.thirtygames.zero.common.service.admintool.balance;

import java.util.List;

import com.thirtygames.zero.common.generic.admintool.AdminMetaService;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipChoiceDecoMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipGachaRateMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.meta.ShopItem;

public interface AdmEquipMetaService extends AdminMetaService<EquipmentMeta, String> {
	
	public List<EquipType> getEquipCategoryTypeList();
	public List<EquipType> getEquipTypeList(Integer subCategory);
	public List<Integer> getGemEquipTypeList(Integer grade);
	public List<Integer> getNoGemEquipTypeList(Integer grade, Integer eqClass);
	public List<EquipType> getEquipSubCategoryTypeList(int category);
	public List<EquipType> getGemStatTypeList();
	
	public EquipmentMeta getEquipmentMeta(Equipment equip);
	public List<EquipStatMeta> getAdminStatMetaList(String sidx, String sord, EquipStatMeta equipMeta);	
	public void setDecoInf(Equipment equip, List<EquipmentStat> decoStatList);

	public int saveStat(EquipStatMeta statMeta) ;
	public int updateStat(EquipStatMeta statMeta);
	public void deleteStat(String id);
	
	public EquipChoiceDecoMeta getChoiceDecoMeta();
	public List<EquipStatType> getStatTypeList();
	public Equipment generateEquip(Equipment equipment, boolean isDecoRateWeight) ;
	public int getMergedEquipmentClass(int eqClass1, int eqClass2) ;
	public int getMergedEquipmentType(int mergedGrade, int mergedClass);
	public Integer getGemGradeUpEquipType(Integer subCategory, Integer grade);
	
	public List<EquipGachaRateMeta> getEquipGachaRateMetaList(int itemKey, int rateType);
	
	
	public List<Equipment> generateGachaEquips(String accountId, ShopItem itemMeta) ;
	public List<Equipment> generateGachaGems(String accountId, ShopItem itemMeta) ;

}