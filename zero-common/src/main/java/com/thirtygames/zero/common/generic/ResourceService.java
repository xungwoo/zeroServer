package com.thirtygames.zero.common.generic;

import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.model.AccountResource;

public interface ResourceService<T, K> extends GenericService<T, K> {
	
	
	/**
	 * Resource only
	 * @param ar
	 * @return
	 * @throws RestException
	 */
	public AccountResource updateAddition(AccountResource ar, boolean isReturnResource) ;

	/**
	 * Resource only
	 * @param ar
	 * @return
	 * @throws RestException
	 */
	public AccountResource updateSubtraction(AccountResource ar, boolean isReturnResource) ;
	
	
	
	// Update By Resource
	public AccountResource updateByResource(T entity, AccountResource ar) ;

}
