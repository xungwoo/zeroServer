package com.thirtygames.zero.common.service.admintool.user;

import java.util.List;

import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmLadderMapper;
import com.thirtygames.zero.common.mapper.admintool.AdmLeagueCountMapper;
import com.thirtygames.zero.common.model.Ladder;
import com.thirtygames.zero.common.model.meta.LeagueCount;

@Service("adminLeagueCountService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmLeagueCountServiceImpl extends
		AdminMetaServiceImpl<AdmLeagueCountMapper, LeagueCount, Integer> implements AdmLeagueCountService  {
}