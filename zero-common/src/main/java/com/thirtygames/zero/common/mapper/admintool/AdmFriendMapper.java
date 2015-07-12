package com.thirtygames.zero.common.mapper.admintool;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.admintool.AdminFriend;


public interface AdmFriendMapper extends GenericMapper<AdminFriend, String> {
	public List<AdminFriend> getFriendList(String accountId);
	
	public List<AdminFriend> getFriendMemberNoList(String accountId);
	
	public List<AdminFriend> getFriendListByMemberNoList(@Param("memberNoList")List<AdminFriend> memberNoList);
}