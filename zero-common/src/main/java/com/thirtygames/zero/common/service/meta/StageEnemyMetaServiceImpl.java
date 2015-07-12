package com.thirtygames.zero.common.service.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.StageEnemyMetaMapper;
import com.thirtygames.zero.common.model.meta.StageEnemy;

@Slf4j
@Service("stageEnemyMetaService")
public class StageEnemyMetaServiceImpl extends MetaServiceImpl<StageEnemyMetaMapper, StageEnemy, Integer> implements StageEnemyMetaService  {

}