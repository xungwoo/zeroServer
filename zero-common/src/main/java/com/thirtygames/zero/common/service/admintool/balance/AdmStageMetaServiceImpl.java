package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmStageMapper;
import com.thirtygames.zero.common.model.meta.Stage;

@Service("adminStageMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmStageMetaServiceImpl extends AdminMetaServiceImpl<AdmStageMapper, Stage, String>  implements AdmStageMetaService {

}
