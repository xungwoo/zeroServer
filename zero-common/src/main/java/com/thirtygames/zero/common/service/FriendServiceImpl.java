package com.thirtygames.zero.common.service;

import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.mapper.index.FriendMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.DeckInfo;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.RewardCategory;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.model.params.MatchingAI;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Service("firendService")
public class FriendServiceImpl implements FriendService {

	@Autowired
	FriendMapper mapper;

	@Autowired
	DeckService deckService;
	
	@Autowired
	DeckInfoService deckInfoService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostDynamicService postDynamicService;
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	AccountService accountService;
	
	

	@Override
	@Transactional
	public int addFriendList(String accountId, String friendMemberNoList, boolean isFacebookFriend, boolean isRestoreDeleted) {
		int resultCount = 0;
		Iterator<String> itFriendMemberNo = Splitter.on(",").trimResults().omitEmptyStrings().split(friendMemberNoList).iterator();

		while (itFriendMemberNo.hasNext()) {
			Long friendMemberNo = Long.parseLong(itFriendMemberNo.next());

			Friend friend = new Friend();
			friend.setAccountId(accountId);
			friend.setFriendMemberNo(friendMemberNo);
			friend.setIsFacebookFriend(isFacebookFriend);
			friend.setIsRestoreDeleted(isRestoreDeleted);
			resultCount += mapper.addListFriendRelation(friend);
		}		

		return resultCount;
	}	

	
	@Override
	public List<Friend> getFriendList(String accountId) {
		return mapper.getFriendList(accountId);
	}	
	

	@Override
	public List<Friend> getFriendMemberNoList(String accountId) {
		return mapper.getFriendMemberNoList(accountId);
	}	
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public Friend getFriendAccount(String friendId) {
		return mapper.getFriendAccount(friendId);
	}
	
	@Override
	@Transactional
	public long updateLastHelpTime(Friend myAccount, Friend friendAccount, String lang)  {
		log.debug("myAccount::" + myAccount);
		String accountId = myAccount.getFriendId();
		String friendId = friendAccount.getFriendId();
		
		// fromFriend toMe
		Post myPost = new Post();
		myPost.setAccountId(myAccount.getFriendId());
		myPost.setPostType(PostType.User.getCode());
		myPost.setFromAccountId(friendId);
		myPost.setFromLeague(friendAccount.getLeague());
		myPost.setFromNickName(friendAccount.getNickName());
		myPost.setFromProfileUrl(friendAccount.getProfileUrl());
		myPost.setRewardCategory(RewardCategory.Resource.getCode());
		myPost.setRewardType(RewardType.FP.getCode());
		myPost.setReward(Reward.WITH_FRIEND_MY_POINT);
		myPost.setText(Post.PostText.findByCode(TextType.FriendPointFrom, LanguageCode.findByCode(lang)).getText());
		myPost.setLang(lang);
		myPost.setRewardDone(false);
		dsManager.switchDataSource(myAccount.getFriendId());
		postDynamicService.sendPostDynamic(myPost);
		
		dsManager.switchDataSource(friendId);
		if (postDynamicService.isOverMaxHelpPost(friendId)) {
			// fromMe toFriend
			Post friendPost = new Post();
			friendPost.setAccountId(friendId);
			friendPost.setPostType(PostType.User.getCode());
			friendPost.setFromAccountId(myAccount.getFriendId());
			friendPost.setFromLeague(myAccount.getLeague());
			friendPost.setFromNickName(myAccount.getNickName());
			friendPost.setFromProfileUrl(myAccount.getProfileUrl());
			friendPost.setRewardCategory(RewardCategory.Resource.getCode());
			friendPost.setRewardType(RewardType.FP.getCode());
			friendPost.setReward(Reward.WITH_FRIEND_PARTNER_POINT);
			friendPost.setText(Post.PostText.findByCode(TextType.FriendPointTo, LanguageCode.findByCode(lang)).getText());
			friendPost.setLang(lang);
			friendPost.setRewardDone(false);
			dsManager.switchDataSource(friendId);
			postDynamicService.sendPostDynamic(friendPost);	
		};
		
		mapper.updateLastHelpTime(accountId, friendAccount.getMemberNo());
		return mapper.getCurrentTimeStamp(); 
	}
	
