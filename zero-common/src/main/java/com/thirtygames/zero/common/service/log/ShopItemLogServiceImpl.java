package com.thirtygames.zero.common.service.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.thirtygames.zero.common.generic.LogServiceImpl;
import com.thirtygames.zero.common.mapper.log.ShopItemLogMapper;
import com.thirtygames.zero.common.model.log.ShopItemLog;

@Slf4j
@Service("shopItemLogService")
public class ShopItemLogServiceImpl extends LogServiceImpl<ShopItemLogMapper, ShopItemLog, Integer> implements ShopItemLogService {

}