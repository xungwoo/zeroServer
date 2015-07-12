package com.thirtygames.zero.common.service.admintool.log;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminLogServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmLadderMatchLogMapper;
import com.thirtygames.zero.common.model.LadderMatch;

@Slf4j
@Service("adminLadderMatchLogService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmLadderMatchLogServiceImpl extends AdminLogServiceImpl<AdmLadderMatchLogMapper, LadderMatch, String> implements AdmLadderMatchLogService  {

}