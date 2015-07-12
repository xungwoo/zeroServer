package com.thirtygames.zero.common.etc.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import lombok.extern.slf4j.Slf4j;

import com.thirtygames.zero.common.model.equipment.meta.EquipGachaRateMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipStatMeta;
import com.thirtygames.zero.common.model.equipment.meta.EquipmentMeta;

/**
 * @author x
 * 
 *         -1 ~ +1 범위를 갖는 유니폼 분포를 보이는 두 난수 x1, x2가 주어질 때, 가우시안 분포의 랜덤 변수 y1, y2를
 *         구하려면,
 * 
 *         y1 = sqrt( - 2 * log(x1) ) * cos( 2 * M_PI * x2 ) y2 = sqrt( - 2 *
 *         log(x1) ) * sin( 2 * M_PI * x2 )
 * 
 *         와 같은 변환을 거치면 됩니다. 이때, y1, y2는 평균 0, 표준편차 1인 가우시안 분포를 보입니다.
 * 
 *         즉, double x1 = 2 * (double)rand() / RAND_MAX - 1; double x2 = 2 *
 *         (double)rand() / RAND_MAX - 1; y1 = sqrt( - 2 * log(x1) ) * cos( 2 *
 *         M_PI * x2 ) y2 = sqrt( - 2 * log(x1) ) * sin( 2 * M_PI * x2 )
 * 
 *         와 같은 꼴이 됩니다. 이때, x1, x2는 -1 ~ +1의 범위에서 유니폼 디스트리뷰션을 보이는 랜덤 변수이며, y1,
 *         y2는 평균 0, 표준편차1인 가우시안 디스트리뷰션을 보이는 랜덤변수가 됩니다.
 * 
 *         위와 같은 변환은 6번의 수학함수 호출이 필요하므로, 위의 계산과 동등한, 다음과 같은 극좌표 형식으로 계산하는 것이
 *         간편합니다.
 * 
 *         double x1, x2, w, y1, y2;
 * 
 *         do { x1 = 2 * (double)rand() / RAND_MAX - 1; x2 = 2 * (double)rand()
 *         / RAND_MAX - 1; w = x1 * x1 + x2 * x2; } while ( w >= 1.0 );
 * 
 *         w = sqrt( (-2 * log( w ) ) / w ); y1 = x1 * w; y2 = x2 * w;
 * 
 */
@Slf4j
public class EquipmentUtil {
	/**
	 * 시그마 법칙 : http://ko.wikipedia.org/wiki/68-95-99.7_%EA%B7%9C%EC%B9%99 장비생성
	 * 시 능력치 분포 조절을 위해 조정가능.
	 */
	private static final int SIGMA = 4;

	public static float getStdevValue(float min, float max, float gaussianRandom) {
		float average = (min + max) / 2;
		float stdev = (max - min) / 2 / SIGMA;
		float result = (stdev * gaussianRandom) + average;
		result = Math.round(result * 10000f) / 10000f;
		return result;
	}

	public static float getGaussianRandom() {
		float v1, v2;
		double s;

		Random r = new Random();
		do {
			v1 = 2 * r.nextFloat() - 1;
			v2 = 2 * r.nextFloat() - 1;
			s = v1 * v1 + v2 * v2;
		} while (s >= 1 || s == 0);
		s = Math.sqrt((-2 * Math.log(s)) / s);
		return (float) (v1 * s);
	}

	// Stat Meta 변경시 기발급된 장비에 대해서 보정을 위해 사용한다.
	public static float getUpdatedStat(float min, float max, float savedRandom) {
		float average = (min + max) / 2;
		float stdev = (max - min) / 2 / SIGMA;

		float stat = (stdev * savedRandom) + average;
		stat = Math.round(stat * 10000f) / 10000f;
		return stat;
	}

	public static boolean isRandomByRate(float rate) {
		Random r = new Random();
		return (rate >= r.nextFloat()) ? true : false;
	}

	// sourceList(전체 decoStat List preset)로 부터 Number수만큼 실제 부여될 Stat을 추출 한다.
	public static List<EquipStatMeta> getRandomIndexListByNumber(List<EquipStatMeta> sourceList, int number) {
		List<EquipStatMeta> resultList = new ArrayList<EquipStatMeta>();
		int sourceListSize = sourceList.size();
		if (sourceListSize > 0 && number > 0) {
			int totalNumber = 0;
			for (EquipStatMeta source : sourceList) {
				totalNumber += source.getWeight();
			}

			Random r = new Random();
			int randomNumber = r.nextInt(totalNumber);
			int totalWeightNumber = 0;

			for (int j = 0; j < number; j++) {
				Iterator<EquipStatMeta> it = sourceList.iterator();
				while (it.hasNext()) {
					EquipStatMeta source = it.next();
					int weight = source.getWeight();
					totalWeightNumber += weight;
					if (weight > 0 && totalWeightNumber > randomNumber) {
						resultList.add(source);
						sourceList.remove(source);
						break;
					}
				}
			}
		}

		return resultList;
	}

	// DB EquipChoiceDecoStatMeta 테이블에 정해진 rate 값을 기반으로 targetStatNumber 값을
	// 결정한다.
	public static int getTargetDecoStatNumber(int maxSize, List<Float> rateList, boolean isDecoRateWeight) {
		double random = Math.ceil(Math.random() * 1000) / 1000;
		if (isDecoRateWeight) {
			random += rateList.get(0);	// Deco 없을 확률 0
		}
		int targetStatNumber = 0;
		float rate = 0;
		for (int i = 0; i <= maxSize; i++) {
			rate += rateList.get(i);
			if (random < rate) {
				targetStatNumber = i;
				break;
			}
		}
		return targetStatNumber;
	}

	// 정해진 비중치에 따라 장식속성의 등급을 구한다.
	public static int getDecoStatGradeByRatio(EquipmentMeta meta) {
		Integer resultGrade = 0;
		Integer grade1 = meta.getDeco1Grade();
		if (grade1 != null && grade1 > 0) {
			resultGrade = grade1;
			float rate1 = meta.getDeco1Rate();
			if (rate1 < 1) {
				Integer grade2 = meta.getDeco2Grade();
				if (grade2 != null && grade2 > 0 && meta.getDeco2Rate() > 0) {
					Random r = new Random();
					if (r.nextFloat() > rate1) {
						resultGrade = grade2;
					}
				}
			}
		}
		return resultGrade;
	}
	
	
	public static int getRandomItem(List<EquipGachaRateMeta> rateList) {
		int item = 0;	// grade or eqClass
		float rate = 0;
		Random r = new Random();
		float random = r.nextFloat();
		log.debug("random::" + random);
		for(EquipGachaRateMeta rateMeta : rateList) {
			rate += rateMeta.getRate();
			if (random < rate) {
				item = rateMeta.getValue();
				break;
			}
		}
		return item;
	}

	public static int getRandomType(List<Integer> equipTypeList) {
		int size = equipTypeList.size();
		
		Random rand = new Random();
		int randomNum = rand.nextInt(size);
		return equipTypeList.get(randomNum); 
	}
}