package com.thirtygames.zero.common.model.equipment;

import java.io.Serializable;
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
public class Equipment extends GridFilter implements Serializable {
	private static final long serialVersionUID = -777214562053897190L;

	// 장비 생성 초기값
	public static final int INIT_COUNT = 1;
	public static final int INIT_LEVEL = 1;
	public static final int INIT_EXP = 0;

	public static final int GEM_CATEGORY_CODE = 6;
	public static final int GEM_MAX_GRADE = 7;
	public static final int EQUIP_MAX_GRADE = 6;

	String equipmentId;
	Integer equipmentType;

	Integer category;
	Integer subCategory; // client : category

	Integer decoration1;
	Integer decoration2;

	String accountId;
	String unitId;
	Integer equipPosition;

	Integer eqClass;
	Integer grade;
	Integer level;
	Integer exp;
	Integer totalExp;
	Integer count;

	Integer openSockets;
	Integer maxSockets;
	String socket1;
	String socket2;
	String socket3;

	List<EquipmentStat> stats = new ArrayList<EquipmentStat>();

	Long genYmdt;

	public void setSocket(String equipmentId, int socketNo) {
		switch (socketNo) {
		case 1:
			this.socket1 = equipmentId;
			break;
		case 2:
			this.socket2 = equipmentId;
			break;
		case 3:
			this.socket3 = equipmentId;
			break;
		default:
			break;
		}
	}
}
