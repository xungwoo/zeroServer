package com.thirtygames.zero.common.model;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class StageParam implements Serializable {
	private static final long serialVersionUID = -2327562855104395572L;

	String accountId;
	String stageKey;
	String deck;
	Integer playTime;
	
	Integer clearMode;
	Integer clearStep;
	Float clearProgress;
	Integer exposedScenes;
	Long gainGold;
	Long dropUnlockKey;
	String gainEquipments;
	Boolean isWin;
	
}
