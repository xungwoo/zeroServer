package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.Post;

public interface PostDynamicService extends GenericService<Post, Long> {

	public void sendPostDynamic(Post post);

	public boolean isOverMaxHelpPost(String friendId);
	
}