package com.thirtygames.zero.common.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.thirtygames.zero.common.generic.GenericMapper;
import com.thirtygames.zero.common.model.BossRaid;
import com.thirtygames.zero.common.model.Friend;

public interface BossRaidHostMapper extends GenericMapper<BossRaid, String> {

	
	public List<BossRaid> getLiveHost(@Param("accountId")String accountId);

	public BossRaid getHostRaidInfo(String bossRaidId);

	public int incPartyCount(String bossRaidId);

	public int checkTimeOver(String bossRaidId);

	public BossRaid getHostResult(String bossRaidId);

	public int isHostClear(String bossRaidId);

	public List<BossRaid> getNoRewardRaidList(@Param("bossRaidIdList")List<String> bossRaidIdList);

	public List<BossRaid> getFriendBossRaidListByMemberNoList(@Param("memberNoList")List<Friend> memberNoList);

	public List<String> getEventBossList(@Param("accountId")String accountId);

	public List<BossRaid> getLiveEventHost(@Param("accountId")String accountId);
}

