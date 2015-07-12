package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmAdminLogMapper;
import com.thirtygames.zero.common.model.admintool.AdminLog;

@Service("admAdminLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmAdminLogServiceImpl extends AdminLogServiceImpl<AdmAdminLogMapper, AdminLog, String> implements AdmAdminLogService {
	
}
