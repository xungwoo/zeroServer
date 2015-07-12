package com.thirtygames.zero.common.service.bossraid;

import java.util.List;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.Friend;

public interface BossRaidHostService extends GenericService<BossRaid, String> {

	public List<BossRaid> getLiveHost(String accountId);

	public BossRaid find(String accountId, int bossUnitLevelMin, int bossUnitLevelMax, BossRaid bossRaidMeta);

	public BossRaid getHostRaidInfo(String bossRaidId);

	public BossRaid join(String accountId, String bossRaidId);

	public BossRaid damageBoss(String accountId, String bossRaidId, boolean isClear, int damage);

	public BossRaid getHostResult(String bossRaidId);

	public void updateRaidStatus(String accountId, String bossRaidId, String bossRaidMetaId, boolean isEvent);

	public List<BossRaid> getNoRewardRaidList(List<String> bossRaidIdList);

	public List<BossRaid> getFriendBossRaidListByMemberNoList(List<Friend> memberNoList);

	
	public List<String> getEventBossList(String accountId);

	public BossRaid eventBossFind(String accountId, BossRaid eventMeta);

	public List<BossRaid> getLiveEventHost(String accountId);


}