package com.thirtygames.zero.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.ResourceServiceImpl;
import com.thirtygames.zero.common.mapper.DeckMapper;
import com.thirtygames.zero.common.mapper.UnitMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.log.UnitLog;
import com.thirtygames.zero.common.model.log.UnitLog.UnitLogStatus;
import com.thirtygames.zero.common.service.equipment.EquipmentService;
import com.thirtygames.zero.common.service.log.UnitLogService;

@Slf4j
@Service("unitService")
public class UnitServiceImpl extends ResourceServiceImpl<UnitMapper, Unit, String> implements UnitService {

	@Autowired
	DeckMapper deckMapper;
	
	@Autowired
	UnitLogService unitLogService;
	
	@Autowired
	EquipmentService eqService;
	
	
	@Override
	public Unit generateUnit(int unitType, String accountId) {
		Unit unit = new Unit();
		unit.setUnitType(unitType);
		unit.setAccountId(accountId);
		List<Unit> unitList = this.search(unit);
		if (!unitList.isEmpty()) throw new RestException(Errors.AlreadyExistUnitType, String.valueOf(unit.getUnitType()));
		
		unit.setUnitId( UUID.randomUUID().toString() );
		unit.setLevel(Unit.DEFAULT_LEVEL);
		unit.setSkill0Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill1Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill2Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill3Lv(Unit.DEFAULT_LEVEL);
		return unit;
	}
	
	/* (non-Javadoc)
	 * @see com.thirtygames.zero.common.service.UnitService#generateRewardUnit(int, java.lang.String)
	 * 
	 * 쿠폰 유닛발급을 위한 // 유닛이 이미 unlock 된 상태면 대체수단인 unlockKey를 지급하기 위한 처리 
	 */
	@Override
	public Unit generateRewardUnit(int unitType, String accountId) {
		Unit unit = new Unit();
		unit.setUnitType(unitType);
		unit.setAccountId(accountId);
		List<Unit> unitList = this.search(unit);
		if (!unitList.isEmpty()) return null;
		
		unit.setUnitId( UUID.randomUUID().toString() );
		unit.setLevel(Unit.DEFAULT_LEVEL);
		unit.setSkill0Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill1Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill2Lv(Unit.DEFAULT_LEVEL);
		unit.setSkill3Lv(Unit.DEFAULT_LEVEL);
		return unit;
	}
	

	@Override
	@Transactional
	public AccountResource unlockUnit(Unit unit, AccountResource ar) {
		mapper.save(unit);
		
		UnitLog unitLog = new UnitLog();
		unitLog.setStatus(UnitLogStatus.Acquire.getCode());
		saveUnitLog(unit, unitLog);
		return super.updateSubtraction(ar, true);
	}	

	
	@Override
	public Unit getUnitEquipments(String unitId) {
		return mapper.getUnitEquipments(unitId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<Unit> getMultiUnitEquipments(List<String> unitIdList) {
		return mapper.getMultiUnitEquipments(unitIdList);
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<Unit> getMultiUnitEquipmentsWithGems(List<String> unitIdList) {
		
		List<Unit> unitList = mapper.getMultiUnitEquipments(unitIdList);
		Iterator<Unit> itUnit = unitList.iterator();
		while(itUnit.hasNext()) {
			Unit unit = itUnit.next();
			Iterator<Equipment> itEquipment = unit.getEquipments().iterator();
			List<Equipment> gemList = new ArrayList<Equipment>();
			while(itEquipment.hasNext()) {
				Equipment eq = itEquipment.next();
				if (eq.getSocket1() != null) gemList.add(eqService.getInstalledGem(eq.getSocket1()));
				if (eq.getSocket2() != null) gemList.add(eqService.getInstalledGem(eq.getSocket2()));
				if (eq.getSocket3() != null) gemList.add(eqService.getInstalledGem(eq.getSocket3()));
			}
			unit.getEquipments().addAll(gemList);
		}
		return unitList;
	}	

	@Override
	@Transactional
	public AccountResource limitExceedStart(Unit unit, AccountResource ar) {
		mapper.update(unit);
		deckMapper.excludeUnit(unit.getUnitId());
		
		UnitLog unitLog = new UnitLog();
		unitLog.setUnitType(unit.getUnitType());
		unitLog.setStatus(UnitLogStatus.LimitExceedStart.getCode());		
		saveUnitLog(unit, unitLog);		
		return super.updateSubtraction(ar, true);
	}

	@Override
	@Transactional
	public AccountResource limitExceedEnd(Unit unit, AccountResource ar) {
		mapper.update(unit);
		
		UnitLog unitLog = new UnitLog();
		unitLog.setUnitType(unit.getUnitType());
		unitLog.setStatus(UnitLogStatus.LimitExceedEnd.getCode());		
		saveUnitLog(unit, unitLog);
		return super.updateSubtraction(ar, true);
	}

	@Override
	public Boolean checkLimitExceedComplete(String unitId) {
		return mapper.checkLimitExceedComplete(unitId) == 1 ? true : false;
	}

	private void saveUnitLog(Unit unit, UnitLog unitLog) {
		log.debug("##UnitLog unit::" + unit);
		log.debug("##UnitLog unitLog::" + unitLog);
		unitLog.setUnitId(unit.getUnitId());
		unitLog.setUnitType(unit.getUnitType());
		unitLog.setAccountId(unit.getAccountId());
		unitLog.setResultLevel(unit.getLevel());
		unitLogService.save(unitLog);
	}


	@Override
	public List<Unit> getDeckUnitList(String accountId, List<String> unitIdList) {
		return mapper.getDeckUnitList(accountId, unitIdList);
	}

	@Override
	public int getEquipmentPoint(String accountId) {
		return mapper.getEquipmentPoint(accountId);
	}
}