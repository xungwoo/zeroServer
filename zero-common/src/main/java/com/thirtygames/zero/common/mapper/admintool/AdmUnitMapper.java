package com.thirtygames.zero.common.mapper.admintool;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminUnit;


public interface AdmUnitMapper extends GenericMapper<AdminUnit, String> {

	public void updateByUnitId(AdminUnit unit);

	public AdminUnit getByUnitId(@Param("unitId") String unitId);

}
