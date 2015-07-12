package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmEquipMergeLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipMergeLog;

@Service("admEquipMergeLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmEquipMergeLogServiceImpl extends AdminLogServiceImpl<AdmEquipMergeLogMapper, EquipMergeLog, String> implements AdmEquipMergeLogService {
	
}
