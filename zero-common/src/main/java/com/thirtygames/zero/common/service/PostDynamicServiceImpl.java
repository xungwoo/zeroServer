package com.thirtygames.zero.common.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.MapperException;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.PostMapper;
import com.thirtygames.zero.common.model.Post;

@Slf4j
@Service("postDynamicService")
public class PostDynamicServiceImpl extends GenericServiceImpl<PostMapper, Post, Long> implements PostDynamicService {
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)	
	public void sendPostDynamic(Post post) throws MapperException {
		mapper.save(post);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)		
	public boolean isOverMaxHelpPost(String accountId) {
		return (mapper.countHelpPost(accountId) >= Post.MAX_COUNT_HELP_POST);
	}
	
}