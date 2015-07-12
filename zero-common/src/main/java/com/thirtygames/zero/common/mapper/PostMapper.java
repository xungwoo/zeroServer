package com.thirtygames.zero.common.mapper;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.Post;

public interface PostMapper extends GenericMapper<Post, Long> {

	void sendPostWithDate(Post post);

	int countHelpPost(String accountId);

	void saveExpireOneDay(Post post);

}