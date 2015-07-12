package com.thirtygames.zero.common.service;

import com.thirtygames.zero.common.generic.ResourceService;
import com.thirtygames.zero.common.model.AccountResource;
import com.thirtygames.zero.common.model.Unit;
import com.thirtygames.zero.common.model.equipment.Equipment;
import com.thirtygames.zero.common.model.meta.Reward;

public interface RewardService extends ResourceService<Equipment, String> {

	public AccountResource rewardResource(Reward reward, boolean isReturnResource) ;

	public Equipment rewardEquipment(Reward reward) ;
	
	public Unit rewardUnit(Reward reward) ;

	public Reward reward(Reward reward, boolean isReturn) ;

}