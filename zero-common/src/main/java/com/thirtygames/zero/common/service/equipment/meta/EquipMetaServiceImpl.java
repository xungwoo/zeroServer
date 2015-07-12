package com.thirtygames.zero.common.service.equipment.meta;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.EquipmentUtil;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.EquipMergeMetaMapper;
import com.thirtygames.zero.common.mapper.meta.EquipMetaMapper;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.equipment.EquipmentStat;
import com.thirtygames.zero.common.model.equipment.meta.EquipChoiceDecoMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipGachaRateMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipMergeMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatType;
import com.thirtygames.zero.common.model.equipment.meta.EquipType;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta.EqClass;
import com.thirtygames.zero.common.model.meta.ShopItem;

@Slf4j
@Service("equipMetaService")
public class EquipMetaServiceImpl extends MetaServiceImpl<EquipMetaMapper, EquipmentMeta, String> implements EquipMetaService {
	
	@Autowired
	EquipMergeMetaMapper emmMapper;

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipType> getEquipCategoryTypeList() {
		return mapper.getEquipCategoryList();
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipType> getEquipTypeList(Integer subCategory) {
		return mapper.getEquipTypeList(subCategory);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Integer> getGemEquipTypeList(Integer grade) {
		return mapper.getGemEquipTypeList(grade);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Integer> getNoGemEquipTypeList(Integer grade, Integer eqClass) {
		return mapper.getNoGemEquipTypeList(grade, eqClass);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipType> getEquipSubCategoryTypeList(int category) {
		return mapper.getEquipSubCategoryList(category);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipStatMeta> getAdminStatMetaList(String sidx, String sord, EquipStatMeta equip) {
		return mapper.getAdminStatMetaList(sidx, sord, equip);
	}
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipStatType> getStatTypeList() {
		return mapper.getStatTypeList();
	}
	
	
	
	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public EquipmentMeta getEquipmentMeta(Equipment equip) {
		return mapper.getEquipmentMeta(equip);
	}

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int saveStat(EquipStatMeta statMeta)  {
		return mapper.saveStat(statMeta);
	}

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int updateStat(EquipStatMeta statMeta) {
		return mapper.updateStat(statMeta);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public void deleteStat(String id) {
		mapper.deleteStat(id);
	}

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public EquipChoiceDecoMeta getChoiceDecoMeta() {
		EquipChoiceDecoMeta meta = mapper.getChoiceDecoMeta();
		return meta;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public Equipment generateEquip(Equipment equip, boolean isDecoRateWeight)  {
		equip.setEquipmentId(UUID.randomUUID().toString());
		equip.setCount(Equipment.INIT_COUNT);
		equip.setLevel(Equipment.INIT_LEVEL);
		equip.setExp(Equipment.INIT_EXP);

		EquipmentMeta meta = mapper.getEquipmentMeta(equip);
		if (meta == null)
			throw new RestException(Errors.NotFoundEquipMeta, " equipmentType::" + equip.getEquipmentType() + ":grade:" + equip.getGrade());
		log.debug("## EquipGen Step1. getMeta::" + meta);

		equip.setGrade(meta.getGrade());
		equip.setMaxSockets(meta.getMaxSockets());
		equip.setOpenSockets(meta.getOpenSockets(meta.getMaxSockets()));
		equip.setCategory(meta.getCategory());
		equip.setSubCategory(meta.getSubCategory());

		List<EquipmentStat> statList = new ArrayList<EquipmentStat>();
		statList.addAll(this.getBaseStatList(equip.getEquipmentId(), meta.getEquipMetaKey(), equip.getAccountId()));
		
		int eqClass = meta.getEqClass();
		List<EquipmentStat> decoStatList = this.getDecoStatList(equip, meta, equip.getAccountId(), isDecoRateWeight);
		if (decoStatList.size() > 0) {
			statList.addAll(decoStatList);
			this.setDecoInf(equip, decoStatList);
			// if Normal, Deco exist then Magic
			if (EqClass.Normal.getCode() == 0) {
				eqClass = EqClass.Magic.getCode();
			}
		}
		
		equip.setEqClass(eqClass);
		equip.setStats(statList);
		
		Timestamp currentTS = new Timestamp(new java.util.Date().getTime());
		equip.setGenYmdt(currentTS.getTime() / 1000);
		return equip;
	}

	public void setDecoInf(Equipment equip, List<EquipmentStat> decoStatList) {
		EquipmentStat eqStat1 = decoStatList.get(0);
		if (eqStat1 != null && eqStat1.getDecoCode() != null && !Strings.isNullOrEmpty(eqStat1.getDecoCode().toString())) {
			equip.setDecoration1(eqStat1.getDecoCode());
		}
		
		int decoStatListSize = decoStatList.size();
		if (decoStatListSize > 1) {
			EquipmentStat eqStat2 = decoStatList.get(decoStatListSize - 1);
			if (eqStat2 != null && eqStat2.getDecoCode() != null && !Strings.isNullOrEmpty(eqStat2.getDecoCode().toString())) {
				if (eqStat1.getDecoCode() != eqStat2.getDecoCode()) {
					equip.setDecoration2(eqStat2.getDecoCode());
				}
			}			
		}
	}
	
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	private List<EquipmentStat> getBaseStatList(String equipmentId, Integer equipMetaKey, String accountId)  {
		log.debug("## EquipGen Step2. BaseStat");

		List<EquipStatMeta> statMetaList = mapper.getStatMetaList(equipMetaKey);
		log.debug("## EquipGen Step2-1. baseStatMetaList::" + statMetaList);
		if (statMetaList == null || statMetaList.size() == 0)
			throw new RestException("Not.found.Equipment.BaseStatMeta");

		return setEquipStatList(accountId, equipmentId, statMetaList);
	}

	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	private List<EquipmentStat> getDecoStatList(Equipment equip, EquipmentMeta meta, String accountId, boolean isDecoRateWeight) {
		log.debug("## EquipGen Step3. DecoStat");
		String equipmentId = equip.getEquipmentId();

		EquipChoiceDecoMeta choiceMeta = mapper.getChoiceDecoMeta();
		int targetStatNumber = EquipmentUtil.getTargetDecoStatNumber(choiceMeta.getMaxSize(), choiceMeta.getRateList(), isDecoRateWeight);

		Map<Integer, Integer> gradeMap = new HashMap<Integer, Integer>();
		for (int i = 0; i < targetStatNumber; i++) {
			Integer grade = EquipmentUtil.getDecoStatGradeByRatio(meta);
			if (grade > 0) {
				int eachGradeCount = 1;
				if (gradeMap.containsKey(grade)) {
					eachGradeCount = gradeMap.get(grade) + 1;
				}
				gradeMap.put(grade, eachGradeCount);
			}
		}

		Integer exceptType = null;
		List<EquipStatMeta> equipStatMetaList = new ArrayList<EquipStatMeta>();
		for (Entry<Integer, Integer> gradeEntry : gradeMap.entrySet()) {
			List<EquipStatMeta> decoStatMetaList = mapper.getDecoStatMetaList(gradeEntry.getKey(), exceptType);
			List<EquipStatMeta> resultList = EquipmentUtil.getRandomIndexListByNumber(decoStatMetaList, gradeEntry.getValue());
			for (EquipStatMeta decoStat : resultList) {
				exceptType = decoStat.getStatType();
			}
			equipStatMetaList.addAll(resultList);
		}
		
		return setEquipStatList(accountId, equipmentId, equipStatMetaList);
	}

	private List<EquipmentStat> setEquipStatList(String accountId, String equipmentId, List<EquipStatMeta> equipStatMetaList) {
		List<EquipmentStat> resultList = new ArrayList<EquipmentStat>();
		for (EquipStatMeta esm : equipStatMetaList) {
			Random r = new Random();
			if (esm.getRate() == null || r.nextFloat() <= esm.getRate()) {
				EquipmentStat es = new EquipmentStat();
				es.setEquipmentId(equipmentId);
				es.setAccountId(accountId);
				
				float min[] = getRandomStat(esm.getMinRange1(), esm.getMinRange2());
				es.setMin(min[0]);
				es.setMinRandom(min[1]);
				
				float max[] = getRandomStat(esm.getMaxRange1(), esm.getMaxRange2());
				es.setMax(max[0]);				
				es.setMaxRandom(max[1]);
				
				float statRateValue = 0f;
				float rateGausValue = 0f;
				if (esm.getDurationMin() > 0 || esm.getDurationMax() > 0) {
					float statRate[] = getRandomStat(esm.getStatRateMin(), esm.getStatRateMax());
					statRateValue = statRate[0];
					rateGausValue = statRate[1];
				}
				es.setStatRate(statRateValue);
				es.setRateRandom(rateGausValue);
				
				float durationValue = 0f;
				float durGausValue = 0f;
				if (esm.getDurationMin() > 0 || esm.getDurationMax() > 0) {
					float duration[] = getRandomStat(esm.getDurationMin(), esm.getDurationMax());
					durationValue = duration[0];
					durGausValue = duration[1];				
				}
				es.setDuration(durationValue);				
				es.setDurRandom(durGausValue);
	
				es.setOnlyFor(esm.getOnlyFor());
				es.setSetStat(esm.getSetStat()==null ? 0 : esm.getSetStat());
				es.setBaseStat(esm.getBaseStat()==null ? 0 : esm.getBaseStat());
				es.setType(esm.getStatType());
				es.setDecoCode(esm.getDecoCode());
				es.setGemStatType(esm.getGemStatType());
				resultList.add(es);
			}
		}
		return resultList;
	}

	private float[] getRandomStat(float min, float max) {
		float gaussianRandom = 0f;
		float stat = min;
		if (min != max) {
			gaussianRandom = EquipmentUtil.getGaussianRandom();
			stat = EquipmentUtil.getStdevValue(min, max, gaussianRandom);
		}
		
		float result[] = { stat, gaussianRandom };
		return result;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int getMergedEquipmentClass(int eqClass1, int eqClass2)  {
		// rate MEta
		EquipMergeMeta mergeMeta = emmMapper.getEquipMergeMeta(eqClass1, eqClass2);
		if (mergeMeta == null) throw new RestException("Not.found.Merge.Meta.");

		int randomClass = 0;	// default Normal
		Random r = new Random();
		if (r.nextFloat() <= mergeMeta.getMagicRate()) {
			randomClass = 1;
		} else if (r.nextFloat() <= mergeMeta.getRareRate()) {
			randomClass = 2;
		} else if (r.nextFloat() <= mergeMeta.getSetRate()) {
			randomClass = 3;
		} else if (r.nextFloat() <= mergeMeta.getUniqueRate()) {
			randomClass = 4;
		}
		
		return randomClass;
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int getMergedEquipmentType(int mergedGrade, int mergedClass, int mergedCategory) {
		return mapper.getRandomMergedEquipmentType(mergedGrade, mergedClass, mergedCategory);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public List<EquipType> getGemStatTypeList() {
		return mapper.getGemStatTypeList();
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public Integer getGemGradeUpEquipType(Integer subCategory, Integer grade) {
		return mapper.getGemGradeUpEquipType(subCategory, grade);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<EquipGachaRateMeta> getEquipGachaRateMetaList(int itemKey, int rateType) {
		return mapper.getEquipGachaRateMetaList(itemKey, rateType);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Equipment> generateGachaEquips(String accountId, ShopItem itemMeta)  {
		List<Equipment> eqList = new ArrayList<Equipment>();
		long eqCount = itemMeta.getItemQuantity();
		boolean specicalGrade6 = false;
		if (eqCount >= 5) {
			eqCount = eqCount - 1;
			specicalGrade6 = true;
		}
		
		for (long i = 0; i < eqCount; i++) {
			Equipment equip = new Equipment();
			equip.setAccountId(accountId);

			List<EquipGachaRateMeta> gradeRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 0);
			if (gradeRateList.isEmpty()) {
				throw new RestException("Not.found.EquipGachaRateMeta gradeRateList itemKey:" + itemMeta.getItemKey());
			}

			int grade = EquipmentUtil.getRandomItem(gradeRateList);
			equip.setGrade(grade);

			List<EquipGachaRateMeta> classRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 1);
			if (classRateList.isEmpty()) {
				throw new RestException("Not.found.EquipGachaRateMeta classRateList itemKey:" + itemMeta.getItemKey());
			}
			int eqClass = EquipmentUtil.getRandomItem(classRateList);
			equip.setEqClass(eqClass);
			
			Integer category = null;
			List<EquipGachaRateMeta> categoryRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 2);
			if (!categoryRateList.isEmpty()) {
				category = EquipmentUtil.getRandomItem(categoryRateList);
				equip.setCategory(category);			
			}

			List<Integer> equipTypeList = mapper.getNoGemEquipTypeList(grade, eqClass, category);
			while(equipTypeList.isEmpty() && eqClass>1) {
				eqClass = eqClass - 1;
				equipTypeList = mapper.getNoGemEquipTypeList(grade, eqClass, category);
			}
			log.debug("equipTypeList::" + equipTypeList);
			int randomType = EquipmentUtil.getRandomType(equipTypeList);
			equip.setEquipmentType(randomType);

			equip = this.generateEquip(equip, false);
			eqList.add(equip);
		}
		
		// 6성 고정이 마지막에 나오도록
		if (specicalGrade6) {
			eqList.add(genSpecialGradeEquip(6, accountId, itemMeta));
		}
		return eqList;
	}

	private Equipment genSpecialGradeEquip(int grade, String accountId, ShopItem itemMeta) {
		Equipment equip = new Equipment();
		equip.setAccountId(accountId);
		equip.setGrade(grade);
		
		List<EquipGachaRateMeta> classRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 1);
		if (classRateList.isEmpty()) {
			throw new RestException("Not.found.EquipGachaRateMeta classRateList itemKey:" + itemMeta.getItemKey());
		}
		int eqClass = EquipmentUtil.getRandomItem(classRateList);
		equip.setEqClass(eqClass);
		
		Integer category = null;
		List<EquipGachaRateMeta> categoryRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 2);
		if (!categoryRateList.isEmpty()) {
			category = EquipmentUtil.getRandomItem(categoryRateList);
			equip.setCategory(category);			
		}		

		List<Integer> equipTypeList = mapper.getNoGemEquipTypeList(grade, eqClass, category);
		while(equipTypeList.isEmpty() && eqClass>1) {
			eqClass = eqClass - 1;
			equipTypeList = mapper.getNoGemEquipTypeList(grade, eqClass, category);
		}
		int randomType = EquipmentUtil.getRandomType(equipTypeList);
		equip.setEquipmentType(randomType);

		equip = this.generateEquip(equip, false);
		return equip;
	}	
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Equipment> generateGachaGems(String accountId, ShopItem itemMeta)  {
		List<Equipment> gemList = new ArrayList<Equipment>();
		long gemCount = itemMeta.getItemQuantity();
		for (long i = 0; i < gemCount; i++) {
			Equipment equip = new Equipment();
			equip.setAccountId(accountId);

			List<EquipGachaRateMeta> gradeRateList = mapper.getEquipGachaRateMetaList(itemMeta.getItemKey(), 0);
			if (gradeRateList.isEmpty()) {
				throw new RestException("Not.found.EquipGachaRateMeta gradeRateList itemKey:" + itemMeta.getItemKey());
			}

			int grade = EquipmentUtil.getRandomItem(gradeRateList);
			equip.setGrade(grade);

			List<Integer> equipTypeList = new ArrayList<Integer>();
			equipTypeList = mapper.getGemEquipTypeList(grade);

			int randomType = EquipmentUtil.getRandomType(equipTypeList);
			equip.setEquipmentType(randomType);

			equip = this.generateEquip(equip, false);
			gemList.add(equip);
		}
		
		return gemList;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int getBossRaidEquipReward(int subCategory, int gradeRatio, int bossUnitLevel) {
		int grade = (int) Math.ceil(bossUnitLevel / gradeRatio) + 1;
		if (grade < 2) {
			grade = 2;
		}
		
		if (grade > Equipment.EQUIP_MAX_GRADE) {
			grade = Equipment.EQUIP_MAX_GRADE;
		}
		return mapper.getBossRaidEquipReward(subCategory, grade);
	}	
	
}
