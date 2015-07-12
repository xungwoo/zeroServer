package com.thirtygames.zero.common.service;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.error.Errors;
import com.thirtygames.zero.common.etc.exception.RestException;
import com.thirtygames.zero.common.etc.util.CalendarUtil;
import com.thirtygames.zero.common.etc.util.LanguageCode;
import com.thirtygames.zero.common.etc.util.ValidationUtil;
import com.thirtygames.zero.common.generic.GenericServiceImpl;
import com.thirtygames.zero.common.mapper.ItemDeliveryMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.PeriodItem;
import com.thirtygames.zero.common.model.Post;
import com.thirtygames.zero.common.model.Post.PostType;
import com.thirtygames.zero.common.model.Post.TextType;
import com.thirtygames.zero.common.model.hsp.HSPItemDelivery;
import com.thirtygames.zero.common.model.hsp.HSPItemDeliveryLog;
import com.thirtygames.zero.common.model.log.ShopItemLog;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.model.meta.ServerInfo;
import com.thirtygames.zero.common.model.meta.ShopItem;
import com.thirtygames.zero.common.model.meta.ShopItem.RepeatType;
import com.thirtygames.zero.common.model.type.PriceType;
import com.thirtygames.zero.common.service.hsp.HSPItemDeliveryManager;
import com.thirtygames.zero.common.service.log.ShopItemLogService;
import com.thirtygames.zero.common.service.meta.FirstBuyingRewardService;
import com.thirtygames.zero.common.service.meta.ServerInfoService;
import com.thirtygames.zero.common.service.meta.ShopItemMetaService;

@Slf4j
@Service("itemDeliveryService")
public class ItemDeliveryServiceImpl extends GenericServiceImpl<ItemDeliveryMapper, HSPItemDeliveryLog, Long> implements ItemDeliveryService {

	@Autowired
	HSPItemDeliveryManager hspItemDeliveryManager;

	@Autowired
	RewardService rewardService;

	@Autowired
	FirstBuyingRewardService firstBuyingRewardService;

	@Autowired
	ShopItemMetaService shopItemMetaService;

	@Autowired
	ShopItemLogService shopItemLogService;

	@Autowired
	AccountResourceService arService;

	@Autowired
	PostService postService;

	@Autowired
	PeriodItemService periodItemService;

	@Autowired
	ServerInfoService serverInfoService;

	@Override
	@Transactional
	public boolean itemDelivery(HSPItemDelivery itemDelivery) {
		String accountId = itemDelivery.getAccountId();
		HSPItemDelivery itemDeliveryResult = new HSPItemDelivery();

		try {
			itemDeliveryResult = hspItemDeliveryManager.verifyDeliveryReceipt(itemDelivery);
			ValidationUtil.isNullModel(itemDeliveryResult, " params:" + itemDelivery);
		} catch (Exception e) {
			throw new RestException(Errors.HSPVerifyDeliveryError, "itemDelivery error:" + itemDelivery);
		}
		
		List<Long> itemDeliverySequences = itemDeliveryResult.getItemDeliverySequences();
		List<String> itemIds = itemDeliveryResult.getItemIds();
		if (itemDeliverySequences.isEmpty() || itemIds.isEmpty() || itemDeliverySequences.size() != itemIds.size()) {
			return false;
		}

		int index = 0;
		for (long itemDeliverySequence : itemDeliverySequences) {
			HSPItemDeliveryLog logParam = new HSPItemDeliveryLog();
			logParam.setItemDeliverySequence(itemDeliverySequence);
			logParam.setCode(0);
			List<HSPItemDeliveryLog> alreadyItemList = this.search(logParam);
			if (alreadyItemList.isEmpty()) {
				HSPItemDeliveryLog itemLog = new HSPItemDeliveryLog();
				itemLog.setAccountId(accountId);
				itemLog.setItemDeliverySequence(itemDeliverySequence);
				itemLog.setJson(itemDeliveryResult.getJson());
				itemLog.setCode(itemDeliveryResult.getCode());
				log.debug("log::::" + itemLog);
				this.save(itemLog);

				String id = itemIds.get(index);
				ShopItem shopItemMeta = shopItemMetaService.getByItemId(id);
				if (shopItemMeta == null) {
					throw new RestException(Errors.NotFoundData, "ShopItemMeta");
				}

				String lang = itemDelivery.getLang();
				this.addRewardPost(accountId, shopItemMeta, lang);
				boolean isRepeatType = this.repeatRewardPost(accountId, shopItemMeta, lang);
				// this.firstBuyingReward(accountId,
				// shopItemMeta.getItemKey(), lang);

				if (!isRepeatType) {
					AccountResource ar = new AccountResource();
					ar.setCash(shopItemMeta.getItemQuantity());
					ar.setAccountId(accountId);
					arService.updateAddition(ar, false);
				}

				shopItemMeta.setAccountId(accountId);
				this.saveLog(shopItemMeta);
			}
			index++;
		}

		return true;
	}

