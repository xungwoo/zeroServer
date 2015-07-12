package com.thirtygames.zero.common.service.admintool.balance;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmEquipLevelMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminEquipLevelMeta;

@Service("adminEquipLevelMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmEquipLevelMetaServiceImpl extends AdminMetaServiceImpl<AdmEquipLevelMetaMapper, AdminEquipLevelMeta, String>  implements AdmEquipLevelMetaService {
	
}
