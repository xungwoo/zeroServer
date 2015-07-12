package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmLadderMapper;
import com.thirtygames.zero.common.model.Ladder;

@Service("adminLadderService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmLadderServiceImpl extends AdminServiceImpl<AdmLadderMapper, Ladder, String> implements AdmLadderService  {
}