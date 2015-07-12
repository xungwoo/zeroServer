package com.thirtygames.zero.common.service.admindata;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.admindata.PostAdminMapper;
import com.thirtygames.zero.common.model.Post;

@Service("postAdminService")
public class PostAdminServiceImpl extends MetaServiceImpl<PostAdminMapper, Post, Long> implements PostAdminService {

}