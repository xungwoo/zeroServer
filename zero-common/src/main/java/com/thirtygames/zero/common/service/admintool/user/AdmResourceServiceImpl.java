package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mysql.jdbc.MysqlDataTruncation;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmResourceMapper;
import com.thirtygames.zero.common.model.admintool.UserResource;
import com.thirtygames.zero.common.model.log.ResourceLog;
import com.thirtygames.zero.common.service.log.ResourceLogService;

@Service("adminResourceService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmResourceServiceImpl extends AdminServiceImpl<AdmResourceMapper, UserResource, String> implements AdmResourceService  {

	@Autowired
	ResourceLogService resourceLogService;
	
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public UserResource updateAddition(UserResource ar, boolean isReturnResource) {
		try {
			mapper.updateAddition(ar);
		} catch (MysqlDataTruncation e) {
			throw new RestException("Fail.resource.update.addition. errorMessage:" + e.getMessage());
		}
		
		this.saveLog(ar, ResourceLog.Status.Addition);
		return (isReturnResource) ? mapper.get(ar.getAccountId()) : null;
	}	
	

	private void saveLog(UserResource ar, ResourceLog.Status status) {
		ResourceLog log = new ResourceLog();
		log.makeLog(ar);
		log.setAccountId(ar.getAccountId());
		log.setStatus(status.getCode());
		resourceLogService.save(log);
	}		
}