package com.thirtygames.zero.common.mapper;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.StageClear;
import com.thirtygames.zero.common.model.meta.Stage;

public interface StageMapper extends GenericMapper<Stage, String> {

	public int saveStageClear(StageClear stageClear);

	public List<StageClear> getClearStepList(String accountId);

	public StageClear getMaxClearStage(String accountId);

	public int deleteAllStageClear(String accountId);
}