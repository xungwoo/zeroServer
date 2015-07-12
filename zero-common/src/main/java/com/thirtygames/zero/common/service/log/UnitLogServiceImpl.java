package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.UnitLogMapper;
import com.thirtygames.zero.common.model.log.UnitLog;

@Slf4j
@Service("unitLogService")
public class UnitLogServiceImpl extends LogServiceImpl<UnitLogMapper, UnitLog, Integer> implements UnitLogService {

}