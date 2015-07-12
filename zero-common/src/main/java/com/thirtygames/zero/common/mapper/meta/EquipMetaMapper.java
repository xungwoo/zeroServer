package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.meta.EquipChoiceDecoMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipGachaRateMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;

public interface EquipMetaMapper extends GenericMapper<EquipmentMeta, String> {

	public List<EquipType> getEquipCategoryList();
	public List<EquipType> getEquipTypeList(@Param("subCategory")Integer subCategory);
	public List<Integer> getGemEquipTypeList(@Param("grade")Integer grade);
	public List<Integer> getNoGemEquipTypeList(@Param("grade")Integer grade, @Param("eqClass")Integer eqClass);
	public List<Integer> getNoGemEquipTypeList(@Param("grade")Integer grade, @Param("eqClass")Integer eqClass, @Param("category")Integer category);
	
	public List<EquipType> getEquipSubCategoryList(int category);
	public List<EquipType> getGemStatTypeList();
	
	public EquipmentMeta getEquipmentMeta(Equipment equip);	
	public List<EquipStatMeta> getStatMetaList(@Param("equipMetaKey")Integer equipMetaKey);
	public List<EquipStatMeta> getAdminStatMetaList(@Param("sidx")String sidx, @Param("sord") String sord , @Param("model")EquipStatMeta esm);
	public List<EquipStatMeta> getDecoStatMetaList(@Param("grade") int grade, @Param("exceptType") Integer exceptType);
	
	
	public EquipStatMeta getStatMeta(EquipStatMeta statMeta);
	public EquipStatMeta getDecoStat(EquipStatMeta statMeta);
	
	public int saveStat(EquipStatMeta statMeta);
	public int updateStat(EquipStatMeta statMeta);
	
	public void deleteStat(String id);
	
	public int saveDeco(EquipStatMeta statMeta);
	public int updateDeco(EquipStatMeta statMeta);
	
	public EquipChoiceDecoMeta getChoiceDecoMeta();
	public List<EquipStatType> getStatTypeList();
	public String getEquipmentName(@Param("equipmentType")int equipmentType);
	public String getStatEngName(@Param("type")int type);
	public int getRandomMergedEquipmentType(@Param("grade")int mergedGrade, @Param("eqClass")int mergedClass, @Param("category")int mergedCategory);
	public int getGemGradeUpEquipType(@Param("subCategory")Integer subCategory, @Param("grade")Integer grade);
	
	public List<EquipGachaRateMeta> getEquipGachaRateMetaList(@Param("itemKey")int itemKey, @Param("rateType")int rateType);
	
	
	
	public int getBossRaidEquipReward(@Param("subCategory")int subCategory, @Param("grade")int grade);
	
}
