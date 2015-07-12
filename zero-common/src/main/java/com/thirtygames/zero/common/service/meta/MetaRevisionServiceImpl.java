package com.thirtygames.zero.common.service.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.MetaRevisionMapper;
import com.thirtygames.zero.common.model.meta.MetaRevision;

@Slf4j
@Service("metaRevisionService")
public class MetaRevisionServiceImpl extends MetaServiceImpl<MetaRevisionMapper, MetaRevision, String> implements MetaRevisionService  {
	
	
}