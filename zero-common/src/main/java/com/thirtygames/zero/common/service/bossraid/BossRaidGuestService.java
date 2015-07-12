package com.thirtygames.zero.common.service.bossraid;

import java.util.List;
import java.util.Map;

import com.thirtygames.zero.common.generic.GenericService;
import com.thirtygames.zero.common.model.BossRaid;

public interface BossRaidGuestService extends GenericService<BossRaid, String> {

	public BossRaid getGuestRaidInfo(String accountId, String bossRaidId);

	public Map<String, Object> reward(BossRaid guestRaid, BossRaid hostRaid);

	public List<BossRaid> getGuestResultList(String bossRaidId);

	public List<String> getNoRewardRaidIdList(String accountId);

	public void updateGuest(BossRaid updateGuest);

	public List<String> getClearBossRaidMetaIds(String accountId);

	public int countPlayingUser(String bossRaidId);

	public void updateAllGuest(BossRaid updateParams);

	public List<BossRaid> getGuestList(String bossRaidId);
	

	public long getRewardGold(long goldReward, double damagePercent);
	public double getDamagePercent(int totalDamage, int damageSum);

}