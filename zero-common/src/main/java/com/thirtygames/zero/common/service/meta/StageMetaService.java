package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.meta.StageEnemy;

public interface StageMetaService extends MetaService<Stage, String> {

	public List<Integer> getChapterList();
	public void upload(List<Stage> list);
	public List<StageEnemy> getStageEnemyList();
}