package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Friend;

public interface FriendService {
	
	public List<Friend> getFriendList(String accountId);
	public List<Friend> getFriendMemberNoList(String accountId);
	
	public int addFriendRelation(Friend friend);
	
	public Friend getFriendAccount(String friendId);
	
	public int addFriendList(String accountId, String friendMemberNoList, boolean isFacebookFriend, boolean isRestoreDeleted);
	public int delFriend(Friend friend);
	public long updateLastHelpTime(Friend myAccount, Friend friendAccount, String lang) ;
	public List<Friend> searchByNickName(String nickName, int from, int length);
	public List<Friend> recommendationList(String accountId, int from, int length);
	public Friend matchingAI(String accountId, int league, int ladder);
	
	public Friend getFriendRelation(Friend friend);
	public List<Friend> getFriendListByMemberNoList(List<Friend> memberNoList);
	
	// 탈퇴 시 친구관계 모두 삭제
	public int removeRelations(long memberNo);
	public Friend getFriendByAccountId(String accountId);
	
	// facebook friend
	public long sendFacebookFriend(Account myAccount, Account friendAccount, String lang);

	
}
