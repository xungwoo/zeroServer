package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.GemMergeMetaMapper;
import com.thirtygames.zero.common.model.meta.GemMerge;

@Service("gemMergeMetaService")
public class GemMergeMetaServiceImpl extends MetaServiceImpl<GemMergeMetaMapper, GemMerge, Integer> implements GemMergeMetaService {

}
