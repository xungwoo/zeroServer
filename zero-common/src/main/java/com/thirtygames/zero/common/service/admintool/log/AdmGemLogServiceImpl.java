package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmGemLogMapper;
import com.thirtygames.zero.common.model.equipment.EquipLog;

@Service("adminGemLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmGemLogServiceImpl extends AdminLogServiceImpl<AdmGemLogMapper, EquipLog, Integer> implements AdmGemLogService {

}
