package com.thirtygames.zero.common.model.equipment.meta;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.fasterxml.jackson.annotation.JsonInclude;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipChoiceDecoMeta implements Serializable {
	private static final long serialVersionUID = 5867667720905133320L;

	Integer metaKey;
	Integer maxSize;  // 아래 모든 확률의 합은 1 / 아래 확률등 중 의미있는 값은 maxSize 이하
	Float zero;
	Float one;  // 1개 Stat이 부여될 확률
	Float two;
	Float three; 
	Float four; 
	Float five; 
	
	List<Float> rateList = new ArrayList<Float>();
	
	public List<Float> getRateList() {
		this.rateList.add(zero);
		this.rateList.add(one);
		this.rateList.add(two);
		this.rateList.add(three);
		this.rateList.add(four);
		this.rateList.add(five);
		return rateList;
	}
}
