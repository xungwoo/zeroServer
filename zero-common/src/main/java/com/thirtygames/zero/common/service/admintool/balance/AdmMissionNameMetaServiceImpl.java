package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmMissionNameMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminMissionMeta;

@Service("adminMissionNameMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmMissionNameMetaServiceImpl extends AdminMetaServiceImpl<AdmMissionNameMetaMapper, AdminMissionMeta, String>  implements AdmMissionNameMetaService {

}
