package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.ResourceService;
import com.thirtygames.zero.common.model.AccountResource;

/**
 * @author xungwoo
 * 
 * Resource 정보관리
 * update 보다는 가급적 예외처리가 포함된 updateAddition, updateSubtracion을 사용한다.
 * 
 */
public interface AccountResourceService extends ResourceService<AccountResource, String> {

	public AccountResource updateAddition(AccountResource resource, boolean hasReturnResource) ;
	public AccountResource updateSubtraction(AccountResource resource, boolean hasReturnResource) ;

}