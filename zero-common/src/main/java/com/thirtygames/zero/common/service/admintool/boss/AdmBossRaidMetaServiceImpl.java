package com.thirtygames.zero.common.service.admintool.boss;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmBossRaidMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminBossRaidMeta;

@Service("adminBossRaidMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmBossRaidMetaServiceImpl extends AdminMetaServiceImpl<AdmBossRaidMetaMapper, AdminBossRaidMeta, String>  implements AdmBossRaidMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int save(AdminBossRaidMeta entity)  {
		mapper.save(entity);
		return mapper.saveName(entity); 
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public int update(AdminBossRaidMeta entity) {
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
