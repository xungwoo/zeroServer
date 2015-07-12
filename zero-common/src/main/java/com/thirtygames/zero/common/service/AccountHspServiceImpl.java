package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.AccountHspMapper;
import com.thirtygames.zero.common.model.Account;

@Slf4j
@Service("accountHspService")
public class AccountHspServiceImpl extends GenericServiceImpl<AccountHspMapper, Account, Long> implements AccountHspService {
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public Account get(Long memberNo) {
		return  mapper.get(memberNo);
	}
	

	
}