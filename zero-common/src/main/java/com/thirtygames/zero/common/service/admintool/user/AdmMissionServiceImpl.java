package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmMissionMapper;
import com.thirtygames.zero.common.model.Mission;

@Service("adminMissionService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmMissionServiceImpl extends AdminServiceImpl<AdmMissionMapper, Mission, Integer> implements AdmMissionService {

}
