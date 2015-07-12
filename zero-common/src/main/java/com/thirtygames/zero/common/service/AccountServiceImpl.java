package com.thirtygames.zero.common.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSource;
import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.AccountMapper;
import com.thirtygames.zero.common.mapper.LadderMapper;
import com.thirtygames.zero.common.mapper.ResourceMapper;
import com.thirtygames.zero.common.mapper.SetupMapper;
import com.thirtygames.zero.common.model.Account;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Friend;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.Setup;
import com.thirtygames.zero.common.model.datasource.Shard;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.model.meta.WellcomePresent;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.meta.ApiMetaService;
import com.thirtygames.zero.common.service.meta.ServerInfoService;
import com.thirtygames.zero.common.service.meta.WellcomePresentService;

@Slf4j
@Service("accountService")
public class AccountServiceImpl extends
		GenericServiceImpl<AccountMapper, Account, String> implements
		AccountService {
	
	@Autowired
	DataSourceService dsManager;
	
	@Autowired
	ResourceMapper arMapper;

	@Autowired
	LadderMapper ladderMapper;

	@Autowired
	SetupMapper setupMapper;
	
	@Autowired
	private FriendService frService;
	
	@Autowired
	private AccountResourceService arService;
	
	@Autowired
	AccountDynamicService accountDynamicService;
	
	@Autowired
	ApiMetaService apiMetaService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	WellcomePresentService wellcomePresentService;
	
	@Autowired
	ServerInfoService serverInfoService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public Account getAccountDynamic(String accountId) {
		return mapper.get(accountId);
	}
	

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public int save(Account account) {
		int result = 0;
		String accountId = account.getAccountId();
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(accountId);
		ar.setGold(AccountResource.INIT_GOLD);
		ar.setCash(AccountResource.INIT_CASH);
		ar.setApMax(AccountResource.INIT_AP_MAX);
		ar.setApExtra(AccountResource.INIT_AP_EXTRA);
		ar.setBpMax(AccountResource.INIT_BP_MAX);
		ar.setBpExtra(AccountResource.INIT_BP_EXTRA);
		ar.setFp(AccountResource.INIT_FP);
		ar.setUnlockKey(AccountResource.INIT_UNLOCK_KEY);
		result += arMapper.save(ar);
		
		Ladder ladder = new Ladder();
		ladder.setAccountId(accountId);
		ladder.setLeague(Ladder.INIT_LEAGUE);
		ladder.setResetDate(Ladder.INIT_RESET_DATE);
		ladder.setLadder(Ladder.INIT_LADDER);
		ladder.setWin(Ladder.INIT_WIN);
		ladder.setLose(Ladder.INIT_LOSE);
		ladder.setIsPrevWin(Ladder.INIT_PREV_WIN);
		ladder.setWinningStreakCnt(Ladder.INIT_WINNING_STREAK);
		ladder.setWinningStreakMax(Ladder.INIT_WINNING_STREAK_MAX);
		result += ladderMapper.save(ladder);
		
		Setup setup = new Setup();
		setup.setAccountId(accountId);
		setup.setGraphic(Setup.INIT_GRAPHIC);
		setup.setProfile(Setup.INIT_PROFILE);
		setup.setPush(Setup.INIT_PUSH);
		setup.setMidnight(Setup.INIT_MIDNIGHT);
		setup.setAutoScreenOff(Setup.INIT_AUTO_SCREEN_OFF);
		setup.setBgSound(Setup.INIT_BG_SOUND);
		setup.setEffectSound(Setup.INIT_EFFECT_SOUND);
		result += setupMapper.save(setup);
		result += mapper.save(account);
		
		Shard shardParam = new Shard();
		shardParam.setMemberNo(account.getMemberNo());
		shardParam.setAccountId(accountId);
		shardParam.setDataSourceType(DataSource.getShardNo(accountId));
		result += dsManager.saveShard(shardParam);		
		return result;
	}
	
	@Override
	@Transactional
	public int update(Account account) {
		boolean isFriendModify = false;
		String accountId = account.getAccountId();
		
		Friend f = new Friend();
		f.setFriendId(accountId);
		
		if (account.getFacebookId() != null) {
			for(DataSource dst : DataSource.GAME_SHARDS) {
				dsManager.switchDataSource(dst);
				accountDynamicService.updateRemoveFacebookId(account.getFacebookId());
			}
		}
		
		if (account.getProfileUrl() != null) {
			f.setProfileUrl(account.getProfileUrl());
			isFriendModify = true;
		}
		
		if (account.getTitle() != null) {
			AccountResource ar = arService.get(accountId);
			if (ar == null) {
				throw new RestException("Not.found.Resource :" + accountId);
			}
			
			if (ar.checkTitle(account.getTitle())) {
				f.setTitle(account.getTitle());
				isFriendModify = true;
			} else {
				throw new RestException("No.have.Title :" + account.getTitle());
			}
			
		}
		
		if (account.getNickName() != null) {
			if (this.existNickName(accountId, account.getNickName())) {
				throw new RestException(HttpServletResponse.SC_NOT_ACCEPTABLE, "NickName.is.duplicated." + account.getNickName());
			}
			
			f.setNickName(account.getNickName());
			isFriendModify = true;			
		}
		
		if (isFriendModify) {
			//frService.updateFriend(f);	
		}
		
		return mapper.update(account);
	}
	

	@Override
	public int updateLastSyncTime(String accountId) {
		return mapper.updateLastSyncTime(accountId);
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public boolean existNickName(String accountId, String nickName) {
		return (mapper.existNickName(accountId, nickName) == 0) ? false : true;
	}	

	@Override
	@Transactional
	public AccountResource nickNameUpdate(Account account, AccountResource ar) {
		mapper.nickNameUpdate(account);
		return arService.updateSubtraction(ar, true);
	}
	
	@Override
	@Transactional
	public AccountResource buyExtraInventory(Account account, AccountResource ar) {
		mapper.buyExtraInventory(account);
		return arService.updateSubtraction(ar, true);
	}
	
	@Override
	public boolean checkMaxInventory(String accountId, int extraInventoryMaxCount) {
		return mapper.checkMaxInventory(accountId, extraInventoryMaxCount);
	}

	@Override
	public Account getAccountLoginInfo(String accountId) {
		return mapper.getAccountLoginInfo(accountId);
	}
	
	@Override
	public String getToken(String accountId) {
		return mapper.getToken(accountId);
	}

	@Override
	public void updateToken(String accountId, String authToken) {
		mapper.updateToken(accountId, authToken);
	}

	@Override
	@Transactional
	public int withdrawUpdate(Account account) {
		for(DataSource dst : DataSource.GAME_SHARDS) {
			dsManager.switchDataSource(dst);
			frService.removeRelations(account.getMemberNo());
		}
		
		return mapper.updateWithdraw(account);
	}


	@Override
	public void getMinuteDiffLastLogin(String accountId, String lang) {
		int diffMin = mapper.getDiffMinuteLastLogin(accountId);
			
		List<WellcomePresent> metaList = wellcomePresentService.getListByLang(lang);
		if (!metaList.isEmpty()) {
			Post rewardPost = new Post();
			
			ServerInfo serverInfo = serverInfoService.getByName("cdn");
			if (serverInfo != null) {
				rewardPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(lang));
			}
			
			rewardPost.setAccountId(accountId);
			rewardPost.setPostType(PostType.User.getCode());
			rewardPost.setLang(lang);
			rewardPost.setText(Post.PostText.findByCode(TextType.WellcomePresent, LanguageCode.findByCode(lang)).getText());
			rewardPost.setRewardDone(false);
			
			for(WellcomePresent meta : metaList) {
				if (diffMin > meta.getMinuteFromLastLogin()) {
					rewardPost.setRewardCategory(Reward.RewardType.findByCode(meta.getRewardType()).getCategory().getCode());
					rewardPost.setRewardType(meta.getRewardType());
					rewardPost.setReward(meta.getReward());
					postService.sendPostExpireOneDay(rewardPost);
					break;
				}
			}
		}
	}
	
}