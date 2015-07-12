package com.thirtygames.zero.common.service.admintool;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Maps;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.mapper.meta.EquipCategoryMapper;
import com.thirtygames.zero.common.mapper.meta.EquipMetaMapper;
import com.thirtygames.zero.common.mapper.meta.EquipTypeMapper;
import com.thirtygames.zero.common.mapper.meta.StageMetaMapper;
import com.thirtygames.zero.common.mapper.meta.UnitTypeMapper;
import com.thirtygames.zero.common.model.equipment.meta.EquipCategory;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.meta.UnitType;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class CommonCodeServiceImpl implements CommonCodeService {

	private static final Integer EQUIP_CATEGORY_MAJOR = 1;
	private static final Integer EQUIP_CATEGORY_MINOR = 2;
	
	@Autowired
	EquipCategoryMapper equipCategoryMapper;
	
	@Autowired
	EquipTypeMapper equipTypeMapper;
	
	@Autowired
	StageMetaMapper stageMetaMapper;
	
	@Autowired
	EquipMetaMapper equipMetaMapper;
	
	@Autowired
	UnitTypeMapper unitTypeMapper;
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<Integer, String> getCategoryList() {
		Map<Integer, String> categoryList = Maps.newHashMap();
		
		EquipCategory criteria = new EquipCategory();
		criteria.setCodeLevel(EQUIP_CATEGORY_MAJOR);
		List<EquipCategory> searchResult = equipCategoryMapper.search(criteria);
		for (EquipCategory equipCategory : searchResult) {
			categoryList.put(equipCategory.getCategoryCode(), equipCategory.getCategoryName());
		}
		
		return categoryList;
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<Integer, String> getSubCategoryList() {
		Map<Integer, String> categoryList = Maps.newHashMap();
		
		EquipCategory criteria = new EquipCategory();
		criteria.setCodeLevel(EQUIP_CATEGORY_MINOR);
		List<EquipCategory> searchResult = equipCategoryMapper.search(criteria);
		for (EquipCategory equipCategory : searchResult) {
			categoryList.put(equipCategory.getSubCategoryCode(), equipCategory.getSubCategoryName());
		}
		
		return categoryList;
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<Integer, String> getEquipNameList() {
		Map<Integer, String> typeList = Maps.newHashMap();
		
		EquipType criteria = new EquipType();
		List<EquipType> searchResult = equipTypeMapper.search(-1, -1, "", "", criteria);
		for (EquipType equipCategory : searchResult) {
			typeList.put(equipCategory.getTypeCode(), equipCategory.getTypeName());
		}
		
		return typeList;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<String, String> getSceneList() {
		Map<String, String> sceneList = Maps.newHashMap();
		
		Stage criteria = new Stage();
		List<Stage> searchResult = stageMetaMapper.search(criteria);
		for (Stage stage : searchResult) {
			sceneList.put(stage.getStageKey(), stage.getScene());
		}
		
		return sceneList;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<Integer, String> getUnitTypeList() {
		Map<Integer, String> unitTypeList = Maps.newHashMap();
		
		List<UnitType> searchResult = unitTypeMapper.range(0, 0);
		for (UnitType unitType : searchResult) {
			unitTypeList.put(unitType.getTypeCode(), unitType.getTypeName());
		}
		
		return unitTypeList;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Map<Integer, String> getEquipStatList() {
		Map<Integer, String> equipmentStatList = Maps.newHashMap();
		
		List<EquipStatType> equipStatTypeList = equipMetaMapper.getStatTypeList();
		for (EquipStatType equipStatType : equipStatTypeList) {
			equipmentStatList.put(equipStatType.getStatType(), equipStatType.getName());
		}
		
		return equipmentStatList;
	}

}
