package com.thirtygames.zero.common.service.equipment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.ResourceServiceImpl;
import com.thirtygames.zero.common.mapper.EquipmentMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.DeckInfo;
import com.thirtygames.zero.common.model.equipment.EquipLevelUpLog;
import com.thirtygames.zero.common.model.equipment.EquipLog;
import com.thirtygames.zero.common.model.equipment.EquipMergeLog;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipLevelMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.params.GemGradeUpParams;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.DeckInfoService;
import com.thirtygames.zero.common.service.UnitService;
import com.thirtygames.zero.common.service.equipment.meta.EquipLevelMetaService;
import com.thirtygames.zero.common.service.equipment.meta.EquipLevelUpMetaService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.log.EquipLevelUpLogService;
import com.thirtygames.zero.common.service.log.EquipMergeLogService;
import com.thirtygames.zero.common.service.log.GemLogService;

@Slf4j
@Service("equipmentService")
public class EquipmentServiceImpl extends ResourceServiceImpl<EquipmentMapper, Equipment, String> implements EquipmentService {
	public final static String COLUMN_DELIMITER = ":";
	public final static String EQUIP_DELIMITER = ",";
	
	@Autowired
	EquipMetaService emService;
	
	@Autowired
	EquipLevelUpLogService levelUpLogService;
	
	@Autowired
	EquipMergeLogService mergeLogService;
	
	@Autowired
	GemLogService gemLogService;
	
	@Autowired
	EquipLevelMetaService levelMetaService;	
	
	@Autowired
	EquipLevelUpMetaService levelUpMetaService;	
	
	@Autowired
	AccountResourceService arService;
	
	@Autowired
	UnitService unitService;
	
	@Autowired
	DeckInfoService deckInfoService;

	@Override
	@Transactional
	public EquipLevelUpLog levelUp(Equipment targetEq, List<Equipment> useEqList, long equipLevelUpDrug)  {
		EquipLevelMeta levelMeta = levelMetaService.getWithStatGrowth(targetEq.getEquipmentType()); 
		if (levelMeta == null) throw new RestException("Not.found.EquipLevelMeta.");
		
		log.debug("targetEq::" + targetEq);
		int level = targetEq.getLevel();
		int grade = targetEq.getGrade();
		int eqClass = targetEq.getEqClass();
		int exp = targetEq.getExp();
		int totalExp = targetEq.getTotalExp();
		int nextLevelExp = levelMeta.getNextLevelExp(grade, level, eqClass);
		// Transaction 유지를 위해 meta조회는 가장 먼저 처리!!
		// TODO 어지간하면 밖으로 뽑자
		boolean levelUpSuccess = levelUpMetaService.isLevelUpSuccess(grade, level, equipLevelUpDrug); 
		
		int levelUpCount = 0;
		long levelUpCost = 0;
		String usedEqListString = "";
		for (Equipment useEq : useEqList) {
			levelUpCost += levelMeta.getLevelUpCost(useEq.getGrade(), useEq.getLevel());
			int useExp = levelMeta.getConsumeExp(useEq.getGrade(), useEq.getLevel(), useEq.getEqClass());
			log.debug("useEqExp::" + useExp);
			exp += useExp; 
			totalExp += useExp; 
			
			usedEqListString += useEq.getEquipmentId() + COLUMN_DELIMITER + useEq.getEquipmentType() + COLUMN_DELIMITER + useEq.getEqClass() + COLUMN_DELIMITER + useEq.getGrade() + COLUMN_DELIMITER + useEq.getLevel() + COLUMN_DELIMITER + useExp + EQUIP_DELIMITER;
			mapper.delete(useEq.getEquipmentId());
		}			

		if (levelUpSuccess) {
			if (exp >= nextLevelExp && level < EquipLevelMeta.MAX_LEVEL) {
				exp = exp - nextLevelExp;
				level = level + 1;
				levelUpCount++;
				
				// levelUp에 소모된 경험치를 제외한 나머지 값에 대해 
				// 차상위 level에 한해서 확률과 무관하게 exp를 인정해준다. (최대 다음 level에 필요한 경험치값 - 1 까지)
				int next2LevelUpExp = levelMeta.getNextLevelExp(grade, level, eqClass);
				if (exp >= next2LevelUpExp) {
					exp = next2LevelUpExp - 1;
				}
			}
			
			Equipment levelUpEq = new Equipment();
			levelUpEq.setLevel(level);
			levelUpEq.setExp(exp);
			levelUpEq.setTotalExp(totalExp);
			levelUpEq.setEquipmentId(targetEq.getEquipmentId());
			mapper.update(levelUpEq);
		}
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(targetEq.getAccountId());
		ar.setGold(levelUpCost);	
		ar.setEquipLevelUpDrug(equipLevelUpDrug);
		arService.updateSubtraction(ar, false);				
		
		EquipLevelUpLog levelUpLog = new EquipLevelUpLog();
		levelUpLog.setAccountId(targetEq.getAccountId());
		levelUpLog.setEquipmentId(targetEq.getEquipmentId());
		levelUpLog.setLevelUpCount(levelUpCount);
		levelUpLog.setLevelUpSuccess(levelUpSuccess);
		String targetEqString = targetEq.getEquipmentType().toString() + COLUMN_DELIMITER + targetEq.getEqClass().toString() + COLUMN_DELIMITER + targetEq.getGrade().toString() + COLUMN_DELIMITER + targetEq.getLevel().toString();
		levelUpLog.setTargetEq(targetEqString);
		levelUpLog.setUsedEqList(usedEqListString);
		levelUpLog.setUsedGold(ar.getGold());
		levelUpLogService.save(levelUpLog);
		
		return levelUpLog;
	}
	
