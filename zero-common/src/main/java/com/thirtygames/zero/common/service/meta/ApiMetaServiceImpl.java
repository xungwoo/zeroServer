package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.ApiMetaMapper;
import com.thirtygames.zero.common.model.meta.ApiMeta;

@Service("apiMetaService")
public class ApiMetaServiceImpl extends MetaServiceImpl<ApiMetaMapper, ApiMeta, String> implements ApiMetaService {

}
