package com.thirtygames.zero.common.service.admintool.log;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmShopItemLogMapper;
import com.thirtygames.zero.common.model.log.ShopItemLog;

@Service("adminShopItemLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmShopItemLogServiceImpl extends AdminLogServiceImpl<AdmShopItemLogMapper, ShopItemLog, Integer> implements AdmShopItemLogService {

}
