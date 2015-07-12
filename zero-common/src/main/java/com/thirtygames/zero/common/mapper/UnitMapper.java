package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Unit;

public interface UnitMapper extends GenericMapper<Unit, String> {

	public Unit getUnitEquipments(String unitId);

	public List<Unit> getMultiUnitEquipments(List<String> unitIdList);

	public Integer checkLimitExceedComplete(String unitId);

	public List<Unit> getDeckUnitList(@Param("accountId")String accountId, @Param("unitIdList")List<String> unitIdList);

	public int getEquipmentPoint(String accountId);


}
