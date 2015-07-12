package com.thirtygames.zero.common.service.admintool.user;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.admintool.AdminUnit;

public interface AdmUnitService extends GenericService<AdminUnit, String> {

	public String unitReset(String unitId, String accountId);
}