package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.StageLogMapper;
import com.thirtygames.zero.common.model.log.StageLog;

@Slf4j
@Service("stageLogService")
public class StageLogServiceImpl extends LogServiceImpl<StageLogMapper, StageLog, Integer> implements StageLogService {

}