	@Override
	public List<Friend> recommendationList(String accountId, int from, int length) {
		return mapper.recommendationList(accountId, from, length);
	}
	
	@Override
	public Friend matchingAI(String accountId, int league, int ladder) {
		DeckInfo deckInfo = deckInfoService.get(accountId);
		if (deckInfo == null) {
			throw new RestException(Errors.NotFoundData, "deckInfo:" + accountId);
		}		
		int levelSum = deckInfo.getTotalLevelSum();
		int eqPoint = deckInfo.getEquipmentPoint();
		
		MatchingAI matchingParams = new MatchingAI();
		matchingParams.setAccountId(accountId);
		matchingParams.setLeague(league);

		int step = 1;
		Friend matchedFriend = null;
		while (matchedFriend == null && (matchingParams.getMinTotalLevelSum() > 0 || matchingParams.getMaxTotalLevelSum() <= MatchingAI.MAX_TOTAL_LEVEL_SUM) && step <= 10) {
			log.debug("####################################################### " + matchingParams.getMinTotalLevelSum() + "::" + levelSum + "::" + step);
			log.debug("####################################################### eqPoint::" + eqPoint);
			matchingParams.calcSearchRange(levelSum, eqPoint, step, ladder);
			matchedFriend = mapper.matchingAI(matchingParams);
			step++;
		}
		
		if (matchedFriend == null) {
			throw new RestException(Errors.NotFoundData, "matchedFriend");
		}
		return matchedFriend;
	}

	@Override
	public int addFriendRelation(Friend friend) {
		return mapper.addFriendRelation(friend);
	}
	
	@Override
	public int delFriend(Friend friend) {
		return mapper.deleteFriend(friend);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public List<Friend> searchByNickName(String nickName, int from, int length) {
		return mapper.searchByNickName(nickName, from, length);
	}

	@Override
	public Friend getFriendRelation(Friend friend) {
		return mapper.getFriendRelation(friend);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public List<Friend> getFriendListByMemberNoList(List<Friend> memberNoList) {
		return mapper.getFriendListByMemberNoList(memberNoList);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public int removeRelations(long memberNo) {
		return mapper.removeRelations(memberNo);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public Friend getFriendByAccountId(String accountId) {
		return mapper.getFriendByAccountId(accountId);
	}


	@Override
	@Transactional
	public long sendFacebookFriend(Account myAccount, Account friendAccount, String lang) {
		if (mapper.getDiffMinuteLastPresent(myAccount.getAccountId(), friendAccount.getMemberNo()) <= Friend.MINUTE_FACEBOOK_PRESENT_TIME) {
			throw new RestException(Errors.LimitTimeFacebookPresent);
		}
		
		// fromMe toFriend
		Post friendPost = new Post();
		friendPost.setAccountId(friendAccount.getAccountId());
		friendPost.setPostType(PostType.User.getCode());
		friendPost.setFromAccountId(myAccount.getAccountId());
		friendPost.setFromLeague(myAccount.getLeague());
		friendPost.setFromNickName(myAccount.getNickName());
		friendPost.setFromProfileUrl(myAccount.getProfileUrl());
		friendPost.setRewardCategory(RewardCategory.Resource.getCode());
		friendPost.setRewardType(RewardType.FP.getCode());
		friendPost.setReward(Reward.WITH_FACEBOOK_FRIEND_POINT);
		friendPost.setText(Post.PostText.findByCode(TextType.FacebookFriendPoint, LanguageCode.findByCode(lang)).getText());
		friendPost.setLang(lang);
		friendPost.setRewardDone(false);
		friendPost.setAvailableReply(true);
		
		dsManager.switchDataSource(friendAccount.getAccountId());
		postDynamicService.sendPostDynamic(friendPost);

		mapper.updateLastPresentTime(myAccount.getAccountId(), friendAccount.getMemberNo());
		return mapper.getCurrentTimeStamp(); 
	}


}