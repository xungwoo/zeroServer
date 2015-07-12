package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.EquipLevelUpLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipLevelUpLog;

@Slf4j
@Service("equipLevelUpLogService")
public class EquipLevelUpLogServiceImpl extends LogServiceImpl<EquipLevelUpLogMapper, EquipLevelUpLog, Integer> implements EquipLevelUpLogService {

}