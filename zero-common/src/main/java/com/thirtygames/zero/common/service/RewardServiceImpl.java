package com.thirtygames.zero.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.ResourceServiceImpl;
import com.thirtygames.zero.common.mapper.EquipmentMapper;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.meta.Reward;
import com.thirtygames.zero.common.model.meta.Reward.RewardType;
import com.thirtygames.zero.common.service.equipment.EquipmentService;
import com.thirtygames.zero.common.service.equipment.meta.EquipMetaService;
import com.thirtygames.zero.common.service.log.RewardLogService;

@Service("rewardService")
public class RewardServiceImpl extends ResourceServiceImpl<EquipmentMapper, Equipment, String> implements RewardService {
	
	@Autowired
	EquipMetaService eqMetaService;
	
	@Autowired
	EquipmentService eqService;
	
	@Autowired
	UnitService unitService;
	
	@Autowired
	RewardLogService rewardLogService;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW)
	public Reward reward(Reward reward, boolean isReturn)  {
		Reward resultReward = new Reward();
		resultReward.setReward(reward.getReward());
		
		RewardType type = RewardType.findByCode(reward.getRewardType());
		resultReward.setRewardType(type.getCode());
		
		switch(type.getCategory()) {
		case Resource:
			AccountResource resultResource = this.rewardResource(reward, isReturn);
			if (isReturn) {
				resultReward.setResource(resultResource);
			}
			break;
		case Equipment:
			resultReward.setEquipment(this.rewardEquipment(reward));
			break;
		case Unit:
			Unit rewardUnit = this.rewardUnit(reward);
			if(rewardUnit == null) { // 쿠폰 유닛지급을 위한 Temporary CODE
				Reward alternativeReward = this.alternativeUnlockKeyReward(reward);
				resultReward.setRewardType(alternativeReward.getRewardType());
				resultReward.setReward(alternativeReward.getReward());
			} else {
				resultReward.setUnit(this.rewardUnit(reward));
			}
			
			resultReward.setAccountId(reward.getAccountId());
			resultReward.setReasonType(reward.getReasonType());
			break;
		default:
			break;
		}
		
		rewardLogService.save(reward);
		return resultReward; 
	}
	
	private Reward alternativeUnlockKeyReward(Reward reward) {
		Reward unlockKeyReward = new Reward();
		unlockKeyReward.setAccountId(reward.getAccountId());
		unlockKeyReward.setRewardType(Reward.RewardType.UnlockKey.getCode());
		unlockKeyReward.setReward(Reward.UNIT_ALTERNATIVE_REWARD_UNLOCKKEY);
		unlockKeyReward.setReasonType(reward.getReasonType());
		this.rewardResource(reward, false);
		return unlockKeyReward;
	}
	
	
	@Override
	public AccountResource rewardResource(Reward rewardParam, boolean isReturnResource)  {
		String accountId = rewardParam.getAccountId();
		RewardType type = RewardType.findByCode(rewardParam.getRewardType());
		Long reward = rewardParam.getReward();
		
		AccountResource ar = new AccountResource();
		ar.setAccountId(accountId);
		
		switch(type) {
		case Gold: 
			ar.setGold(reward);
			break;
		case Cash:
			ar.setCash(reward);
			break;
		case AP:
			ar.setApExtra(reward);
			break;
		case BP:
			ar.setBpExtra(reward);
			break;			
		case FP:
			ar.setFp(reward);
			break;			
		case Title:
			ar.setTitle(reward);
			break;			
		case UnlockKey:
			ar.setUnlockKey(reward);
			break;			
		case EquipTicket:
			ar.setEquipTicket(reward);
			break;			
		case EquipLevelUpDrug:
			ar.setEquipLevelUpDrug(reward);
			break;			
		default:
			break;
		}		
		
		return super.updateAddition(ar, isReturnResource);
	}

	@Override
	public Equipment rewardEquipment(Reward reward)  {
		int equipmentType = safeRewardLongToInt(reward.getReward());
		Equipment equip = new Equipment();
		equip.setAccountId(reward.getAccountId());
		equip.setEquipmentType(equipmentType);
		Equipment rewardEq = eqMetaService.generateEquip(equip, false);
		eqService.save(rewardEq);
		return rewardEq;
	}

	@Override
	public Unit rewardUnit(Reward reward)  {
		int unitType = safeRewardLongToInt(reward.getReward());
		Unit rewardUnit = unitService.generateRewardUnit(unitType, reward.getAccountId());
		if (rewardUnit != null) {
			unitService.save(rewardUnit);
		}
		return rewardUnit;
	}
	
	private int safeRewardLongToInt(long reward) {
		if (reward < Integer.MIN_VALUE || reward > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(reward + " cannot be cast to int without changing its value.");
		}
		
		return (int) reward;
	}
}