package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmAccountMapper;
import com.thirtygames.zero.common.mapper.admintool.AdmResourceMapper;
import com.thirtygames.zero.common.model.admintool.AdminLog;
import com.thirtygames.zero.common.model.admintool.AdminLog.AdminLogType;
import com.thirtygames.zero.common.model.admintool.UserAccount;
import com.thirtygames.zero.common.service.admintool.log.AdmAdminLogService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Service("adminAccountService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmAccountServiceImpl extends AdminServiceImpl<AdmAccountMapper, UserAccount, String> implements AdmAccountService  {

	@Autowired
	AdmAdminLogService adminLogService;
	
	@Autowired
	DataSourceService dsService;
	
	@Autowired
	AdmResourceMapper resourceMapper;
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int update(UserAccount entity) {
		AdminLog adminLog = new AdminLog();
		adminLog.setMemo(entity.getMemo());
		adminLog.setDataKey(entity.getAccountId());
		adminLog.setModId("SYSTEM");
		adminLog.setType(AdminLogType.ACCOUNT.getCode());
		
		dsService.switchDataSource(entity.getAccountId());
		adminLogService.save(adminLog);
		return mapper.update(entity);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public void resetAuthTokenValidYmdt(String accountId) {
		mapper.resetAuthTokenValidYmdt(accountId);
	}
	
}