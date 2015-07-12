package com.thirtygames.zero.common.mapper.meta;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossRaid;

public interface BossRaidMetaMapper extends GenericMapper<BossRaid, String> {

	public List<BossRaid> getEventBossMetaListByLang(@Param("lang")String lang);
	
	public List<BossRaid> getEventBossRaidList(@Param("bossRaidMetaIds")List<String> bossRaidMetaIds);

	public List<BossRaid> getEventBossList4Push();

	public void eventBossPushLog(@Param("bossRaidMetaId")String bossRaidMetaId);

	public BossRaid getByLang(@Param("bossRaidMetaId")String bossRaidMetaId, @Param("lang")String lang);

	public BossRaid getEventMeta(String bossRaidMetaId);

	public BossRaid getEventMetaByLang(@Param("bossRaidMetaId")String bossRaidMetaId, @Param("lang")String lang);



}

