package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.UnitLimitExceedMetaMapper;
import com.thirtygames.zero.common.model.meta.UnitLimitExceed;

@Service("unitLimitExceedMetaService")
public class UnitLimitExceedMetaServiceImpl extends MetaServiceImpl<UnitLimitExceedMetaMapper, UnitLimitExceed, Integer> implements UnitLimitExceedMetaService {

}
