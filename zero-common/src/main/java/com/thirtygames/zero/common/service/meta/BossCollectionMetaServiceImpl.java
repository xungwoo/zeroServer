package com.thirtygames.zero.common.service.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.BossCollectionMetaMapper;
import com.thirtygames.zero.common.model.BossCollection;

@Slf4j
@Service("bossCollectionMetaService")
public class BossCollectionMetaServiceImpl extends MetaServiceImpl<BossCollectionMetaMapper, BossCollection, String> implements BossCollectionMetaService {
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<BossCollection> getCollectionMetaList(String lang) {
		return mapper.getCollectionMetaList(lang);
	}

}
