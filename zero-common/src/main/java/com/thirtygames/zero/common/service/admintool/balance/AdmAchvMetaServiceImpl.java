package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmAchvMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminAchvMeta;

@Service("adminAchvMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmAchvMetaServiceImpl extends AdminMetaServiceImpl<AdmAchvMetaMapper, AdminAchvMeta, String>  implements AdmAchvMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminAchvMeta entity)  {
		mapper.save(entity);
		return mapper.saveName(entity); 
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminAchvMeta entity) {
		mapper.saveName(entity);
		return mapper.update(entity);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int delete(String id) {
		mapper.deleteName(id);
		return mapper.delete(id);
	}
}
