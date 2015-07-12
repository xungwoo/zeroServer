package com.thirtygames.zero.common.service.equipment;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.junit.Ignore;
import org.junit.Test;

import com.thirtygames.zero.common.etc.util.EquipmentUtil;
import com.thirtygames.zero.common.model.equipment.meta.EquipGachaRateMeta;

@Slf4j
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "/test-dispatcher-servlet.xml" })
public class EquipmentServiceImplTest {

//	@Autowired
//	EquipmentService eqService;
//
//	@Autowired
//	EquipMetaService metaService;
//
//	@Autowired
//	EquipUpdateService updateService;
//	
//	@Autowired
//	EquipLevelMetaService levelMetaService;
	
//	@Autowired
//	EquipLevelUpMetaService levelUpMetaService;
//	
//	@Ignore
//	@Test
//	public void EquipStatUpdateTest() {
//		DataSource[] dataSourceType = { DataSource.ZERO_GAME_1 };
//		int equpimentType[] = { 7011, 4012 };
//		int resultCount = 0;
//
//		List<EquipmentStat> resultList = new ArrayList<EquipmentStat>();
//		
//		for (int type : equpimentType) {
//			List<EquipStatUpdate> statMetaList = updateService.getUpdatedStatMeta(type);
//			log.debug("statMetaList:::" + statMetaList);
//
//			for (DataSource dst : dataSourceType) {
//				ContextHolder.setDataSource(dst);
//				
//
//
//				List<EquipStatUpdate> updateList = new ArrayList<EquipStatUpdate>();
//				for (EquipStatUpdate stat : statMetaList) {
//					List<EquipStatUpdate> targetList = updateService.getTargetStatList(stat.getEquipmentType());
//					log.debug("targetList:::" + targetList);
//					for (EquipStatUpdate target : targetList) {
////							log.debug("## target::" + target.getGrade());
////							log.debug("## stat::" + stat.getGrade());
////							log.debug("## stat::" + stat.getMinRange1());
////							log.debug("## stat::" + stat.getMinRange2());
////							log.debug("## stat::" + stat.getMaxRange1());
////							log.debug("## stat::" + stat.getMaxRange2());
////							log.debug("## stat::" + stat.getStatRateMin());
////							log.debug("## stat::" + stat.getStatRateMax());
////							log.debug("## stat::" + stat.getDurationMin());
////							log.debug("## stat::" + stat.getDurationMax());
//							EquipStatUpdate result = new EquipStatUpdate();
//							result.setAccountId(target.getAccountId());
//							result.setEquipmentId(target.getEquipmentId());
//							result.setStatKey(target.getStatKey());
//							result.setType(target.getType());
//							result.setMinRandom(target.getMinRandom());
//							result.setMaxRandom(target.getMaxRandom());
//							result.setRateRandom(target.getRateRandom());
//							result.setDurRandom(target.getDurRandom());
//							result.setMinRange1(stat.getMinRange1());
//							result.setMinRange2(stat.getMinRange2());
//							result.setMaxRange1(stat.getMaxRange1());
//							result.setMaxRange2(stat.getMaxRange2());
//							result.setStatRateMin(stat.getStatRateMin());
//							result.setStatRateMax(stat.getStatRateMax());
//							result.setDurationMin(stat.getDurationMin());
//							result.setDurationMax(stat.getDurationMax());
//
//							updateList.add(result);
//					}
//				}
//
//				resultList.addAll(this.setEquipStatList(updateList));
//				for (EquipmentStat es : resultList) {
//					log.debug("Eq STat:::" + es);
//					resultCount += updateService.updateEquipStat(es);
//				}
////				log.debug("############ DataSource:::" + dst);
////				log.debug("############ resultList:::" + resultList);
//				log.debug("############ resultCount:::" + resultCount);				
//			}
//		}
//
//	}	
//
//	@Ignore
//	@Test
//	public void EquipDecoStatUpdateTest() {
//		DataSource[] dataSourceType = { DataSource.ZERO_GAME_1 };
//		int statType[] = { 13, 32, 37, 45 };
//		int resultCount = 0;
//
//		List<EquipmentStat> resultList = new ArrayList<EquipmentStat>();
//		
//		for (int type : statType) {
//			List<EquipStatUpdate> decoStatMetaList = updateService.getUpdatedDecoStatMeta(type);
//			log.debug("decoStatMetaList:::" + decoStatMetaList);
//
//			for (DataSource dst : dataSourceType) {
//				ContextHolder.setDataSource(dst);
//
//				List<EquipStatUpdate> targetList = updateService.getTargetDecoStatList(type);
//				log.debug("targetList:::" + targetList);
//
//				List<EquipStatUpdate> updateList = new ArrayList<EquipStatUpdate>();
//				for (EquipStatUpdate target : targetList) {
//					for (EquipStatUpdate deco : decoStatMetaList) {
//						if (target.getGrade() == deco.getGrade()) {
////							log.debug("## target::" + target.getGrade());
////							log.debug("## deco::" + deco.getGrade());
////							log.debug("## deco::" + deco.getMinRange1());
////							log.debug("## deco::" + deco.getMinRange2());
////							log.debug("## deco::" + deco.getMaxRange1());
////							log.debug("## deco::" + deco.getMaxRange2());
////							log.debug("## deco::" + deco.getStatRateMin());
////							log.debug("## deco::" + deco.getStatRateMax());
////							log.debug("## deco::" + deco.getDurationMin());
////							log.debug("## deco::" + deco.getDurationMax());
//							EquipStatUpdate result = new EquipStatUpdate();
//							result.setAccountId(target.getAccountId());
//							result.setEquipmentId(target.getEquipmentId());
//							result.setStatKey(target.getStatKey());
//							result.setType(target.getType());
//							result.setMinRandom(target.getMinRandom());
//							result.setMaxRandom(target.getMaxRandom());
//							result.setRateRandom(target.getRateRandom());
//							result.setDurRandom(target.getDurRandom());
//							result.setMinRange1(deco.getMinRange1());
//							result.setMinRange2(deco.getMinRange2());
//							result.setMaxRange1(deco.getMaxRange1());
//							result.setMaxRange2(deco.getMaxRange2());
//							result.setStatRateMin(deco.getStatRateMin());
//							result.setStatRateMax(deco.getStatRateMax());
//							result.setDurationMin(deco.getDurationMin());
//							result.setDurationMax(deco.getDurationMax());
//
//							updateList.add(result);
//						}
//					}
//				}
//
//				resultList.addAll(this.setEquipStatList(updateList));
//				for (EquipmentStat es : resultList) {
//					log.debug("Eq STat:::" + es);
//					//resultCount += updateService.updateEquipStat(es);
//				}
//				log.debug("############ DataSource:::" + dst);
//				log.debug("############ resultList:::" + resultList);
//				log.debug("############ resultCount:::" + resultCount);				
//			}
//		}
//
//	}
//
//	private List<EquipmentStat> setEquipStatList(List<EquipStatUpdate> equipStatUpdateList) {
//		List<EquipmentStat> resultList = new ArrayList<EquipmentStat>();
//		for (EquipStatUpdate esu : equipStatUpdateList) {
//			EquipmentStat es = new EquipmentStat();
//			es.setEquipmentId(esu.getEquipmentId());
//			es.setAccountId(esu.getAccountId());
//
//			float min[] = getRandomStat(esu.getMinRange1(), esu.getMinRange2(), esu.getMinRandom());
//			es.setMin(min[0]);
//			es.setMinRandom(min[1]);
//
//			float max[] = getRandomStat(esu.getMaxRange1(), esu.getMaxRange2(), esu.getMaxRandom());
//			es.setMax(max[0]);
//			es.setMaxRandom(max[1]);
//
//			float statRateValue = 0f;
//			float rateGausValue = 0f;
//			if (esu.getStatRateMin() > 0 || esu.getStatRateMax() > 0) {
//				float statRate[] = getRandomStat(esu.getStatRateMin(), esu.getStatRateMax(), esu.getRateRandom());
//				statRateValue = statRate[0];
//				rateGausValue = statRate[1];
//			}
//			es.setStatRate(statRateValue);
//			es.setRateRandom(rateGausValue);
//
//			float durationValue = 0f;
//			float durGausValue = 0f;
//			if (esu.getDurationMin() > 0 || esu.getDurationMax() > 0) {
//				float duration[] = getRandomStat(esu.getDurationMin(), esu.getDurationMax(), esu.getDurRandom());
//				durationValue = duration[0];
//				durGausValue = duration[1];
//			}
//			es.setDuration(durationValue);
//			es.setDurRandom(durGausValue);
//
//			es.setSetStat(esu.getSetStat() == null ? 0 : esu.getSetStat());
//			es.setBaseStat(esu.getBaseStat() == null ? 0 : esu.getBaseStat());
//			es.setType(esu.getType());
//			//es.setDecoCode(esu.getDecoCode());
//			resultList.add(es);
//		}
//		return resultList;
//	}
//
//	private float[] getRandomStat(float min, float max, float savedRandom) {
//		// float gaussianRandom = 0f;
//		float stat = min;
//		if (min != max) {
//			// gaussianRandom = StatUtil.getGaussianRandom();
//			stat = EquipmentUtil.getUpdatedStat(min, max, savedRandom);
//		}
//
//		float result[] = { stat, savedRandom };
//		return result;
//	}
//	
//	@Ignore
//	@Test
//	public void powTest() {
//	    int SIZE = 1000000;
//
//	    int[] arr1 = new int[SIZE];
//	    long st1, end1, st2, end2, st3, end3;
//
//	    st1 = System.currentTimeMillis();
//	    for (int i = 0; i < SIZE; i++) {
//	        arr1[i] = (int) Math.pow(i, 100);
//	    }
//	    end1 = System.currentTimeMillis();
//	    log.debug("pow: " + (end1 - st1));
//
//	    arr1 = new int[SIZE];
//	    st2 = System.currentTimeMillis();
//	    for (int i = 0; i < SIZE; i++) {
//	    	for(int j=0; j<100; j++) {
//	    		arr1[i] = i * i;
//	    	}
//	    }
//	    end2 = System.currentTimeMillis();
//	    log.debug("mul: " + (end2 - st2));
//
//	    arr1 = new int[SIZE];
//	    st3 = System.currentTimeMillis();
//	    for (int i = 0; i < SIZE; i++) {
//	        arr1[i] = i^100;
//	    }
//	    end3 = System.currentTimeMillis();
//	    log.debug("  ^: " + (end3 - st3));		
//	}
//	
//	@Ignore
//	@Test
//	public void levelUpTest() {
//		int levels[] = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
//		int grades[] = { 1, 2, 3, 4, 5, 6 };
//		int eqClasses[] = { 0, 1, 2, 3, 4 };
//		
//		EquipLevelMeta levelMeta = levelMetaService.get(2016);
//		log.debug("Class/Grade/level/consumeExp::");
//		for (int eqClass : eqClasses) {
//			for (int grade : grades) {
//				for (int level : levels) {
//					int useEqNextLevelExp = levelMeta.getNextLevelExp(grade, level, eqClass);
//					int useExp = (int) (useEqNextLevelExp * levelMeta.getConsumeExpRate());
//					log.debug(eqClass + "," + grade + "," + level + "," + useExp);
//				}
//			}
//		}
//		
//		Assert.assertEquals(true, true);
//	}
//	
//	@Ignore
//	@Test
//	public void mergeTest()  {
//		int eqClasses1[] = { 0, 1, 2, 3, 4 };
//		int eqClasses2[] = { 0, 1, 2, 3, 4 };
//		int eachClassCount[] = { 0, 0, 0, 0, 0 };
//		for (int eqClass1 : eqClasses1) {
//			for (int eqClass2 : eqClasses2) {
//				if (eqClass2 >= eqClass1) {
//					for (int i=0; i<=100000; i++) {
//						int mergedGrade = 1;
//						int mergedClass = metaService.getMergedEquipmentClass(eqClass1, eqClass2);
//						int mergedEquipmentType = metaService.getMergedEquipmentType(mergedGrade, mergedClass);
//						
//						Equipment equip = new Equipment();
//						equip.setAccountId("xman");
//						equip.setEquipmentType(mergedEquipmentType); 
//						equip.setGrade(mergedGrade);
//						equip = metaService.generateEquip(equip, false);			
//						
//						eachClassCount[equip.getEqClass()]++;
//					}
//					
//					log.debug("Count::" + eachClassCount[0] + "," + eachClassCount[1] + "," + eachClassCount[2] + "," + eachClassCount[3] + "," + eachClassCount[4] + ",");	
//					for (int j=0; j<=4; j++) {
//						eachClassCount[j] = 0;	
//					}					
//				}
//			}
//		}
//		
//
//		
//		Assert.assertEquals(true, true);
//	}
	
