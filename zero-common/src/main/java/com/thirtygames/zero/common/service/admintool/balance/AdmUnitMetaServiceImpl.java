package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmUnitMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminUnitMeta;

@Service("adminUnitMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmUnitMetaServiceImpl extends AdminMetaServiceImpl<AdmUnitMetaMapper, AdminUnitMeta, String>  implements AdmUnitMetaService {

}