	@Override
	@Transactional
	public AccountResource sell(EquipmentMeta meta, Equipment eq)  {
		if (eq.getCategory() == Equipment.GEM_CATEGORY_CODE) {
			mapper.clearSocketByGemId(eq.getEquipmentId());
		}
		mapper.delete(eq.getEquipmentId());
		
		if (eq.getUnitId() != null) {
			DeckInfo deckInfo = new DeckInfo();
			deckInfo.setAccountId(eq.getAccountId());
			int eqPoint = unitService.getEquipmentPoint(eq.getAccountId());
			deckInfo.setEquipmentPoint(eqPoint);
			deckInfoService.update(deckInfo);			
		}
		
		EquipLog eqLog = new EquipLog();
		eqLog.setStatus(EquipLog.EquipLogStatus.Sell.getCode());
		saveEquipLog(eq, eqLog);

		AccountResource ar = new AccountResource();
		ar.setAccountId(eq.getAccountId());
		ar.setGold((long) meta.getSellPrice());

		return arService.updateAddition(ar, true);
	}
	
	@Override
	@Transactional
	public Integer sellMulti(String ids, String accountId)  {
		Iterable<String> equips = Splitter.on(',').trimResults().omitEmptyStrings().split(ids);
		if (!equips.iterator().hasNext()) throw new RestException("Invalid.Param");		
		
		int count = 0;
		long sumPrice = 0;
		for(String id : equips) {
			Equipment equip = this.get(id);
			if (equip == null) throw new RestException(Errors.NotFoundEquipment, "equipmentId:" + id);

			EquipmentMeta meta = emService.getEquipmentMeta(equip);		
			if (meta == null) throw new RestException(Errors.NotFoundEquipMeta, "equipmentId:" + id);
			sumPrice = sumPrice + meta.getSellPrice();

			if (equip.getCategory() == Equipment.GEM_CATEGORY_CODE) {
				mapper.clearSocketByGemId(equip.getEquipmentId());
			}
			mapper.delete(equip.getEquipmentId());
			
			EquipLog eqLog = new EquipLog();
			equip.setAccountId(accountId);
			eqLog.setStatus(EquipLog.EquipLogStatus.Sell.getCode());
			eqLog.setUsedEqList(ids);
			saveEquipLog(equip, eqLog);
			count++;
		}
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(accountId);
		ar.setGold(sumPrice);
		arService.updateAddition(ar, false);
		return count;
	}