	@Ignore
	@Test
	public void gachaRateTest() {
		int result[] = {0, 0, 0, 0, 0};
		List<EquipGachaRateMeta> classRateList = new ArrayList<EquipGachaRateMeta>();
		EquipGachaRateMeta meta0 = new EquipGachaRateMeta();
		meta0.setRate(4.0f);
		meta0.setRateType(1);
		meta0.setValue(0);
		classRateList.add(meta0);
		EquipGachaRateMeta meta2 = new EquipGachaRateMeta();
		meta2.setRate(4.5f);
		meta2.setRateType(1);
		meta2.setValue(2);
		classRateList.add(meta2);
		EquipGachaRateMeta meta3 = new EquipGachaRateMeta();
		meta3.setRate(1.0f);
		meta3.setRateType(1);
		meta3.setValue(3);
		classRateList.add(meta3);
		EquipGachaRateMeta meta4 = new EquipGachaRateMeta();
		meta4.setRate(0.5f);
		meta4.setRateType(1);
		meta4.setValue(4);
		classRateList.add(meta4);
		
		System.out.println("classRateList::" + classRateList);		
		
		for (int i=0; i<=1000; i++) {
		
			int eqClass = EquipmentUtil.getRandomItem(classRateList);
			switch(eqClass) {
				case 0: result[0]++;break;
				case 1: result[1]++;break;
				case 2: result[2]++;break;
				case 3: result[3]++;break;
				case 4: result[4]++;break;
				default:break;
			}
		}
		
		
		System.out.println("eqClass::" + result[0]);
		System.out.println("eqClass::" + result[1]);
		System.out.println("eqClass::" + result[2]);
		System.out.println("eqClass::" + result[3]);
		System.out.println("eqClass::" + result[4]);
		
		
	}
	
//	@Ignore
//	@Test
//	public void levelUpTest()  {
//		
//		int successCount = 0;
//		for (int i=0; i<=1000; i++) {
//			boolean levelUpSuccess = levelUpMetaService.isLevelUpSuccess(3, 8, 0);
//			if (levelUpSuccess) successCount++;
//		}
//
//		
//		Assert.assertEquals(true, successCount > 400);
//	}	
	
}
