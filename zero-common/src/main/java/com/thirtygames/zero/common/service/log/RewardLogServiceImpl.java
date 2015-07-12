package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.RewardLogMapper;
import com.thirtygames.zero.common.model.meta.Reward;

@Slf4j
@Service("rewardLogService")
public class RewardLogServiceImpl extends LogServiceImpl<RewardLogMapper, Reward, Integer> implements RewardLogService {

}