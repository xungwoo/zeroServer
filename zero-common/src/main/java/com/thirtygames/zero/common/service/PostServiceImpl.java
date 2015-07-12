package com.thirtygames.zero.common.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.MapperException;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.PostMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.RewardCategory;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.service.admindata.PostAdminService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;

@Slf4j
@Service("postService")
public class PostServiceImpl extends GenericServiceImpl<PostMapper, Post, Long> implements PostService {
	
	@Autowired
	PostAdminService postAdminService;
	
	@Autowired
	PostDynamicService postDynamicService;
	
	@Autowired
	RewardService rewardService;
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	AccountService accountService;	
	

	@Override
	public List<Post> getAllPostList(String accountId, int from, int length, String lang) {
		// postAdminList은 Account langauage 설정을 따름, 어뷰징 방지
		List<Post> postAdminList = this.getPostAdminList(accountId, from, length);
		int extraLength = (length > 0) ? length - postAdminList.size() : 0;
		if (extraLength > 0) {
			List<Post> postList = this.getPostList(accountId, from, extraLength, lang);
			postAdminList.addAll(postList);
		}
		log.debug("postAdminList::" + postAdminList);
		return postAdminList;
	}

	private List<Post> getPostList(String accountId, int from, int extraLength, String lang) {
		Post post = new Post();
		post.setPostType(PostType.User.getCode());
		post.setAccountId(accountId);
		post.setLang(lang);
		post.setRewardDone(false);
		List<Post> postList = mapper.search(from, extraLength, post);
		return postList;
	}

	private List<Post> getPostAdminList(String accountId, int from, int length) {
		Account account = accountService.get(accountId);
		String accountLang = account.getLanguage();
		
		Post postAdminParam = new Post();
		postAdminParam.setLang(accountLang);
		List<Post> postAdminList = postAdminService.search(from, length, postAdminParam);
		Iterator<Post> itPostAdmin = postAdminList.iterator();
		
		Post searchParam = new Post();
		searchParam.setAccountId(accountId);
		searchParam.setPostType(PostType.Admin.getCode());
		searchParam.setLang(accountLang);
		List<Post> postCheckList = mapper.search(0, 0, searchParam);
		
		while(itPostAdmin.hasNext()) {
			Post postAdmin = itPostAdmin.next();
			log.debug("postAdmin::" + postAdmin);
			Iterator<Post> itPostCheck = postCheckList.iterator();
			while(itPostCheck.hasNext()) {
				Post postCheck = itPostCheck.next();
				log.debug("postCheck::" + postCheck);
				if (postAdmin.getPostAdminKey() == postCheck.getPostAdminKey()) {
					itPostAdmin.remove();
					itPostCheck.remove();
					break;
				}
			}
		}
		return postAdminList;
	}		

	@Override
	@Transactional
	public Reward reward(String accountId, int type, long key)  {
		if (type == PostType.Admin.getCode()) {
			Post postAdmin = postAdminService.get(key);
			if (postAdmin == null) {
				throw new RestException("Not.found.Post key:" + key);
			}
			
			return rewardPostAdmin(accountId, postAdmin);
		} else if (type == PostType.User.getCode()) {
			Post post = mapper.get(key);
			if (post== null) {
				throw new RestException("Not.found.Post key:" + key);
			}
			
			return rewardPost(accountId, post);
		} else {
			throw new RestException("Invalid.PostType :" + type);
		}
	}

	private Reward rewardPost(String accountId, Post post)  {
		int postType = post.getPostType();
		long postKey = post.getPostKey();
		
		Post searchParam = new Post();
		searchParam.setAccountId(accountId);
		searchParam.setPostType(postType);
		searchParam.setPostKey(postKey);
		searchParam.setRewardDone(true);
		List<Post> searchPostList = mapper.search(0, 0, searchParam);
		if (!searchPostList.isEmpty()) {
			throw new RestException(Errors.AlreadyRewardPost, " postKey:" + postKey);
		}			
		
		post.setRewardDone(true);
		mapper.update(post);
		
		Reward reward = new Reward();
		reward.setAccountId(accountId);
		reward.setReward(post.getReward());
		reward.setRewardType(post.getRewardType());
		reward.setReasonType(Reward.ReasonType.Post.getCode());
		return rewardService.reward(reward, false);
	}

