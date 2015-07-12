package com.thirtygames.zero.common.service.admintool;

import java.util.Map;

public interface CommonCodeService {
	public Map<Integer, String> getCategoryList();
	public Map<Integer, String> getSubCategoryList();
	public Map<Integer, String> getEquipNameList();
	public Map<String, String> getSceneList();
	public Map<Integer, String> getUnitTypeList();
	public Map<Integer, String> getEquipStatList();
}
