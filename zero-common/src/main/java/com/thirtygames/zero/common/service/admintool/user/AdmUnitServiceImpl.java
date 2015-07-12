package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmUnitMapper;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.admintool.AdminUnit;
import com.thirtygames.zero.common.model.admintool.AdminUnitMeta;
import com.thirtygames.zero.common.model.admintool.UserAccount;
import com.thirtygames.zero.common.model.log.UnitLog;
import com.thirtygames.zero.common.model.meta.Reward.RewardCategory;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.model.meta.UnitLimitExceed;
import com.thirtygames.zero.common.model.meta.UnitSkill;
import com.thirtygames.zero.common.service.PostDynamicService;
import com.thirtygames.zero.common.service.PostService;
import com.thirtygames.zero.common.service.UnitService;
import com.thirtygames.zero.common.service.admintool.balance.AdmUnitMetaService;
import com.thirtygames.zero.common.service.admintool.log.AdmUnitLogService;
import com.thirtygames.zero.common.service.datasource.DataSourceService;
import com.thirtygames.zero.common.service.meta.ServerInfoService;
import com.thirtygames.zero.common.service.meta.UnitLimitExceedMetaService;
import com.thirtygames.zero.common.service.meta.UnitSkillMetaService;

@Slf4j
@Service("adminUnitService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmUnitServiceImpl extends AdminServiceImpl<AdmUnitMapper, AdminUnit, String> implements AdmUnitService {

	@Autowired
	DataSourceService dsManager;	
	
	@Autowired
	AdmResourceService arService;
	
	@Autowired
	AdmAccountService accountService;

	@Autowired
	UnitService unitService;
	
	@Autowired
	AdmUnitLogService unitLogService;

	@Autowired
	AdmUnitMetaService unitMetaService;
	
	@Autowired
	PostService postService;
	
	@Autowired
	PostDynamicService postDynamicService;

	@Autowired
	UnitSkillMetaService unitSkillMetaService;
	
	@Autowired
	UnitLimitExceedMetaService unitLimitExceedMetaService;
	
	@Autowired
	ServerInfoService serverInfoService;

	@Override
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@DataSourceAnnotation(DataSourceType.ZERO_DYNAMIC)
	public String unitReset(String unitId, String accountId) {

		List<UnitLimitExceed> limitMetaList = unitLimitExceedMetaService.range(0, 0);

		AdminUnit unit = mapper.getByUnitId(unitId);
		int currentLevel = unit.getLevel();
		int currentLevelLimit = unit.getLevel();
		int currentSkill0Lv = unit.getSkill0Lv();
		int currentSkill1Lv = unit.getSkill1Lv();
		int currentSkill2Lv = unit.getSkill2Lv();
		//int currentSkill3Lv = unit.getSkill3Lv();
		long resetUnlockKey = 0;
		long resetLimitExceedGold = 0;

		String logStr = "";
		for (UnitLimitExceed meta : limitMetaList) {
			int metaLevel = meta.getLevel();
			if (currentLevelLimit >= metaLevel) {
				resetUnlockKey += meta.getUnlockKey();
				resetLimitExceedGold += meta.getGold();
				if (metaLevel != 30) {
					currentLevel = currentLevel + 3;
				}
				
				logStr += "########### LimitExceed Level:: from" + metaLevel + " Gold::" + resetLimitExceedGold + "<br>";
				log.debug("########### LimitExceed Level:: from" + metaLevel + " Gold::" + resetLimitExceedGold);
				logStr += "########### LimitExceed Level:: from" + metaLevel + " unlockKey::" + resetUnlockKey + "<br>";
				log.debug("########### LimitExceed Level:: from" + metaLevel + " unlockKey::" + resetUnlockKey);
			}
		}

		long resetLevelUpGold = 0;
		AdminUnitMeta unitMeta = unitMetaService.get(Integer.toString(unit.getUnitType()));
		if (currentLevel > 1) {
			long levelUpGold = unitMeta.getGoldLv1();
			float goldGrowth = unitMeta.getGoldGrowth();
			
			for (int i=1; i < currentLevel; i++) {
				levelUpGold = (long) ((int)(levelUpGold * goldGrowth / 10 )) * 10;
				if (i!=9 && i!=10 && i!=11 && i!=12 && i!=22 && i!=23 && i!=24 && i!=25 && i!=35) {
					resetLevelUpGold += levelUpGold;
					logStr += "########### levelUp Level:: from" + i + " Gold::" + levelUpGold + "<br>";
					log.debug("########### levelUp Level:: from" + i + " Gold::" + levelUpGold);					
				}
			}
		}
		
		long resetSkill0LvGold = 0; 
		if (currentSkill0Lv > 1) {
			UnitSkill unit0Skill = new UnitSkill();
			unit0Skill.setSkillType(unitMeta.getSkill0Type());
			List<UnitSkill> unitSkill0List = unitSkillMetaService.search(unit0Skill);
			
			for (int index0=1; index0 < currentSkill0Lv; index0++) {
				resetSkill0LvGold += unitSkill0List.get(index0).getUpgradeCost();
				logStr += "########### Skill 0 Level:: from" + index0 + " Gold::" + unitSkill0List.get(index0).getUpgradeCost() + "<br>";
				log.debug("########### Skill 0 Level:: from" + index0 + " Gold::" + unitSkill0List.get(index0).getUpgradeCost());
			}
		}
		
		long resetSkill1LvGold = 0; 
		if (currentSkill1Lv > 1) {
			UnitSkill unit1Skill = new UnitSkill();
			unit1Skill.setSkillType(unitMeta.getSkill1Type());
			List<UnitSkill> unitSkill1List = unitSkillMetaService.search(unit1Skill);
			
			for (int index1=1; index1 < currentSkill1Lv; index1++) {
				resetSkill1LvGold += unitSkill1List.get(index1).getUpgradeCost();
				logStr += "########### Skill 1 Level:: from" + index1 + " Gold::" + unitSkill1List.get(index1).getUpgradeCost() + "<br>";
				log.debug("########### Skill 1 Level:: from" + index1 + " Gold::" + unitSkill1List.get(index1).getUpgradeCost());
			}
		}
		
		long resetSkill2LvGold = 0; 
		if (currentSkill2Lv > 1) {
			UnitSkill unit2Skill = new UnitSkill();
			unit2Skill.setSkillType(unitMeta.getSkill2Type());
			List<UnitSkill> unitSkill2List = unitSkillMetaService.search(unit2Skill);
			
			for (int index2=1; index2 < currentSkill2Lv; index2++) {
				resetSkill2LvGold += unitSkill2List.get(index2).getUpgradeCost();
				logStr += "########### Skill 2 Level:: from" + index2 + " Gold::" + unitSkill2List.get(index2).getUpgradeCost() + "<br>";
				log.debug("########### Skill 2 Level:: from" + index2 + " Gold::" + unitSkill2List.get(index2).getUpgradeCost());
			}
		}

//		UserResource ar = new UserResource();
//		ar.setAccountId(accountId);
//		logStr += "########### TotalResetGold Gold::" + totalResetGold;
//		log.debug("########### TotalResetGold Gold::" + totalResetGold);
//		ar.setGold(totalResetGold);
//		ar.setUnlockKey(resetUnlockKey);
//		arService.updateAddition(ar, false);

		long totalResetGold = resetSkill0LvGold + resetSkill1LvGold + resetSkill2LvGold + resetLevelUpGold + resetLimitExceedGold;
		logStr += "########### TotalResetGold Gold::" + totalResetGold;
		log.debug("########### TotalResetGold Gold::" + totalResetGold);
		
		UserAccount account = accountService.get(accountId);
		
		ServerInfo serverInfo = serverInfoService.getByName("cdn");
		if (totalResetGold > 0) {
			Post goldPost = new Post();
			goldPost.setAccountId(accountId);
			goldPost.setPostType(PostType.User.getCode());
			
			if (serverInfo != null) {
				goldPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(account.getLanguage()));
			}			
			
			goldPost.setRewardCategory(RewardCategory.Resource.getCode());
			goldPost.setRewardType(RewardType.Gold.getCode());
			goldPost.setReward(totalResetGold);
			goldPost.setText(Post.PostText.findByCode(TextType.UnitRestGold, LanguageCode.findByCode(account.getLanguage())).getText());
			goldPost.setLang(account.getLanguage());
			goldPost.setRewardDone(false);
			dsManager.switchDataSource(accountId);
			postDynamicService.sendPostDynamic(goldPost);
		}
		
		if (resetUnlockKey > 0) {
			Post unlockKeyPost = new Post();
			unlockKeyPost.setAccountId(accountId);
			unlockKeyPost.setPostType(PostType.User.getCode());

			if (serverInfo != null) {
				unlockKeyPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(account.getLanguage()));
			}	
			
			unlockKeyPost.setRewardCategory(RewardCategory.Resource.getCode());
			unlockKeyPost.setRewardType(RewardType.UnlockKey.getCode());
			unlockKeyPost.setReward(resetUnlockKey);
			unlockKeyPost.setText(Post.PostText.findByCode(TextType.UnitRestUnlockKey, LanguageCode.findByCode(account.getLanguage())).getText());
			unlockKeyPost.setLang(account.getLanguage());
			unlockKeyPost.setRewardDone(false);
			dsManager.switchDataSource(accountId);
			postDynamicService.sendPostDynamic(unlockKeyPost);
		}
		
		UnitLog unitLog = new UnitLog();
		unitLog.setAccountId(accountId);
		unitLog.setUnitId(unitId);
		unitLog.setStatus(UnitLog.UnitLogStatus.Reset.getCode());
		unitLog.setPrevLevel(unit.getLevel());
		unitLog.setPrevSkill0Lv(unit.getSkill0Lv());
		unitLog.setPrevSkill1Lv(unit.getSkill1Lv());
		unitLog.setPrevSkill2Lv(unit.getSkill2Lv());
		unitLog.setPrevSkill3Lv(unit.getSkill3Lv());
		unitLog.setResultLevel(Unit.DEFAULT_LEVEL);
		unitLogService.save(unitLog);
		
		accountService.resetAuthTokenValidYmdt(accountId);

		AdminUnit resetedUnit = new AdminUnit();
		resetedUnit.setUnitId(unitId);
		resetedUnit.setLevel(Unit.DEFAULT_LEVEL);
		resetedUnit.setSkill0Lv(Unit.DEFAULT_LEVEL);
		resetedUnit.setSkill1Lv(Unit.DEFAULT_LEVEL);
		resetedUnit.setSkill2Lv(Unit.DEFAULT_LEVEL);
		resetedUnit.setSkill3Lv(Unit.DEFAULT_LEVEL);
		resetedUnit.setLimitExceedEndYmdt("0");
		mapper.updateByUnitId(resetedUnit);
		
		return logStr;
	}

}
