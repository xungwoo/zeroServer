package com.thirtygames.zero.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.StageMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.StageClear;
import com.thirtygames.zero.common.model.StageParam;
import com.thirtygames.zero.common.model.log.StageLog;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.qatool.StageClearTool;
import com.thirtygames.zero.common.service.log.StageLogService;

@Slf4j
@Service("stageService")
public class StageServiceImpl extends GenericServiceImpl<StageMapper, Stage, String> implements StageService {

	@Autowired
	AccountResourceService arService;
	
	@Autowired
	AccountService accountService;	
	
	@Autowired
	StageLogService stageLogService;
	
	/* (non-Javadoc)
	 * @see com.thirtygames.zero.common.service.StageService#saveStageClearDynamic(com.thirtygames.zero.common.model.StageClear)
	 * AdminTool
	 */
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int saveStageClearDynamic(StageClearTool stageClearTool) {
		int resultCount = 0;
		StageClear stageClear = new StageClear();
		stageClear.setAccountId(stageClearTool.getAccountId());
		mapper.deleteAllStageClear(stageClearTool.getAccountId());

		int clearMode = stageClearTool.getClearMode();
		int chapter = stageClearTool.getChapter();
		int stage = stageClearTool.getStage();
		int maxChapter = 4;
		int maxStage = 10;
		int startClearMode = 1000;
		if (clearMode > startClearMode) {
			startClearMode = clearMode - 1000;
		}
		
		for (int k = startClearMode; k <= clearMode; k=k+1000) {
			if (k == clearMode) {
				maxChapter = chapter;
			}
			for (int i = 1; i <= maxChapter; i++) {
				if (i == chapter) {
					maxStage = stage;
				}
				for (int j = 1; j <= maxStage; j++) {
					stageClear.setClearMode(k);
					stageClear.setChapter(i);
					stageClear.setStage(j);
					stageClear.setStageKey(StageClear.makeStageKey(i, j));
					stageClear.setClearStep(1);
					stageClear.setClearProgress(1f);
					stageClear.setExposedScenes(0);				
					resultCount += mapper.saveStageClear(stageClear);
				}
			}
		}
		
		return resultCount;
	}

	@Override
	public int saveStageClear(StageClear stageClear) {
		return mapper.saveStageClear(stageClear);
	}

	@Override
	@Transactional
	public Map<String, Object> stageResult(StageParam stageParam)  {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		if ((stageParam.getGainGold() != null && stageParam.getGainGold() > 0) || (stageParam.getDropUnlockKey() != null && stageParam.getDropUnlockKey() > 0)) {
			AccountResource ar = new AccountResource();
			ar.setAccountId(stageParam.getAccountId());
			ar.setGold(stageParam.getGainGold().longValue());
			if (stageParam.getDropUnlockKey() != null && stageParam.getDropUnlockKey() > 0) ar.setUnlockKey(stageParam.getDropUnlockKey());
			arService.updateAddition(ar, false);
		}
		
		StageClear stageClear = new StageClear();
		if (stageParam.getIsWin() || StageClear.StageClearMode.Easy.getCode() == stageParam.getClearMode()) {
			boolean isBreakRecord = this.isBreakStageClearRecord(stageParam.getAccountId(), stageParam.getStageKey(), stageParam.getClearMode());
			if (isBreakRecord) {
				Account account = new Account();
				account.setAccountId(stageParam.getAccountId());
				account.setMaxClearStage(stageParam.getStageKey());
				account.setMaxClearMode(stageParam.getClearMode());
				accountService.update(account);
			}	
			resultMap.put("isBreakRecord", isBreakRecord);		
	
			stageClear.setAccountId(stageParam.getAccountId());
			stageClear.setStageKey(stageParam.getStageKey());
			stageClear.setClearMode(stageParam.getClearMode());
			stageClear.setClearStep(stageParam.getClearStep());
			stageClear.setClearProgress();
			stageClear.setExposedScenes(stageParam.getExposedScenes());
			mapper.saveStageClear(stageClear);
		}

		StageLog stageLog = new StageLog();
		stageLog.setClearStep(stageClear.getClearStep());
		stageLog.setClearProgress(stageClear.getClearProgress());
		stageLog.setClearMode(stageParam.getClearMode());
		stageLog.setAccountId(stageParam.getAccountId());
		stageLog.setStageKey(stageParam.getStageKey());
		stageLog.setDeck(stageParam.getDeck());
		stageLog.setGainGold(stageParam.getGainGold());
		stageLog.setGainEquipments(stageParam.getGainEquipments());
		stageLog.setIsWin(stageParam.getIsWin());
		stageLog.setPlayTime(stageParam.getPlayTime());

		if (stageLogService.save(stageLog) > 0) {
			resultMap.put("stageLog", stageLog);
		}
		
		return resultMap;
	}

	public boolean isBreakStageClearRecord(String myAccountId, String stageKey, int clearMode)  {
		StageClear maxStageClear = this.getMaxClearStage(myAccountId);
		if (maxStageClear == null) {
			return true;
		}
		
		log.debug("maxStageClear::" + maxStageClear);
		boolean isBreakRecord = false;
		int maxChapter = maxStageClear.getChapter();
		int maxStage = maxStageClear.getStage();
		int maxClearMode = maxStageClear.getClearMode();

		List<String> stageKeySplitList = Splitter.on("_").splitToList(stageKey);
		if (stageKeySplitList.size() != 2) {
			throw new RestException("Invalid.stageKey.format");
		}
		int clearChapter = Integer.parseInt(stageKeySplitList.get(0));
		int clearStage = Integer.parseInt(stageKeySplitList.get(1));
		log.debug("clear:::" + clearChapter + "::" + clearStage + "::" + clearMode);
		
		if (clearChapter > 100) {
			return false;
		}

		if ((clearMode >= maxClearMode) && ((clearChapter > maxChapter) || (clearChapter == maxChapter && clearStage > maxStage))) {
			isBreakRecord = true;
		}
		
		return isBreakRecord;
	}

	@Override
	public List<StageClear> getClearStepList(String accountId) {
		return mapper.getClearStepList(accountId);
	}

	@Override
	public StageClear getMaxClearStage(String accountId) {
		return mapper.getMaxClearStage(accountId);
	}

}