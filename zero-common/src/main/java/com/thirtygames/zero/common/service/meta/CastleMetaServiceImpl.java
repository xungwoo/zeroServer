package com.thirtygames.zero.common.service.meta;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.CastleMetaMapper;
import com.thirtygames.zero.common.model.Castle;

@Slf4j
@Service("castleMetaService")
public class CastleMetaServiceImpl extends MetaServiceImpl<CastleMetaMapper, Castle, Integer> implements CastleMetaService {

}
