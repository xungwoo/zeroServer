package com.thirtygames.zero.api.controller;

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

import com.thirtygames.zero.api.controller.common.ApiGenericController;
import com.thirtygames.zero.api.validator.PostValidator;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.common.ApiJsonResult;
import com.thirtygames.zero.common.service.AccountResourceService;
import com.thirtygames.zero.common.service.AccountService;
import com.thirtygames.zero.common.service.PostService;

@Slf4j
@Controller
@RequestMapping(value = "/post")
public class PostController extends ApiGenericController<Post, Long, PostService, PostValidator> {
	
	@Autowired
	AccountService accountService;
	
	@Autowired
	AccountResourceService arService;
	
	@Override
	protected Post preAdd(Post post, String accountId)  {
		Account account = accountService.get(accountId);
		post.setFromAccountId(accountId);
		post.setFromNickName(account.getNickName());
		post.setFromProfileUrl(account.getProfileUrl());
		post.setFromLeague(account.getLeague());
		post.setAccountId(accountId);
		return post;
	}
	
	@Override
	protected Post preSearch(Post post, String accountId)  {
		post.setAccountId(accountId);
		return post;
	}

	@Override
	protected Post preUpdate(Post post, String accountId)  {
		post.setAccountId(accountId);
		return post;
	}
	
	

	@RequestMapping(value="/{from}/{length}/langs/{lang}", method = { RequestMethod.GET })
	public @ResponseBody
	ApiJsonResult<Post> range(@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp, 
			@PathVariable(value = "from") int from, 
			@PathVariable(value = "length") int length,
			@PathVariable(value = "lang") String lang)
			throws RestException {

		ApiJsonResult<Post> result = new ApiJsonResult<Post>();
		List<Post> postList = service.getAllPostList(myAccountId, from, length, lang);
		result.setResult(postList);
		return result;
	}
	
	
	@RequestMapping(value="/reward", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Post> reward(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required=true, value="key") int key,
			@RequestParam(required=true, value="type") int type
			)  {
		
		ApiJsonResult<Post> result = new ApiJsonResult<Post>();
		result.setResult(service.reward(myAccountId, type, key));
		result.setResource(arService.get(myAccountId));
		return result;
	}	
	
	@RequestMapping(value="/reward/multi", method = { RequestMethod.PUT })
	public @ResponseBody
	ApiJsonResult<Post> rewardMulti(
			@RequestHeader("myAccountId") String myAccountId, 
			@RequestHeader("myAccessToken") String myAccessToken, 
			@RequestHeader("myClientVersion") String myClientVersion, 
			@RequestHeader("myClientPlatform") String myClientPlatform, @RequestHeader("myTimeStamp") String myTimeStamp,
			@RequestParam(required = true, value = "friendIds") String friendIds,
			@RequestParam(required = true, value = "lang") String lang)  {
		
		Account myAccount = accountService.get(myAccountId);
		ValidationUtil.isNullModel(myAccount, "accountId:" + myAccountId);
		
		ApiJsonResult<Post> result = new ApiJsonResult<Post>();
		result.setResult(service.rewardMulti(myAccount, friendIds, lang));
		result.setResource(arService.get(myAccountId));
		return result;
	}	

}