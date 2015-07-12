package com.thirtygames.zero.common.service.admintool.user;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmStageClearMapper;
import com.thirtygames.zero.common.model.admintool.AdminStageClear;

@Service("adminStageClearService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmStageClearServiceImpl extends AdminServiceImpl<AdmStageClearMapper, AdminStageClear, String>  implements AdmStageClearService {

}
