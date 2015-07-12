package com.thirtygames.zero.common.model.log;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

import com.thirtygames.zero.common.model.params.grid.GridFilter;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageLog extends GridFilter {

	Integer logKey;
	String accountId;
	String stageKey;
	String deck;
	Integer playTime;
	Integer clearMode;
	Integer clearStep;
	Float clearProgress;
	Long gainGold;
	String gainEquipments;
	List<String> gainEquipmentList;
	Boolean isWin;
	String logYmdt;
	
	List<String> unitTypeList;
}
