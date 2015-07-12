package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmEquipLevelUpLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipLevelUpLog;

@Service("admEquipLevelUpLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmEquipLevelUpLogServiceImpl extends AdminLogServiceImpl<AdmEquipLevelUpLogMapper, EquipLevelUpLog, String> implements AdmEquipLevelUpLogService {
	
}