	@Override
	@Transactional
	public Integer gemInstall(String equipmentId, String gemId, int socketNo)  {
		Equipment target = mapper.get(equipmentId);
		if (target == null)
			throw new RestException("Not.found.Equip id::" + equipmentId);
		if (target.getOpenSockets() <= 0)
			throw new RestException("No.Socket openSockets:: " + target.getOpenSockets());
		if (socketNo <= 0 || socketNo > target.getOpenSockets()) 
			throw new RestException("Invalid.SocketNo openSockets:: " + target.getOpenSockets());
		if (mapper.checkInstalledGem(gemId) == 1) 
			throw new RestException("Already.installed.Gem id:: " + gemId);

		Equipment gem = mapper.get(gemId);
		if (gem == null)
			throw new RestException("Not.found.Gem id::" + gemId);
		if (gem.getCategory() != Equipment.GEM_CATEGORY_CODE)
			throw new RestException("No.Gem category::" + gem.getCategory());
		
		EquipLog gemLog = new EquipLog();
		gemLog.setStatus(EquipLog.EquipLogStatus.Install.getCode());
		gemLog.setInstalledEquipId(equipmentId);
		gemLog.setInstalledSocketNo(socketNo);
		saveEquipLog(gem, gemLog);		

		return mapper.gemInstall(equipmentId, gemId, socketNo);
	}

	@Override
	@Transactional
	public Integer gemClear(String equipmentId, String gemId, int socketNo)  {
		Equipment target = mapper.get(equipmentId);
		if (target == null)
			throw new RestException("Not.found.Equip");
		if (socketNo <= 0 || socketNo > target.getOpenSockets()) {
			throw new RestException("Invalid.SocketNo. openSockets:: " + target.getOpenSockets());
		}
		
		Equipment gem = mapper.get(gemId);
		if (gem == null)
			throw new RestException("Not.found.Gem id::" + gemId);
		if (gem.getCategory() != Equipment.GEM_CATEGORY_CODE)
			throw new RestException("No.Gem category::" + gem.getCategory());		
		
		EquipLog gemLog = new EquipLog();
		gemLog.setStatus(EquipLog.EquipLogStatus.Clear.getCode());
		gemLog.setInstalledEquipId(equipmentId);
		gemLog.setInstalledSocketNo(socketNo);
		saveEquipLog(gem, gemLog);		
		
		return mapper.gemClear(equipmentId, socketNo);
	}

	@Override
	@Transactional
	public Equipment gemGradeUp(GemGradeUpParams params)  {
		List<String> usedEqList = new ArrayList<String>();
		
		Equipment gem1 = params.getGem1();
		int gem1Grade = gem1.getGrade();
		mapper.delete(params.getGem1Id());
		
		if(params.getGem2Id() != null) {
			usedEqList.add(params.getGem2Id());
			Equipment gem2 = mapper.get(params.getGem2Id());
			if (gem2 == null) throw new RestException("Not.found.Gem2");
			if (gem1Grade != gem2.getGrade()) throw new RestException("No.same.grade.Gem1.Gem2");
			mapper.delete(params.getGem2Id());
			
			EquipLog gemLog = new EquipLog();
			gemLog.setStatus(EquipLog.EquipLogStatus.MergeMaterial.getCode());
			saveEquipLog(gem2, gemLog);			
		}
		
		if(params.getGem3Id() != null) {
			usedEqList.add(params.getGem3Id());
			Equipment gem3 = mapper.get(params.getGem3Id());
			if (gem3 == null) throw new RestException("Not.found.Gem3");
			if (gem1Grade != gem3.getGrade()) throw new RestException("No.same.grade.Gem1.Gem3");
			mapper.delete(params.getGem3Id());

			EquipLog gemLog = new EquipLog();
			gemLog.setStatus(EquipLog.EquipLogStatus.MergeMaterial.getCode());
			saveEquipLog(gem3, gemLog);
		}
		
		Equipment gradeUpGem = params.getGradeUpGem();
		gradeUpGem.setEquipmentId(gem1.getEquipmentId());
		mapper.save(gradeUpGem);
		for(EquipmentStat stat : gradeUpGem.getStats()) {
			stat.setEquipmentId(gem1.getEquipmentId());
			mapper.saveStat(stat);
		}
		
		EquipLog gemLog = new EquipLog();
		gemLog.setStatus(EquipLog.EquipLogStatus.MergeAcquire.getCode());
		gemLog.setUsedEqList(Joiner.on(",").join(usedEqList));
		saveEquipLog(gradeUpGem, gemLog);

		AccountResource ar = new AccountResource();
		ar.setAccountId(params.getAccountId());
		ar.setGold(params.getUseGold());
		ar.setCash(params.getUseCash());

		arService.updateSubtraction(ar, false);
		gradeUpGem.setGenYmdt(System.currentTimeMillis()/1000);
		return gradeUpGem;
	}

