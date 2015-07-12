package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.Friend;

public interface BossRaidMapper extends GenericMapper<BossRaid, String> {

	
	public void saveGuest(BossRaid bossRaid);
	public List<BossRaid> getFriendBossRaidList(@Param("accountId")String accountId);
	public List<BossRaid> getFriendBossRaidListByMemberNoList(@Param("memberNoList")List<Friend> memberNoList);
	public int incPartyCount(@Param("raidKey") String raidKey);
	
	public BossRaid getBossHostInfo(@Param("bossRaidId")String bossRaidId);
	public BossRaid getBossGuestInfo(@Param("bossRaidId")String bossRaidId, @Param("accountId")String accountId);
	public List<BossRaid> getLiveBossRaid(@Param("accountId")String accountId);
	
	public BossRaid getHostResult(@Param("bossRaidId")String bossRaidId);
	public List<BossRaid> getGuestResultList(@Param("bossRaidId")String bossRaidId);
	
	public int checkTimeOver(@Param("bossRaidId")String bossRaidId);
	public int isClear(@Param("bossRaidId")String bossRaidId);
	
	public void updateHostBossRaid(BossRaid bossRaid);
	public void updateGuestBossRaid(BossRaid bossRaid);
	
	public List<String> getClearBossRaidMetaIds(@Param("accountId")String accountId);
	
	public List<String> getNoRewardBossRaidIdList(@Param("accountId")String accountId);
	public List<BossRaid> getNoRewardBossRaidList(@Param("bossRaidIdList")List<String> bossRaidIdList);
}

