package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.generic.ResourceService;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Unit;

public interface UnitService extends ResourceService<Unit, String> {

	public Unit getUnitEquipments(String unitId);

	public List<Unit> getMultiUnitEquipments(List<String> unitIdList);

	public AccountResource limitExceedStart(Unit unit, AccountResource ar);

	public AccountResource limitExceedEnd(Unit unit, AccountResource ar);

	public Boolean checkLimitExceedComplete(String unitId);

	public AccountResource unlockUnit(Unit unit, AccountResource ar);

	public Unit generateUnit(int unitType, String accountId);

	public List<Unit> getDeckUnitList(String accountId, List<String> unitIdList);

	public Unit generateRewardUnit(int unitType, String accountId);

	public List<Unit> getMultiUnitEquipmentsWithGems(List<String> unitIdList);

	public int getEquipmentPoint(String accountId);

}