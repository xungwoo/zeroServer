package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmStageLogMapper;
import com.thirtygames.zero.common.model.log.StageLog;

@Service("adminStageLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmStageLogServiceImpl extends AdminLogServiceImpl<AdmStageLogMapper, StageLog, String> implements AdmStageLogService  {

}