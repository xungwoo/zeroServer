package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmUnitLogMapper;
import com.thirtygames.zero.common.model.log.UnitLog;

@Service("adminUnitLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmUnitLogServiceImpl extends AdminLogServiceImpl<AdmUnitLogMapper, UnitLog, Integer> implements AdmUnitLogService {

}
