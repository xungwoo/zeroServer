package com.thirtygames.zero.common.mapper.index;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.params.MatchingAI;

public interface FriendMapper {

	public List<Friend> getFriendList(String accountId);
	public List<Friend> getFriendMemberNoList(String accountId);

	public int addFriendRelation(Friend friend);

	public int updateFriend(Friend friend);

	public int deleteFriend(Friend friend);

	public Friend getFriendAccount(@Param("friendId")String friendId);

	public Set<Long> getFriendMemberNoSet(Friend f);

	public int updateLastHelpTime(@Param("accountId")String accountId, @Param("friendMemberNo")long friendMemberNo);

	public Integer getCurrentTimeStamp();

	public List<Friend> searchByNickName(@Param("nickName")String nickName, @Param("from")int from, @Param("length")int length);

	public List<Friend> recommendationList(@Param("accountId")String accountId, @Param("from")int from, @Param("length")int length);

	public Friend getFriendRelation(Friend friend);
	
	public List<Friend> getFriendListByMemberNoList(@Param("memberNoList")List<Friend> memberNoList);
	public int removeRelations(@Param("memberNo")long memberNo);
	
	public Friend matchingAI(MatchingAI matchingParams);
	public Friend getFriendByAccountId(@Param("accountId")String accountId);
	
	
	
	
	public int addListFriendRelation(Friend f);
	public void updateLastPresentTime(@Param("accountId")String accountId, @Param("friendMemberNo")long friendMemberNo);
	public int getDiffMinuteLastPresent(@Param("accountId")String accountId, @Param("friendMemberNo")Long friendMemberNo);
	
	

}
