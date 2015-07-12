package com.thirtygames.zero.common.model.equipment;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Slf4j
@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class EquipmentVirtual extends GridFilter {
	private static final long serialVersionUID = -777214562053897190L;

	// 장비 생성 초기값
	public static final int INIT_COUNT = 1;
	public static final int INIT_LEVEL = 1;
	public static final int INIT_EXP = 0;
	
	public static final int GEM_CATEGORY_CODE = 6;

	String equipmentId;
	Integer equipmentType;
	
	Integer category;
	Integer subCategory;	// client : category
	
	Integer decoration1;
	Integer decoration2;

	String accountId;
	String unitId;
	Integer equipPosition;

	Integer grade;
	Integer level;
	Integer exp;
	Float statGrowth;	// statLevelUp 시 증가율

	Integer count;

	Integer openSockets;
	Integer maxSockets;
	String socket1; // GemDiamond
	String socket2;
	String socket3;
	
	String image;
	String equipName;

	List<EquipmentStat> stats = new ArrayList<EquipmentStat>();

	Integer genYmdt;

	public void setSocketByPosition(String gemEquipmentId) {
		this.socket1 = gemEquipmentId;
	}
	
	public void increaseCount() {
		this.count++;
	}
	
	public void decreaseCount() {
		this.count--;
	}
}
