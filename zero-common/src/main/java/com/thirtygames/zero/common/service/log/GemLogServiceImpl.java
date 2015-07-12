package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.GemLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipLog;

@Slf4j
@Service("gemLogService")
public class GemLogServiceImpl extends LogServiceImpl<GemLogMapper, EquipLog, Integer> implements GemLogService {

}