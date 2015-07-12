package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.CheatLogMapper;
import com.thirtygames.zero.common.model.log.CheatLog;

@Slf4j
@Service("cheatLogService")
public class CheatLogServiceImpl extends LogServiceImpl<CheatLogMapper, CheatLog, Long> implements CheatLogService {

}