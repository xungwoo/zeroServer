package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmRewardLogMapper;
import com.thirtygames.zero.common.model.admintool.AdminRewardLog;

@Service("admRewardLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmRewardLogServiceImpl extends AdminLogServiceImpl<AdmRewardLogMapper, AdminRewardLog, String> implements AdmRewardLogService {
	
}
