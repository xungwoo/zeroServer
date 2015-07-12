package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.ResourceServiceImpl;
import com.thirtygames.zero.common.mapper.ResourceMapper;
import com.thirtygames.zero.common.model.AccountResource;

@Slf4j
@Service("resourceService")
public class AccountResourceServiceImpl extends ResourceServiceImpl<ResourceMapper, AccountResource, String> implements AccountResourceService  {

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public AccountResource updateAddition(AccountResource ar, boolean hasReturn) {
		return super.updateAddition(ar, hasReturn);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED)
	public AccountResource updateSubtraction(AccountResource ar, boolean hasReturn) {
		return super.updateSubtraction(ar, hasReturn);
	}
	
	

}