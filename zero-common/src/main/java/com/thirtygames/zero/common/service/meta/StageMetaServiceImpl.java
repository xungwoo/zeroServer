package com.thirtygames.zero.common.service.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.StageMetaMapper;
import com.thirtygames.zero.common.model.meta.Stage;
import com.thirtygames.zero.common.model.meta.StageEnemy;

@Slf4j
@Service("stageMetaService")
public class StageMetaServiceImpl extends MetaServiceImpl<StageMetaMapper, Stage, String> implements StageMetaService  {
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Integer> getChapterList() {
		return mapper.selectChapterList();
	}

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public void upload(List<Stage> list) {
		for(Stage stage : list) {
			mapper.update(stage);
		}
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<StageEnemy> getStageEnemyList() {
		return mapper.getStageEnemyList();
	}	
}