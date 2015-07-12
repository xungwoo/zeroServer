package com.thirtygames.zero.common.service.admintool;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmShopItemMapper;
import com.thirtygames.zero.common.model.admintool.AdminShopItem;

@Service("adminShopItemService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmShopItemServiceImpl extends AdminMetaServiceImpl<AdmShopItemMapper, AdminShopItem, String> implements AdmShopItemService  {
	
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminShopItem shopItem)  {
		mapper.save(shopItem);
		return mapper.saveName(shopItem); 
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminShopItem shopItem) {
		mapper.updateName(shopItem);
		return mapper.update(shopItem);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int delete(String id) {
		mapper.deleteName(id);
		return mapper.delete(id);
	}	
	
}