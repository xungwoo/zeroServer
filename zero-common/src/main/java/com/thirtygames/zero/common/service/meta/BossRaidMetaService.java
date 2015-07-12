package com.thirtygames.zero.common.service.meta;

import java.util.List;

import com.thirtygames.zero.common.generic.MetaService;
import com.thirtygames.zero.common.model.BossRaid;

public interface BossRaidMetaService extends MetaService<BossRaid, String> {

	public List<BossRaid> getEventBossRaidList(List<String> bossRaidMetaIds);
	
	public List<BossRaid> getEventBossMetaListByLang(String lang);

	public List<BossRaid> getEventBossList4Push();

	public void eventBossPushLog(String bossRaidMetaId);

	public BossRaid getByLang(String bossRaidMetaId, String lang);

	public BossRaid getEventMeta(String bossRaidMetaId);

	public BossRaid getEventMetaByLang(String bossRaidMetaId, String lang);

	

}