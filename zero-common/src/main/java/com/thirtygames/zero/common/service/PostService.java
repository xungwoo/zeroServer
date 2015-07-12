package com.thirtygames.zero.common.service;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.meta.Reward;

public interface PostService extends GenericService<Post, Long> {

	public Reward reward(String accountId, int postType, long postKey) ;

	public List<Reward> rewardMulti(Account account, String firendIds, String lang) ;

	public List<Post> getAllPostList(String accountId, int from, int length, String lang);

	public void sendPost(Post post);

	public void sendPostWithDate(Post rewardPost);
	
	public void sendPostExpireOneDay(Post post);
	
}