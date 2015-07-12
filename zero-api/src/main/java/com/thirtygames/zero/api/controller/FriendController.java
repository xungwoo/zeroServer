package com.thirtygames.zero.api.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountDynamicService;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.DeckService;
import com.thirtygames.zero.common.service.FriendService;
import com.thirtygames.zero.common.service.StageService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Controller
@RequestMapping(value = "/friends")
public class FriendController {

	@Autowired
	FriendService service;

	@Autowired
	AccountService accountService;

	@Autowired
	AccountDynamicService accountDynamicService;

	@Autowired
	DeckService deckService;

	@Autowired
	StageService stageService;

	@Autowired
	DataSourceService dsManager;

	@RequestMapping(method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Friend> addFriend(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(value = "friendMemberNo", required = true) Long friendMemberNo, @RequestParam(value = "friendId", required = true) String friendId) {
		Friend friend = new Friend();
		friend.setAccountId(myAccountId);
		friend.setFriendMemberNo(friendMemberNo);

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		result.setParams(friend);
		int resultCount = 0;

		Friend oldFriend = service.getFriendRelation(friend);
		if (oldFriend == null) {
			friend.setIsFacebookFriend(false);
			resultCount = service.addFriendRelation(friend);
		} else {
			if (oldFriend.getIsDel()) {
				friend.setFriendRelationKey(oldFriend.getFriendRelationKey());
				friend.setIsFacebookFriend(oldFriend.getIsFacebookFriend());
				resultCount = service.addFriendRelation(friend);
			}
		}

		dsManager.switchDataSource(friendId);
		Friend friendAccount = service.getFriendAccount(friendId);
		ValidationUtil.isNullModel(friendAccount, "friendId:" + friendId);

		Friend friendRelationParams = new Friend();
		friendRelationParams.setAccountId(myAccountId);
		friendRelationParams.setFriendMemberNo(friendMemberNo);
		Friend friendRelation = service.getFriendRelation(friendRelationParams);
		ValidationUtil.isNullModel(friendRelation, "params:" + friendRelationParams);

		friendAccount.setIsFacebookFriend(friendRelation.getIsFacebookFriend());
		friendAccount.setLastHelpYmdt(friendRelation.getLastHelpYmdt());
		friendAccount.setLastPresentYmdt(friendRelation.getLastPresentYmdt());
		result.setResult(friendAccount);
		result.setResultCount(resultCount);
		return result;
	}

	@RequestMapping(value = "/add-list", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Friend> addFriends(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(value = "friendMemberNoList", required = true) String friendMemberNoList, @RequestParam(required = false, value = "isFacebookFriend", defaultValue = "false") boolean isFacebookFriend,
			@RequestParam(required = false, value = "isRestoreDeleted", defaultValue = "false") boolean isRestoreDeleted) {
		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		result.setResultCount(service.addFriendList(myAccountId, friendMemberNoList, isFacebookFriend, isRestoreDeleted));
		accountService.updateLastSyncTime(myAccountId);
		return result;
	}

	@RequestMapping(value = "/{id}", method = { RequestMethod.DELETE })
	public @ResponseBody
	ApiJsonResult<Friend> delFriend(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @PathVariable("id") Long friendMemberNo) {
		Friend friend = new Friend();
		friend.setAccountId(myAccountId);
		friend.setFriendMemberNo(friendMemberNo);

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		result.setParams(friend);
		int resultCount = 0;

		Friend oldFriend = service.getFriendRelation(friend);
		if (oldFriend != null) {
			if (oldFriend.getIsFacebookFriend()) {
				throw new RestException(Errors.FacebookFriendCannotDelete);
			}

			if (oldFriend.getLimitDeleteMinute() < Friend.MINUTE_DELETE_LIMIT) {
				throw new RestException(Errors.LimitTimeFriendDelete);
			}

			resultCount = service.delFriend(friend);
		}
		result.setResultCount(resultCount);
		return result;
	}

	@RequestMapping(value = "/help", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Friend> updateLastHelpTime(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(value = "friendId", required = true) String friendId, @RequestParam(value = "lang", required = true) String lang) {
		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();

		dsManager.switchDataSource(myAccountId);
		Friend myAccount = service.getFriendAccount(myAccountId);
		if (myAccount == null) {
			throw new RestException(Errors.InvalidDeckInfo, myAccountId);
		}

		dsManager.switchDataSource(friendId);
		Friend friendAccount = service.getFriendAccount(friendId); // friendId,
																	// accountId

		result.setResult(service.updateLastHelpTime(myAccount, friendAccount, lang));
		return result;
	}

	@RequestMapping(value = "/search", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Friend> searchByNickName(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(required = false, value = "from", defaultValue = "0") int from, @RequestParam(required = false, value = "length", defaultValue = "0") int length, @RequestParam(required = true, value = "nickName") String nickName) {

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();

		List<Friend> searchList = new ArrayList<Friend>();
		for (DataSource dst : DataSource.GAME_SHARDS) {
			dsManager.switchDataSource(dst);
			List<Friend> resultList = service.searchByNickName(nickName, 0, length);
			int resultLength = resultList.size();
			searchList.addAll(resultList);
			if (length > 0 && resultLength >= length) {
				break;
			}
			length -= resultLength;
		}

		result.setResultCount(searchList.size());
		result.setResult(searchList);
		return result;
	}

	@RequestMapping(value = "/ignore-relations", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Friend> searchByAccountId(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(value = "friendIds") String friendIds) {

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();

		List<Friend> friendList = Lists.newArrayList();
		Iterator<String> itFriendId = Splitter.on(',').trimResults().omitEmptyStrings().split(friendIds).iterator();
		while (itFriendId.hasNext()) {
			String friendId = itFriendId.next();
			log.debug("friendId:" + friendId);
			dsManager.switchDataSource(friendId);
			Friend f = service.getFriendByAccountId(friendId);
			if (f != null) {
				friendList.add(f);
			}
		}
		result.setResult(friendList);
		return result;
	}

	@RequestMapping(value = "/recommendation", method = { RequestMethod.POST })
	public @ResponseBody
	ApiJsonResult<Friend> recommendation(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam(required = false, value = "from", defaultValue = "0") int from, @RequestParam(required = false, value = "length", defaultValue = "0") int length) {

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		List<Friend> resultList = service.recommendationList(myAccountId, from, length);
		result.setResultCount(resultList.size());
		result.setResult(resultList);
		return result;
	}

	@RequestMapping(method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Friend> getFriens(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform) {

		Friend friend = new Friend();
		friend.setAccountId(myAccountId);

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		result.setParams(friend);

		List<Friend> resultList = new ArrayList<Friend>();
		List<Friend> myShardFriendList = service.getFriendList(myAccountId);
		resultList.addAll(myShardFriendList);

		List<Friend> memberNoList = service.getFriendMemberNoList(myAccountId);

		if (!memberNoList.isEmpty()) {
			for (DataSource dst : DataSource.GAME_SHARDS) {
				if (dst != DataSource.getAccountDS(myAccountId)) {
					dsManager.switchDataSource(dst);
					List<Friend> friendList = service.getFriendListByMemberNoList(memberNoList);

					Iterator<Friend> itFr = friendList.iterator();
					while (itFr.hasNext()) {
						Friend fr = itFr.next();
						Iterator<Friend> itMember = memberNoList.iterator();
						while (itMember.hasNext()) {
							Friend memberFr = itMember.next();
							if (memberFr.getFriendMemberNo().equals(fr.getMemberNo())) {
								fr.setLastHelpYmdt(memberFr.getLastHelpYmdt());
								fr.setLastPresentYmdt(memberFr.getLastPresentYmdt());
								fr.setIsFacebookFriend(memberFr.getIsFacebookFriend());
								fr.setAddedYmdt(memberFr.getAddedYmdt());
								itMember.remove();
								break;
							}
						}
					}

					log.debug("flist::" + friendList);
					resultList.addAll(friendList);
				}
			}
		}

		result.setResult(resultList);
		result.setResultCount(resultList.size());
		return result;
	}

	@RequestMapping(value = "/facebook", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Friend> facebookSend(@RequestHeader("myAccountId") String myAccountId, @RequestHeader("myAccessToken") String myAccessToken, @RequestHeader("myClientVersion") String myClientVersion, @RequestHeader("myClientPlatform") String myClientPlatform,
			@RequestHeader("myTimeStamp") String myTimeStamp, @RequestParam("friendId") String friendId, @RequestParam("lang") String lang) {

		Account myAccount = accountService.get(myAccountId);
		ValidationUtil.isNullModel(myAccount, "accountId:" + myAccountId);

		dsManager.switchDataSource(friendId);
		Account friendAccount = accountDynamicService.get(friendId);
		ValidationUtil.isNullModel(friendAccount, "friendAccountId:" + friendId);

		ApiJsonResult<Friend> result = new ApiJsonResult<Friend>();
		result.setResult(service.sendFacebookFriend(myAccount, friendAccount, lang));
		return result;
	}

}
