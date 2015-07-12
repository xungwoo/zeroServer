package com.thirtygames.zero.common.service.admintool.user;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.admintool.UserResource;

public interface AdmResourceService extends GenericService<UserResource, String> {

	UserResource updateAddition(UserResource ar, boolean isReturnResource);
}