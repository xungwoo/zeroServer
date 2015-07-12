package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.admintool.AdminFriend;

public interface AdmFriendService extends GenericService<AdminFriend, String> {
	public List<AdminFriend> getFriendList(String accountId);
	public List<AdminFriend> getFriendMemberNoList(String accountId);
	public List<AdminFriend> getFriendListByMemberNoList(List<AdminFriend> memberNoList);
}