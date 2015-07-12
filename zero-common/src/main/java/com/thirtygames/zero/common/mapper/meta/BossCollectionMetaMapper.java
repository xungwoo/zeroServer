package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossCollection;

public interface BossCollectionMetaMapper extends GenericMapper<BossCollection, String> {

	public List<BossCollection> getCollectionMetaList(@Param("lang")String lang);


}

