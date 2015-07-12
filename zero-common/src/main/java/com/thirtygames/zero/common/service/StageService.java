package com.thirtygames.zero.common.service;

import java.util.List;
import java.util.Map;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.StageClear;
import com.thirtygames.zero.common.model.StageParam;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.qatool.StageClearTool;

public interface StageService extends GenericService<Stage, String> {
	
	public Map<String, Object> stageResult(StageParam stageParam) ;
	public List<StageClear> getClearStepList(String myAccountId);
	public StageClear getMaxClearStage(String myAccountId);
	public boolean isBreakStageClearRecord(String accountId, String stageKey, int clearMode) ;
	public int saveStageClear(StageClear stageClear);
	public int saveStageClearDynamic(StageClearTool stageClearTool);
	
}