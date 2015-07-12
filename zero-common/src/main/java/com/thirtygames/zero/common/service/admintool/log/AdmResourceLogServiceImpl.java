package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmResourceLogMapper;
import com.thirtygames.zero.common.model.log.ResourceLog;

@Service("admResourceLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmResourceLogServiceImpl extends AdminLogServiceImpl<AdmResourceLogMapper, ResourceLog, String> implements AdmResourceLogService {
	
}