	private boolean repeatRewardPost(String accountId, ShopItem shopItemMeta, String lang) {
		boolean isRepeatType = false;
		if (RepeatType.None.getCode() != shopItemMeta.getRepeatType()) {
			Post rewardPost = new Post();

			ServerInfo serverInfo = serverInfoService.getByName("cdn");
			if (serverInfo != null) {
				rewardPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(lang));
			}

			rewardPost.setAccountId(accountId);
			rewardPost.setPostType(PostType.User.getCode());
			rewardPost.setRewardCategory(Reward.RewardType.findByCode(shopItemMeta.getItemType()).getCategory().getCode());
			rewardPost.setRewardType(RewardType.Cash.getCode());
			rewardPost.setReward(shopItemMeta.getItemQuantity());
			rewardPost.setLang(lang);
			rewardPost.setText(Post.PostText.findByCode(TextType.PeriodItem, LanguageCode.findByCode(lang)).getText());
			rewardPost.setRewardDone(false);

			String ymdFormat = "yyyyMMdd";
			Calendar nowCal = Calendar.getInstance();
			Calendar endCal = Calendar.getInstance();
			int repeatCount = RepeatType.findByCode(shopItemMeta.getRepeatType()).getCount();

			PeriodItem periodItem = new PeriodItem();
			periodItem.setAccountId(accountId);
			periodItem.setRepeatType(shopItemMeta.getRepeatType());
			periodItem.setMetaItemKey(shopItemMeta.getItemKey());
			periodItem.setStartYmd(CalendarUtil.toString(nowCal, ymdFormat, TimeZone.getTimeZone("Asia/Seoul")));
			endCal.add(Calendar.DATE, repeatCount);
			periodItem.setEndYmd(CalendarUtil.toString(endCal, ymdFormat, TimeZone.getTimeZone("Asia/Seoul")));
			log.debug("periodItem::" + periodItem);
			periodItemService.save(periodItem);

			for (int i = 1; i <= repeatCount; i++) {
				rewardPost.setStartYmd(CalendarUtil.toString(nowCal, ymdFormat, TimeZone.getTimeZone("Asia/Seoul")));
				nowCal.add(Calendar.DATE, 1);
				rewardPost.setExpireYmd(CalendarUtil.toString(nowCal, ymdFormat, TimeZone.getTimeZone("Asia/Seoul")));
				postService.sendPostWithDate(rewardPost);
			}

			isRepeatType = true;
		}

		return isRepeatType;
	}

	private void addRewardPost(String accountId, ShopItem shopItemMeta, String lang) {

		if (shopItemMeta.getAddRewardType1() != null && shopItemMeta.getAddReward1() != null) {
			this.sendPost(accountId, shopItemMeta.getAddRewardType1(), shopItemMeta.getAddReward1(), TextType.OpenPackage, lang);
		}

		if (shopItemMeta.getAddRewardType2() != null && shopItemMeta.getAddReward2() != null) {
			this.sendPost(accountId, shopItemMeta.getAddRewardType2(), shopItemMeta.getAddReward2(), TextType.OpenPackage, lang);
		}

		if (shopItemMeta.getAddRewardType3() != null && shopItemMeta.getAddReward3() != null) {
			this.sendPost(accountId, shopItemMeta.getAddRewardType3(), shopItemMeta.getAddReward3(), TextType.OpenPackage, lang);
		}

		if (shopItemMeta.getAddRewardType4() != null && shopItemMeta.getAddReward4() != null) {
			this.sendPost(accountId, shopItemMeta.getAddRewardType4(), shopItemMeta.getAddReward4(), TextType.OpenPackage, lang);
		}
	}

	private void sendPost(String accountId, int rewardType, long reward, TextType textType, String lang) {
		Post rewardPost = new Post();
		ServerInfo serverInfo = serverInfoService.getByName("cdn");
		if (serverInfo != null) {
			rewardPost.makeSystemUser(serverInfo.getUrl(), LanguageCode.findByCode(lang));
		}

		rewardPost.setAccountId(accountId);
		rewardPost.setPostType(PostType.User.getCode());
		rewardPost.setRewardCategory(Reward.RewardType.findByCode(rewardType).getCategory().getCode());
		rewardPost.setRewardType(rewardType);
		rewardPost.setReward(reward);
		rewardPost.setLang(lang);
		rewardPost.setText(Post.PostText.findByCode(textType, LanguageCode.findByCode(lang)).getText());
		rewardPost.setRewardDone(false);
		postService.sendPost(rewardPost);
	}

	private void firstBuyingReward(String accountId, int itemKey, String lang) {
		List<Reward> rewardList = firstBuyingRewardService.rewardList(itemKey);
		if (!rewardList.isEmpty()) {
			log.debug("rewardList::" + rewardList);

			ShopItemLog logParams = new ShopItemLog();
			logParams.setAccountId(accountId);
			logParams.setBuyType(PriceType.Billing.getCode());
			List<ShopItemLog> logList = shopItemLogService.search(logParams);
			log.debug("logList::" + logList);
			if (logList.isEmpty()) {
				for (Reward reward : rewardList) {
					this.sendPost(accountId, reward.getRewardType(), reward.getReward(), TextType.FirstBuyuing, lang);
				}
			}
		}
	}

	private void saveLog(ShopItem itemMeta) {
		ShopItemLog log = new ShopItemLog();
		log.setItemKey(itemMeta.getItemKey());
		log.setAccountId(itemMeta.getAccountId());
		log.setBuyType(itemMeta.getBuyType());
		log.setItemType(itemMeta.getItemType());
		log.setItemCategory(itemMeta.getItemCategory());
		log.setItemId(itemMeta.getItemId());
		log.setItemQuantity(itemMeta.getItemQuantity());
		log.setItemQuantityBonus(itemMeta.getItemQuantityBonus());
		log.setPrice(itemMeta.getPrice());
		log.setProductId(itemMeta.getProductId());
		shopItemLogService.save(log);
	}
}
