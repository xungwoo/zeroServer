package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmAchievementMapper;
import com.thirtygames.zero.common.model.Achievement;

@Service("adminAchievementService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmAchievementServiceImpl extends AdminServiceImpl<AdmAchievementMapper, Achievement, Integer> implements AdmAchievementService {

}
