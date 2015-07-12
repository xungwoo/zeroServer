package com.thirtygames.zero.common.service.admintool.boss;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.thirtygames.zero.common.generic.admintool.AdminMetaServiceImpl;
import com.thirtygames.zero.common.mapper.admintool.AdmBossCollectionMetaMapper;
import com.thirtygames.zero.common.model.admintool.AdminBossCollection;

@Service("adminBossCollectionMetaService")
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class AdmBossCollectionMetaServiceImpl extends AdminMetaServiceImpl<AdmBossCollectionMetaMapper, AdminBossCollection, String>  implements AdmBossCollectionMetaService {
}
