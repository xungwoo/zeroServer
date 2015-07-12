package com.thirtygames.zero.common.service.admintool.security;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.security.AdminUserMapper;
import com.thirtygames.zero.common.model.security.AdminUser;


@Slf4j
@Service("adminUserDetailsService")
public class AdminUserDetailsServiceImpl extends GenericServiceImpl<AdminUserMapper, AdminUser, String> implements UserDetailsService  {
	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_INDEX)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("loadUserByUsername username:::" + username);
		AdminUser user = mapper.get(username);
		
		log.debug("loadUserByUsername user:::" + user);
		return user;
	}

}

