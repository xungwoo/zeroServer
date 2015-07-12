package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.AccountMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Service("accountDynamicService")
public class AccountDynamicServiceImpl extends
		GenericServiceImpl<AccountMapper, Account, String> implements
		AccountDynamicService {
	
	@Autowired
	DataSourceService dsManager;
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public void updateRemoveFacebookId(String facebookId) {
		mapper.updateRemoveFacebookId(facebookId);
	}	
	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public Account get(String accountId) {
		return  mapper.get(accountId);
	}
	
	
}