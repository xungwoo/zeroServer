package com.thirtygames.zero.common.service.meta;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.meta.BossRaidMetaMapper;
import com.thirtygames.zero.common.model.BossRaid;

@Slf4j
@Service("bossRaidMetaService")
public class BossRaidMetaServiceImpl extends MetaServiceImpl<BossRaidMetaMapper, BossRaid, String> implements BossRaidMetaService {

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public List<BossRaid> getEventBossRaidList(List<String> bossRaidMetaIds) {
		return mapper.getEventBossRaidList(bossRaidMetaIds);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)		
	public List<BossRaid> getEventBossMetaListByLang(String lang) {
		return mapper.getEventBossMetaListByLang(lang);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)		
	public List<BossRaid> getEventBossList4Push() {
		return mapper.getEventBossList4Push();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public void eventBossPushLog(String bossRaidMetaId) {
		mapper.eventBossPushLog(bossRaidMetaId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)	
	public BossRaid getByLang(String bossRaidMetaId, String lang) {
		return mapper.getByLang(bossRaidMetaId, lang);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)		
	public BossRaid getEventMeta(String bossRaidMetaId) {
		return mapper.getEventMeta(bossRaidMetaId);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_META)		
	public BossRaid getEventMetaByLang(String bossRaidMetaId, String lang) {
		return mapper.getEventMetaByLang(bossRaidMetaId, lang);
	}

}
