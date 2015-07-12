package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.EquipMergeLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipMergeLog;

@Slf4j
@Service("equipMergeLogService")
public class EquipMergeLogServiceImpl extends LogServiceImpl<EquipMergeLogMapper, EquipMergeLog, Integer> implements EquipMergeLogService {

}