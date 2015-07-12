package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.BossCollection;

public interface BossCollectionMetaService extends MetaService<BossCollection, String> {

	List<BossCollection> getCollectionMetaList(String lang);


}