package com.thirtygames.zero.common.service.meta;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.ShopItemMetaMapper;
import com.thirtygames.zero.common.model.meta.ShopItem;

@Service("shopItemMetaService")
public class ShopItemMetaServiceImpl extends MetaServiceImpl<ShopItemMetaMapper, ShopItem, Integer> implements ShopItemMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public ShopItem getByItemId(String itemId) {
		return mapper.getByItemId(itemId);
	}

}