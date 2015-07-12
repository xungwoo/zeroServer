package com.thirtygames.zero.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import org.junit.Assert;
import org.junit.Test;

import com.thirtygames.zero.common.etc.util.EquipmentUtil;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;

@Slf4j
public class StatUtilTest {

	@Test
	public void GaussianRandomTest() {
		// 실제 분포를 확인하기 위한 테스트들.
		// for (int i = 0; i < 100; i++) {
		// // log.debug(String.valueOf(Math.random()));
		// }
		// for (int i = 0; i < 10000; i++) {
		// // log.debug(String.valueOf(StatUtil.gaussianRandom(0, 10)));
		// }
		// for (double i = 0.01; i < 1; i = i + 0.01) {
		// // log.debug(String.valueOf(StatUtil.getInvCDF(i, false)));
		// }
	}

	@Test
	public void randomStatTest() {
		int count = 0;
		for (int i = 0; i < 100000; i++) {
			float min = (float) 10.1234;
			float max = (float) 20.1234;
			float stat = EquipmentUtil.getStdevValue(min, max, EquipmentUtil.getGaussianRandom());
			 log.debug("index:" + i + "::min:" + min + "::max:" + max + "::stat:" + stat);

			if (min > stat || max < stat)
				count++;
		}
		log.debug("count::" + count);
		Assert.assertTrue(count < 20);
	}

	@Test
	public void rateGenerateStatTest() {
		for (int i = 0; i < 100000; i++) {
			int generateNumber = (int) (Math.random() * 10) + 1;
			Assert.assertTrue(generateNumber > 0 && generateNumber <= 10);
			log.debug("Math.random" + generateNumber);
		}
	}

	@Test
	public void getTargetDecoStatNumberTest1() {
		List<Float> rateList = new ArrayList<Float>();
		rateList.add((float) 1.0);
		rateList.add((float) 0.0);
		rateList.add((float) 0.0);

		for (int i = 0; i < 100; i++) {
			int targetNumber = EquipmentUtil.getTargetDecoStatNumber(2, rateList, false)
					;
			Assert.assertEquals(0, targetNumber);
		}
	}

	@Test
	public void getTargetDecoStatNumberTest2() {
		List<Float> rateList = new ArrayList<Float>();
		rateList.add((float) 0.9);
		rateList.add((float) 0.1);
		rateList.add((float) 0.0);

		int zeroCount = 0;
		int oneCount = 0;
		int twoCount = 0;
		for (int i = 0; i < 10000; i++) {
			int targetNumber = EquipmentUtil.getTargetDecoStatNumber(2, rateList, false);
			switch (targetNumber) {
			case 0:
				zeroCount++;
				break;
			case 1:
				oneCount++;
				break;
			case 2:
				twoCount++;
				break;
			}
		}

		log.debug("count::" + zeroCount + ":" + oneCount + ":" + twoCount);
		Assert.assertEquals(twoCount, 0);
		Assert.assertTrue(zeroCount > oneCount);
	}

	@Test
	public void getTargetDecoStatNumberTest3() {
		List<Float> rateList = new ArrayList<Float>();
		rateList.add((float) 0.4);
		rateList.add((float) 0.35);
		rateList.add((float) 0.25);

		int zeroCount = 0;
		int oneCount = 0;
		int twoCount = 0;
		for (int i = 0; i < 10000; i++) {
			int targetNumber = EquipmentUtil.getTargetDecoStatNumber(2, rateList, false);
			switch (targetNumber) {
			case 0:
				zeroCount++;
				break;
			case 1:
				oneCount++;
				break;
			case 2:
				twoCount++;
				break;
			}
		}

		log.debug("count::" + zeroCount + ":" + oneCount + ":" + twoCount);
		Assert.assertTrue(zeroCount > oneCount);
		Assert.assertTrue(oneCount > twoCount);
	}

	@Test
	public void getRandomIndexListByNumberTest() {
		int targetDecoNumber = 2;
		List<EquipStatMeta> sourceDecoStatMetaList = new ArrayList<EquipStatMeta>();
		EquipStatMeta esm = new EquipStatMeta();
		esm.setWeight(10);
		sourceDecoStatMetaList.add(esm);
		sourceDecoStatMetaList.add(esm);
		sourceDecoStatMetaList.add(esm);
		sourceDecoStatMetaList.add(esm);
		List<EquipStatMeta> resultList = EquipmentUtil.getRandomIndexListByNumber(sourceDecoStatMetaList, targetDecoNumber);
		log.debug("count::" + sourceDecoStatMetaList.size() + ":" + resultList.size());
		Assert.assertTrue(resultList.size() == targetDecoNumber);
	}

	@Test
	public void getDecoStatGradeByRatio() {
		EquipmentMeta meta1 = new EquipmentMeta();
		meta1.setDeco1Grade(1);
		meta1.setDeco1Rate(1f);
		Assert.assertEquals(EquipmentUtil.getDecoStatGradeByRatio(meta1), 1);

		final int grade1 = 1;
		final int grade2 = 2;
		final float rate1 = 0.44f;
		final float rate2 = 0.56f;
		EquipmentMeta meta2 = new EquipmentMeta();
		meta2.setDeco1Grade(grade1);
		meta2.setDeco1Rate(rate1);
		meta2.setDeco2Grade(grade2);
		meta2.setDeco2Rate(rate2);
		int[] count = { 0, 0 };
		for (int i = 0; i < 10000; i++) {
			int targetNumber = EquipmentUtil.getDecoStatGradeByRatio(meta2);
			switch (targetNumber) {
			case grade1:
				count[0]++;
				break;
			case grade2:
				count[1]++;
				break;
			}
		}
		Assert.assertTrue(count[0] < count[1]);
		log.debug("count:" + Float.toString(rate1) + " :" + count[0] + " " + Float.toString(rate2) + " :" + count[1]);

	}
	
	@Test
	public void randomStatNomalize() {
		
		float average = (1 + 10) / 2;
		float stdev = (10 - 1) / 2 / 4;
		
		float v1, v2, temp;
		float s;
		Random r = new Random();
		do {
			float n1 = r.nextFloat();
			v1 = 2 * n1 - 1;
			log.debug("Rand 1::" + n1);
			
			float n2 = r.nextFloat();
			v2 = 2 * n2 - 1;
			log.debug("Rand 2::" + n2);
			s = v1 * v1 + v2 * v2;
		} while (s >= 1 || s == 0);
		s = (float) Math.sqrt((-2 * Math.log(s)) / s);
		temp = v1 * s;
		temp = (stdev * temp) + average;
		log.debug("Rand 3::" + temp);
		float result = Math.round(temp * 10000f) / 10000f;
		
		log.debug("Rand 4::" + result);
		
		//0.376212482065692
		//0.12781555828123647
		//4.689

	}

}
