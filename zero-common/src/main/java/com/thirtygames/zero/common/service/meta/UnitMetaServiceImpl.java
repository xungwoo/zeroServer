package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.UnitMetaMapper;
import com.thirtygames.zero.common.model.meta.UnitMeta;

@Service("unitMetaService")
public class UnitMetaServiceImpl extends MetaServiceImpl<UnitMetaMapper, UnitMeta, Integer> implements UnitMetaService {


}
