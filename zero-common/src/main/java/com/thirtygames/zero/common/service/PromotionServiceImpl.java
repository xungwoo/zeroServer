package com.thirtygames.zero.common.service;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.hsp.CGPResponse;
import com.thirtygames.zero.common.model.hsp.CGPReward;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.service.hsp.CGPManager;
import com.thirtygames.zero.common.service.hsp.CGPManager.CGPRewardMsg;
import com.thirtygames.zero.common.service.meta.ServerInfoService;

@Slf4j
@Service("promotionService")
public class PromotionServiceImpl implements PromotionService {

	@Autowired
	CGPManager cgpManager;

	@Autowired
	RewardService rewardService;

	@Autowired
	PostService postService;
	
	@Autowired
	ServerInfoService serverInfoService;	
	
	@Override
	@Transactional
	public List<Reward> checkMissionAndReward(String accountId, String memberNo, String missionKey, String lang) {
		CGPResponse result = new CGPResponse();

		try {
			result = cgpManager.checkMission(memberNo, missionKey);
			ValidationUtil.isNullModel(result, " params:" + memberNo);
		} catch (Exception e) {
			throw new RestException(Errors.CGPCheckMissionError, "memberNo:" + memberNo);
		}
		
		List<Reward> resultRewardList  = Lists.newArrayList(); 
		for(CGPReward cgpReward : result.getRewardList()) {
			Reward reward = new Reward();
			reward.setAccountId(accountId);
			reward.setReasonType(Reward.ReasonType.CGPPromotion.getCode());
			reward.setRewardType(cgpReward.getRewardType());
			reward.setReward(cgpReward.getRewardValue());
			
			Post rewardPost = new Post();
			ServerInfo serverInfo = serverInfoService.getByName("cdn");
			if (serverInfo != null) {
				rewardPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(lang));
			}
			
			rewardPost.setAccountId(accountId);
			rewardPost.setPostType(PostType.User.getCode());
			rewardPost.setRewardType(cgpReward.getRewardType());
			rewardPost.setReward(cgpReward.getRewardValue());
			rewardPost.setText(Post.PostText.findByCode(TextType.PromotionReward, LanguageCode.findByCode(lang)).getText());
			rewardPost.setLang(lang);
			rewardPost.setRewardDone(false);
			postService.sendPost(rewardPost);
			
			resultRewardList.add(reward);
		}
		
		
		log.debug("resultRewardList::" + resultRewardList);
		
		return resultRewardList;
	}

	public String missionKeyFiltering(String missionKey) {
		for (CGPRewardMsg c : CGPRewardMsg.values()) {
			if (missionKey.indexOf(c.getMissionKey()) >= 0) {
				missionKey = c.getMissionKey();
			}
		}
		log.debug("missionKey::" + missionKey);
		return missionKey;
	}
}