	@Override
	@Transactional
	public void multiAdd(List<Equipment> equipList)  {
		for (Equipment equip : equipList) {
			mapper.save(equip);
			for (EquipmentStat stat : equip.getStats()) {
				mapper.saveStat(stat);
			}
			
			EquipLog eqLog = new EquipLog();
			eqLog.setStatus(EquipLog.EquipLogStatus.Acquire.getCode());
			saveEquipLog(equip, eqLog);
		}
	}
	
	@Override
	@Transactional
	public int save(Equipment equip)  {
		int resultCount = mapper.save(equip);
		for (EquipmentStat stat : equip.getStats()) {
			mapper.saveStat(stat);
		}
		
		EquipLog eqLog = new EquipLog();
		eqLog.setStatus(EquipLog.EquipLogStatus.Acquire.getCode());
		saveEquipLog(equip, eqLog);		
		return resultCount;
	}

	private void saveEquipLog(Equipment equip, EquipLog gemLog)  {
		log.debug("##GemLog equip::" + equip);
		log.debug("##GemLog gemLog::" + gemLog);
		gemLog.setEquipmentId(equip.getEquipmentId());
		gemLog.setEquipmentType(equip.getEquipmentType());
		gemLog.setAccountId(equip.getAccountId());
		gemLog.setSubCategory(equip.getSubCategory());
		gemLog.setGrade(equip.getGrade());
		gemLogService.save(gemLog);
	}

	@Override
	@Transactional
	public void addMergedEquipByResource(Equipment equip, Equipment eq1, Equipment eq2, AccountResource ar)  {
		mapper.save(equip);
		for (EquipmentStat stat : equip.getStats()) {
			mapper.saveStat(stat);
		}
		
		mapper.delete(eq1.getEquipmentId());
		mapper.delete(eq2.getEquipmentId());
		
		EquipMergeLog mergeLog = new EquipMergeLog();
		mergeLog.setAccountId(equip.getAccountId());
		mergeLog.setEquipmentId(equip.getEquipmentId());
		mergeLog.setMergedEqClass(equip.getEqClass());
		mergeLog.setMergedEqGrade(equip.getGrade());
		String usedEqList = eq1.getEquipmentId() + ":" + eq1.getEquipmentType() + ":" + eq1.getEqClass().toString() + ":" + eq1.getGrade().toString() + ","; 
		usedEqList += eq2.getEquipmentId() + ":" + eq2.getEquipmentType() + ":" + eq2.getEqClass().toString() + ":" + eq2.getGrade().toString();
		mergeLog.setUsedEqList(usedEqList);
		mergeLog.setUsedGold(ar.getGold());		
		mergeLogService.save(mergeLog);
		
		if (ar.getGold() > 0 || ar.getCash() > 0) { 
			arService.updateSubtraction(ar, false);
		}		
	}
	

	@Override
	public List<EquipmentStat> getStatList(String accountId) {
		return mapper.getStatList(accountId);
	}

	@Override
	@Transactional
	public Integer equipSwitch(Equipment eq, String removeId) {
		int result = 0;
		if (!Strings.isNullOrEmpty(eq.getEquipmentId())) {
			result += mapper.update(eq);
		}

		if (!Strings.isNullOrEmpty(removeId)) {
			eq.setEquipmentId(removeId);
			eq.setUnitId("");
			result += mapper.update(eq);
		}
		
		DeckInfo deckInfo = new DeckInfo();
		deckInfo.setAccountId(eq.getAccountId());
		int eqPoint = unitService.getEquipmentPoint(eq.getAccountId());
		deckInfo.setEquipmentPoint(eqPoint);
		deckInfoService.update(deckInfo);
		return result;
	}

	@Override
	public Map<String, Equipment> getEquipList(String accountId) {
		return mapper.getEquipList(accountId);
	}

	@Override
	public List<Equipment> getUseEquipments(List<String> eqIdList) {
		return mapper.getUseEquipments(eqIdList);
	}

	@Override
	public Equipment getInstalledGem(String equipmentId) {
		return mapper.getInstalledGem(equipmentId);
	}
}
