package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.meta.StageEnemy;

public interface StageMetaMapper extends GenericMapper<Stage, String> {

	public List<Integer> selectChapterList();

	public List<StageEnemy> getStageEnemyList();	
}