	private Reward rewardPostAdmin(String accountId, Post post) {
		long postAdminKey = post.getPostAdminKey();
		int postType = post.getPostType();
		
		Post searchParam = new Post();
		searchParam.setAccountId(accountId);
		searchParam.setPostType(postType);
		searchParam.setPostAdminKey(postAdminKey);
		searchParam.setRewardDone(true);
		List<Post> searchPostList = mapper.search(0, 0, searchParam);
		if (!searchPostList.isEmpty()) {
			throw new RestException(Errors.AlreadyRewardPost, " postAdminKey:" + postAdminKey);
		}
		
		Post postParam = new Post();
		postParam.setPostAdminKey(postAdminKey);
		postParam.setAccountId(accountId);
		postParam.setPostType(postType);
		postParam.setReward(post.getReward());
		postParam.setRewardType(post.getRewardType());
		postParam.setRewardCategory(post.getRewardCategory());
		postParam.setRewardDone(true);
		mapper.save(postParam);
		
		Reward reward = new Reward();
		reward.setAccountId(accountId);
		reward.setReward(post.getReward());
		reward.setRewardType(post.getRewardType());
		reward.setReasonType(Reward.ReasonType.Post.getCode());
		return rewardService.reward(reward, false);
	}
	
	@Override
	@Transactional
	public List<Reward> rewardMulti(Account myAccount, String friendIds, String lang)  {
		String accountId = myAccount.getAccountId();
		
		List<Reward> resultList = new ArrayList<Reward>();
		List<Post> postAdminList = this.getPostAdminList(accountId, 0, Post.MAX_VIEW_POST);
		for (Post postAdmin : postAdminList) {
			resultList.add(this.reward(accountId, postAdmin.getPostType(), postAdmin.getPostAdminKey()));
		}
		
		int extraLength = Post.MAX_VIEW_POST - resultList.size();
		if (extraLength > 0) {
			List<Post> postList = this.getPostList(accountId, 0, extraLength, lang);
			for (Post post : postList) {
				resultList.add(this.reward(accountId, post.getPostType(), post.getPostKey()));
			}	
		}
		log.debug("resultList.size()" + resultList.size());
		
		if (!Strings.isNullOrEmpty(friendIds)) {
			Post friendPost = new Post();
			friendPost.setFromAccountId(accountId);
			friendPost.setFromLeague(myAccount.getLeague());
			friendPost.setFromNickName(myAccount.getNickName());
			friendPost.setFromProfileUrl(myAccount.getProfileUrl());
			friendPost.setPostType(PostType.User.getCode());
			friendPost.setRewardCategory(RewardCategory.Resource.getCode());
			friendPost.setRewardType(RewardType.FP.getCode());
			friendPost.setReward(Reward.WITH_FACEBOOK_FRIEND_POINT);
			friendPost.setText(Post.PostText.findByCode(TextType.FacebookFriendPoint, LanguageCode.findByCode(lang)).getText());
			friendPost.setLang(lang);
			friendPost.setRewardDone(false);
			friendPost.setAvailableReply(true);
			
			Iterator<String> itFriendId = Splitter.on(',').trimResults().omitEmptyStrings().split(friendIds).iterator();
			while(itFriendId.hasNext()) {
				String friendId = itFriendId.next();
				friendPost.setAccountId(friendId);
				dsManager.switchDataSource(friendId);
				postDynamicService.sendPostDynamic(friendPost);			
			}
		}
		
		return resultList;
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sendPostExpireOneDay(Post post) {
		mapper.saveExpireOneDay(post);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public void sendPost(Post post) throws MapperException {
		mapper.save(post);
	}

	@Override
	public void sendPostWithDate(Post post) {
		mapper.sendPostWithDate(post);
	}
	
}