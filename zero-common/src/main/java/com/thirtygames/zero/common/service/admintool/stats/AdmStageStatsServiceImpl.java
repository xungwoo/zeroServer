package com.thirtygames.zero.common.service.admintool.stats;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmStageStatsMapper;
import com.thirtygames.zero.common.model.admintool.AdminStageStats;

@Service("adminStageStatsService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmStageStatsServiceImpl extends AdminLogServiceImpl<AdmStageStatsMapper, AdminStageStats, String>  implements AdmStageStatsService {
	
	
	
	
}
