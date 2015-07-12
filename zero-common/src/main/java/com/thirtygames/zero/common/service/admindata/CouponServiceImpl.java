package com.thirtygames.zero.common.service.admindata;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.etc.datasource.DataSourceAnnotation;
import com.thirtygames.zero.common.etc.datasource.DataSourceType;
import com.thirtygames.zero.common.generic.MetaServiceImpl;
import com.thirtygames.zero.common.mapper.admindata.CouponMapper;
import com.thirtygames.zero.common.model.Coupon;
import com.thirtygames.zero.common.model.log.CouponLog;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.service.RewardService;
import com.thirtygames.zero.common.service.log.CouponLogService;

@Slf4j
@Service("couponService")
public class CouponServiceImpl extends MetaServiceImpl<CouponMapper, Coupon, String> implements CouponService {

	@Autowired
	RewardService rewardService;

	@Autowired
	CouponLogService couponLogService;

	@Override
	@Transactional
	@DataSourceAnnotation(DataSourceType.ZERO_META)
	public List<Reward> useCoupon(Coupon coupon) {
		log.debug("Coupon::" + coupon);
		if (Coupon.CouponType.User.getCode() == coupon.getCouponType()) {
			coupon.setRewardDone(true);
			this.update(coupon);
		}

		CouponLog couponLog = new CouponLog();
		couponLog.setAccountId(coupon.getAccountId());
		couponLog.setCouponId(coupon.getCouponId());
		couponLog.setCouponType(coupon.getCouponType());
		couponLog.setBundleCode(coupon.getBundleCode());
		couponLog.setReward1(coupon.getReward1());
		couponLog.setReward2(coupon.getReward2());
		couponLog.setReward3(coupon.getReward3());
		couponLog.setReward4(coupon.getReward4());
		couponLog.setReward5(coupon.getReward5());
		couponLog.setRewardType1(coupon.getRewardType1());
		couponLog.setRewardType2(coupon.getRewardType2());
		couponLog.setRewardType3(coupon.getRewardType3());
		couponLog.setRewardType4(coupon.getRewardType4());
		couponLog.setRewardType5(coupon.getRewardType5());
		couponLog.setGenYmdt(coupon.getGenYmdt());
		couponLog.setExpireYmd(coupon.getExpireYmd());
		log.debug("coupon log::" + couponLog);
		couponLogService.save(couponLog);

		List<Reward> rewardList = new ArrayList<Reward>();
		
		Reward reward1 = new Reward();
		reward1.setAccountId(coupon.getAccountId());
		reward1.setReasonType(Reward.ReasonType.Coupon.getCode());
		reward1.setRewardType(coupon.getRewardType1());
		reward1.setReward(coupon.getReward1());
		rewardList.add(rewardService.reward(reward1, false));
		
		if (coupon.getRewardType2() != null && coupon.getRewardType2() > 0 && coupon.getReward2() != null && coupon.getReward2() > 0) {
			Reward reward2 = new Reward();
			reward2.setAccountId(coupon.getAccountId());
			reward2.setReasonType(Reward.ReasonType.Coupon.getCode());
			reward2.setRewardType(coupon.getRewardType2());
			reward2.setReward(coupon.getReward2());
			rewardService.reward(reward2, false);
			rewardList.add(reward2);
		}
		
		if (coupon.getRewardType3() != null && coupon.getRewardType3() > 0 && coupon.getReward3() != null && coupon.getReward3() > 0) {
			Reward reward3 = new Reward();
			reward3.setAccountId(coupon.getAccountId());
			reward3.setReasonType(Reward.ReasonType.Coupon.getCode());
			reward3.setRewardType(coupon.getRewardType3());
			reward3.setReward(coupon.getReward3());
			rewardService.reward(reward3, false);
			rewardList.add(reward3);
		}
		
		if (coupon.getRewardType4() != null && coupon.getRewardType4() > 0 && coupon.getReward4() != null && coupon.getReward4() > 0) {
			Reward reward4 = new Reward();
			reward4.setAccountId(coupon.getAccountId());
			reward4.setReasonType(Reward.ReasonType.Coupon.getCode());
			reward4.setRewardType(coupon.getRewardType4());
			reward4.setReward(coupon.getReward4());
			rewardService.reward(reward4, false);
			rewardList.add(reward4);
		}
		
		if (coupon.getRewardType5() != null && coupon.getRewardType5() > 0 && coupon.getReward5() != null && coupon.getReward5() > 0) {
			Reward reward5 = new Reward();
			reward5.setAccountId(coupon.getAccountId());
			reward5.setReasonType(Reward.ReasonType.Coupon.getCode());
			reward5.setRewardType(coupon.getRewardType5());
			reward5.setReward(coupon.getReward5());
			rewardService.reward(reward5, false);
			rewardList.add(reward5);
		}
		
		return rewardList;
	}

}