package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossRaid;

public interface BossRaidGuestMapper extends GenericMapper<BossRaid, String> {

	BossRaid getGuestRaidInfo(@Param("accountId")String accountId, @Param("bossRaidId")String bossRaidId);

	List<BossRaid> getGuestResultList(String bossRaidId);

	List<String> getNoRewardRaidIdList(@Param("accountId")String accountId);

	List<String> getClearBossRaidMetaIds(@Param("accountId")String accountId);

	int countPlayingUser(String bossRaidId);

	void updateAllGuest(BossRaid updateParams);

	List<BossRaid> getGuestList(String bossRaidId);

}

