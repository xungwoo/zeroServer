package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmFriendMapper;
import com.thirtygames.zero.common.model.admintool.AdminFriend;

@Service("adminFriendService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmFriendServiceImpl extends AdminServiceImpl<AdmFriendMapper, AdminFriend, String> implements AdmFriendService  {
	
	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int delete(String id) {
		return mapper.delete(id);
	}


	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<AdminFriend> getFriendList(String accountId) {
		return mapper.getFriendList(accountId);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<AdminFriend> getFriendMemberNoList(String accountId) {
		return mapper.getFriendMemberNoList(accountId);
	}

	@Override
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<AdminFriend> getFriendListByMemberNoList(List<AdminFriend> memberNoList) {
		return mapper.getFriendListByMemberNoList(memberNoList);
	}
	
	
}